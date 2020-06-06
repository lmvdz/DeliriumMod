package net.lmvdz.delirium.client.mixin;

import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.FontStorage;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FontManager.class)
public interface FontManagerAccess {

    @Accessor
    Map<Identifier, FontStorage> getFontStorages();
}
