package net.lmvdz.delirium.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import net.lmvdz.delirium.item.DeliriumItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

/**
 * ItemStackMixin
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    
    @Inject(at = @At(value = "RETURN"), method = "getTooltip")
    public void getTooltip(PlayerEntity playerEntity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> info) {
        if (playerEntity != null) {
            DeliriumItemTooltipCallback.EVENT.invoker().getTooltip(((ItemStack) (Object) this), playerEntity,
                    tooltipContext, info.getReturnValue());
        }
    }
}