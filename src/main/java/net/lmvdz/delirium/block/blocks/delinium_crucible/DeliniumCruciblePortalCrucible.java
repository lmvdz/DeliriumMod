package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class DeliniumCruciblePortalCrucible extends DeliriumBlock {

    public static DeliniumCruciblePortalCrucible DELINIUM_CRUCIBLE_PORTAL_CRUCIBLE_BLOCK;

    public DeliniumCruciblePortalCrucible() {
        super(FabricBlockSettings.of(Delinium.MAP_MATERIAL).nonOpaque().build(), RenderLayer.getEntitySolid(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/delinium_crucible")).getTextureId()));

        if (DELINIUM_CRUCIBLE_PORTAL_CRUCIBLE_BLOCK == null) {
            DELINIUM_CRUCIBLE_PORTAL_CRUCIBLE_BLOCK = this;
            registerDeliriumBlock(DELINIUM_CRUCIBLE_PORTAL_CRUCIBLE_BLOCK);
        }
    }

}