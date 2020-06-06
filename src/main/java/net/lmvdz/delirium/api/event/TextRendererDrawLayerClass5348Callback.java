package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_5348;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Matrix4f;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface TextRendererDrawLayerClass5348Callback {
    /**
     * Fired after each TextRenderer drawLayer call
     */
    Event<TextRendererDrawLayerClass5348Callback> EVENT = EventFactory
            .createArrayBacked(TextRendererDrawLayerClass5348Callback.class, (listeners) -> (text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn) -> {
                for (TextRendererDrawLayerClass5348Callback handler : listeners) {
                    handler.onDrawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light, cancelAndReturn);
                }
            });

    void onDrawLayer(class_5348 text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, BiConsumer<Boolean, Float> cancelAndReturn);
}
