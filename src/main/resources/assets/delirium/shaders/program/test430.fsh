#version 120

// The main texture
uniform sampler2D DiffuseSampler;
// The depth map
uniform sampler2D DepthSampler;


uniform ivec4 ViewPort;
uniform float STime;
uniform vec3 CameraPosition;
uniform vec3 Center;
uniform mat4 InverseTransformMatrix;

varying vec2 texCoord;
varying vec3 vertexPosition;

void main()
{
    vec4 tex = texture2D(DiffuseSampler, texCoord);

    vec3 ndc = vPosition.xyz / vPosition.w; //perspective divide/normalize
    vec2 viewportCoord = ndc.xy * 0.5 + 0.5; //ndc is -1 to 1 in GL. scale for 0 to 1

    // Depth fading
    float sceneDepth = texture2D(DepthSampler, viewportCoord).x;
    vec3 pixelPosition = CalcEyeFromWindow(sceneDepth).xyz + CameraPosition;

    // Ping effect: color pixels that are some distance away from the center
    float d = distance(pixelPosition, Center);
    d /= mod(STime, 20); // Scale the world to make the ping diffuse over time
    vec3 rainbow = vec3(sin(STime), cos(STime), sin(STime + 1.)) * smoothstep(2, 3., d) * smoothstep(4, 3., d);

    gl_FragColor = vec4(tex.rgb + pow(rainbow, vec3(3)), 1.0);
}
