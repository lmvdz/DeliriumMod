package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

public class CustomAxeItem extends AxeItem {
    public static HashMap<Identifier, CustomAxeItem> AXES = new HashMap<>();
    private String modid;
    private String name = "";
    private Identifier identifier;

    protected CustomAxeItem(String modid, CustomToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomAxeItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }
    public static Identifier getIdentifier(CustomAxeItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomAxeItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomAxeItem item, String name) {
        item.name = name;
    }

    protected CustomAxeItem registerAxeItem() {
        AXES.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered AxeItem: " + this.getTranslationKey());
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

    public static CustomAxeItem generate(String modid, CustomToolMaterial itemToolMaterial, float attackDamage, float attackSpeed, Item.Settings settings) {
        CustomAxeItem dai = new CustomAxeItem(modid, itemToolMaterial, (int)attackDamage, attackSpeed, settings);
        CustomAxeItem.setItemNameFromIngredient(dai, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getIngredientName()));
        CustomAxeItem.setIdentifier(dai);
        return dai.registerAxeItem();
    }

    public static void setItemNameFromIngredient(CustomAxeItem item, String name) {
        setItemName(item, name + "_axe");
    }

    public static CustomAxeItem generate(String modid, String ingredientName, Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, i), attackDamage, attackSpeed, settings);
    }

}
