package net.lmvdz.delirium.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import java.util.Map;

public class CustomArmorMaterial implements ArmorMaterial {

    public Map<EquipmentSlot, Integer> armorDurabilities;
    public Map<EquipmentSlot, Integer> armorProtectionAmounts;
    public int armorEnchantability;
    public SoundEvent equipSound;
    public Item repairIngredientAsItem;
    public Ingredient repairIngredient;
    public String repairIngredientName;
    public String name;
    public float toughness;
    public float knockbackResistance;

    public CustomArmorMaterial(Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, String name, float toughness, float knockbackResistance, String repairIngredientName, Item repairIngredientAsItem) {
        this.armorDurabilities = armorDurabilities;
        this.armorProtectionAmounts = armorProtectionAmounts;
        this.armorEnchantability = armorEnchantability;
        this.equipSound = equipSound;
        this.repairIngredientAsItem = repairIngredientAsItem;
        this.repairIngredient = Ingredient.ofItems(repairIngredientAsItem);
        this.repairIngredientName = repairIngredientName;
        this.name = name;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }


    @Override
    public int getDurability(EquipmentSlot slot) {
        return armorDurabilities.getOrDefault(slot, 1);
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return armorProtectionAmounts.getOrDefault(slot, 1);
    }

    @Override
    public int getEnchantability() {
        return armorEnchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    public Item getRepairIngredientAsItem() {
        return this.repairIngredientAsItem;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public CustomArmorMaterial createChest(String modid) {
        CustomArmorItem.generate(modid, this, EquipmentSlot.CHEST, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public CustomArmorMaterial createLegs(String modid) {
        CustomArmorItem.generate(modid,this, EquipmentSlot.LEGS, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public CustomArmorMaterial createFeet(String modid) {
        CustomArmorItem.generate(modid,this, EquipmentSlot.FEET, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public CustomArmorMaterial createHead(String modid) {
        CustomArmorItem.generate(modid,this, EquipmentSlot.HEAD, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public void createArmor(String modid) {
        this.createChest(modid).createLegs(modid).createFeet(modid).createHead(modid);
    }

    public String getIngredientName() {
        return repairIngredientName;
    }
}
