#version 330 core

//Uses a 2 component lighting model

//Ambient light is cast by the sun
//A portion of the light will be from this
//And this portion will be modulated by sun colour

//Point lights soon

#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
    //query texture for colour of fragment
    vec4 texColor = texture2D(u_texture, v_texCoords).rgba;
    
    //discard transparent fragments
    //transparency is not supported for rendering
    //outside of pure transparency (with transparency less that 0.1)
    if (texColor.a < 0.1f) {
        discard;
    }
    
    vec3 ambientComponent = texColour;
    
    gl_FragColor = vec4(
        ambientComponent,
        1.0
    );
}