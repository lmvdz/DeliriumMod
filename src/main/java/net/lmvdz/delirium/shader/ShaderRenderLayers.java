package net.lmvdz.delirium.shader;

import java.util.HashMap;
import java.util.Map;

import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.impl.RenderLayerDuplicator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;

public class ShaderRenderLayers {
    public static final Map<RenderLayer, RenderLayer> shaderRenderLayers = new HashMap<>();

    @Environment(EnvType.CLIENT)
    public static RenderLayer getRenderLayer(RenderLayer baseLayer, String newName, RenderPhase.Target renderTarget) {
        return shaderRenderLayers.computeIfAbsent(baseLayer, tex -> RenderLayerHelper.copy(baseLayer, newName, builder -> builder.target(renderTarget)));
    }
}
