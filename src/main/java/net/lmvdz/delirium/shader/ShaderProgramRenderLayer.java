package net.lmvdz.delirium.shader;

import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.lmvdz.delirium.api.event.LoadProjectionMatrixCallback;
import net.lmvdz.delirium.mixin.LightmapTextureManagerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.texture.AbstractTexture;
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
                .manageProgram(new Identifier(shaderDomain, shaderName));
        managedUniforms = new ManagedUniforms(managedShaderProgram);
        this.shaderProgramTarget = new RenderPhase.Target(
                shaderDomain + ":" + shaderName + "_program_target", managedShaderProgram::enable,
                managedShaderProgram::disable);

        managedUniforms.putUniform(ManagedUniformType.vec, "STime");
//                    managedUniforms.putUniform(ManagedUniformType.mat4, "ProjMat");

        ClientTickCallback.EVENT.register(this);
        EntitiesPreRenderCallback.EVENT.register(this);
        LoadProjectionMatrixCallback.EVENT.register(this);
        consumer.accept(managedShaderProgram);
    }

    @Environment(EnvType.CLIENT)
    public ShaderProgramRenderLayer(String shaderDomain, String shaderName, List<Pair<ManagedUniformType, String>> uniforms, Consumer<ManagedShaderProgram> consumer) {
        this.shaderDomain = shaderDomain;
        this.shaderName = shaderName;
        this.managedShaderProgram = ShaderEffectManager.getInstance().manageProgram(new Identifier(shaderDomain, shaderName));
        managedUniforms = new ManagedUniforms(managedShaderProgram);
        this.shaderProgramTarget = new RenderPhase.Target(
                shaderDomain + ":" + shaderName + "_program_target", managedShaderProgram::enable,
                managedShaderProgram::disable);

        putUniforms(uniforms);

        ClientTickCallback.EVENT.register(this);
        EntitiesPreRenderCallback.EVENT.register(this);
        LoadProjectionMatrixCallback.EVENT.register(this);
        consumer.accept(managedShaderProgram);
    }

    public void putUniforms(List<Pair<ManagedUniformType, String>> uniforms) {
        uniforms.forEach(pair -> {
            managedUniforms.putUniform(pair.getLeft(), pair.getRight());
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
    public RenderLayer getRenderLayer(RenderLayer baseLayer) {
        return ShaderRenderLayers.getRenderLayer(baseLayer, this.shaderDomain + ":" + this.shaderName, this.shaderProgramTarget);
    }

    public static class Emissive extends ShaderProgramRenderLayer implements IEmissiveSampler {

        public Identifier emissive;

        public Emissive(String shaderDomain, String shaderName, Identifier emissive, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, consumer);
            this.emissive = emissive;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "EmissiveSampler");
        }
        public Emissive(String shaderDomain, String shaderName, List<Pair<ManagedUniformType, String>> uniforms, Identifier emissive, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, uniforms, consumer);
            this.emissive = emissive;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "EmissiveSampler");
        }

        @Override
        public AbstractTexture getEmissive() {
            MinecraftClient client = MinecraftClient.getInstance();
            TextureManager textureManager = client.getTextureManager();
            return textureManager.getTexture(emissive);
        }
    }

    public static class Lightmap extends ShaderProgramRenderLayer implements ILightmapSampler {

        public Identifier lightmap;

        public Lightmap(String shaderDomain, String shaderName, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, consumer);
            MinecraftClient client = MinecraftClient.getInstance();
            this.lightmap = ((LightmapTextureManagerAccessor)client.gameRenderer.getLightmapTextureManager()).getTextureIdentifier();
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "LightmapSampler");
        }

        public Lightmap(String shaderDomain, String shaderName, Identifier lightmap, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, consumer);
            this.lightmap = lightmap;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "LightmapSampler");
        }

        public Lightmap(String shaderDomain, String shaderName, List<Pair<ManagedUniformType, String>> uniforms, Identifier lightmap, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, uniforms, consumer);
            this.lightmap = lightmap;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "LightmapSampler");
        }

        @Override
        public AbstractTexture getLightmap() {
            MinecraftClient client = MinecraftClient.getInstance();
            TextureManager textureManager = client.getTextureManager();
            return textureManager.getTexture(lightmap);
        }
    }

    public static class EmissiveLightmap extends ShaderProgramRenderLayer implements IEmissiveSampler, ILightmapSampler {

        public Identifier lightmap;
        public Identifier emissive;

        public EmissiveLightmap(String shaderDomain, String shaderName, Identifier emissive, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, consumer);

            this.emissive = emissive;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "EmissiveSampler");

            MinecraftClient client = MinecraftClient.getInstance();
            this.lightmap = ((LightmapTextureManagerAccessor)client.gameRenderer.getLightmapTextureManager()).getTextureIdentifier();
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "LightmapSampler");
        }

        public EmissiveLightmap(String shaderDomain, String shaderName, Identifier emissive, Identifier lightmap, Consumer<ManagedShaderProgram> consumer) {
            super(shaderDomain, shaderName, consumer);

            this.emissive = emissive;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "EmissiveSampler");

            this.lightmap = lightmap;
            this.managedUniforms.putUniform(ManagedUniformType.sampler, "LightmapSampler");
        }

        @Override
        public AbstractTexture getEmissive() {
            MinecraftClient client = MinecraftClient.getInstance();
            TextureManager textureManager = client.getTextureManager();
            return textureManager.getTexture(emissive);
        }

        @Override
        public AbstractTexture getLightmap() {
            MinecraftClient client = MinecraftClient.getInstance();
            TextureManager textureManager = client.getTextureManager();
            return textureManager.getTexture(lightmap);
        }


        @Override
        public RenderLayer getRenderLayer(RenderLayer baseLayer) {
            AbstractTexture emissiveTexture = getEmissive();
            if (emissiveTexture != null && this.managedUniforms.getUniform(ManagedUniformType.sampler, "EmissiveSampler") != null) {
                System.out.println("emissive texture not null yay!");
                System.out.println(emissiveTexture);
                this.managedUniforms.setUniform(ManagedUniformType.sampler, "EmissiveSampler", getEmissive());
            }

            AbstractTexture lightmapTexture = getLightmap();
            if (lightmapTexture != null && this.managedUniforms.getUniform(ManagedUniformType.sampler, "LightmapSampler") != null) {
                System.out.println("lightmap texture not null yay!");
                System.out.println(lightmapTexture);
                this.managedUniforms.setUniform(ManagedUniformType.sampler, "LightmapSampler", getLightmap());
            }
            return super.getRenderLayer(baseLayer);
        }

    }





}
