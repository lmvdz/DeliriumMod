package net.lmvdz.delirium.mixin;

import net.lmvdz.delirium.api.event.AfterReloadCallback;
import net.lmvdz.delirium.api.event.BeforeReloadCallback;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("RETURN"), method="reloadResources")
    public void onReloaded(CallbackInfoReturnable<CompletableFuture<Void>> cir) throws Exception {
        AfterReloadCallback.AFTER.invoker().reloaded();
    }

    @Inject(at = @At("HEAD"), method="reloadResources")
    public void onReloading(CallbackInfoReturnable<CompletableFuture<Void>> cir) throws Exception {
        BeforeReloadCallback.BEFORE.invoker().reloading();
    }
}
