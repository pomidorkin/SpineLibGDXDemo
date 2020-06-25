package com.mygdx.spinegdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

public class SpineGdx extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;

	private TextureAtlas atlas;

	private SkeletonRenderer renderer;
	private Skeleton skeleton;
	private AnimationState state;

	@Override
	public void create () {
	    camera = new OrthographicCamera();
	    camera.zoom = -100f;
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(false);
		atlas = new TextureAtlas(Gdx.files.internal("outline.atlas"));

        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("outline.json"));

        skeleton = new Skeleton(skeletonData);
        skeleton.setPosition(0, 0);

        AnimationStateData stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);

        final AnimationState.TrackEntry track = state.setAnimation(0, "animation", true);
	}

	@Override
	public void render () {
		state.update(Gdx.graphics.getDeltaTime());
		state.apply(skeleton);
		skeleton.updateWorldTransform();

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
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
	}
}
