package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DYNAMIC_ENUM;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DynamicPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class DeliniumCruciblePortalTransparentModel extends DynamicModel {

    private static SpriteIdentifier transparentSprite = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/transparent"));
    private static float[] TRANSPARENT_ROTATION = new float[] {0.0F, 0.0F, 0.0F};
    private static float[] TRANSPARENT_CUBOIDS = new float[] {0, 0, -1.0F, -8.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -4.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -5.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -6.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -7.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -8.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -9.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -10.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -11.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -12.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -13.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -14.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -15.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -16.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -17.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -19.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -20.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -21.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -22.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -23.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -24.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -25.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -30.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -29.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -28.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -27.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -26.5F, -18.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, 0.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -1.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -2.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -3.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -4.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -5.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -6.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -7.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -8.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -9.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -10.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -11.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -12.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -13.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -14.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -15.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -16.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -17.0F, 1, 1, 1, 0.0F, 0, 0, -1.0F, -18.5F, -18.0F, 1, 1, 1, 0.0F };

    public DeliniumCruciblePortalTransparentModel() {
		super(RenderLayer::getEntityTranslucent);
		ObjectList<DynamicPart[]> seeds = new ObjectArrayList<>();
		for(int i = 0; i < TRANSPARENT_CUBOIDS.length; i++) {
			seeds.add(new DynamicPart[] { 
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.X, true, -.05f, .075f, 0f, .5f, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.Y, true, -.05f, .075f, 0f, .5f, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.Z, true, -.05f, .075f, 0f, .5f, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.RED, true, -.5f, .75f, 0f, .5f, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.GREEN, false),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.BLUE, false),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.ALPHA, true, -.5f, -.75f, 0f, .5f, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
				(DynamicModelPart.EMPTY).new DynamicPart(DYNAMIC_ENUM.LIGHT, true, -5F, 5F, 0f, .15F, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, 1F),
			});
		}
        this.withParts(ObjectArrayList.wrap(new DynamicModelPart[]{generateModelPart(this, TRANSPARENT_CUBOIDS.clone(), TRANSPARENT_ROTATION.clone(), transparentSprite, seeds, layerFactory).with(false, false, false, false, 0, false, false, 0, false, true, 25, false, false, 0)})).buildUsingSeeds();
    }

}