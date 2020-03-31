package net.lmvdz.delirium.item.delinium.tools.swords;

import net.lmvdz.delirium.item.DeliriumSwordItem;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.item.Item;


/**
 * DeliniumDagger
 */
public class DeliniumIngotSword extends DeliriumSwordItem {

    public static DeliniumIngotSword DELINIUM_INGOT_SWORD;

    public DeliniumIngotSword() {
        super(DeliniumIngot.DELINIUM_INGOT, 1, 5f, new Item.Settings());
        if (DELINIUM_INGOT_SWORD == null) {
            DELINIUM_INGOT_SWORD = this;
            registerSwordItem(DELINIUM_INGOT_SWORD);
        }
        
    }
}