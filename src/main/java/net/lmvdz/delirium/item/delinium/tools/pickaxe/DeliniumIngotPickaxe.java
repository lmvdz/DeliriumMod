package net.lmvdz.delirium.item.delinium.tools.pickaxe;

import net.lmvdz.delirium.item.DeliriumPickaxeItem;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.item.Item;

/**
 * DeliniumIngotPickaxe
 */
public class DeliniumIngotPickaxe extends DeliriumPickaxeItem {
    public static DeliniumIngotPickaxe DELINIUM_INGOT_PICKAXE;

    public DeliniumIngotPickaxe() {
        super(DeliniumIngot.DELINIUM_INGOT, 1, 5f, new Item.Settings());
        if (DELINIUM_INGOT_PICKAXE == null) {
            DELINIUM_INGOT_PICKAXE = this;
            registerPickaxeItem(DELINIUM_INGOT_PICKAXE);
        }
    }

    
}