package net.lmvdz.delirium.model;

import java.util.function.Function;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Give Model the ability to randomize different parts of each cuboid
 */
public class DynamicModel extends Model {

    public ObjectList<DynamicModelPart> modelParts;
    public ObjectList<DynamicModel> models;

    /**
     * 1 param Constructor required to support Model extension
     * 
     * @param layerFactory Function<Identifier, RenderLayer>, which renderLayer this DynamicModel
     *                     with use.
     */
    public DynamicModel(Function<Identifier, RenderLayer> layerFactory) {
        super(layerFactory);
        modelParts = new ObjectArrayList<>();
        models = new ObjectArrayList<>();
    }

    /**
     * Attach DyanmicModelParts to this instance of DynamicModel
     * 
     * @param modelParts
     * @return DynamicModel instance
     */
    public DynamicModel withParts(ObjectList<DynamicModelPart> modelParts) {
        this.modelParts = modelParts;
        return this;
    }

    /**
     * Attach DynamicModels to this instance of DynamicModel
     * 
     * @param models ObjectList<DynamicModel>, list of models to DynamicModel to this instance of
     *               DynamicModel
     * @return DynamicModel instance
     */
    public DynamicModel withModels(ObjectList<DynamicModel> models) {
        this.models = models;
        return this;
    }

    /** builds the cuboids */
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

    /** builds the cuboids using the linked seeds */
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

    /**
     * rebuilds the cuboids
     */
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


    /**
     * Forwards to renderDynamic()
     */
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay,
            float red, float green, float blue, float alpha) {
        models.forEach(model -> {
            model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        });
    }

    /**
     * Main DynamicModel Render function
     * 
     * @param ticked          -- whether or not the tick has ticked a full tick and not a delta tick
     * @param tick            -- the number of ticks
     * @param matrices        -- the matrixStack - same as render()
     * @param vertexConsumers -- the vertexConsumerProvider -- different from render
     * @param light           -- the amount of light for the model
     * @param overlay         -- the overlay of the model
     * @param red             -- R
     * @param green           -- G
     * @param blue            -- B
     * @param alpha           -- A
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green,
            float blue, float alpha) {
        models.forEach(model -> {
            model.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red, green,
                    blue, alpha);
        });
        modelParts.forEach(modelPart -> {
            modelPart.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red,
                    green, blue, alpha);
        });
    }

}
