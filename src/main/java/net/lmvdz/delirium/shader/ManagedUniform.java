package net.lmvdz.delirium.shader;

import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.UniformFinder;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ManagedUniform {


    UniformFinder finder;
    String uniformName;
    ManagedUniformType type;

    public ManagedUniform(UniformFinder finder, String uniformName, ManagedUniformType type) {
        this.finder = finder;
        this.uniformName = uniformName;
        this.type = type;
    }

    public void update(ManagedUniformType newUniform, Consumer<ManagedUniform> callback) {
        setUniform(newUniform);
        callback.accept(this);
    }

    public void init(Consumer<ManagedUniform> callback) {
        callback.accept(this);
    }

    public Object getUniform() {
        switch(type) {
            case vec:
                return finder.findUniform1f(uniformName);
            case vec2:
                return finder.findUniform2f(uniformName);
            case vec3:
                return finder.findUniform3f(uniformName);
            case vec4:
                return finder.findUniform4f(uniformName);
            case veci:
                return finder.findUniform1i(uniformName);
            case vec2i:
                return finder.findUniform2i(uniformName);
            case vec3i:
                return finder.findUniform3i(uniformName);
            case vec4i:
                return finder.findUniform4i(uniformName);
            case sampler:
                return finder.findUniformMat4(uniformName);
            default:
                return null;
        }
    }

    public void setUniform(Object uniform) {
        switch(type) {
            case vec:
                if (uniform instanceof Float)
                    finder.findUniform1f(uniformName).set((float)uniform);
            case vec2:
                if (uniform instanceof Vec2f)
                    finder.findUniform2f(uniformName).set(((Vec2f)uniform).x, ((Vec2f)uniform).y);
            case vec3:
                if (uniform instanceof Vector3f)
                    finder.findUniform3f(uniformName).set(((Vector3f)uniform).getX(), ((Vector3f)uniform).getY(), ((Vector3f)uniform).getZ());
            case vec4:
                if (uniform instanceof Vector4f)
                    finder.findUniform4f(uniformName).set(((Vector4f) uniform).getW(), ((Vector4f) uniform).getX(), ((Vector4f) uniform).getY(), ((Vector4f) uniform).getZ());
            case veci:
                if (uniform instanceof Integer)
                    finder.findUniform1i(uniformName).set((int) uniform);
            case vec2i:
                if (uniform instanceof Vec2f)
                    finder.findUniform2i(uniformName).set((int) ((Vec2f) uniform).x, (int) ((Vec2f) uniform).y);
            case vec3i:
                if (uniform instanceof Vec3i)
                    finder.findUniform3i(uniformName).set(((Vec3i)uniform).getX(), ((Vec3i)uniform).getY(), ((Vec3i)uniform).getZ());
            case vec4i:
                if (uniform instanceof Vector4f)
                    finder.findUniform4i(uniformName).set((int)((Vector4f)uniform).getW(), (int)((Vector4f)uniform).getX(), (int)((Vector4f)uniform).getY(), (int)((Vector4f)uniform).getZ());
            case sampler:
                if (uniform instanceof AbstractTexture) {
                    finder.findSampler(uniformName).set((AbstractTexture)uniform);
                } else if (uniform instanceof Framebuffer) {
                    finder.findSampler(uniformName).set((Framebuffer)uniform);
                } else if (Integer.class.isInstance(uniform)) {
                    finder.findSampler(uniformName).set((int)uniform);
                }
            case mat4:
                if (uniform instanceof Matrix4f)
                    finder.findUniformMat4(uniformName).set(((Matrix4f)uniform).copy());
        }
    }


}
