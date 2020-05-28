#version 120

// The main texture
uniform sampler2D DiffuseSampler;
// The depth map
uniform sampler2D DepthSampler;

attribute vec3 Position;
attribute vec4 Color;
attribute vec2 Texture;
attribute vec2 Overlay;
attribute vec2 Light;
attribute vec3 Normal;

uniform mat4 ProjMat;
uniform vec2 OutSize;

uniform ivec4 ViewPort;
uniform float STime;
uniform vec3 CameraPosition;
uniform vec3 Center;
uniform mat4 InverseTransformMatrix;

varying vec3 vertexPosition;
varying vec2 texCoord;



void main() {
    vec4 outPos = ProjMat * vec4(Position.xy, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);
    vertexPosition = gl_Position;

    texCoord = Position.xy / OutSize;
}