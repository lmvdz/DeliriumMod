package net.lmvdz.delirium.api.event;

        import net.fabricmc.fabric.api.event.Event;
        import net.fabricmc.fabric.api.event.EventFactory;
        import net.minecraft.client.render.VertexConsumerProvider;
        import net.minecraft.util.math.Matrix4f;

        import java.util.function.BiConsumer;

@FunctionalInterface
public interface TextRendererDrawLayerStringCallback {
    /**
     * Fired after each TextRenderer drawLayer call
     */
    Event<TextRendererDrawLayerStringCallback> EVENT = EventFactory
            .createArrayBacked(TextRendererDrawLayerStringCallback.class, (listeners) -> (text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn) -> {
                for (TextRendererDrawLayerStringCallback handler : listeners) {
                    handler.onDrawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn);
                }
            });

    void onDrawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, BiConsumer<Boolean, Float> cancelAndReturn);
}
