package net.lmvdz.delirium.font;

import com.google.common.collect.Lists;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.api.event.TextRendererDrawLayerClass5348Callback;
import net.lmvdz.delirium.api.event.TextRendererDrawLayerStringCallback;
import net.lmvdz.delirium.client.mixin.TextRendererAccessor;
import net.minecraft.class_5348;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public class CustomFontTextRenderer implements TextRendererDrawLayerStringCallback, TextRendererDrawLayerClass5348Callback {
    private Identifier fontId;


    public static final HashMap<Identifier, CustomFontTextRenderer> CustomFontTextRenderers = new HashMap<>();
    public static final CustomFontTextRenderer DefaultFontTextRenderer = of(Style.DEFAULT_FONT_ID, false);
    public static Identifier activeFont = Style.DEFAULT_FONT_ID;


    CustomFontTextRenderer(Identifier fontId) {
        this.fontId = fontId;
        TextRendererDrawLayerStringCallback.EVENT.register(this);
        TextRendererDrawLayerClass5348Callback.EVENT.register(this);
    }


    public static void setActiveFont(Identifier fontId) {
        CustomFontTextRenderers.putIfAbsent(fontId, new CustomFontTextRenderer(fontId));
        activeFont = fontId;
    }


    public static CustomFontTextRenderer of(Identifier fontId, boolean setActive) {
        CustomFontTextRenderers.putIfAbsent(fontId, new CustomFontTextRenderer(fontId));
        if (setActive) {
            activeFont = fontId;
        }
        return CustomFontTextRenderers.get(fontId);
    }

    @Environment(EnvType.CLIENT)
    private float drawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light) {
        CustomShadowDrawer shadowDrawer = new CustomShadowDrawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
        TextVisitFactory.visitFormatted((String)text, Style.EMPTY.withFont(this.fontId), shadowDrawer);
        return shadowDrawer.drawLayer(this.fontId, underlineColor, x);
    }

    @Environment(EnvType.CLIENT)
    private float drawLayer(class_5348 arg, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light) {
        CustomShadowDrawer shadowDrawer = new CustomShadowDrawer(vertexConsumerProvider, x, y, color, shadow, matrix, seeThrough, light);
        TextVisitFactory.visitFormatted((class_5348)arg, Style.EMPTY.withFont(this.fontId), shadowDrawer);
        return shadowDrawer.drawLayer(this.fontId, underlineColor, x);
    }


    // string callback
    @Override
    @Environment(EnvType.CLIENT)
    public void onDrawLayer(String text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, BiConsumer<Boolean, Float> cancelAndReturn) {
        if (activeFont == this.fontId) {
            cancelAndReturn.accept(true, this.drawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light));
        }
    }

    // class 5348 callback
    @Override
    @Environment(EnvType.CLIENT)
    public void onDrawLayer(class_5348 text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int underlineColor, int light, BiConsumer<Boolean, Float> cancelAndReturn) {
        if (activeFont == this.fontId) {
            cancelAndReturn.accept(true, this.drawLayer(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough, underlineColor, light));
        }
    }


    public class CustomShadowDrawer implements TextVisitFactory.CharacterVisitor {

        final VertexConsumerProvider vertexConsumers;
        private final boolean shadow;
        private final float brightnessMultiplier;
        private final float red;
        private final float green;
        private final float blue;
        private final float alpha;
        private final Matrix4f matrix;
        private final boolean seeThrough;
        private final int light;
        private float x;
        private float y;
        private List<GlyphRenderer.Rectangle> rectangles;

        private void addRectangle(GlyphRenderer.Rectangle rectangle) {
            if (this.rectangles == null) {
                this.rectangles = Lists.newArrayList();
            }

            this.rectangles.add(rectangle);
        }

        public CustomShadowDrawer(VertexConsumerProvider vertexConsumers, float x, float y, int color, boolean shadow, Matrix4f matrix, boolean seeThrough, int light) {
            this.vertexConsumers = vertexConsumers;
            this.x = x;
            this.y = y;
            this.shadow = shadow;
            this.brightnessMultiplier = shadow ? 0.25F : 1.0F;
            this.red = (float)(color >> 16 & 255) / 255.0F * this.brightnessMultiplier;
            this.green = (float)(color >> 8 & 255) / 255.0F * this.brightnessMultiplier;
            this.blue = (float)(color & 255) / 255.0F * this.brightnessMultiplier;
            this.alpha = (float)(color >> 24 & 255) / 255.0F;
            this.matrix = matrix;
            this.seeThrough = seeThrough;
            this.light = light;
        }

        @Environment(EnvType.CLIENT)
        public boolean onChar(int i, Style style, int j) {
            MinecraftClient client = MinecraftClient.getInstance();
            FontStorage fontStorage = ((TextRendererAccessor)client.textRenderer).callGetFontStorage(style.getFont());
            Glyph glyph = fontStorage.getGlyph(j);
            GlyphRenderer glyphRenderer = style.isObfuscated() && j != 32 ? fontStorage.getObfuscatedGlyphRenderer(glyph) : fontStorage.getGlyphRenderer(j);
            boolean bl = style.isBold();
            float f = this.alpha;
            TextColor textColor = style.getColor();
            float m;
            float n;
            float o;
            if (textColor != null) {
                int k = textColor.getRgb();
                m = (float)(k >> 16 & 255) / 255.0F * this.brightnessMultiplier;
                n = (float)(k >> 8 & 255) / 255.0F * this.brightnessMultiplier;
                o = (float)(k & 255) / 255.0F * this.brightnessMultiplier;
            } else {
                m = this.red;
                n = this.green;
                o = this.blue;
            }

            float s;
            float r;
            if (!(glyphRenderer instanceof EmptyGlyphRenderer)) {
                r = bl ? glyph.getBoldOffset() : 0.0F;
                s = this.shadow ? glyph.getShadowOffset() : 0.0F;
                VertexConsumer vertexConsumer = this.vertexConsumers.getBuffer(glyphRenderer.method_24045(this.seeThrough));
                ((TextRendererAccessor)client.textRenderer).callDrawGlyph(glyphRenderer, bl, style.isItalic(), r, this.x + s, this.y + s, this.matrix, vertexConsumer, m, n, o, f, this.light);
            }

            r = glyph.getAdvance(bl);
            s = this.shadow ? 1.0F : 0.0F;
            if (style.isStrikethrough()) {
                this.addRectangle(new GlyphRenderer.Rectangle(this.x + s - 1.0F, this.y + s + 4.5F, this.x + s + r, this.y + s + 4.5F - 1.0F, 0.01F, m, n, o, f));
            }

            if (style.isUnderlined()) {
                this.addRectangle(new GlyphRenderer.Rectangle(this.x + s - 1.0F, this.y + s + 9.0F, this.x + s + r, this.y + s + 9.0F - 1.0F, 0.01F, m, n, o, f));
            }

            this.x += r;
            return true;
        }

        @Environment(EnvType.CLIENT)
        public float drawLayer(Identifier fontId, int underlineColor, float x) {
            if (underlineColor != 0) {
                float f = (float)(underlineColor >> 24 & 255) / 255.0F;
                float g = (float)(underlineColor >> 16 & 255) / 255.0F;
                float h = (float)(underlineColor >> 8 & 255) / 255.0F;
                float i = (float)(underlineColor & 255) / 255.0F;
                this.addRectangle(new GlyphRenderer.Rectangle(x - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, g, h, i, f));
            }

            if (this.rectangles != null) {
                MinecraftClient client = MinecraftClient.getInstance();
                GlyphRenderer glyphRenderer = ((TextRendererAccessor)client.textRenderer).callGetFontStorage(fontId).getRectangleRenderer();
                VertexConsumer vertexConsumer = this.vertexConsumers.getBuffer(glyphRenderer.method_24045(this.seeThrough));
                Iterator var9 = this.rectangles.iterator();

                while(var9.hasNext()) {
                    GlyphRenderer.Rectangle rectangle = (GlyphRenderer.Rectangle)var9.next();
                    glyphRenderer.drawRectangle(rectangle, this.matrix, vertexConsumer, this.light);
                }
            }

            return this.x;
        }
    }
}
