package net.lmvdz.delirium.item.delinium.swords;

import net.lmvdz.delirium.item.DeliriumSwordItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.item.Item;


/**
 * DeliniumDagger
 */
public class DeliniumSword extends DeliriumSwordItem {

    public static DeliniumSword DELINIUM_SWORD;

    public DeliniumSword() {
        // item tool material, attack damage, attack speed, settings
        super(Delinium.DELINIUM, 1, 5f, new Item.Settings());
        if (DELINIUM_SWORD == null) {
            DELINIUM_SWORD = this;
            registerSwordItem(DELINIUM_SWORD);
        }
    }
}