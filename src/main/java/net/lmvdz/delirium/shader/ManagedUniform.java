package net.lmvdz.delirium.shader;

import ladysnake.satin.api.managed.uniform.*;
import net.lmvdz.delirium.api.event.ReloadCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ManagedUniform implements ReloadCallback {


    UniformFinder finder;
    String uniformName;
    ManagedUniformType type;
    Object uniform;

    public ManagedUniform(UniformFinder finder, String uniformName, ManagedUniformType type) {
        this.finder = finder;
        this.uniformName = uniformName;
        this.type = type;
        this.uniform = getUniform();
        ReloadCallback.EVENT.register(this);
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
            case mat4:
                return finder.findUniformMat4(uniformName);
            case sampler:
                return finder.findSampler(uniformName);
            default:
                return null;
        }
    }

    public void setUniform(Object uniform) {
        switch(type) {
            case vec:
                if (uniform instanceof Float && this.uniform instanceof Uniform1f) {
                    ((Uniform1f)this.uniform).set((float)uniform);
                }
            case vec2:
                if (uniform instanceof Vec2f && this.uniform instanceof Uniform2f)
                    ((Uniform2f)this.uniform).set(((Vec2f)uniform).x, ((Vec2f)uniform).y);
            case vec3:
                if (uniform instanceof Vector3f && this.uniform instanceof Uniform3f)
                    ((Uniform3f)this.uniform).set(((Vector3f)uniform).getX(), ((Vector3f)uniform).getY(), ((Vector3f)uniform).getZ());
            case vec4:
                if (uniform instanceof Vector4f && this.uniform instanceof Uniform4f)
                    ((Uniform4f)this.uniform).set(((Vector4f) uniform).getW(), ((Vector4f) uniform).getX(), ((Vector4f) uniform).getY(), ((Vector4f) uniform).getZ());
            case veci:
                if (uniform instanceof Integer && this.uniform instanceof Uniform1i)
                    ((Uniform1i)this.uniform).set((int) uniform);
            case vec2i:
                if (uniform instanceof Vec2f && this.uniform instanceof Uniform2i)
                    ((Uniform2i)this.uniform).set((int) ((Vec2f) uniform).x, (int) ((Vec2f) uniform).y);
            case vec3i:
                if (uniform instanceof Vec3i && this.uniform instanceof Uniform3i)
                    ((Uniform3i)this.uniform).set(((Vec3i)uniform).getX(), ((Vec3i)uniform).getY(), ((Vec3i)uniform).getZ());
            case vec4i:
                if (uniform instanceof Vector4f && this.uniform instanceof Uniform4i)
                    ((Uniform4i)this.uniform).set((int)((Vector4f)uniform).getW(), (int)((Vector4f)uniform).getX(), (int)((Vector4f)uniform).getY(), (int)((Vector4f)uniform).getZ());
            case sampler:
                if (this.uniform instanceof SamplerUniform) {
                    if (uniform instanceof AbstractTexture) {
                        ((SamplerUniform)this.uniform).set((AbstractTexture)uniform);
                    } else if (uniform instanceof Framebuffer) {
                        ((SamplerUniform)this.uniform).set((Framebuffer)uniform);
                    } else if (Integer.class.isInstance(uniform)) {
                        ((SamplerUniform)this.uniform).set((int)uniform);
                    }
                }
            case mat4:
                if (uniform instanceof Matrix4f && this.uniform instanceof UniformMat4)
                    ((UniformMat4)this.uniform).set(((Matrix4f)uniform).copy());
        }
    }

    @Override
    public void reloaded() {
        this.uniform = getUniform();
    }
}
