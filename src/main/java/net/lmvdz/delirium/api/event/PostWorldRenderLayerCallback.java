package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;

@FunctionalInterface
public interface PostWorldRenderLayerCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<PostWorldRenderLayerCallback> EVENT =
            EventFactory.createArrayBacked(PostWorldRenderLayerCallback.class,
                    (listeners) -> (renderLayer, matrixStack, d, e, f) -> {
                        for (PostWorldRenderLayerCallback handler : listeners) {
                            handler.afterLayerRendered(renderLayer, matrixStack, d, e, f);
                        }
                    });

    void afterLayerRendered(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e,
            double f);
}
