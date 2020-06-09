package net.lmvdz.delirium.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LightmapTextureManager.class)
public interface LightmapTextureManagerAccessor {

    @Accessor
    Identifier getTextureIdentifier();
}
