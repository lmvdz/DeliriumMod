package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * DeliriumSwordItem
 */
public class DeliriumSwordItem extends SwordItem {
    
    private String name = "";
    private Identifier identifier;


    public DeliriumSwordItem(ItemToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.group(DeliriumMod.ITEM_GROUP));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(DeliriumSwordItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }
    public static Identifier getIdentifier(DeliriumSwordItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumSwordItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumSwordItem item, String name) {
        item.name = name;
    }

    protected DeliriumSwordItem registerSwordItem() {
        DeliriumMod.SWORDS.put(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
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
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
    }

    public static DeliriumSwordItem makeOutOf(ItemToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        DeliriumSwordItem dsi = new DeliriumSwordItem(itemToolMaterial, attackDamage, attackSpeed, settings);
        DeliriumSwordItem.setItemNameFromIngredient(dsi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumSwordItem.setIdentifier(dsi);
        return dsi.registerSwordItem();
    }

    public static void setItemNameFromIngredient(DeliriumSwordItem item, String name) {
        setItemName(item, name + "_sword");
    }

    public static DeliriumSwordItem makeOutOf(Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return makeOutOf(new ItemToolMaterial(settings, attackDamage,  durability, enchantability, miningLevel, miningSpeed,attackSpeed, i), attackDamage, attackSpeed, settings);
    }
}