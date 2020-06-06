package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Consumer;


@FunctionalInterface
public interface PreWorldRenderLayerVertexBufferDrawCallback {
    /**
     * Fired after each WorldRenderer#renderLayer call
     */
    Event<PreWorldRenderLayerVertexBufferDrawCallback> EVENT =
            EventFactory.createArrayBacked(PreWorldRenderLayerVertexBufferDrawCallback.class,
                    (listeners) -> (matrix, mode, vertexBuffer, cancel) -> {
                        for (PreWorldRenderLayerVertexBufferDrawCallback handler : listeners) {
                            handler.redirectVertexBufferDraw(matrix, mode, vertexBuffer, cancel);
                        }
                    });

    void redirectVertexBufferDraw(Matrix4f matrix, int mode, VertexBuffer vertexBuffer,
                                  Consumer<Boolean> cancel);
}
