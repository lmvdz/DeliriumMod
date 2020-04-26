package net.lmvdz.delirium.blockitem.blockitems;

import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.blockitem.DeliriumBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

/**
 * DeliniumCrucible
 */
public class DeliniumCrucibleBlockItem extends DeliriumBlockItem {

    public static DeliniumCrucibleBlockItem DELINIUM_CRUCIBLE_BLOCK_ITEM;

    public DeliniumCrucibleBlockItem() {
        super(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK, new Item.Settings().rarity(Rarity.EPIC));
        
        if (DELINIUM_CRUCIBLE_BLOCK_ITEM == null) {
            DELINIUM_CRUCIBLE_BLOCK_ITEM = this;
            registerBlockItem(DELINIUM_CRUCIBLE_BLOCK_ITEM);
        }
    }
    
}