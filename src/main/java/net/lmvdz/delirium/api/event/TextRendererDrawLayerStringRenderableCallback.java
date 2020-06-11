package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.StringRenderable;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Matrix4f;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface TextRendererDrawLayerStringRenderableCallback {
    /**
     * Fired after each TextRenderer drawLayer call
     */
    Event<TextRendererDrawLayerStringRenderableCallback> EVENT = EventFactory
            .createArrayBacked(TextRendererDrawLayerStringRenderableCallback.class, (listeners) -> (text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn) -> {
                for (TextRendererDrawLayerStringRenderableCallback handler : listeners) {
                    handler.onDrawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn);
                }
            });

    void onDrawLayer(StringRenderable text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, BiConsumer<Boolean, Float> cancelAndReturn);
}
