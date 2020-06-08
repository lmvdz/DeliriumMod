package net.lmvdz.delirium.mixin;

import net.lmvdz.delirium.api.event.MinecraftServerInitCallback;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("RETURN"), method = "<init>*")
    public void onInit(CallbackInfo ci) {
        MinecraftServerInitCallback.EVENT.invoker().onInitMinecraftServer((MinecraftServer)(Object)this);
    }
}
