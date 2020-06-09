package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;


@FunctionalInterface
public interface PlayerKilledOtherCallback {
    /**
     * Fired after each PlayerEntity#onKilledOther
     */
    Event<PlayerKilledOtherCallback> EVENT =
            EventFactory.createArrayBacked(PlayerKilledOtherCallback.class,
                    (listeners) -> (killer, victim) -> {
                        for (PlayerKilledOtherCallback handler : listeners) {
                            handler.onKilledOther(killer, victim);
                        }
                    });

    void onKilledOther(PlayerEntity killer, LivingEntity victim);
}
