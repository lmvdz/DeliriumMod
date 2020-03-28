package net.lmvdz.delirium.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.IntUnaryOperator;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public final class FormattingEngine {

    /**
     * Valid color format code used by Minecraft.
     * 
     * §0 §{BLACK}
     * 
     * §1 §{DARK_BLUE}
     * 
     * §2 §{DARK_GREEN}
     * 
     * §3 §{DARK_CYAN}
     * 
     * §3 §{DARK_AQUA}
     * 
     * §4 §{DARK_RED}
     * 
     * §5 §{DARK_PURPLE}
     * 
     * §6 §{GOLD}
     *
     * §7 §{GREY}
     * 
     * §7 §{GRAY}
     * 
     * §8 §{DARK_GREY}
     * 
     * §8 §{DARK_GRAY}
     * 
     * §9 §{BLUE}
     * 
     * §a §{GREEN}
     * 
     * §b §{CYAN}
     * 
     * §b §{AQUA}
     * 
     * §c §{RED}
     * 
     * §d §{LIGHT_PURPLE}
     * 
     * §e §{YELLOW}
     * 
     * §f §{WHITE}
     * 
     * 
     */
    private static final String COLOR_CODE_STRING = "0123345677889abbcdef";

    private static final String[] COLOR_CODE_LIST = new String[] { 
        "BLACK", // 0
        "DARK_BLUE", // 1
        "DARK_GREEN", // 2
        "DARK_CYAN",  // 3
        "DARK_AQUA", // 3
        "DARK_RED", // 4
        "DARK_PURPLE", // 5
        "GOLD", // 6
        "GREY", // 7
        "GRAY", // 7
        "DARK_GREY", // 8 
        "DARK_GRAY", // 8
        "BLUE",  // 9
        "GREEN", // a
        "CYAN",  // b
        "AQUA",  // b
        "RED",  // c
        "LIGHT_PURPLE", // d
        "YELLOW", // e
        "WHITE" // f
    };

    public static enum COLOR_CODE {
        BLACK, 
        DARK_BLUE, 
        DARK_GREEN, 
        DARK_CYAN, 
        DARK_AQUA, 
        DARK_RED, 
        DARK_PURPLE,
        GOLD, 
        GREY, 
        GRAY, 
        DARK_GREY, 
        DARK_GRAY, 
        BLUE, 
        GREEN, 
        CYAN, 
        AQUA, 
        RED,
        LIGHT_PURPLE, 
        YELLOW, 
        WHITE
    }

    /**
     * Valid special format code used by Minecraft
     * 
     * §k §{OBFUSCATED}
     * 
     * §l §{BOLD}
     * 
     * §m §{STRIKETHROUGH}
     * 
     * §n §{UNDERLINE}
     * 
     * §o §{ITALIC}
     * 
     * §r §{RESET}
     */
    private static final String FORMATTING_CODE_STRING = "klmnor";

    private static final String[] FORMATTING_CODE_LIST = new String[] { "OBFUSCATED", "BOLD", "STRIKETHROUGH",
            "UNDERLINE", "ITALIC", "RESET" };

    public static enum FORMATTING_CODE {
        OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET
    }

    public static List<String> wrapStringToWidth(final String str, final int wrapWidth,
            final IntUnaryOperator charWidthGetter, final Locale currentLocale, final COLOR_CODE defaultColor) {
        return wrapStringToWidth(str, wrapWidth, charWidthGetter, currentLocale,
                COLOR_CODE_STRING.charAt(defaultColor.ordinal()));
    }

    public static String replaceColorCodeEnumInString(String str) {
        for (String color_code : COLOR_CODE_LIST) {
            str = str.replace("{" + color_code + "}",
                    new String("" + COLOR_CODE_STRING.charAt(COLOR_CODE.valueOf(color_code).ordinal())));
        }
        for (String formatting_code : FORMATTING_CODE_LIST) {
            str = str.replace("{" + formatting_code + "}",
                    new String("" + FORMATTING_CODE_STRING.charAt(FORMATTING_CODE.valueOf(formatting_code).ordinal())));
        }
        return str;
    }


    public static LiteralText replaceColorCodeInTranslatableText(TranslatableText text) {
        return new LiteralText(replaceColorCodeEnumInString(text.asString()));
    }

    /**
     * Split given {@link String} ({@code str}) into a list of strings, each string
     * has length that does not exceed the length given by {@code wrapWidth}, in
     * terms of character width, which is defined by {@code charWidthGetter}. The
     * splitting result will respects line breaking rules of the given
     * {@link Locale} ({@code currentLocale}).
     *
     * @implNote {@link BreakIterator} provides the per-locale line breaking rules
     *           support.
     *
     * @param str             String
     * @param wrapWidth       maximum length of one line, based on character width
     * @param charWidthGetter an int (char) to int function that returns width of
     *                        character
     * @param currentLocale   target locale
     *
     * @return A list of String, each occupies one line
     */
    public static List<String> wrapStringToWidth(final String str, final int wrapWidth,
            final IntUnaryOperator charWidthGetter, final Locale currentLocale, final char defaultColor) {
        BreakIterator lineBreakEngine = BreakIterator.getLineInstance(currentLocale);
        String strColorCodeEnumReplaced = replaceColorCodeEnumInString(str);
        lineBreakEngine.setText(strColorCodeEnumReplaced);
        ArrayList<String> lines = new ArrayList<>(8);
        String cachedFormat = "";
        char color = defaultColor, format = 'r'; // 0 is format code for black-colored-text; r is format code to reset
                                                 // format to
        // default
        int start = 0; // Position of first character of each line, in terms of source string, i.e. 1st
                       // param of substring call
        int width = 0; // Width tracker
        boolean boldMode = false; // Bold font occupies extra width of one unit. Set up a tracker to track it
        int rememberIndex = -1;

        for (int index = 0; index < strColorCodeEnumReplaced.length(); index++) {
            if (rememberIndex > index) {
                continue;
            } else {
                rememberIndex = -1;
            }
            char c = strColorCodeEnumReplaced.charAt(index);
            if (c == '\n') { // Unconditionally cut string when there is new line
                lines.add(cachedFormat + strColorCodeEnumReplaced.substring(start, index));
                // Set start to appropriate position before next String::substring call
                start = index + 1;
                width = 0; // Clear width counter
                // And now, cache the current format for next line
                if (format != 'r') {
                    cachedFormat = new String(new char[] { '\u00A7', color, '\u00A7', format });
                } else {
                    cachedFormat = new String(new char[] { '\u00A7', color });
                }
                continue;
            } else if (c == '\u00A7') { // a.k.a. '§'. Used by Minecraft to denote special format, don't count it
                index++;
                Character f = strColorCodeEnumReplaced.charAt(index);
                if (f == 'r') {
                    color = defaultColor;
                    format = 'r';
                } else if (FORMATTING_CODE_STRING.indexOf(f) != -1) {
                    format = f;
                    boldMode = f == 'l';
                } else if (COLOR_CODE_STRING.indexOf(f) != -1) {
                    color = f;
                    format = 'r'; // Reset format when new color code appears
                    boldMode = false; // Reset special format anyway, so we turn bold mode off
                } /*
                   * else { width += charWidthGetter.applyAsInt('\u00A7'); width +=
                   * charWidthGetter.applyAsInt(str.charAt(index)); }
                   */ // Vanilla seems to ignore invalid format code. We follow vanilla's logic.
                continue;
            } else {
                // Regular content, add its width to the tracker
                width += charWidthGetter.applyAsInt(c);
                if (boldMode) {
                    width++; // If we are bold font, occupy one more unit
                }
            }

            if (width > wrapWidth) {
                int end = lineBreakEngine.preceding(index);
                if (lineBreakEngine.isBoundary(index)) {
                    // Greedy approach: try to include as many characters as possible in one line,
                    // while not violating the rules set by BreakIterator
                    end = Math.max(end, index);
                }
                String result;
                if (end <= start) {
                    // If the closest valid line break is before the starting point,
                    // we just take the line as it is, in order to avoid infinite loop.
                    result = cachedFormat + strColorCodeEnumReplaced.substring(start, index);
                    start = index;
                } else {
                    // If the closest valid line break is after the starting point,
                    // we will insert line break there.
                    result = cachedFormat + strColorCodeEnumReplaced.substring(start, end);
                    start = end; // substring call excludes the char at position of `end', we need to track it
                    index = start;
                }
                // System.out.println(result);
                lines.add(result);
                index--; // Shift 1 left, so that we don't forget to count any character's width.
                width = 0; // Reset width tracker
                // And now, cache the current format for next line
                if (format != 'r') {
                    cachedFormat = new String(new char[] { '\u00A7', color, '\u00A7', format });
                } else {
                    cachedFormat = new String(new char[] { '\u00A7', color });
                }
            }
        }

        // Add the last piece, if exists
        String lastPiece = strColorCodeEnumReplaced.substring(start);
        if (!lastPiece.isEmpty()) {
            lines.add(cachedFormat + strColorCodeEnumReplaced.substring(start));
        }

        lines.trimToSize(); // Consider omit this call?
        return lines;
    }
}
