package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * DeliriumSwordItem
 */
public class CustomSwordItem extends SwordItem {
    public static HashMap<Identifier, CustomSwordItem> SWORDS = new HashMap<>();

    private String modid;
    private String name = "";
    private Identifier identifier;


    public CustomSwordItem(String modid, CustomToolMaterial material, int attackDamage, float attackSpeed,
                           Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomSwordItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomSwordItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomSwordItem item) {
        return item.name;
    }

    public static void setItemName(CustomSwordItem item, String name) {
        item.name = name;
    }

    protected CustomSwordItem registerSwordItem() {
        SWORDS.put(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered SwordItem: " + this.getTranslationKey());
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

    public static CustomSwordItem generate(String modid, CustomToolMaterial itemToolMaterial, int attackDamage,
                                                       float attackSpeed, Item.Settings settings) {
        CustomSwordItem dsi =
                new CustomSwordItem(modid, itemToolMaterial, attackDamage, attackSpeed, settings);
        CustomSwordItem.setItemNameFromIngredient(dsi,
                CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                        itemToolMaterial.getIngredientName()));
        CustomSwordItem.setIdentifier(dsi);
        return dsi.registerSwordItem();
    }

    public static void setItemNameFromIngredient(CustomSwordItem item, String name) {
        setItemName(item, name + "_sword");
    }

    public static CustomSwordItem generate(String modid, String ingredientName, Item ingredient, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, ingredient), attackDamage, attackSpeed, settings);
    }
}
