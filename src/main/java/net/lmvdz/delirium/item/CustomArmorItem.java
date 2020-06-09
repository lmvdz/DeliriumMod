package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class CustomArmorItem extends ArmorItem {
    private String modid;
    private String name = "";
    private Identifier identifier;
    public static HashMap<Identifier, CustomArmorItem> ARMOR_ITEMS = new HashMap<>();


    public CustomArmorItem(String modid, ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomArmorItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomArmorItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomArmorItem item) {
        return item.name;
    }

    public static void setItemName(CustomArmorItem item, String name) {
        item.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getItemName(this).toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new TranslatableText("item." + modid + "." + this.getTranslationKey());
    }

    protected CustomArmorItem registerArmorItem() {
        ARMOR_ITEMS.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered Armor Item: " + this.getTranslationKey());
        return this;
    }

    public static void setItemNameFromIngredient(CustomArmorItem item, String name) {
        setItemName(item, name + "_" + item.slot.getName() + "_armor");
    }

    public static CustomArmorItem generate(String modid, CustomArmorMaterial customArmorMaterial, EquipmentSlot slot, Item.Settings settings) {
        CustomArmorItem dti = new CustomArmorItem(modid, customArmorMaterial, slot, settings);
        CustomArmorItem.setItemNameFromIngredient(dti, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, customArmorMaterial.getIngredientName()));
        CustomArmorItem.setIdentifier(dti);
        return dti.registerArmorItem();
    }

}
