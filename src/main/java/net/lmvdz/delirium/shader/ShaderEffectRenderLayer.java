//package net.lmvdz.delirium.shader;
//
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import ladysnake.satin.api.event.EntitiesPreRenderCallback;
//import ladysnake.satin.api.event.ShaderEffectRenderCallback;
//import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
//import ladysnake.satin.api.experimental.managed.Uniform1f;
//import ladysnake.satin.api.experimental.managed.Uniform3f;
//import ladysnake.satin.api.experimental.managed.UniformMat4;
//import ladysnake.satin.api.managed.ManagedShaderEffect;
//import ladysnake.satin.api.managed.ShaderEffectManager;
//import ladysnake.satin.api.util.GlMatrices;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.fabricmc.fabric.api.event.client.ClientTickCallback;
//import net.lmvdz.delirium.api.event.LoadProjectionMatrixCallback;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gl.Framebuffer;
//import net.minecraft.client.render.Camera;
//import net.minecraft.client.render.Frustum;
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.RenderPhase;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.Matrix4f;
//import net.minecraft.util.math.Vec3d;
//
//import java.util.Objects;
//import java.util.function.Consumer;
//
//
//public class ShaderEffectRenderLayer implements ShaderEffectRenderCallback, ClientTickCallback,
//        EntitiesPreRenderCallback, LoadProjectionMatrixCallback {
//
//
//    public String shaderDomain;
//    public String shaderName;
//
//    public ManagedShaderEffect managedShaderEffect;
//    public Framebuffer shaderEffectFrameBuffer;
//    public RenderPhase.Target shaderEffectTarget;
//
//    private int ticks = 0;
//    private Uniform1f uniformSTime;
//    private UniformMat4 uniformInverseTransformMatrix;
//    private Uniform3f uniformCameraPosition;
//    private Uniform3f uniformCenter;
//    private UniformMat4 uniformProjectionMatrix;
//    private Matrix4f projectionMatrix = new Matrix4f();
//
//    @Environment(EnvType.CLIENT)
//    public ShaderEffectRenderLayer(String shaderDomain, String shaderName,
//            Consumer<ManagedShaderEffect> consumer) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        this.shaderDomain = shaderDomain;
//        this.shaderName = shaderName;
//        this.managedShaderEffect = ShaderEffectManager.getInstance().manage(
//                new Identifier(shaderDomain, "shaders/post/" + shaderName + ".json"), effect -> {
//                    this.shaderEffectFrameBuffer = Objects.requireNonNull(effect.getShaderEffect())
//                            .getSecondaryTarget("final");
//                    this.shaderEffectTarget = new RenderPhase.Target(
//                            shaderDomain + ":" + shaderName + "_effect_target", () -> {
//                                shaderEffectFrameBuffer.beginWrite(false);
//                            }, () -> {
//                                MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
//                            });
//                    effect.setSamplerUniform("DepthSampler",
//                            ((ReadableDepthFramebuffer) client.getFramebuffer())
//                                    .getStillDepthMap());
//                    effect.setUniformValue("ViewPort", 0, 0,
//                            client.getWindow().getFramebufferWidth(),
//                            client.getWindow().getFramebufferHeight());
//                    this.uniformSTime = effect.findUniform1f("STime");
//                    this.uniformInverseTransformMatrix =
//                            effect.findUniformMat4("InverseTransformMatrix");
//                    this.uniformCameraPosition = effect.findUniform3f("CameraPosition");
//                    this.uniformCenter = effect.findUniform3f("Center");
//                    ShaderEffectRenderCallback.EVENT.register(this);
//                    ClientTickCallback.EVENT.register(this);
//                    EntitiesPreRenderCallback.EVENT.register(this);
//                    LoadProjectionMatrixCallback.EVENT.register(this);
//                    consumer.accept(effect);
//                });
//    }
//
//
//    @Override
//    public void onLoadProjectionMatrix(Matrix4f matrix4f) {
//        this.uniformProjectionMatrix.set(matrix4f);
//    }
//
//    @Override
//    public void beforeEntitiesRender(Camera camera, Frustum frustum, float tickDelta) {
//        this.uniformSTime.set((ticks + tickDelta) * 0.05f);
//    }
//
//    @Override
//    @Environment(EnvType.CLIENT)
//    public void tick(MinecraftClient client) {
//        if (!client.isPaused()) {
//            ticks++;
//        }
//
//    }
//
//    @Override
//    @Environment(EnvType.CLIENT)
//    public void renderShaderEffects(float tickDelta) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        // set uniforms
//        this.setUniforms(client, tickDelta);
//
//        // render
//        this.render(client, tickDelta);
//
//    }
//
//    @Environment(EnvType.CLIENT)
//    public void setUniforms(MinecraftClient client, float tickDelta) {
//        uniformSTime.set((ticks + tickDelta) / 20f);
//        uniformInverseTransformMatrix.set(GlMatrices.getInverseTransformMatrix(projectionMatrix));
//        Camera camera = client.gameRenderer.getCamera();
//        Vec3d cameraPos = camera.getPos();
//        uniformCameraPosition.set((float) cameraPos.x, (float) cameraPos.y, (float) cameraPos.z);
//        Entity e = camera.getFocusedEntity();
//        uniformCenter.set(lerpf(e.getX(), e.prevX, tickDelta), lerpf(e.getY(), e.prevY, tickDelta),
//                lerpf(e.getZ(), e.prevZ, tickDelta));
//    }
//
//    @Environment(EnvType.CLIENT)
//    public void render(MinecraftClient client, float tickDelta) {
//        managedShaderEffect.render(tickDelta);
//        client.getFramebuffer().beginWrite(true);
//        RenderSystem.enableBlend();
//        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA,
//                GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO,
//                GlStateManager.DstFactor.ONE);
//        shaderEffectFrameBuffer.draw(client.getWindow().getFramebufferWidth(),
//                client.getWindow().getFramebufferHeight(), false);
//        shaderEffectFrameBuffer.clear(false);
//        client.getFramebuffer().beginWrite(true);
//        RenderSystem.disableBlend();
//    }
//
//    private static float lerpf(double n, double prevN, float tickDelta) {
//        return (float) MathHelper.lerp(tickDelta, prevN, n);
//    }
//
//    public RenderLayer getRenderLayer(RenderLayer baseLayer) {
//        return ShaderRenderLayers.computeShaderRenderLayerIfAbsent(baseLayer,
//                shaderDomain + ":" + shaderName, shaderEffectTarget);
//    }
//
//}
