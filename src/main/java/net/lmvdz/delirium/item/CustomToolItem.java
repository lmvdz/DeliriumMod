package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;


public class CustomToolItem extends ToolItem {
    public static HashMap<Identifier, CustomToolItem> TOOLS = new HashMap<>();

    private String modid;
    private String name = "";
    private Identifier identifier;

    public CustomToolItem(String modid, CustomToolMaterial material, Settings settings) {
        super(material, settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomToolItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomToolItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomToolItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomToolItem item, String name) {
        item.name = name;
    }

    public static void setItemNameFromIngredient(CustomToolItem item, String name) {
        setItemName(item, name + "_tool");
    }

    protected CustomToolItem registerToolItem() {
        Identifier id = getIdentifier(this);
        TOOLS.put(id, Registry.register(Registry.ITEM, id, this));
        System.out.println("Registered ToolItem: " + this.getTranslationKey());
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

    public static CustomToolItem generate(String modid, String ingredientName, Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return generate(modid, new CustomToolMaterial(attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, ingredientName, i), settings);
    }

    public static CustomToolItem generate(String modid, CustomToolMaterial itemToolMaterial, Item.Settings settings) {
        CustomToolItem dti = new CustomToolItem(modid, itemToolMaterial, settings);
        CustomToolItem.setItemNameFromIngredient(dti, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getIngredientName()));
        CustomToolItem.setIdentifier(dti);
        return dti.registerToolItem();
    }
    
}