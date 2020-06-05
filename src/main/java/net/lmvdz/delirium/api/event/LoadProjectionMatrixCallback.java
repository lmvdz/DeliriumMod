package net.lmvdz.delirium.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.Matrix4f;

@FunctionalInterface
public interface LoadProjectionMatrixCallback {
    /**
     * Fired after each WorldRenderer#loadProjectionMatrix call
     */
    Event<LoadProjectionMatrixCallback> EVENT = EventFactory
            .createArrayBacked(LoadProjectionMatrixCallback.class, (listeners) -> (matrix4f) -> {
                for (LoadProjectionMatrixCallback handler : listeners) {
                    handler.onLoadProjectionMatrix(matrix4f);
                }
            });

    void onLoadProjectionMatrix(Matrix4f matrix4f);
}
