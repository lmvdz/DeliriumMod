package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * Item
 */
public class CustomItem extends Item {
    private String name = "";
    private String modid;
    private boolean isDamageable = false;
    private Identifier identifier;
    public static HashMap<Identifier, CustomItem> ITEMS = new HashMap<>();

    public CustomItem(String modid, Item.Settings settings) {
        super(settings);
        this.modid = modid;
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(CustomItem item) {
        item.identifier = new Identifier(item.modid, item.getTranslationKey());
    }

    public static Identifier getIdentifier(CustomItem item) {
        return item.identifier;
    }

    public static String getItemName(CustomItem item) {
        return item.name;
    }
    
    public static void setItemName(CustomItem item, String name) {
        item.name = name;
    }

    protected CustomItem registerItem() {
        ITEMS.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
        System.out.println("Registered Item: " + this.getTranslationKey());
        return this;
    }

    public String getModid() {return modid;}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        return super.use(world, playerEntity, hand);
    }

    @Override
    public String getTranslationKey() {
        return getItemName(this).toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return new TranslatableText("item." + modid + "." + this.getTranslationKey());
    }

    @Override
    public boolean isDamageable() {
        return this.isDamageable;
    }

    public static Ingredient asIngredient(Item item) {
        return Ingredient.ofItems(item);
    }


    public static CustomItem createRegisterReturn(String modid, Item.Settings settings) {
        CustomItem item = new CustomItem(modid, settings);
        return item.registerItem();
    }
}