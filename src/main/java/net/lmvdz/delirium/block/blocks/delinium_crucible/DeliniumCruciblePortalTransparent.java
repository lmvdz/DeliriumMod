package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class DeliniumCruciblePortalTransparent extends DeliriumBlock {

    public static DeliniumCruciblePortalTransparent DELINIUM_CRUCIBLE_PORTAL_TRANSPARENT_BLOCK;

    public DeliniumCruciblePortalTransparent() {
        super(FabricBlockSettings.of(Delinium.MAP_MATERIAL).nonOpaque().build(), RenderLayer.getEntitySolid(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/transparent")).getTextureId()));

        if (DELINIUM_CRUCIBLE_PORTAL_TRANSPARENT_BLOCK == null) {
            DELINIUM_CRUCIBLE_PORTAL_TRANSPARENT_BLOCK = this;
            registerDeliriumBlock(DELINIUM_CRUCIBLE_PORTAL_TRANSPARENT_BLOCK);
        }
    }

}