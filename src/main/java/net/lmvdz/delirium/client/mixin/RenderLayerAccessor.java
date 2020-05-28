package net.lmvdz.delirium.client.mixin;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.class)
public interface RenderLayerAccessor {
    @Accessor
    boolean isTranslucent();

    @Accessor
    boolean isHasCrumbling();
}
