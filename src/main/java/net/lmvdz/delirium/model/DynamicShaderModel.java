package net.lmvdz.delirium.model;

import net.lmvdz.delirium.shader.ShaderProgramRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class DynamicShaderModel extends DynamicModel {

    public ShaderProgramRenderLayer program;

    public DynamicShaderModel(ShaderProgramRenderLayer program, Function<Identifier, RenderLayer> layerFactory) {
        super((Identifier id) -> program.getRenderLayer(layerFactory.apply(id)));
        this.program = program;
    }
}
