package net.lmvdz.delirium.model;

import java.util.function.Function;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DYNAMIC_ENUM;
import net.lmvdz.delirium.modelpart.DynamicModelPart.DynamicPart;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DynamicModel extends Model {

    public ObjectList<DynamicModelPart> modelParts;
    public ObjectList<DynamicModel> models;

    public DynamicModel(Function<Identifier, RenderLayer> layerFactory) {
        super(layerFactory);
        modelParts = new ObjectArrayList<>();
        models = new ObjectArrayList<>();
    }

    // public DynamicModel(Function<Identifier, RenderLayer> layerFactory, ObjectList<DynamicModelPart> modelParts) {
    //     super(layerFactory);
    //     this.withParts(modelParts).buildUsingSeeds();
    // }
    // public DynamicModel(Function<Identifier, RenderLayer> layerFactory, ObjectList<DynamicModel> models) {
    //     super(layerFactory);
    //     this.withModels(models).buildUsingSeeds();
    // }

    public DynamicModel withParts(ObjectList<DynamicModelPart> modelParts) {
        this.modelParts = modelParts;
        return this;
    }

    public DynamicModel withModels(ObjectList<DynamicModel> models) {
        this.models = models;
        return this;
    }

    public void build() {
        if (modelParts.size() > 0) {
            modelParts.forEach(modelPart -> {
                modelPart.build();
            });
        }
        if (models.size() > 0) {
            models.forEach(model -> {
                model.build();
            });
        }
    }

    public void buildUsingSeeds() {
        if (modelParts.size() > 0) {
            modelParts.forEach(modelPart -> {
                modelPart.buildUsingSeeds();
            });
        }
        if (models.size() > 0) {
            models.forEach(model -> {
                model.buildUsingSeeds();
            });
        }
    }

    public void rebuild() {
        if (modelParts.size() > 0) {
            modelParts.forEach(modelPart -> {
                modelPart.rebuild();
            });
        }
        if (models.size() > 0) {
            models.forEach(model -> {
                model.rebuild();
            });
        }
    }

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
    
	protected ObjectList<DynamicModelPart> generateModelParts(DynamicModel dynamicModel, float[][] x, float[][] y, float[][] z, int[][] sizeX, int[][] sizeY, int[][] sizeZ, float[][] extra, int[][] u, int[][]v, float[][] rotation, SpriteIdentifier[] sprite, Function<Identifier, RenderLayer> layerFactory) {		
		ObjectList<DynamicModelPart> MODEL_PARTS = new ObjectArrayList<DynamicModelPart>();
        ObjectList<DynamicPart[]> SEEDS = defaultSeeds(x.length);
        for(int i = 0; i < x.length; i++) {
            MODEL_PARTS.add(generateModelPart(dynamicModel, u[i], v[i], x[i], y[i], z[i], sizeX[i], sizeY[i], sizeZ[i], extra[i], rotation[i], SEEDS, sprite[i], layerFactory));
        }
		return MODEL_PARTS;
    }
    
    protected DynamicModelPart generateModelPart(DynamicModel dynamicModel, int[] u,int[] v,float[] x,float[] y,float[] z,int[] sizeX,int[] sizeY,int[] sizeZ, float[] extra, float[] rotation, ObjectList<DynamicPart[]> seeds, SpriteIdentifier spriteId, Function<Identifier,RenderLayer> layerFactory) {
        return new DynamicModelPart(dynamicModel, x, y, z, sizeX, sizeY, sizeZ, extra, u, v, rotation, seeds, spriteId, layerFactory);
    }

    protected DynamicModelPart generateModelPart(DynamicModel dynamicModel, float[] allCuboids, float[] rotation, SpriteIdentifier spriteId, ObjectList<DynamicPart[]> seeds, Function<Identifier, RenderLayer> layerFactory) {
        int[] u = new int[allCuboids.length/9];
        int[] v = new int[allCuboids.length/9];
        float[] x = new float[allCuboids.length/9];
        float[] y = new float[allCuboids.length/9];
        float[] z = new float[allCuboids.length/9];
        int[] sizeX = new int[allCuboids.length/9];
        int[] sizeY = new int[allCuboids.length/9];
        int[] sizeZ = new int[allCuboids.length/9];
        float[] extra = new float[allCuboids.length/9];
        for(int i = 0; i < allCuboids.length/9; i++) {
            int scale = (i*9);
            u[i] = (int)allCuboids[scale];
            v[i] = (int)allCuboids[scale+1];

            x[i] = allCuboids[scale+2];
            y[i] = allCuboids[scale+3];
            z[i] = allCuboids[scale+4];

            sizeX[i] = (int)allCuboids[scale+5];
            sizeY[i] = (int)allCuboids[scale+6];
            sizeZ[i] = (int)allCuboids[scale+7];

            extra[i] = allCuboids[scale+8];
        }
        return this.generateModelPart(dynamicModel, u, v, x, y, z, sizeX, sizeY, sizeZ, extra, rotation, seeds, spriteId, layerFactory);
    }

    protected ObjectList<DynamicPart[]> defaultSeeds(int numberOfCuboids) {
		ObjectList<DynamicPart[]> SEEDS = new ObjectArrayList<DynamicPart[]>();
        for(int index = 0; index < numberOfCuboids; index++) {
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
		return SEEDS;
	} 


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        models.forEach(model -> {
            model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
        
    }

    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green, float blue, float alpha) {
        models.forEach(model -> {
            model.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red, green, blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red, green, blue, alpha);
        });
	}

}