package net.lmvdz.delirium.model;

import java.util.function.Function;
import org.apache.commons.lang3.tuple.MutablePair;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DynamicModel extends Model {

    public DynamicModelPart main;
    public MatrixStack mainRotation;

    public float[] x;
	public float[] y;
	public float[] z;
	public int[] sizeX;
	public int[] sizeY;
	public int[] sizeZ;
	public float[] extra;
	public int[] u;
    public int[] v;
    public float[] rotation;



    public DynamicModel(Function<Identifier, RenderLayer> layerFactory) {
        super(layerFactory);
    }

    public DynamicModel(Function<Identifier, RenderLayer> layerFactory, float[] x, float[] y, float[] z, int[] sizeX, int[] sizeY, int[] sizeZ, float[] extra, int[] u, int[] v) {
        super(layerFactory);
        this.x = x;
        this.y = y;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.extra = extra;
        this.u = u;
        this.v = v;
        build();
    }

    public DynamicModel(Function<Identifier, RenderLayer> layerFactory, float[] x, float[] y, float[] z, int[] sizeX, int[] sizeY, int[] sizeZ, float[] extra, int[] u, int[] v, float xRotation, float yRotation, float zRotation) {
        super(layerFactory);
        this.x = x;
        this.y = y;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.extra = extra;
        this.u = u;
        this.v = v;
        this.rotation = new float[] {xRotation, yRotation, zRotation};
        build();
    }

    public DynamicModel set(DynamicModelPart main) {
        this.main = main;
        return this;
    }

    public DynamicModel rotate(float[] rotation) {
        MatrixStack rotationStack = new MatrixStack();
        rotationStack.translate(-8.0F, 16.0F, 8.0F);
        this.main.rotate(rotationStack);
        return this;
    }

    public DynamicModel build() {
        return this.set(new DynamicModelPart(this)).rotate(this.rotation).addCuboids();
    }
    public DynamicModel addCuboids() {
        for(int i = 0; i < this.x.length; i++) {
			this.main.addCuboid(this.x[i], this.y[i], this.z[i], this.sizeX[i], this.sizeY[i], this.sizeZ[i], this.extra[i], this.u[i], this.v[i]);
        }
        return this;
    }

	public DynamicModel rebuild() {
        this.set(new DynamicModelPart(this, this.main.getDynamicCuboidParts())).rotate(this.rotation).addCuboids();
        return this;
	}

	public DynamicModel shiftU(int shift) {
        this.u = shiftIntArray(this.u, shift);
        return this;
	}

	public DynamicModel shiftV(int shift) {
        this.v = shiftIntArray(this.v, shift);
        return this;
	}

	public DynamicModel shiftUV(int shift) {
        return shiftU(shift).shiftV(shift);
	}


    private int[] shiftIntArray(int[] array, int shift) {
		int shiftCounter = 0;
		while(shiftCounter < Math.abs(shift)) {
			int temp = array[array.length-1]; // last number
			for (int i = array.length-1; i > 0; i--) {
				array[i] = array[i-1];
			}
			array[0] = temp;
			shiftCounter++;
		}
		return array;
	}

    public void setDynamics() {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderDynamic(MutablePair<Boolean, Integer> shiftUV, int tick, int updatePerTicks, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.renderDynamic(shiftUV, tick, updatePerTicks, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

}