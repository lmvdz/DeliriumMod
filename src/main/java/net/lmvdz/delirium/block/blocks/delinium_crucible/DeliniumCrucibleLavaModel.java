package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DeliniumCrucibleLavaModel extends DynamicModel {

	public static final SpriteIdentifier sprite = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/delinium_crucible_lava"));
	
	private static final int[] u = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final int[] v = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
	private static final float[] x = new float[] {10.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 7.5F, 8.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 5.5F, 5.5F, 5.5F, 5.5F, 6.5F, 8.5F, 8.5F, 6.5F, 6.5F, 6.5F, 8.5F, 8.5F, 9.5F, 9.5F, 9.5F, 9.5F, 7.5F, 6.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 10.5F, 4.5F, 4.5F, 3.5F, 3.5F, 3.5F, 3.5F, 4.5F, 4.5F, 10.5F, 10.5F, 11.5F, 11.5F};
	private static final float[] y = new float[] {6.0F, 6.0F, 4.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 6.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 6.0F, 6.0F, 5.0F, 6.0F, 1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F};
	private static final float[] z = new float[] {-12.5F, -11.5F, -11.5F, -9.5F, -7.5F, -8.5F, -8.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -7.5F, -7.5F, -8.5F, -9.5F, -9.5F, -9.5F, -8.5F, -8.5F, -7.5F, -7.5F, -9.5F, -9.5F, -7.5F, -10.5F, -10.5F, -10.5F, -10.5F, -6.5F, -6.5F, -6.5F, -6.5F, -7.5F, -7.5F, -9.5F, -9.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -7.5F, -8.5F, -9.5F, -8.5F, -12.5F, -12.5F, -12.5F, -11.5F, -11.5F, -5.5F, -5.5F, -4.5F, -4.5F, -4.5F, -4.5F, -5.5F, -5.5F};
	private static final int[] sizeX = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeY = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeZ = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final float[] extra = new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	private static final float[] rotation = new float[] {-8.0F, 16.0F, 8.0F};

    public DeliniumCrucibleLavaModel() {
		super(RenderLayer::getEntityTranslucent);
		this.withParts(ObjectArrayList.wrap(new DynamicModelPart[] { generateModelPart(this, u.clone(), v.clone(), x.clone(), y.clone(), z.clone(), sizeX.clone(), sizeY.clone(), sizeZ.clone(), extra.clone(), rotation.clone(), defaultSeeds(x.length), sprite, layerFactory)})).buildUsingSeeds();
	}
	
}