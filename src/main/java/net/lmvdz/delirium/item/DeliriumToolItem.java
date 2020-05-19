package net.lmvdz.delirium.item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * DeliriumToolItem
 */
public class DeliriumToolItem extends ToolItem {

    

    private String name = "";
    private Identifier identifier;

    public DeliriumToolItem(ItemToolMaterial material, Settings settings) {
        super(material, settings.group(DeliriumMod.ITEM_GROUP));
        setItemName(this, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getClass().getSimpleName()));
        setIdentifier(this);
    }
    public static void setIdentifier(DeliriumToolItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, item.getTranslationKey());
    }
    public static Identifier getIdentifier(DeliriumToolItem item) {
        return item.identifier;
    }

    public static String getItemName(DeliriumToolItem item) {
        return item.name;
    }
    
    public static void setItemName(DeliriumToolItem item, String name) {
        item.name = name;
    }

    protected DeliriumToolItem registerToolItem() {
        Identifier id = getIdentifier(this);
        DeliriumMod.TOOLS.put(id, Registry.register(Registry.ITEM, id, this));
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
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
    }

    public static DeliriumToolItem makeOutOf(ItemToolMaterial itemToolMaterial, Item.Settings settings) {
        DeliriumToolItem dti = new DeliriumToolItem(itemToolMaterial, settings);
        DeliriumToolItem.setItemNameFromIngredient(dti, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, itemToolMaterial.getRepairIngredientAsItem().getClass().getSimpleName()));
        DeliriumToolItem.setIdentifier(dti);
        return dti.registerToolItem();
    }

    public static void setItemNameFromIngredient(DeliriumToolItem item, String name) {
        setItemName(item, name + "_tool");
    }

    public static DeliriumToolItem makeOutOf(Item i, int attackDamage, int durability, int enchantability, int miningLevel, float miningSpeed, float attackSpeed, Item.Settings settings) {
        return makeOutOf(new ItemToolMaterial(settings, attackDamage, durability, enchantability, miningLevel, miningSpeed, attackSpeed, i), settings);
    }
    
}