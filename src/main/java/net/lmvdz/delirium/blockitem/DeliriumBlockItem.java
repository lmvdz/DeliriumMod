package net.lmvdz.delirium.blockitem;

import java.util.HashMap;
import com.google.common.base.CaseFormat;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * DeliriumBlockItem
 */
public class DeliriumBlockItem extends BlockItem {

    public static HashMap<Identifier, DeliriumBlockItem> BLOCK_ITEMS = new HashMap<>();

    private String name = "";
    private Identifier identifier;
    public DeliriumBlockItem(Block block, Settings settings) {
        super(block, settings.group(DeliriumMod.ITEM_GROUP));
    }

    public static void registerBlockItem(DeliriumBlockItem item) {
        setBlockItemName(item, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, item.getClass().getSimpleName()));
        setIdentifier(item);
        BLOCK_ITEMS.put(getIdentifier(item), Registry.register(Registry.ITEM, getIdentifier(item), item));
        System.out.println("Registered Block Item: " + item.getTranslationKey());
    }
    public static void setIdentifier(DeliriumBlockItem item) {
        item.identifier = new Identifier(DeliriumMod.MODID, getBlockItemName(item));
    }
    public static Identifier getIdentifier(DeliriumBlockItem item) {
        return item.identifier;
    }
    public static String getBlockItemName(DeliriumBlockItem item) {
        return item.name;
    }
    public static void setBlockItemName(DeliriumBlockItem item, String name) {
        item.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getBlockItemName(this).toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        if (itemStack.getItem() instanceof DeliriumBlockItem) {
            return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("item." + DeliriumMod.MODID + "." + this.getTranslationKey()));
        } else {
            return super.getName(itemStack);
        }
    }
}