package net.lmvdz.delirium.ppag;

import net.fabricmc.fabric.impl.renderer.SpriteFinderImpl;
import net.lmvdz.delirium.mixin.SpriteAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Arrays;
import java.util.List;

public class ProceduralPixelArtGenerator {
    List<SpriteIdentifier> sprites;

    public ProceduralPixelArtGenerator(List<SpriteIdentifier> sprites) {
        this.sprites = sprites;
        this.generate();
    }

    public void generate() {
//        MinecraftClient client = MinecraftClient.getInstance();
        sprites.forEach(sprite -> {
            Arrays.asList(((SpriteAccessor)sprite.getSprite()).getImages()).forEach(nativeImage -> {
                System.out.println(nativeImage.toString());
            });
        });
    }

}
