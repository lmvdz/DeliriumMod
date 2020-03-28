package net.lmvdz.delirium.item.delinium.tools.shovel;

import net.lmvdz.delirium.item.DeliriumShovelItem;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.item.Item;

/**
 * DeliniumIngotShovel
 */
public class DeliniumIngotShovel extends DeliriumShovelItem {
    public static DeliniumIngotShovel DELINIUM_INGOT_SHOVEL;

    public DeliniumIngotShovel() {
        super(DeliniumIngot.DELINIUM_INGOT, 1, 5f, new Item.Settings());
        if (DELINIUM_INGOT_SHOVEL == null) {
            DELINIUM_INGOT_SHOVEL = this;
            registerShovelItem(DELINIUM_INGOT_SHOVEL);
        }
    }

    
}