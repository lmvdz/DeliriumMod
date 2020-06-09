package net.lmvdz.delirium.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;


public class CustomToolMaterial implements ToolMaterial {
    float attackDamage;
    float attackSpeed;
    int durability;
    int enchantability;
    int miningLevel;
    float miningSpeed;
    Item repairIngredientAsItem;
    Ingredient repairIngredient;
    String repairIngredientName;


    public CustomToolMaterial(float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, String repairIngredientName, Item ingredientAsItem) {
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantability = enchantability;
        this.miningLevel = miningLevel;
        this.repairIngredientAsItem = ingredientAsItem;
        this.repairIngredient = Ingredient.ofItems(ingredientAsItem);
        this.repairIngredientName = repairIngredientName;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
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
    public int getEnchantability() { return enchantability; }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    public Item getRepairIngredientAsItem() {
        return this.repairIngredientAsItem;
    }

    public CustomToolMaterial createTool(String modid) {
        CustomToolItem.generate(modid, this, new Item.Settings().group(ItemGroup.TOOLS));
        return this;
    }

    public CustomToolMaterial createSword(String modid) {
        CustomSwordItem.generate(modid, this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings().group(ItemGroup.COMBAT));
        return this; 
    }

    public CustomToolMaterial createShovel(String modid) {
        CustomShovelItem.generate(modid, this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings().group(ItemGroup.TOOLS));
        return this;
    }

    public CustomToolMaterial createPickaxe(String modid) {
        CustomPickaxeItem.generate(modid, this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings().group(ItemGroup.TOOLS));
        return this;
    }

    public CustomToolMaterial createHoe(String modid) {
        CustomHoeItem.generate(modid, this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings().group(ItemGroup.TOOLS));
        return this;
    }
    
    public CustomToolMaterial createAxe(String modid) {
        CustomAxeItem.generate(modid, this, (int)this.getAttackDamage(), this.getAttackSpeed(), new Item.Settings().group(ItemGroup.TOOLS));
        return this;
    }

    public void createTools(String modid) {
        this.createTool(modid).createSword(modid).createShovel(modid).createPickaxe(modid).createHoe(modid).createAxe(modid);
    }

    public String getIngredientName() {
        return repairIngredientName;
    }
    
}