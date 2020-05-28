package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.lmvdz.delirium.shader.ShaderRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DeliniumCrucibleLavaModel extends DynamicModel {

	public static final SpriteIdentifier sprite =
			new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX,
					new Identifier(DeliriumMod.MODID, "block/delinium_crucible_lava"));
	// private static final int[] u = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2,
	// 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5,
	// 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8,
	// 9, 10, 11};
	// private static final int[] v = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2,
	// 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6,
	// 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
	// 10, 10};
	// private static final float[] x = new float[] {10.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F,
	// 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 7.5F, 8.5F,
	// 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F,
	// 3.5F, 3.5F, 3.5F, 3.5F, 5.5F, 5.5F, 5.5F, 5.5F, 6.5F, 8.5F, 8.5F, 6.5F, 6.5F, 6.5F, 8.5F,
	// 8.5F, 9.5F, 9.5F, 9.5F, 9.5F, 7.5F, 6.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 10.5F,
	// 4.5F, 4.5F, 3.5F, 3.5F, 3.5F, 3.5F, 4.5F, 4.5F, 10.5F, 10.5F, 11.5F, 11.5F};
	// private static final float[] y = new float[] {6.0F, 6.0F, 4.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F,
	// 6.0F, 6.0F, 6.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 6.0F, 6.0F,
	// 5.0F, 6.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 6.0F, 6.0F, 5.0F,
	// 6.0F, 1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F,
	// -1.0F, -1.0F, 1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, 4.0F,
	// 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F};
	// private static final float[] z = new float[] {-12.5F, -11.5F, -11.5F, -9.5F, -7.5F, -8.5F,
	// -8.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F,
	// -4.5F, -4.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -7.5F,
	// -7.5F, -8.5F, -9.5F, -9.5F, -9.5F, -8.5F, -8.5F, -7.5F, -7.5F, -9.5F, -9.5F, -7.5F, -10.5F,
	// -10.5F, -10.5F, -10.5F, -6.5F, -6.5F, -6.5F, -6.5F, -7.5F, -7.5F, -9.5F, -9.5F, -9.5F, -9.5F,
	// -8.5F, -7.5F, -7.5F, -7.5F, -8.5F, -9.5F, -8.5F, -12.5F, -12.5F, -12.5F, -11.5F, -11.5F,
	// -5.5F, -5.5F, -4.5F, -4.5F, -4.5F, -4.5F, -5.5F, -5.5F};
	// private static final int[] sizeX = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	// private static final int[] sizeY = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	// private static final int[] sizeZ = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	// 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	// private static final float[] extra = new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	// 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	// 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	// 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	// 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	// 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	private static final float[] rotation = new float[] {-8.0F, 16.0F, 8.0F};

	private static final float[] cuboids = new float[] {0, 0, 10.5F, 6.0F, -12.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 0, 0, 10.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 0, 11.5F, 6.0F, -11.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 1, 0, 11.5F, 2.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 0, 11.5F,
			4.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 0, 11.5F, 0.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			3, 0, 11.5F, 4.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 0, 11.5F, 0.0F, -9.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 4, 0, 11.5F, 4.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 0, 11.5F, 0.0F, -7.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 5, 0, 11.5F, 4.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 0, 11.5F,
			0.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 0, 11.5F, 5.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			6, 0, 11.5F, 1.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 0, 11.5F, 5.0F, -9.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 7, 0, 11.5F, 1.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 0, 11.5F, 6.0F, -9.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 8, 0, 11.5F, 2.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 0, 11.5F,
			6.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 0, 11.5F, 2.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			10, 0, 11.5F, 6.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 0, 11.5F, 2.0F, -7.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 11, 0, 11.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 0, 11.5F, 1.0F,
			-7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 0, 8.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 0,
			8.5F, 1.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 2, 8.5F, 4.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 0, 2, 8.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 2, 7.5F, 4.0F, -4.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 1, 2, 7.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 2, 6.5F, 4.0F,
			-4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 2, 6.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 2,
			6.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 2, 6.5F, 1.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 4, 2, 7.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 2, 7.5F, 1.0F, -4.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 5, 2, 7.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 2, 7.5F, 2.0F,
			-4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 2, 8.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 2,
			8.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 2, 6.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 7, 2, 6.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 2, 6.5F, 6.0F, -12.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 8, 2, 6.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 2, 7.5F, 6.0F,
			-12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 2, 7.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 2,
			8.5F, 5.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 2, 8.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 10, 2, 8.5F, 6.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 2, 8.5F, 2.0F, -12.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 12, 2, 8.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 2, 8.5F,
			0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 4, 7.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			0, 4, 7.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 4, 6.5F, 4.0F, -12.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 1, 4, 6.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 4, 6.5F, 5.0F, -12.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 2, 4, 6.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 4, 7.5F,
			5.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 4, 7.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			4, 4, 3.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 4, 3.5F, 1.0F, -7.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 5, 4, 3.5F, 4.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 4, 3.5F, 0.0F, -7.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 6, 4, 3.5F, 4.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 4, 3.5F,
			0.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 4, 3.5F, 4.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7,
			4, 3.5F, 0.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 4, 3.5F, 5.0F, -9.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 8, 4, 3.5F, 1.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 4, 3.5F, 6.0F, -9.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 9, 4, 3.5F, 2.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 4, 3.5F, 6.0F,
			-8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 4, 3.5F, 2.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 4,
			3.5F, 5.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 4, 3.5F, 1.0F, -8.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 12, 4, 3.5F, 6.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 4, 3.5F, 2.0F, -7.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 12, 8, 10.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 8, 10.5F,
			0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 10, 4.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			0, 10, 4.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 10, 4.5F, 6.0F, -12.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 1, 10, 4.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 10, 3.5F, 6.0F,
			-11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 10, 3.5F, 2.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3,
			10, 3.5F, 4.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 10, 3.5F, 0.0F, -11.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 4, 10, 3.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 10, 3.5F, 0.0F, -5.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 5, 10, 3.5F, 6.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 10, 3.5F,
			2.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 10, 4.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			6, 10, 4.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 10, 4.5F, 4.0F, -4.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 7, 10, 4.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 10, 10.5F, 4.0F, -4.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 8, 10, 10.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 10, 10.5F,
			6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 10, 10.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			10, 10, 11.5F, 6.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 10, 11.5F, 2.0F, -5.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 11, 10, 11.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 10, 11.5F,
			0.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F};

	public DeliniumCrucibleLavaModel() {
		super((Identifier id) -> ShaderRenderLayer.computeShaderRenderLayerIfAbsent(
				RenderLayer.getEntityTranslucent(id), "delirium:dclm:illusion",
				ShaderRenderLayer.ExampleShaderRenderLayer.shaderTarget));
		this.withParts(ObjectArrayList.wrap(new DynamicModelPart[] {
				DynamicModelPart.generateModelPart(this, cuboids.clone(), rotation.clone(), sprite,
						DynamicModelPart.defaultSeeds(cuboids.length / 9), layerFactory)}));
	}

}
