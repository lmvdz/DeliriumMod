package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * DeliriumShovelItem
 */
public class CustomShovelItem extends ShovelItem {
    public static HashMap<Identifier, CustomShovelItem> SHOVELS = new HashMap<>();

    private String modid;
    private String name = "";
    private Identifier identifier;
    public CustomShovelItem(String modid, ToolMaterial material, float attackDamage, float attackSpeed,
                            Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }
    public static void setIdentifier(CustomShovelItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomShovelItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomShovelItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomShovelItem item, String name) {
        item.name = name;
    }

    protected CustomShovelItem registerShovelItem() {
        SHOVELS.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
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
        return new TranslatableText("item." + modid + "." + this.getTranslationKey());
    }

    public static CustomShovelItem generate(String modid, CustomToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        CustomShovelItem dsi = new CustomShovelItem(modid, itemToolMaterial, attackDamage, attackSpeed, settings);
        CustomShovelItem.setItemNameFromIngredient(dsi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getIngredientName()));
        CustomShovelItem.setIdentifier(dsi);
        return dsi.registerShovelItem();
    }

    public static void setItemNameFromIngredient(CustomShovelItem item, String name) {
        setItemName(item, name + "_shovel");
    }

    public static CustomShovelItem generate(String modid, String ingredientName, Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, i), attackDamage, attackSpeed, settings);
    }
    
}