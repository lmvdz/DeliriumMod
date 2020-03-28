package net.lmvdz.delirium.block.blocks;

import net.lmvdz.delirium.block.DeliriumBlock;
import net.minecraft.client.render.RenderLayer;

/**
 * DeliniumBlock
 */
public class DeliniumBlock extends DeliriumBlock {

    public static DeliniumBlock DELINIUM_BLOCK;

    public DeliniumBlock(Settings settings, RenderLayer renderLayer) {
        super(settings, renderLayer);

        if (DELINIUM_BLOCK == null) {
            DELINIUM_BLOCK = this;
            registerDeliriumBlock(DELINIUM_BLOCK);
        }
    }

    
}