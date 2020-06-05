#version 120

attribute vec3 Position;
attribute vec4 Color;
attribute vec2 Texture;
attribute vec2 Overlay;
attribute vec2 Light;
attribute vec3 Normal;

uniform mat4 ProjMat;
uniform vec2 InSize;
uniform vec2 OutSize;

varying vec2 texCoord;

void main(){
    
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);

    texCoord = Position.xy / OutSize;
}
