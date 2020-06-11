package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface BeforeReloadCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<BeforeReloadCallback> BEFORE =
            EventFactory.createArrayBacked(BeforeReloadCallback.class,
                    (listeners) -> () -> {
                        for (BeforeReloadCallback handler : listeners) {
                            handler.reloading();
                        }
                    });

    void reloading() throws Exception;
}
