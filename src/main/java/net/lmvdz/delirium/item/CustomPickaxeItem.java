package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

public class CustomPickaxeItem extends PickaxeItem {
    public static HashMap<Identifier, CustomPickaxeItem> PICKAXES = new HashMap<>();

    private String modid;
    private String name = "";
    private Identifier identifier;

    protected CustomPickaxeItem(String modid, CustomToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomPickaxeItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomPickaxeItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomPickaxeItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomPickaxeItem item, String name) {
        item.name = name;
    }

    protected CustomPickaxeItem registerPickaxeItem() {
        PICKAXES.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
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
        return new TranslatableText("item." + modid + "." + this.getTranslationKey());
    }


    public static CustomPickaxeItem generate(String modid, CustomToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        CustomPickaxeItem dpi = new CustomPickaxeItem(modid, itemToolMaterial, attackDamage, attackSpeed, settings);
        CustomPickaxeItem.setItemNameFromIngredient(dpi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getIngredientName()));
        CustomPickaxeItem.setIdentifier(dpi);
        return dpi.registerPickaxeItem();
    }

    public static void setItemNameFromIngredient(CustomPickaxeItem item, String name) {
        setItemName(item, name + "_pickaxe");
    }

    public static CustomPickaxeItem generate(String modid, String ingredientName, Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, i), attackDamage, attackSpeed, settings);
    }

}
