package net.lmvdz.delirium.shader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.client.DeliriumClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;

public class ShaderRenderLayer implements ShaderEffectRenderCallback {

    public static final ShaderRenderLayer ExampleShaderRenderLayer =
            new ShaderRenderLayer(DeliriumMod.MODID, "illusion", (shader -> {
                System.out.println(shader);
            }));;

    public static final Map<RenderLayer, RenderLayer> shaderRenderLayers = new HashMap<>();

    public static RenderLayer computeShaderRenderLayerIfAbsent(RenderLayer baseLayer,
            String newName, RenderPhase.Target renderTarget) {
        return shaderRenderLayers.computeIfAbsent(baseLayer, tex -> RenderLayerDuplicator
                .copyFrom(newName, baseLayer, builder -> builder.target(renderTarget)));
    }

    public String shaderDomain;
    public String shaderName;
    public ManagedShaderEffect managedShaderEffect;
    public Framebuffer shaderFrameBuffer;
    public RenderPhase.Target shaderTarget;

    public ShaderRenderLayer(String shaderDomain, String shaderName,
            Consumer<ManagedShaderEffect> mConsumer) {
        this.shaderDomain = shaderDomain;
        this.shaderName = shaderName;
        this.managedShaderEffect = ShaderEffectManager.getInstance().manage(
                new Identifier(shaderDomain, "shaders/post/" + shaderName + ".json"), shader -> {
                    mConsumer.accept(shader);
                    this.shaderFrameBuffer = Objects.requireNonNull(shader.getShaderEffect())
                            .getSecondaryTarget("final");
                    this.shaderTarget = new RenderPhase.Target(
                            shaderDomain + ":" + shaderName + "_target", () -> {
                                shaderFrameBuffer.beginWrite(false);
                            }, () -> {
                                MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
                            });
                });

    }

    @Override
    public void renderShaderEffects(float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (managedShaderEffect != null) {
            managedShaderEffect.render(tickDelta);
            client.getFramebuffer().beginWrite(true);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA,
                    GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO,
                    GlStateManager.DstFactor.ONE);
            shaderFrameBuffer.draw(client.getWindow().getFramebufferWidth(),
                    client.getWindow().getFramebufferHeight(), false);
            shaderFrameBuffer.clear(false);
            client.getFramebuffer().beginWrite(true);
            RenderSystem.disableBlend();
        }
    }

}
