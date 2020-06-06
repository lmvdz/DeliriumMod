package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.TextRenderer;

@FunctionalInterface
public interface TextRendererInitCallback {
    /**
     * Fired after each TextRenderer init call
     */
    Event<TextRendererInitCallback> EVENT = EventFactory
            .createArrayBacked(TextRendererInitCallback.class, (listeners) -> (textRenderer) -> {
                for (TextRendererInitCallback handler : listeners) {
                    handler.onInit(textRenderer);
                }
            });

    void onInit(TextRenderer textRenderer);
}
