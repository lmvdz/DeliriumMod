package net.lmvdz.delirium.ppag;

import net.lmvdz.delirium.api.event.ReloadCallback;
import net.lmvdz.delirium.mixin.SpriteAccessor;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.SpriteIdentifier;

import java.util.Arrays;

public class ProceduralPixelArtGenerator implements ReloadCallback {

    NativeImageBackedTexture nIBT;
    SpriteIdentifier spriteIdentifier;

    public ProceduralPixelArtGenerator(SpriteIdentifier spriteIdentifier) {
        this.spriteIdentifier = spriteIdentifier;
        ReloadCallback.EVENT.register(this);
    }

    public void generate() {
//        MinecraftClient client = MinecraftClient.getInstance();
        Arrays.asList(((SpriteAccessor)this.spriteIdentifier.getSprite()).getImages()).forEach(nativeImage -> {
            System.out.println(nativeImage.toString());
        });
    }

    @Override
    public void reloaded() {
        this.generate();
    }
}
