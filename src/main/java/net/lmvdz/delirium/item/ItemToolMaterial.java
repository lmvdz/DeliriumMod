package net.lmvdz.delirium.item;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;


public class ItemToolMaterial extends DeliriumItem implements ToolMaterial {
    float attackDamage;
    float attackSpeed;
    int durability;
    int enchantability;
    int miningLevel;
    float miningSpeed;
    Item repairIngredientAsItem;
    Ingredient repairIngredient;

    public ItemToolMaterial(Item.Settings settings, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed) {
        super(settings);
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.miningLevel = miningLevel;
        this.repairIngredientAsItem = this;
        this.repairIngredient = asIngredient(this);
    }

    public ItemToolMaterial(Item.Settings settings, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item ingredient) {
        super(settings);
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.miningLevel = miningLevel;
        this.repairIngredientAsItem = ingredient;
        this.repairIngredient = asIngredient(ingredient);
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

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    public Item getRepairIngredientAsItem() {
        return this.repairIngredientAsItem;
    }

    public ItemToolMaterial createTool() {
        DeliriumToolItem.makeOutOf(this, new Item.Settings());
        return this;
    }

    public ItemToolMaterial createSword() {
        DeliriumSwordItem.makeOutOf(this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings());
        return this; 
    }

    public ItemToolMaterial createShovel() {
        DeliriumShovelItem.makeOutOf(this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings());
        return this;
    }

    public ItemToolMaterial createPickaxe() {
        DeliriumPickaxeItem.makeOutOf(this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings());
        return this;
    }

    public ItemToolMaterial createHoe() {
        DeliriumHoeItem.makeOutOf(this, this.getAttackSpeed(), new Item.Settings());
        return this;
    }
    
    public ItemToolMaterial createAxe() {
        DeliriumAxeItem.makeOutOf(this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings());
        return this;
    }

    public ItemToolMaterial create() {
        return this.createTool().createSword().createShovel().createPickaxe().createHoe().createAxe();
    }
    
}