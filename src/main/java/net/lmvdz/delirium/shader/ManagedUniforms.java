package net.lmvdz.delirium.shader;

import ladysnake.satin.api.managed.uniform.UniformFinder;

import java.util.HashMap;
import java.util.Map;

public class ManagedUniforms {
    public Map<ManagedUniformType, Map<String, ManagedUniform>> UNIFORMS = new HashMap<>();

    public void setUniform(ManagedUniformType type, String uniformName, Object uniform) {
        UNIFORMS.computeIfAbsent(type, (newType) -> {
            return new HashMap<>();
        }).computeIfAbsent(uniformName, (newUniformName) -> {
            return new ManagedUniform(finder, newUniformName, type);
        }).setUniform(uniform);
    }

    public void putUniform(ManagedUniformType type, String uniformName) {
        UNIFORMS.computeIfAbsent(type, (newType) -> {
            return new HashMap<>();
        }).put(uniformName, new ManagedUniform(finder, uniformName, type));
    }

    public UniformFinder finder;

    public ManagedUniforms(UniformFinder finder) {
        this.finder = finder;
    }

}
