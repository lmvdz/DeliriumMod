package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.model.DynamicModel;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class DeliniumCrucibleLavaModel extends DynamicModel {

	private static final float[] x = new float[] {10.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 11.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 7.5F, 8.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 6.5F, 6.5F, 7.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 3.5F, 5.5F, 5.5F, 5.5F, 5.5F, 6.5F, 8.5F, 8.5F, 6.5F, 6.5F, 6.5F, 8.5F, 8.5F, 9.5F, 9.5F, 9.5F, 9.5F, 7.5F, 6.5F, 6.5F, 6.5F, 7.5F, 8.5F, 8.5F, 8.5F, 7.5F, 10.5F, 4.5F, 4.5F, 3.5F, 3.5F, 3.5F, 3.5F, 4.5F, 4.5F, 10.5F, 10.5F, 11.5F, 11.5F};
	private static final float[] y = new float[] {6.0F, 6.0F, 4.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 6.0F, 4.0F, 4.0F, 4.0F, 5.0F, 5.0F, 5.0F, 4.0F, 4.0F, 4.0F, 5.0F, 6.0F, 6.0F, 5.0F, 6.0F, 1.0F, 1.0F, -1.0F, -1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F, 4.0F, 6.0F, 6.0F, 4.0F};
	private static final float[] z = new float[] {-12.5F, -11.5F, -11.5F, -9.5F, -7.5F, -8.5F, -8.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -4.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -12.5F, -7.5F, -7.5F, -8.5F, -9.5F, -9.5F, -9.5F, -8.5F, -8.5F, -7.5F, -7.5F, -9.5F, -9.5F, -7.5F, -10.5F, -10.5F, -10.5F, -10.5F, -6.5F, -6.5F, -6.5F, -6.5F, -7.5F, -7.5F, -9.5F, -9.5F, -9.5F, -9.5F, -8.5F, -7.5F, -7.5F, -7.5F, -8.5F, -9.5F, -8.5F, -12.5F, -12.5F, -12.5F, -11.5F, -11.5F, -5.5F, -5.5F, -4.5F, -4.5F, -4.5F, -4.5F, -5.5F, -5.5F};
	private static final int[] sizeX = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeY = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final int[] sizeZ = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private static final float[] extra = new float[] {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	private static final int[] u = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	private static final int[] v = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

    public DeliniumCrucibleLavaModel() {
        super(RenderLayer::getEntityTranslucent, x, y, z, sizeX, sizeY, sizeZ, extra, u, v);
	}

	@Override
	public void setDynamics() {

	}
	
}