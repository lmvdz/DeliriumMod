package net.lmvdz.delirium.ppag;


import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.common.ArtificeRegistry;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.lmvdz.delirium.api.event.AfterReloadCallback;
import net.lmvdz.delirium.api.event.BeforeReloadCallback;
import net.lmvdz.delirium.api.event.GetHeldItemModelCallback;
import net.lmvdz.delirium.mixin.SpriteAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.lang.annotation.Native;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class ProceduralPixelArtGenerator implements AfterReloadCallback, BeforeReloadCallback, GetHeldItemModelCallback {

    NativeImageBackedTexture nIBT;
    SpriteIdentifier spriteIdentifier;
    Identifier dynamicTexture;
    NativeImage nativeImage;
    Item i;
    int numberOfArtifice = 0;

    public ProceduralPixelArtGenerator(Item i, SpriteIdentifier spriteIdentifier) {
        this.i = i;
        this.spriteIdentifier = spriteIdentifier;
        AfterReloadCallback.AFTER.register(this);
        BeforeReloadCallback.BEFORE.register(this);
    }

    public void generate() throws Exception {
        MinecraftClient client = MinecraftClient.getInstance();
        NativeImage nIBTimage =  ((SpriteAccessor)this.spriteIdentifier.getSprite()).getImages()[0];
        if (nIBTimage != null) {
            for(int x = 0; x < nIBTimage.getWidth(); x++) {
                for(int y = 0; y < nIBTimage.getHeight(); y++) {
                    nIBTimage.setPixelColor(x, y, NativeImage.getAbgrColor((int)(Math.random() * 255)/2,(int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)));
                }
            }
        }
        assert nIBTimage != null;
        NativeImageBackedTexture nIBT = new NativeImageBackedTexture(nIBTimage);
        client.getTextureManager().registerTexture(this.spriteIdentifier.getTextureId(), nIBT);
        nIBT.upload();
//        Registry.register(ArtificeRegistry.ASSETS, Registry.ITEM.getRawId(this.i), spriteIdentifier.getTextureId().getNamespace() + ":" + spriteIdentifier.getTextureId().getPath(), ArtificeResourcePack.ofAssets((packBuilder) -> {
//            String[] pathSplit = spriteIdentifier.getTextureId().getPath().split("/");
//            Identifier dynamicTexture = client.getTextureManager().registerDynamicTexture(pathSplit[pathSplit.length-1], nIBT);
////            client.getTextureManager().registerTexture(spriteIdentifier.getTextureId(), nIBT);
//            nIBT.bindTexture();
//            packBuilder.addItemModel(new Identifier(spriteIdentifier.getTextureId().getNamespace(), pathSplit[pathSplit.length-1]), modelBuilder -> {
//                modelBuilder.parent(new Identifier("item/handheld")).texture("layer0", dynamicTexture);
//            });
//        }));


//        ((MutableRegistry)Registry.ITEM).set(Registry.ITEM.getRawId(this.i), RegistryKey.of(Registry.ITEM_KEY, new Identifier("minecraft", "dynamic/ppag__1")), i);
//      Registry.register(Registry.ITEM, Registry.ITEM.getRawId(this.i), spriteIdentifier.getTextureId().getNamespace() + ":" + spriteIdentifier.getTextureId().getPath() , i);
    }

    @Override
    public void reloaded() throws Exception {
//        MinecraftClient client = MinecraftClient.getInstance();
//        client.getTextureManager().bindTexture(spriteIdentifier.getTextureId());
//        nIBT.bindTexture();
    }

    @Override
    public void reloading() throws Exception {
        this.generate();
    }

    @Override
    public void onGetHeldItemModel(ItemStack stack, World world, LivingEntity entity, Consumer<BakedModel> callback) {
//        callback.accept();
    }
}
