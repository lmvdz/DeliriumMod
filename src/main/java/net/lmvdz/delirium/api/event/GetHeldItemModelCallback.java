package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface GetHeldItemModelCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<GetHeldItemModelCallback> EVENT =
            EventFactory.createArrayBacked(GetHeldItemModelCallback.class,
                    (listeners) -> (stack,world,entity,callback) -> {
                        for (GetHeldItemModelCallback handler : listeners) {
                            handler.onGetHeldItemModel(stack,world,entity,callback);
                        }
                    });

    void onGetHeldItemModel(ItemStack stack, World world, LivingEntity entity, Consumer<BakedModel> callback);
}
