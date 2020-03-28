package net.lmvdz.delirium.item;

import java.util.HashMap;
import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * Item
 */
public class DeliriumItem extends Item {
    private String name = "";
    private boolean isDamageable = false;
    
    public static HashMap<Identifier, DeliriumItem> ITEMS = new HashMap<>();

    public DeliriumItem(Item.Settings settings) {
        super(settings.group(DeliriumMod.ITEM_GROUP));
    }

    public static Identifier getIdentifier(DeliriumItem item) {
        return new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }

    public static String getItemName(DeliriumItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumItem item, String name) {
        item.name = name;
    }

    protected static void registerItem(DeliriumItem item) {
        setItemName(item, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, item.getClass().getSimpleName()));
        ITEMS.put(getIdentifier(item), Registry.register(Registry.ITEM, getIdentifier(item), item));
        System.out.println("Registered Item: " + item.getTranslationKey());
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

    @Override
    public boolean isDamageable() {
        return this.isDamageable;
    }

    public Ingredient asIngredient() {
        return Ingredient.ofItems(this);
    }
}