package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

public class CustomHoeItem extends HoeItem {

    public static HashMap<Identifier, CustomHoeItem> HOES = new HashMap<>();

    private String modid;
    private String name = "";
    private Identifier identifier;
    protected CustomHoeItem(String modid, CustomToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomHoeItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomHoeItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomHoeItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomHoeItem item, String name) {
        item.name = name;
    }

    protected CustomHoeItem registerHoeItem() {
        HOES.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered HoeItem: " + this.getTranslationKey());
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

    public static CustomHoeItem generate(String modid, CustomToolMaterial customToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        CustomHoeItem dhi = new CustomHoeItem(modid, customToolMaterial, attackDamage, attackSpeed, settings);
        CustomHoeItem.setItemNameFromIngredient(dhi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, customToolMaterial.getIngredientName()));
        CustomHoeItem.setIdentifier(dhi);
        return dhi.registerHoeItem();
    }

    public static void setItemNameFromIngredient(CustomHoeItem item, String name) {
        setItemName(item, name + "_hoe"); 
    }

    public static CustomHoeItem generate(String modid, String ingredientName, Item i, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, i), (int)attackDamage, attackSpeed, settings);
    }

}
