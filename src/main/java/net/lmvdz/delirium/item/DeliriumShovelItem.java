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
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * DeliriumShovelItem
 */
public class DeliriumShovelItem extends ShovelItem {
    
    private String name = "";
    private Identifier identifier;
    public DeliriumShovelItem(ToolMaterial material, float attackDamage, float attackSpeed,
            Settings settings) {
        super(material, attackDamage, attackSpeed, settings.group(DeliriumMod.ITEM_GROUP));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }
    public static void setIdentifier(DeliriumShovelItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }

    public static Identifier getIdentifier(DeliriumShovelItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumShovelItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumShovelItem item, String name) {
        item.name = name;
    }

    protected DeliriumShovelItem registerShovelItem() {
        DeliriumMod.SHOVELS.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered ShovelItem: " + this.getTranslationKey());
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

    public static DeliriumShovelItem makeOutOf(ItemToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        DeliriumShovelItem dsi = new DeliriumShovelItem(itemToolMaterial, attackDamage, attackSpeed, settings);
        DeliriumShovelItem.setItemNameFromIngredient(dsi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumShovelItem.setIdentifier(dsi);
        return dsi.registerShovelItem();
    }

    public static void setItemNameFromIngredient(DeliriumShovelItem item, String name) {
        setItemName(item, name + "_shovel");
    }

    public static DeliriumShovelItem makeOutOf(Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return makeOutOf(new ItemToolMaterial(settings, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, i), attackDamage, attackSpeed, settings);
    }
    
}