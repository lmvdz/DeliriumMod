package net.lmvdz.delirium.shader;

import java.util.function.Consumer;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.experimental.managed.Uniform1f;
import ladysnake.satin.api.experimental.managed.UniformFinder;
import ladysnake.satin.api.experimental.managed.UniformMat4;
import ladysnake.satin.api.managed.ManagedShaderProgram;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.lmvdz.delirium.api.event.LoadProjectionMatrixCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.util.Identifier;

public class ShaderProgramRenderLayer
        implements ClientTickCallback, EntitiesPreRenderCallback, LoadProjectionMatrixCallback {


    public String shaderDomain;
    public String shaderName;

    public ManagedShaderProgram managedShaderProgram;
    public UniformMat4 programProjectionMatrix;
    public RenderPhase.Target shaderProgramTarget;


    private Uniform1f uniformSTime;

    private int ticks;

    public ShaderProgramRenderLayer(String shaderDomain, String shaderName,
            Consumer<UniformFinder> consumer) {
        // MinecraftClient client = MinecraftClient.getInstance();
        this.shaderDomain = shaderDomain;
        this.shaderName = shaderName;
        this.managedShaderProgram = ShaderEffectManager.getInstance()
                .manageProgram(new Identifier(shaderDomain, shaderName), program -> {
                    this.shaderProgramTarget = new RenderPhase.Target(
                            shaderDomain + ":" + shaderName + "_program_target", program::enable,
                            program::disable);
                    ClientTickCallback.EVENT.register(this);
                    EntitiesPreRenderCallback.EVENT.register(this);
                    LoadProjectionMatrixCallback.EVENT.register(this);
                    consumer.accept(program);

                });
    }

    @Override
    public void tick(MinecraftClient client) {
        if (!client.isPaused()) {
            ticks++;
        }

    }

    @Override
    public void beforeEntitiesRender(Camera camera, Frustum frustum, float tickDelta) {
        this.uniformSTime.set((ticks + tickDelta) * 0.05f);
    }

    @Override
    public void onLoadProjectionMatrix(Matrix4f matrix4f) {
        this.programProjectionMatrix.set(matrix4f);
    }

    public RenderLayer getRenderLayer(RenderLayer baseLayer) {
        return ShaderRenderLayers.computeShaderRenderLayerIfAbsent(baseLayer,
                shaderDomain + ":" + shaderName, shaderProgramTarget);
    }


}
