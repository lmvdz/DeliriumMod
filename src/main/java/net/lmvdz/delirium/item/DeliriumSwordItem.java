package net.lmvdz.delirium.item;

import net.minecraft.item.SwordItem;
import java.util.HashMap;
import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
    public static HashMap<Identifier, DeliriumSwordItem> SWORDS = new HashMap<>();
    private String name = "";
    private Identifier identifier;


    public DeliriumSwordItem(DeliriumItemToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.group(DeliriumMod.ITEM_GROUP));
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

    protected static void registerSwordItem(DeliriumSwordItem item) {
        setItemName(item, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, item.getClass().getSimpleName()));
        setIdentifier(item);
        SWORDS.put(getIdentifier(item), Registry.register(Registry.ITEM, getIdentifier(item), item));
        System.out.println("Registered SwordItem: " + item.getTranslationKey());
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
}