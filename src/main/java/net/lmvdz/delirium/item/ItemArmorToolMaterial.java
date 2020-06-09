package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;

import java.util.Map;

public class ItemArmorToolMaterial extends CustomItem {

    private final CustomArmorMaterial armorMaterial;
    private final CustomToolMaterial toolMaterial;

    public CustomArmorMaterial getArmorMaterial() {
        return this.armorMaterial;
    }

    public CustomToolMaterial getToolMaterial() { return toolMaterial; }

    public static ItemArmorToolMaterial of(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        ItemArmorToolMaterial iATM = new ItemArmorToolMaterial(modid, itemName, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, toughness, knockbackResistance);
        iATM.registerItem();
        return iATM;
    }

    private ItemArmorToolMaterial(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        super(modid, new Settings().group(ItemGroup.MATERIALS));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemName));
        setIdentifier(this);
        this.toolMaterial = new CustomToolMaterial(attackDamage,
                durability,
                enchantability,
                miningLevel,
                miningSpeed,
                attackSpeed, getItemName(this),this);
        this.armorMaterial = new CustomArmorMaterial(armorDurabilities,
                armorProtectionAmounts,
                armorEnchantability,
                equipSound,
                getItemName(this) + "_armor_material",
                toughness,
                knockbackResistance, getItemName(this), this);
    }

    public ItemArmorToolMaterial(String modid, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        super(modid, new Settings().group(ItemGroup.MATERIALS));
        this.toolMaterial = new CustomToolMaterial(attackDamage,
                durability,
                enchantability,
                miningLevel,
                miningSpeed,
                attackSpeed, getItemName(this),this);
        this.armorMaterial = new CustomArmorMaterial(armorDurabilities,
                armorProtectionAmounts,
                armorEnchantability,
                equipSound,
                getItemName(this) + "_armor_material",
                toughness,
                knockbackResistance, getItemName(this), this);
    }

    /*
        Armor and Tools creation helper functions
     */
    public ItemArmorToolMaterial createArmorAndTools() {
        return this.createArmor().createTools();
    }

    public ItemArmorToolMaterial createSelectedArmorAndTools(boolean head, boolean chest, boolean legs, boolean feet, boolean axe, boolean sword, boolean shovel, boolean hoe, boolean pickaxe, boolean tool) {
        return this.createSelectedArmor(head, chest, legs, feet).createSelectedTools(axe, sword, shovel, hoe, pickaxe, tool);
    }

    /*
        Armor creation helper functions
     */
    public ItemArmorToolMaterial createArmor() {
        this.getArmorMaterial().createArmor(getModid());
        return this;
    }

    public ItemArmorToolMaterial createSelectedArmor(boolean head, boolean chest, boolean legs, boolean feet) {
        if (head) createHead();
        if (chest) createChest();
        if (legs) createLegs();
        if (feet) createFeet();
        return this;
    }

    public ItemArmorToolMaterial createHead() { this.getArmorMaterial().createHead(getModid()); return this; }
    public ItemArmorToolMaterial createChest() { this.getArmorMaterial().createChest(getModid()); return this; }
    public ItemArmorToolMaterial createLegs() { this.getArmorMaterial().createLegs(getModid()); return this; }
    public ItemArmorToolMaterial createFeet() { this.getArmorMaterial().createFeet(getModid()); return this; }


    /*
        Tools creation helper functions
     */
    public ItemArmorToolMaterial createTools() {
        this.getToolMaterial().createTools(getModid());
        return this;
    }

    public ItemArmorToolMaterial createSelectedTools(boolean axe, boolean sword, boolean shovel, boolean hoe, boolean pickaxe, boolean tool) {
        if (axe) createAxe();
        if (sword) createSword();
        if (shovel) createShovel();
        if (hoe) createHoe();
        if (pickaxe) createPickaxe();
        if (tool) createTool();
        return this;
    }

    public ItemArmorToolMaterial createAxe() { this.getToolMaterial().createAxe(getModid()); return this; }
    public ItemArmorToolMaterial createSword() { this.getToolMaterial().createSword(getModid()); return this; }
    public ItemArmorToolMaterial createShovel() { this.getToolMaterial().createShovel(getModid()); return this; }
    public ItemArmorToolMaterial createHoe() { this.getToolMaterial().createHoe(getModid()); return this; }
    public ItemArmorToolMaterial createPickaxe() { this.getToolMaterial().createPickaxe(getModid()); return this; }
    public ItemArmorToolMaterial createTool() { this.getToolMaterial().createTool(getModid()); return this; }



}
