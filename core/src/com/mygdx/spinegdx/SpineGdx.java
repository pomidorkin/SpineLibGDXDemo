package com.mygdx.spinegdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineGdx extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;

	private TextureAtlas atlas;

	private SkeletonRenderer renderer;
	private Skeleton skeleton;
	private AnimationState state;

	// Shader test
    private ShaderProgram shader;


    @Override
	public void create () {
	    camera = new OrthographicCamera();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(false);
		atlas = new TextureAtlas(Gdx.files.internal("spineboy.atlas"));

        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spineboy.json"));

        skeleton = new Skeleton(skeletonData);
        skeleton.setPosition(300f, 100f);

        AnimationStateData stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);

        final AnimationState.TrackEntry track = state.setAnimation(0, "jump", true);
        track.setListener(new AnimationState.AnimationStateListener() {
            @Override
            public void start(AnimationState.TrackEntry entry) {

            }

            @Override
            public void interrupt(AnimationState.TrackEntry entry) {

            }

            @Override
            public void end(AnimationState.TrackEntry entry) {

            }

            @Override
            public void dispose(AnimationState.TrackEntry entry) {

            }

            @Override
            public void complete(AnimationState.TrackEntry entry) {
                state.setAnimation(0, "jump", true); // By the completion of one animation, this one fires
            }

            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
//                if (event.getString().equals("half")){
//                    System.out.println("Half way through");
//                }
            }
        });

        Gdx.input.setInputProcessor(this);

        // Shader test
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/red.vsh"),
                Gdx.files.internal("shaders/red.fsh"));
        System.out.println(shader.isCompiled() ? "Shader compiled" : shader.getLog());
        batch.setShader(shader);
	}

	@Override
	public void render () {
		state.update(Gdx.graphics.getDeltaTime());
		state.apply(skeleton);
		skeleton.updateWorldTransform();

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shader.begin();
//        shader.setUniformf("texture", 0);
        shader.end();
//        batch.getProjectionMatrix().set(camera.combined);
		batch.begin();
//		batch.draw(img, 0, 0);
        renderer.draw(batch, skeleton);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		atlas.dispose();
		shader.dispose();
	}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            state.setAnimation(0, "walk", true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            state.setAnimation(0, "jump", true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
