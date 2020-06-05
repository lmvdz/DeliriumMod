#version 120

// The main texture
uniform sampler2D DiffuseSampler;
// The depth map
// uniform sampler2D DepthSampler;

// Position of the camera
uniform vec3 CameraPosition;
// Position of the center of the ping effect
uniform vec3 Center;
// Time in seconds (+ tick delta)
uniform float STime;

// The matrix passed by world render
// uniform mat4 ModelView;

// The magic matrix to get world coordinates from pixel ones
// uniform mat4 InverseTransformMatrix;
// The size of the viewport (typically, [0,0,1080,720])
uniform ivec4 ViewPort;

varying vec2 texCoord;
varying vec4 vPosition;

void Vgnette (inout vec3 color) {
    float dist = distance(texCoord.st, vec2(0.5)) * 2;
    dist /= 1.5142f;
    dist = pow(dist, 1.1);
    color.rgb *= 1.0f - dist;
}

vec3 convertToHRD(in vec3 color) {
    vec3 HRDImage;
    vec3 overExposed = color * 1.2f;
    vec3 underExposed = color / 1.5f;
    HRDImage = mix(underExposed, overExposed, color);
    return HRDImage;
}


// vec4 CalcEyeFromWindow(in float depth)
// {
//   // derived from https://www.khronos.org/opengl/wiki/Compute_eye_space_from_window_space
//   // ndc = Normalized Device Coordinates
//   vec3 ndcPos;
//   ndcPos.xy = ((2.0 * gl_FragCoord.xy) - (2.0 * ViewPort.xy)) / (ViewPort.zw) - 1;
//   ndcPos.z = (2.0 * depth - gl_DepthRange.near - gl_DepthRange.far) / (gl_DepthRange.far - gl_DepthRange.near);
//   vec4 clipPos = vec4(ndcPos, 1.);
//   vec4 homogeneous = InverseTransformMatrix * clipPos;
//   vec4 eyePos = vec4(homogeneous.xyz / homogeneous.w, homogeneous.w);
//   return eyePos;
// }

float random (in vec2 _st) {
    return fract(sin(dot(_st.xy,
                         vec2(12.9898,78.233)))*
        43758.5453123);
}

// Based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
float noise (in vec2 _st) {
    vec2 i = floor(_st);
    vec2 f = fract(_st);

    // Four corners in 2D of a tile
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(a, b, u.x) +
            (c - a)* u.y * (1.0 - u.x) +
            (d - b) * u.x * u.y;
}

#define NUM_OCTAVES 5

float fbm ( in vec2 _st) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    // Rotate to reduce axial bias
    mat2 rot = mat2(cos(0.5), sin(0.5),
                    -sin(0.5), cos(0.50));
    for (int i = 0; i < NUM_OCTAVES; ++i) {
        v += a * noise(_st);
        _st = rot * _st * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

void main() {


    vec4 tex = texture2D(DiffuseSampler, texCoord);


    // vec3 ndc = vPosition.xyz / vPosition.w; //perspective divide/normalize
    // vec2 viewportCoord = ndc.xy * 0.5 + 0.5; //ndc is -1 to 1 in GL. scale for 0 to 1

    // float sceneDepth = texture2D(DepthSampler, viewportCoord).x;
    // vec3 pixelPosition = CalcEyeFromWindow(sceneDepth).xyz + CameraPosition;

    // float d = distance(pixelPosition, Center);
    // d /= mod(STime, 20);


    vec2 st = gl_FragCoord.xy/ViewPort.zw;
    // st += st * abs(sin(STime*0.1)*3.0);
    vec3 color = vec3(1.0);

    vec2 q = vec2(0.);
    q.x = fbm( st + 0.00*STime);
    q.y = fbm( st + vec2(1.0));

    vec2 r = vec2(0.);
    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*STime );
    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*STime );

    float f = fbm(st+r);

    color = mix(vec3(0.101961,0.619608,0.666667),
                vec3(0.666667,0.666667,0.498039),
                clamp((f*f)*4.0,0.0,1.0));

    color = mix(color,
                vec3(0,0,0.164706),
                clamp(length(q),0.0,1.0));

    color = mix(color,
                vec3(0.666667,1,1),
                clamp(length(r.x),0.0,1.0));

    // color = convertToHRD(color);
   
    // Vgnette(color);

    color *= vec3(abs(sin(STime * .5f))*.5f);
    color *= (f*f*f+.6*f*f+.5*f);
    // gl_FragColor = tex * vec4(color, 1.0); -- TO DARK
    gl_FragColor = tex * vec4(tex.r + 1 - color.r, tex.g + 1 -color.g, tex.b + 1 -color.b, .5);
    // gl_FragColor = tex * vec4(1.0);
}