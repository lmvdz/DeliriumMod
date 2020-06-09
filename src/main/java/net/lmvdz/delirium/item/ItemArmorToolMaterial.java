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

    public static ItemArmorToolMaterial build(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Map<EquipmentSlot, Integer> armorDurabilities, Map<EquipmentSlot, Integer> armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        ItemArmorToolMaterial iATM = new ItemArmorToolMaterial(modid, itemName, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, toughness, knockbackResistance);
        iATM.registerItem();
        iATM.createArmorAndTools();
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


    public void createArmorAndTools() {
        this.createTools();
        this.createArmor();
    }

    public void createArmor() {
        this.getArmorMaterial().createArmor(getModid());
    }

    public void createTools() {
        this.getToolMaterial().createTools(getModid());
    }

}
