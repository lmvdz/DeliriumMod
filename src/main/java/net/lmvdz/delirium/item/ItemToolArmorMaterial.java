package net.lmvdz.delirium.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import java.util.Map;

public class ItemToolArmorMaterial extends ItemToolMaterial {

    public ItemArmorMaterial armorMaterial;

    public ItemArmorMaterial getArmorMaterial() {
        return this.armorMaterial;
    }

    public ItemToolArmorMaterial(Settings settings, float toolAttackDamage, int toolDurability, int toolEnchantability, int toolMiningLevel, float toolMiningSpeed, float toolAttackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, Ingredient repairIngredient, String name, float toughness, float knockbackResistance) {
        super(settings, toolAttackDamage, toolDurability, toolEnchantability, toolMiningLevel, toolMiningSpeed, toolAttackSpeed);
        this.armorMaterial = new ItemArmorMaterial(armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, this.repairIngredient, name, toughness, knockbackResistance);
    }

    public ItemToolArmorMaterial(Settings settings, float toolAttackDamage, int toolDurability, int toolEnchantability, int toolMiningLevel, float toolMiningSpeed, float toolAttackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, Ingredient repairIngredient, String name, float toughness, float knockbackResistance, Item ingredient) {
        super(settings,
                toolAttackDamage,
                toolDurability,
                toolEnchantability,
                toolMiningLevel,
                toolMiningSpeed,
                toolAttackSpeed,
                ingredient);
        this.armorMaterial = new ItemArmorMaterial(armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, this.repairIngredient, name, toughness, knockbackResistance);
    }

    public class ItemArmorMaterial implements ArmorMaterial {

        public Map<EquipmentSlot, Integer> armorDurabilities;
        public Map<EquipmentSlot, Integer> armorProtectionAmounts;
        public int armorEnchantability;
        public SoundEvent equipSound;
        public Ingredient repairIngredient;
        public String name;
        public float toughness;
        public float knockbackResistance;

        public ItemArmorMaterial(Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, Ingredient repairIngredient, String name, float toughness, float knockbackResistance) {
            this.armorDurabilities = armorDurabilities;
            this.armorProtectionAmounts = armorProtectionAmounts;
            this.armorEnchantability = armorEnchantability;
            this.equipSound = equipSound;
            this.repairIngredient = repairIngredient;
            this.name = name;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }


        @Override
        public int getDurability(EquipmentSlot slot) {
            return armorDurabilities.get(slot);
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return armorProtectionAmounts.get(slot);
        }

        @Override
        public int getEnchantability() {
            return enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient;
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
    }


    public ItemToolArmorMaterial createChest() {
        new DeliriumArmorItem(this.armorMaterial, EquipmentSlot.CHEST, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public ItemToolArmorMaterial createLegs() {
        new DeliriumArmorItem(this.armorMaterial, EquipmentSlot.LEGS, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public ItemToolArmorMaterial createFeet() {
        new DeliriumArmorItem(this.armorMaterial, EquipmentSlot.FEET, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public ItemToolArmorMaterial createHead() {
        new DeliriumArmorItem(this.armorMaterial, EquipmentSlot.HEAD, (new Item.Settings()).group(ItemGroup.COMBAT));
        return this;
    }

    public void createArmor() {
        this.createChest().createLegs().createFeet().createHead();
    }
}
