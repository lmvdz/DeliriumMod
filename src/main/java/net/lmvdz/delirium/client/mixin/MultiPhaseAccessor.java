package net.lmvdz.delirium.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.client.render.RenderLayer$MultiPhase")
public interface MultiPhaseAccessor {
//    @Accessor
//    RenderLayer.MultiPhaseParameters getPhases();
}
