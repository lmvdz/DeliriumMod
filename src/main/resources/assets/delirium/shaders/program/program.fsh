#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D LightSampler;
uniform sampler2D EmissiveSampler;


uniform float STime;

varying vec2 texCoord;
varying vec2 lightTexCoord;
varying vec3 vNormal;

float random (in vec2 _st) {
    return fract(sin(dot(_st.xy,
    vec2(12.9898,78.233)))*
    43758.5453123);
}


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

    #define NUM_OCTAVES 6

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
//    float shift = texCoord.y * 5.;
//    vec3 rainbow = vec3(sin(STime + shift) + 0.2, cos(STime + shift + 0.5) + 0.2, sin(STime + shift + 4.) + 0.2);
//    vec4 tex = texture2D(DiffuseSampler, texCoord);
//    gl_FragColor = min(tex * vec4(rainbow, 1.) * 3., vec4(1.)) + tex;

//    vec4 diffuse = vec4(max(dot(lDirection, -vNormal), 0) * lColor, 1);

    vec4 diffuse = texture2D(DiffuseSampler, texCoord);
    //                                        (lightTexCoord + 8) / 256
    vec4 light = vec4(texture2D(LightSampler, lightTexCoord * 0.00367647 + 0.03125).rgb, 1);
    vec4 emissive = texture2D(EmissiveSampler, texCoord);

    gl_FragColor = (diffuse * light) + vec4(emissive.rgb * emissive.a, 0);




//    vec4 tex = texture2D(DiffuseSampler, texCoord);
//
//    vec2 st = gl_FragCoord.xy;
//
//    vec3 color = vec3(1.0);
//
//    vec2 q = vec2(0.);
//    q.x = fbm( st + 0.00*STime);
//    q.y = fbm( st + vec2(1.0));
//
//    vec2 r = vec2(0.);
//    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*STime );
//    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*STime );
//
//    float f = fbm(st+r);
//
//    color = mix(vec3(0.101961,0.619608,0.666667),
//    vec3(0.666667,0.666667,0.498039),
//    clamp((f*f)*4.0,0.0,1.0));
//
//    color = mix(color,
//    vec3(0,0,0.164706),
//    clamp(length(q),0.0,1.0));
//
//    color = mix(color,
//    vec3(0.666667,1,1),
//    clamp(length(r.x),0.0,1.0));
//
//    color *= vec3(abs(sin(STime * .5f))*.5f);
//    color *= (f*f*f+.6*f*f+.5*f);
//
//    gl_FragColor = tex * (vec4(tex.r + 1 - color.r, tex.g + 1 - color.g, tex.b + 1 - color.b, .5) + mod(vec4(tex.r + 1 - color.r, tex.g + 1 - color.g, tex.b + 1 - color.b, .5), 10)/10);

}