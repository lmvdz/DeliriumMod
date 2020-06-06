package net.lmvdz.delirium.item;

import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DeliriumHoeItem extends HoeItem {

    

    private String name = "";
    private Identifier identifier;
    protected DeliriumHoeItem(ItemToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.group(DeliriumMod.ITEM_GROUP));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }

    public static void setIdentifier(DeliriumHoeItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }

    public static Identifier getIdentifier(DeliriumHoeItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumHoeItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumHoeItem item, String name) {
        item.name = name;
    }

    protected DeliriumHoeItem registerHoeItem() {
        DeliriumMod.HOES.putIfAbsent(getIdentifier(this), Registry.register(Registry.ITEM, getIdentifier(this), this));
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
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
    }

    public static DeliriumHoeItem makeOutOf(ItemToolMaterial itemToolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        DeliriumHoeItem dhi = new DeliriumHoeItem(itemToolMaterial, attackDamage, attackSpeed, settings);
        DeliriumHoeItem.setItemNameFromIngredient(dhi, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumHoeItem.setIdentifier(dhi);
        return dhi.registerHoeItem();
    }

    public static void setItemNameFromIngredient(DeliriumHoeItem item, String name) {
        setItemName(item, name + "_hoe"); 
    }

    public static DeliriumHoeItem makeOutOf(Item i, float attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return makeOutOf(new ItemToolMaterial(settings, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, i), (int)attackDamage, attackSpeed, settings);
    }

}
