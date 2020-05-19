package net.lmvdz.delirium.item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DeliriumPickaxeItem extends PickaxeItem {

    
    private String name = "";
    private Identifier identifier;

    protected DeliriumPickaxeItem(ItemToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.group(DeliriumMod.ITEM_GROUP));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(DeliriumPickaxeItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }

    public static Identifier getIdentifier(DeliriumPickaxeItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumPickaxeItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumPickaxeItem item, String name) {
        item.name = name;
    }

    protected DeliriumPickaxeItem registerPickaxeItem() {
        DeliriumMod.PICKAXES.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered PickaxeItem: " + this.getTranslationKey());
        return this;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }

    @Override
    public String getTranslationKey() {
        return getItemName(this).toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
    }


    public static DeliriumPickaxeItem makeOutOf(ItemToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        DeliriumPickaxeItem dpi = new DeliriumPickaxeItem(itemToolMaterial, attackDamage, attackSpeed, settings);
        DeliriumPickaxeItem.setItemNameFromIngredient(dpi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumPickaxeItem.setIdentifier(dpi);
        return dpi.registerPickaxeItem();
    }

    public static void setItemNameFromIngredient(DeliriumPickaxeItem item, String name) {
        setItemName(item, name + "_pickaxe");
    }

    public static DeliriumPickaxeItem makeOutOf(Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return makeOutOf(new ItemToolMaterial(settings, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, i), attackDamage, attackSpeed, settings);
    }

}
