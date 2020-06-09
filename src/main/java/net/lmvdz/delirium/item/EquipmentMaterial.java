package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EquipmentMaterial extends CustomItem {


    private CustomArmorMaterial armorMaterial;
    private CustomToolMaterial toolMaterial;


    public CustomArmorMaterial getArmorMaterial() {
        return this.armorMaterial;
    }
    public boolean hasArmorMaterial() {return this.armorMaterial != null;}

    public CustomToolMaterial getToolMaterial() { return toolMaterial; }
    public boolean hasToolMaterial() {return this.toolMaterial != null;}

    public EquipmentMaterial armorMaterial(CustomArmorMaterial customArmorMaterial) {
        this.armorMaterial = customArmorMaterial;
        return this;
    }
    public EquipmentMaterial toolMaterial(CustomToolMaterial customToolMaterial) {
        this.toolMaterial = customToolMaterial;
        return this;
    }

    public static Builder builder(String modid, String itemName) {
        return Builder.builder(modid, itemName);
    }

    public static Builder builder(Identifier identifier) {
        return builder(identifier.getNamespace(), identifier.getPath());
    }
    private static EquipmentMaterial of(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, int[] armorDurabilities, int[] armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        EquipmentMaterial iATM = new EquipmentMaterial(modid, itemName, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, toughness, knockbackResistance);
        iATM.registerItem();
        return iATM;
    }

    private static EquipmentMaterial tool(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed) {
        EquipmentMaterial iATM = new EquipmentMaterial(modid, itemName, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed);
        iATM.registerItem();
        return iATM;
    }

    private static EquipmentMaterial armor(String modid, String itemName, int[] armorDurabilities, int[] armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        EquipmentMaterial iATM = new EquipmentMaterial(modid, itemName, armorDurabilities, armorProtectionAmounts, armorEnchantability, equipSound, toughness, knockbackResistance);
        iATM.registerItem();
        return iATM;
    }

    public static class Builder {
        String modid = "example";
        String itemName = "unknown-" + UUID.randomUUID().toString().split("-")[0];
        float attackDamage = 1;
        int durability = 500;
        int enchantability = 10;
        int miningLevel = 1;
        float miningSpeed = 5f;
        float attackSpeed = 1f;
        int[] armorDurabilities = new int[4];
        int[] armorProtectionAmounts = new int[4];
        int armorEnchantability = 1;
        SoundEvent equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        float toughness = 10f;
        float knockbackResistance = 5f;

        private Builder() {}

        public static Builder builder(String modid, String itemName) {
            Builder b = new Builder();
            b.modid = modid;
            b.itemName = itemName;
            return b;
        }

        public Builder modid(String modid) {
            this.modid = modid;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder attackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }
        public Builder durability(int durability) {
            this.durability = durability;
            return this;
        }
        public Builder enchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }
        public Builder miningLevel(int miningLevel) {
            this.miningLevel = miningLevel;
            return this;
        }
        public Builder miningSpeed(float miningSpeed) {
            this.miningSpeed = miningSpeed;
            return this;
        }
        public Builder attackSpeed(float attackSpeed) {
            this.attackSpeed = attackSpeed;
            return this;
        }
        public Builder armorDurabilities(int head, int chest, int legs, int feet) {
            this.armorDurabilities = new int[]{head, chest, legs, feet};
            return this;
        }
        public Builder armorDurability(EquipmentSlot slot, int value) {
            switch(slot) {
                case HEAD:
                    this.armorDurabilities[0] = value;
                case CHEST:
                    this.armorDurabilities[1] = value;
                case LEGS:
                    this.armorDurabilities[2] = value;
                case FEET:
                    this.armorDurabilities[3] = value;

            }
            return this;
        }
        public Builder armorProtectionAmounts(int head, int chest, int legs, int feet) {
            this.armorProtectionAmounts = new int[]{head, chest, legs, feet};
            return this;
        }
        public Builder armorProtectionAmount(EquipmentSlot slot, int value) {

            switch(slot) {
                case HEAD:
                    this.armorProtectionAmounts[0] = value;
                case CHEST:
                    this.armorProtectionAmounts[1] = value;
                case LEGS:
                    this.armorProtectionAmounts[2] = value;
                case FEET:
                    this.armorProtectionAmounts[3] = value;
            }
            return this;
        }
        public Builder armorEnchantability(int armorEnchantability) {
            this.armorEnchantability = armorEnchantability;
            return this;
        }
        public Builder equipSound(SoundEvent equipSound) {
            this.equipSound = equipSound;
            return this;
        }
        public Builder toughness(float toughness) {
            this.toughness = toughness;
            return this;
        }
        public Builder knockbackResistance(float knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
            return this;
        }
        public EquipmentMaterial build() {
            return EquipmentMaterial.of(this.modid, this.itemName, this.attackDamage, this.durability, this.enchantability, this.miningLevel, this.miningSpeed, this.attackSpeed, this.armorDurabilities, this.armorProtectionAmounts, this.armorEnchantability, this.equipSound, this.toughness, this.knockbackResistance);
        }

        public EquipmentMaterial buildArmor() {
            return EquipmentMaterial.armor(this.modid, this.itemName, this.armorDurabilities, this.armorProtectionAmounts, this.armorEnchantability, this.equipSound, this.toughness, this.knockbackResistance);
        }

        public EquipmentMaterial buildTool() {
            return EquipmentMaterial.tool(this.modid, this.itemName, this.attackDamage, this.durability, this.enchantability, this.miningLevel, this.miningSpeed, this.attackSpeed);
        }
    }



    private EquipmentMaterial(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, int[] armorDurabilities, int[] armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
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

    private EquipmentMaterial(String modid, String itemName, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed) {
        super(modid, new Settings().group(ItemGroup.MATERIALS));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemName));
        setIdentifier(this);
        this.toolMaterial = new CustomToolMaterial(attackDamage,
                durability,
                enchantability,
                miningLevel,
                miningSpeed,
                attackSpeed, getItemName(this),this);
    }

    private EquipmentMaterial(String modid, String itemName, int[] armorDurabilities, int[] armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
        super(modid, new Settings().group(ItemGroup.MATERIALS));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemName));
        setIdentifier(this);
        this.armorMaterial = new CustomArmorMaterial(armorDurabilities,
                armorProtectionAmounts,
                armorEnchantability,
                equipSound,
                getItemName(this) + "_armor_material",
                toughness,
                knockbackResistance, getItemName(this), this);
    }

    public EquipmentMaterial(String modid, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, int[] armorDurabilities, int[] armorProtectionAmounts, int armorEnchantability, SoundEvent equipSound, float toughness, float knockbackResistance) {
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
    public EquipmentMaterial createArmorAndTools() {
        return this.createArmor().createTools();
    }

    public EquipmentMaterial createSelectedArmorAndTools(boolean head, boolean chest, boolean legs, boolean feet, boolean axe, boolean sword, boolean shovel, boolean hoe, boolean pickaxe, boolean tool) {
        return this.createArmor(head, chest, legs, feet).createTools(axe, sword, shovel, hoe, pickaxe, tool);
    }

    /*
        Armor creation helper functions
     */
    public EquipmentMaterial createArmor() {
        if (this.hasArmorMaterial())
            this.getArmorMaterial().createArmor(getModid());
        else
            System.out.println("ERROR: No Armor Material Found for " + this.toString());
        return this;
    }

    public EquipmentMaterial createArmor(EquipmentSlot[] slots) {
        if (this.hasArmorMaterial()) {
            for (int x = 0; x < slots.length; x++) {
                createArmor(slots[x]);
            }
        } else {
            System.out.println("ERROR: No Armor Material Found for " + this.toString());
        }
        return this;
    }

    public EquipmentMaterial createArmor(boolean head, boolean chest, boolean legs, boolean feet) {
        if (this.hasArmorMaterial()) {
            if (head) createArmor(EquipmentSlot.HEAD);
            if (chest) createArmor(EquipmentSlot.CHEST);
            if (legs) createArmor(EquipmentSlot.LEGS);
            if (feet) createArmor(EquipmentSlot.FEET);
        } else {
            System.out.println("ERROR: No Armor Material Found for " + this.toString());
        }
        return this;
    }

    public EquipmentMaterial createArmor(EquipmentSlot slot) {
        if (this.hasArmorMaterial()) {
            switch (slot) {
                case HEAD:
                    this.getArmorMaterial().createHead(getModid());
                    break;
                case CHEST:
                    this.getArmorMaterial().createChest(getModid());
                    break;
                case LEGS:
                    this.getArmorMaterial().createLegs(getModid());
                    break;
                case FEET:
                    this.getArmorMaterial().createFeet(getModid());
                    break;
            }
        } else {
             System.out.println("ERROR: No Armor Material Found for " + this.toString());
        }
        return this;
    }

    /*
        Tools creation helper functions
     */
    public EquipmentMaterial createTools() {
        if (this.hasToolMaterial()) {
            this.getToolMaterial().createTools(getModid());
        } else {
             System.out.println("ERROR: No Tool Material Found for " + this.toString());
        }
        return this;
    }

    public EquipmentMaterial createTools(ToolType[] tools) {
        if (this.hasArmorMaterial()) {
            for (int x = 0; x < tools.length; x++) {
                createTool(tools[x]);
            }
        } else {
            System.out.println("ERROR: No Armor Material Found for " + this.toString());
        }
        return this;
    }

    public EquipmentMaterial createTools(boolean axe, boolean sword, boolean shovel, boolean hoe, boolean pickaxe, boolean tool) {
        if (this.hasToolMaterial()) {
            if (axe) createTool(ToolType.AXE);
            if (sword) createTool(ToolType.SWORD);
            if (shovel) createTool(ToolType.SHOVEL);
            if (hoe) createTool(ToolType.HOE);
            if (pickaxe) createTool(ToolType.PICKAXE);
            if (tool) createTool(ToolType.TOOL);
        } else {
            System.out.println("ERROR: No Tool Material Found for " + this.toString());
        }
        return this;
    }
    public enum ToolType {
        AXE,
        SWORD,
        SHOVEL,
        HOE,
        PICKAXE,
        TOOL
    }
    public EquipmentMaterial createTool(ToolType toolType) {
        if (this.hasToolMaterial()) {
            switch (toolType) {
                case AXE:
                    this.getToolMaterial().createAxe(getModid());
                    break;
                case SWORD:
                    this.getToolMaterial().createSword(getModid());
                    break;
                case SHOVEL:
                    this.getToolMaterial().createShovel(getModid());
                    break;
                case HOE:
                    this.getToolMaterial().createHoe(getModid());
                    break;
                case PICKAXE:
                    this.getToolMaterial().createPickaxe(getModid());
                    break;
                case TOOL:
                    this.getToolMaterial().createTool(getModid());
                    break;
            }
        } else {
            System.out.println("ERROR: No Tool Material Found for " + this.toString());
        }
        return this;
    }

}
