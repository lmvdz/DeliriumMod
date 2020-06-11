package net.lmvdz.delirium.mixin;

import net.lmvdz.delirium.api.event.GetHeldItemModelCallback;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/client/render/item/ItemRenderer;getHeldItemModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/client/render/model/BakedModel;")
    public void onGetHeldItemModel(ItemStack stack, World world, LivingEntity entity, CallbackInfoReturnable<BakedModel> cir) {
        GetHeldItemModelCallback.EVENT.invoker().onGetHeldItemModel(stack, world, entity, cir::setReturnValue);
    }
}
