varying vec4 v_color;
varying vec2 v_texCoord0;

// getting single pixels from texture (getting pixel's color)
uniform sampler2D u_sampler2D;

void main() {
        gl_FragColor = v_color * texture2D(u_sampler2D, v_texCoord0);


//        gl_FragColor = v_color * texture2D(u_sampler2D, v_texCoord0); // Red shader
//        gl_FragColor.rgb = vec3(1.0, 0.0, 0.0);

//        gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * vec4 (1.0, 0.0, 0.0, 1.0); // Red filter

//        gl_FragColor = v_color * texture2D(u_sampler2D, v_texCoord0); // Reverse colors
//        gl_FragColor.rgb = 1.0 - gl_FragColor.rgb;

        //    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color; // Passthrough shader
}