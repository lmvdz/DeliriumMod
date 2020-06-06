package net.lmvdz.delirium.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccess {
    @Accessor
    FontManager getFontManager();
}
