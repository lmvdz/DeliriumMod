package net.lmvdz.delirium.mixin;

import net.lmvdz.delirium.api.event.PlayerKilledOtherCallback;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("RETURN"), method = "onKilledOther")
    public void onKill(LivingEntity other, CallbackInfo ci) {
        PlayerKilledOtherCallback.EVENT.invoker().onKilledOther((PlayerEntity)(Object)this, other);
    }
}
