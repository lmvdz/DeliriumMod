package net.lmvdz.delirium.item.delinium.tools.shovel;


import net.lmvdz.delirium.item.DeliriumShovelItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.item.Item;

/**
 * DeliniumShovel
 */
public class DeliniumShovel extends DeliriumShovelItem {
 public static DeliniumShovel DELINIUM_SHOVEL;
    public DeliniumShovel() {
        super(Delinium.DELINIUM, 1, 5f, new Item.Settings());
        if (DELINIUM_SHOVEL == null) {
            DELINIUM_SHOVEL = this;
            registerShovelItem(DELINIUM_SHOVEL);
        }
    }

    
}