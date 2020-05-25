package net.lmvdz.delirium.item.delinium.items;

import net.lmvdz.delirium.item.ItemToolMaterial;
import net.minecraft.item.Item;

/**
 * DeliniumIngot
 */
public class DeliniumIngot extends ItemToolMaterial {
    
    public static DeliniumIngot DELINIUM_INGOT;

    public DeliniumIngot() {
        super(new Item.Settings(), 2.5f, 1000, 25, 1, 5f, 2f);
        if (DELINIUM_INGOT == null) {
            DELINIUM_INGOT = this;
            registerItem();
            createTools();
        }
    }
}