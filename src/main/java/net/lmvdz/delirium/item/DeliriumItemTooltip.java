package net.lmvdz.delirium.item;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.client.MinecraftClient;

import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DeliriumItemTooltip {
        public static final int WRAP_WIDTH = 100;

        public static List<Text> wrapAndTranslateKeyToTextList(String key, MinecraftClient client) {
                return wrapAndTranslateKeyToTextList(key, client, new ArrayList<Text>());
        }

        public static List<Text> wrapAndTranslateKeyToTextList(String key, MinecraftClient client,
                        List<Text> tooltipTextList) {
                List<Text> wrappedLocalizedTextAsTextList = tooltipTextList;
                wrapAndTranslateKeyToStringList(key, client)
                                .forEach(string -> wrappedLocalizedTextAsTextList.add(new LiteralText(string)));
                return wrappedLocalizedTextAsTextList;
        }

        public static List<String> wrapAndTranslateKeyToStringList(String key, MinecraftClient client) {
                TranslatableText keyTranslatedText = new TranslatableText(key);
                return FormattingEngine.wrapStringToWidth(
                                FormattingEngine.replaceColorCodeEnumInString(keyTranslatedText.asString()), // string
                                WRAP_WIDTH, // wrapWidth
                                (c) -> (int) client.textRenderer.getCharWidth((char) c), // character width function
                                Locale.forLanguageTag(client.getLanguageManager().getLanguage().getCode()), // locale of
                                                                                                            // minecraft
                                                                                                            // client
                                FormattingEngine.COLOR_CODE.WHITE);// default color
                                                                   // code
        }

        public static List<Text> addDurabilityOfItemStackToTooltip(List<Text> tooltipTextList, ItemStack itemStack) {
                int durability = itemStack.getMaxDamage() - itemStack.getDamage();
                int maxDurability = itemStack.getMaxDamage();
                double percentage = (((double) durability) / ((double) maxDurability));
                // System.out.println(percentage);
                String durabitlityColor = "GREEN";
                if (percentage <= .75 && percentage > .50) {
                        durabitlityColor = "DARK_GREEN";
                } else if (percentage > .25 && percentage <= .50) {
                        durabitlityColor = "YELLOW";
                } else if (percentage < .25) {
                        durabitlityColor = "RED";
                }
                String durabitlityFormatted = "§{GRAY}Durability: §{" + durabitlityColor + "}" +((int)(percentage * 100)) + "§{RESET}%";
                tooltipTextList.add(
                                new LiteralText(FormattingEngine.replaceColorCodeEnumInString(durabitlityFormatted)));
                return tooltipTextList;
        }

}