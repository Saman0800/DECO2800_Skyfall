#version 330 core

attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

out vec4 worldSpacePos;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
    worldSpacePos = a_position;
    gl_Position = u_projTrans * a_position;
}