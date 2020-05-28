package net.lmvdz.delirium.api.event;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;

@FunctionalInterface
public interface PreWorldRenderLayerCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<PreWorldRenderLayerCallback> EVENT =
            EventFactory.createArrayBacked(PreWorldRenderLayerCallback.class,
                    (listeners) -> (renderLayer, matrixStack, d, e, f, cancel) -> {
                        for (PreWorldRenderLayerCallback handler : listeners) {
                            handler.beforeLayerRendered(renderLayer, matrixStack, d, e, f, cancel);
                        }
                    });

    void beforeLayerRendered(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e,
            double f, Consumer<Boolean> cancel);
}
