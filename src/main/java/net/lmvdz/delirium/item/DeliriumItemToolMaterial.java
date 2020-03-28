package net.lmvdz.delirium.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;


public class DeliriumItemToolMaterial extends DeliriumItem implements ToolMaterial {
    float attackDamage;
    int durability;
    int enchantability;
    int miningLevel;
    float miningSpeed;
    Ingredient repairIngredient;

    public DeliriumItemToolMaterial(Item.Settings settings, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed) {
        super(settings);
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.miningLevel = miningLevel;
        this.repairIngredient = this.asIngredient();
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeed() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    
}