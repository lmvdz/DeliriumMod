package net.lmvdz.delirium.item.delinium.tools.pickaxe;

import net.lmvdz.delirium.item.DeliriumPickaxeItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.item.Item;

/**
 * DeliniumPickaxe
 */
public class DeliniumPickaxe extends DeliriumPickaxeItem {
 public static DeliniumPickaxe DELINIUM_PICKAXE;
    public DeliniumPickaxe() {
        super(Delinium.DELINIUM, 1, 5f, new Item.Settings());
        if (DELINIUM_PICKAXE == null) {
            DELINIUM_PICKAXE = this;
            registerPickaxeItem(DELINIUM_PICKAXE);
        }
    }

    
}