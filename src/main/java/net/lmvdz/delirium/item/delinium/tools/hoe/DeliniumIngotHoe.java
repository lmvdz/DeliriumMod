package net.lmvdz.delirium.item.delinium.tools.hoe;

import net.lmvdz.delirium.item.DeliriumHoeItem;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.item.Item;

/**
 * DeliniumIngotHoe
 */
public class DeliniumIngotHoe extends DeliriumHoeItem {
    public static DeliniumIngotHoe DELINIUM_INGOT_HOE;

    public DeliniumIngotHoe() {
        super(DeliniumIngot.DELINIUM_INGOT, 5f, new Item.Settings());
        if (DELINIUM_INGOT_HOE == null) {
            DELINIUM_INGOT_HOE = this;
            registerHoeItem(DELINIUM_INGOT_HOE);
        }
    }

    
}