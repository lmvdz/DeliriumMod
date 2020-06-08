package net.lmvdz.delirium.api.event;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface ReloadCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<ReloadCallback> EVENT =
            EventFactory.createArrayBacked(ReloadCallback.class,
                    (listeners) -> () -> {
                        for (ReloadCallback handler : listeners) {
                            handler.reloaded();
                        }
                    });

    void reloaded();
}
