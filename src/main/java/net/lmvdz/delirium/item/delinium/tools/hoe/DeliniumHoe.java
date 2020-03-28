package net.lmvdz.delirium.item.delinium.tools.hoe;

import net.lmvdz.delirium.item.DeliriumHoeItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.item.Item;

/**
 * DeliniumHoe
 */
public class DeliniumHoe extends DeliriumHoeItem {
 public static DeliniumHoe DELINIUM_HOE;
    public DeliniumHoe() {
        super(Delinium.DELINIUM, 5f, new Item.Settings());
        if (DELINIUM_HOE == null) {
            DELINIUM_HOE = this;
            registerHoeItem(DELINIUM_HOE);
        }
    }

    
}