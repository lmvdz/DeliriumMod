package net.lmvdz.delirium.item.delinium.tools.axe;

import net.lmvdz.delirium.item.DeliriumAxeItem;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.item.Item;

/**
 * DeliniumIngotAxe
 */
public class DeliniumIngotAxe extends DeliriumAxeItem {
    public static DeliniumIngotAxe DELINIUM_INGOT_AXE;

    public DeliniumIngotAxe() {
        super(DeliniumIngot.DELINIUM_INGOT, 1, 5f, new Item.Settings());
        if (DELINIUM_INGOT_AXE == null) {
            DELINIUM_INGOT_AXE = this;
            registerAxeItem(DELINIUM_INGOT_AXE);
        }
    }

    
}