// attributes are final
attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;

// uniform is a normal variable. We get it`s value from the code
uniform mat4 u_projTrans;
uniform vec4 u_color;

// varying are passed to the retex shader
varying vec4 v_color;
varying vec2 v_texCoord0;

void main() {
//    v_color = a_color; // Color of the texture
    v_color = u_color; // Custom color
    v_texCoord0 = a_texCoord0;
    // gl_Position built-in variable. Sets the position of the vertex
    gl_Position = u_projTrans * vec4(a_position, 1.0);
}