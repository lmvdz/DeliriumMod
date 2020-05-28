package net.lmvdz.delirium.api.event;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;

@FunctionalInterface
public interface PreRenderLayerDrawCallback {
    /**
     * Fired when RenderLayer#draw() is called.
     */
    Event<PreRenderLayerDrawCallback> EVENT = EventFactory
            .createArrayBacked(PreRenderLayerDrawCallback.class, (listeners) -> (renderLayer,
                    buffer, translucent, cameraX, cameraY, cameraZ, cancel) -> {
                for (PreRenderLayerDrawCallback handler : listeners) {
                    handler.onDraw(renderLayer, buffer, translucent, cameraX, cameraY, cameraZ,
                            cancel);
                }
            });

    void onDraw(RenderLayer renderLayer, BufferBuilder buffer, boolean translucent, int cameraX,
            int cameraY, int cameraZ, Consumer<Boolean> cancel);
}
