package net.lmvdz.delirium.shader;

import ladysnake.satin.api.managed.uniform.*;

public enum ManagedUniformType {
    vec4(Uniform4f.class),
    vec3(Uniform3f.class),
    vec2(Uniform2f.class),
    vec(Uniform1f.class),
    veci(Uniform1i.class),
    vec2i(Uniform2i.class),
    vec3i(Uniform3i.class),
    vec4i(Uniform4i.class),
    mat4(UniformMat4.class),
    sampler(SamplerUniform.class);

    private final Class type;


    ManagedUniformType(Class type) {
        this.type = type;
    }

    public static ManagedUniformType fromClass(Class type) {
        if (type.equals(vec4.type)) {
            return vec4;
        } else if (type.equals(vec3.type)) {
            return vec3;
        } else if (type.equals(vec2.type)) {
            return vec2;
        } else if (type.equals(vec.type)) {
            return vec;
        } else if (type.equals(vec4i.type)) {
            return vec4i;
        } else if (type.equals(vec3i.type)) {
            return vec3i;
        } else if (type.equals(vec2i.type)) {
            return vec2i;
        } else if (type.equals(veci.type)) {
            return veci;
        } else if (type.equals(sampler.type)) {
            return sampler;
        } else if (type.equals(mat4.type)) {
            return mat4;
        } else {
            return null;
        }
    }


}
