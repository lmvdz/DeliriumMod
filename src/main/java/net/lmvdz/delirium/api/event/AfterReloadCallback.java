package net.lmvdz.delirium.api.event;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface AfterReloadCallback {
    /**
     * Fired after each MinecraftClient#reloadResources call
     */
    Event<AfterReloadCallback> AFTER =
            EventFactory.createArrayBacked(AfterReloadCallback.class,
                    (listeners) -> () -> {
                        for (AfterReloadCallback handler : listeners) {
                            handler.reloaded();
                        }
                    });
    void reloaded() throws Exception;

}
