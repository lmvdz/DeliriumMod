package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DYNAMIC_ENUM;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DynamicPart;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class DeliniumCrucibleLavaModel extends DynamicModel {

	private static final float[] rotation = new float[] {-8.0F, 16.0F, 8.0F};

	private static final float[] x = new float[] {10.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 7.5F, 8.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 5.5F, 5.5F, 5.5F, 5.5F, 6.5F, 8.5F, 8.5F, 6.5F, 6.5F, 6.5F, 8.5F, 8.5F, 9.5F, 9.5F, 9.5F, 9.5F, 7.5F, 6.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 10.5F, 4.5F, 4.5F, 3.5F, 3.5F, 3.5F, 3.5F, 4.5F, 4.5F, 10.5F, 10.5F, 11.5F, 11.5F};
	private static final float[] y = new float[] {6.0F, 6.0F, 4.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 6.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 6.0F, 6.0F, 5.0F, 6.0F, 1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F};
	private static final float[] z = new float[] {-12.5F, -11.5F, -11.5F, -9.5F, -7.5F, -8.5F, -8.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -7.5F, -7.5F, -8.5F, -9.5F, -9.5F, -9.5F, -8.5F, -8.5F, -7.5F, -7.5F, -9.5F, -9.5F, -7.5F, -10.5F, -10.5F, -10.5F, -10.5F, -6.5F, -6.5F, -6.5F, -6.5F, -7.5F, -7.5F, -9.5F, -9.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -7.5F, -8.5F, -9.5F, -8.5F, -12.5F, -12.5F, -12.5F, -11.5F, -11.5F, -5.5F, -5.5F, -4.5F, -4.5F, -4.5F, -4.5F, -5.5F, -5.5F};
	private static final int[] sizeX = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeY = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeZ = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final float[] extra = new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	private static final int[] u = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final int[] v = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
	
	private static final boolean[][] state = new boolean[][] { 
		DynamicModelPart.DEFAULT_ALL_STATE.clone()
	};
	private static final float[][] min = new float[][] {
		DynamicModelPart.DEFAULT_MIN.clone()
	};
	private static final float[][] max = new float[][] {
		DynamicModelPart.DEFAULT_MAX.clone()
	};
	private static final float[][] lerpPercent = new float[][] {
		DynamicModelPart.DEFAULT_LERP_PERCENT.clone()
	};
	private static final float[][] applyRandomMax = new float[][] {
		new float[] { DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX, DynamicModelPart.DEFAULT_APPLY_RANDOM_MAX  }
	};
	private static final float[][] applyRandomMin = new float[][] {
		new float[] { DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN, DynamicModelPart.DEFAULT_APPLY_RANDOM_MIN  }
	};
	private static final float[][] applyRandomMultiplier = new float[][] { 
		DynamicModelPart.DEFAULT_APPLY_RANDOM_MULTIPLIER.clone()
	};

	private static ObjectList<DynamicPart[]> SEEDS;

    public DeliniumCrucibleLavaModel() {
        super(RenderLayer::getEntityTranslucent, x, y, z, sizeX, sizeY, sizeZ, extra, u, v, rotation, getSeeds());
        // super(RenderLayer::getEntityTranslucent, x, y, z, sizeX, sizeY, sizeZ, extra, u, v, rotation);
	}

	private static ObjectList<DynamicPart[]> getSeeds() {
		if (SEEDS == null) {
			SEEDS = new ObjectArrayList<DynamicPart[]>();
			for(int index = 0; index < x.length; index++) {
				DynamicPart[] parts = new DynamicPart[DynamicModelPart.DYNAMIC_ENUM_LENGTH];
				for(int dEnumIndex = 0; dEnumIndex < DynamicModelPart.DYNAMIC_ENUM_LENGTH; dEnumIndex++) {
					DYNAMIC_ENUM dEnum = DYNAMIC_ENUM.values()[dEnumIndex];
					DynamicPart part = (DynamicModelPart.EMPTY).new DynamicPart(dEnum, 
					(state.length-1 < index) ? state[state.length-1][dEnumIndex] : state[index][dEnumIndex], 
					(min.length-1 < index) ? min[min.length-1][dEnumIndex] : min[index][dEnumIndex], 
					(max.length-1 < index) ? max[max.length-1][dEnumIndex] : max[index][dEnumIndex], 
					0F,
					(lerpPercent.length-1 < index) ? lerpPercent[lerpPercent.length-1][dEnumIndex] : lerpPercent[index][dEnumIndex], 
					(applyRandomMax.length-1 < index) ? applyRandomMax[applyRandomMax.length-1][dEnumIndex] : applyRandomMax[index][dEnumIndex], 
					(applyRandomMin.length-1 < index) ? applyRandomMin[state.length-1][dEnumIndex] : applyRandomMin[index][dEnumIndex], 
					(applyRandomMultiplier.length-1 < index) ? applyRandomMultiplier[applyRandomMultiplier.length-1][dEnumIndex] : applyRandomMultiplier[index][dEnumIndex]);
					parts[dEnumIndex] = part;
				}
				SEEDS.add(index, parts);
			}
			
		}
		return SEEDS;
	}
	
}