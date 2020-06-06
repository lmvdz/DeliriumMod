//package net.lmvdz.delirium.shader;
//
//import java.util.HashMap;
//import java.util.Map;
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.RenderPhase;
//
//public class ShaderRenderLayers {
//    public static final Map<RenderLayer, RenderLayer> shaderRenderLayers = new HashMap<>();
//
//    public static RenderLayer computeShaderRenderLayerIfAbsent(RenderLayer baseLayer,
//            String newName, RenderPhase.Target renderTarget) {
//        return shaderRenderLayers.computeIfAbsent(baseLayer, tex -> RenderLayerDuplicator
//                .copyFrom(newName, baseLayer, builder -> builder.target(renderTarget)));
//    }
//}
