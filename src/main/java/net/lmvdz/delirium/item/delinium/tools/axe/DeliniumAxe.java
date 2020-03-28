package net.lmvdz.delirium.item.delinium.tools.axe;

import net.lmvdz.delirium.item.DeliriumAxeItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.item.Item;

/**
 * DeliniumAxe
 */
public class DeliniumAxe extends DeliriumAxeItem {
 public static DeliniumAxe DELINIUM_AXE;
    public DeliniumAxe() {
        super(Delinium.DELINIUM, 1, 5f, new Item.Settings());
        if (DELINIUM_AXE == null) {
            DELINIUM_AXE = this;
            registerAxeItem(DELINIUM_AXE);
        }
    }

    
}