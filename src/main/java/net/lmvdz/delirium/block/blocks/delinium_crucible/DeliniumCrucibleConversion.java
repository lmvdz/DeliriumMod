package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.HashMap;
import net.minecraft.item.Item;

public class DeliniumCrucibleConversion {

    public static HashMap<Item, DeliniumCrucibleConversion> smeltConversions = new HashMap<>();

    public static void registerSmeltConversion(Item from, int fromCount, Item product, int productCount) {
        smeltConversions.put(from, new DeliniumCrucibleConversion(product, productCount, fromCount));
    }

    Item product;
    int count;
    int per;

    public DeliniumCrucibleConversion(Item product, int count, int per) {
        this.product = product;
        this.count = count;
        this.per = per;
    }
}