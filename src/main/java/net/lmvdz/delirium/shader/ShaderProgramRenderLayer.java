package net.lmvdz.delirium.shader;

import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.lmvdz.delirium.api.event.LoadProjectionMatrixCallback;
import net.lmvdz.delirium.client.mixin.LightmapTextureManagerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Matrix4f;

import java.util.*;
import java.util.function.Consumer;

public class ShaderProgramRenderLayer implements ClientTickCallback, EntitiesPreRenderCallback, LoadProjectionMatrixCallback {

    public String shaderDomain;
    public String shaderName;

    public ManagedShaderProgram managedShaderProgram;

    public ManagedUniforms managedUniforms;

    public RenderPhase.Target shaderProgramTarget;

    private int ticks;

    @Environment(EnvType.CLIENT)
    public ShaderProgramRenderLayer(String shaderDomain, String shaderName, Consumer<ManagedShaderProgram> consumer) {
        this.shaderDomain = shaderDomain;
        this.shaderName = shaderName;
        this.managedShaderProgram = ShaderEffectManager.getInstance()
                .manageProgram(new Identifier(shaderDomain, shaderName), program -> {
                    managedUniforms = new ManagedUniforms(program);
                    this.shaderProgramTarget = new RenderPhase.Target(
                            shaderDomain + ":" + shaderName + "_program_target", program::enable,
                            program::disable);

                    managedUniforms.putUniform(ManagedUniformType.vec, "STime");
//                    managedUniforms.putUniform(ManagedUniformType.mat4, "ProjMat");

                    ClientTickCallback.EVENT.register(this);
                    EntitiesPreRenderCallback.EVENT.register(this);
                    LoadProjectionMatrixCallback.EVENT.register(this);
                    consumer.accept(program);
                });
    }

    @Environment(EnvType.CLIENT)
    public ShaderProgramRenderLayer(String shaderDomain, String shaderName, List<Pair<ManagedUniformType, String>> uniforms, Consumer<ManagedShaderProgram> consumer) {
        this.shaderDomain = shaderDomain;
        this.shaderName = shaderName;
        this.managedShaderProgram = ShaderEffectManager.getInstance()
                .manageProgram(new Identifier(shaderDomain, shaderName), program -> {
                    managedUniforms = new ManagedUniforms(program);
                    this.shaderProgramTarget = new RenderPhase.Target(
                            shaderDomain + ":" + shaderName + "_program_target", program::enable,
                            program::disable);

                    uniforms.forEach(pair -> {
                        managedUniforms.putUniform(pair.getLeft(), pair.getRight());
                    });

                    ClientTickCallback.EVENT.register(this);
                    EntitiesPreRenderCallback.EVENT.register(this);
                    LoadProjectionMatrixCallback.EVENT.register(this);
                    consumer.accept(program);
                });
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void tick(MinecraftClient client) {
        if (!client.isPaused()) {
            ticks++;
        }
    }

    @Override
    public void beforeEntitiesRender(Camera camera, Frustum frustum, float tickDelta) {
        managedUniforms.setUniform(ManagedUniformType.vec, "STime", (ticks + tickDelta) * 0.05f);
    }

    @Override
    public void onLoadProjectionMatrix(Matrix4f matrix4f) {
        managedUniforms.setUniform(ManagedUniformType.mat4, "ProjMat", matrix4f);
    }


    @Environment(EnvType.CLIENT)
    public static RenderLayer getRenderLayer(ShaderProgramRenderLayer program, RenderLayer baseLayer) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextureManager textureManager = client.getTextureManager();
        if (program instanceof IEmissiveSampler) {
            program.managedUniforms.setUniform(ManagedUniformType.sampler, "EmmisiveSampler", textureManager.getTexture(((IEmissiveSampler)program).getEmmisiveIdentifier().getTextureId()));
        }

        if (program instanceof ILightmapSampler) {
            program.managedUniforms.setUniform(ManagedUniformType.sampler, "LightmapSampler", textureManager.getTexture(((LightmapTextureManagerAccessor)client.gameRenderer.getLightmapTextureManager()).getTextureIdentifier()));
        }

        return ShaderRenderLayers.computeShaderRenderLayerIfAbsent(baseLayer, program.shaderDomain + ":" + program.shaderName, program.shaderProgramTarget);
    }


}
