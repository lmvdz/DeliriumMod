package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeliriumArmorItem extends ArmorItem {
    private String name = "";
    private Identifier identifier;

    public DeliriumArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(DeliriumArmorItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }

    public static Identifier getIdentifier(DeliriumArmorItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumArmorItem item) {
        return item.name;
    }

    public static void setItemName(DeliriumArmorItem item, String name) {
        item.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getItemName(this).toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
    }

    protected DeliriumArmorItem registerArmorItem() {
        DeliriumMod.ARMOR_ITEMS.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered Armor Item: " + this.getTranslationKey());
        return this;
    }

    public static void setItemNameFromIngredient(DeliriumArmorItem item, String name) {
        setItemName(item, name + "_" + item.slot.getName() + "_armor");
    }

    public static DeliriumArmorItem makeOutOf(ItemToolArmorMaterial itemArmorMaterial, EquipmentSlot slot, Item.Settings settings) {
        DeliriumArmorItem dti = new DeliriumArmorItem(itemArmorMaterial.getArmorMaterial(), slot, settings);
        DeliriumArmorItem.setItemNameFromIngredient(dti, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemArmorMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumArmorItem.setIdentifier(dti);
        return dti.registerArmorItem();
    }

}
