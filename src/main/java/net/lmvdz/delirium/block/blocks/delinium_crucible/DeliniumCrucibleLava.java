package net.lmvdz.delirium.block.blocks.delinium_crucible;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class DeliniumCrucibleLava extends DeliriumBlock {

    public static DeliniumCrucibleLava DELINIUM_CRUCIBLE_LAVA_BLOCK;

    @Environment(EnvType.CLIENT)
    public DeliniumCrucibleLava() {
        super(FabricBlockSettings.of(DeliniumCrucible.MAP_MATERIAL).nonOpaque(), RenderLayer.getEntitySolid(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/delinium_crucible_lava")).getTextureId()));

        if (DELINIUM_CRUCIBLE_LAVA_BLOCK == null) {
            DELINIUM_CRUCIBLE_LAVA_BLOCK = this;
            registerDeliriumBlock(DELINIUM_CRUCIBLE_LAVA_BLOCK);
        }
    }

}