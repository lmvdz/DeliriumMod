package net.lmvdz.delirium.item.delinium.items;

import net.fabricmc.fabric.api.block.FabricMaterialBuilder;
import net.lmvdz.delirium.item.DeliriumItemToolMaterial;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;

/**
 * Delirite
 */
public class Delinium extends DeliriumItemToolMaterial {
    
    public static Delinium DELINIUM;

    public final static Material MAP_MATERIAL = new FabricMaterialBuilder(MaterialColor.GREEN).build();
    
    public Delinium() {
        // attackDamage, durability, enchantability, mining level, miningSpeed
        super((new Item.Settings()), 2.5f, 1000, 25, 1, 5f);
        if (DELINIUM == null) {
            DELINIUM = this;
            registerItem(DELINIUM);
        }
    }

}