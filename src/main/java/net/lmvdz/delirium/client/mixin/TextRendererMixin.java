package net.lmvdz.delirium.client.mixin;

import net.lmvdz.delirium.api.event.TextRendererDrawLayerClass5348Callback;
import net.lmvdz.delirium.api.event.TextRendererDrawLayerStringCallback;
import net.lmvdz.delirium.api.event.TextRendererInitCallback;
import net.minecraft.class_5348;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @Inject(at = @At("RETURN"), method = "<init>*")
    public void onInit(CallbackInfo ci) {
        TextRendererInitCallback.EVENT.invoker().onInit((TextRenderer)(Object)this);
    }

    @Inject(at = @At("HEAD"), method = "drawLayer(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F", cancellable = true)
    public void onDrawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> cir) {
        TextRendererDrawLayerStringCallback.EVENT.invoker().onDrawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, (Boolean cancel, Float returnValue) -> {
            if (cancel) {
                cir.cancel();
                cir.setReturnValue(returnValue);
            }
        });
    }

    @Inject(at = @At("HEAD"), method = "drawLayer(Lnet/minecraft/class_5348;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)F", cancellable = true)
    public void onDrawLayer(class_5348 arg, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, CallbackInfoReturnable<Float> cir) {
        TextRendererDrawLayerClass5348Callback.EVENT.invoker().onDrawLayer(arg, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, (Boolean cancel, Float returnValue) -> {
            if (cancel) {
                cir.cancel();
                cir.setReturnValue(returnValue);
            }
        });
    }




}
