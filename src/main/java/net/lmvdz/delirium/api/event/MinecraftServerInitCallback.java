package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

@FunctionalInterface
public interface MinecraftServerInitCallback {
    /**
     * Fired after each new MinecraftServer
     */
    Event<MinecraftServerInitCallback> EVENT = EventFactory
            .createArrayBacked(MinecraftServerInitCallback.class, (listeners) -> (server) -> {
                for (MinecraftServerInitCallback handler : listeners) {
                    handler.onInitMinecraftServer(server);
                }
            });

    void onInitMinecraftServer(MinecraftServer server);
}
