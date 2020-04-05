package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.List;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.impl.renderer.RendererAccessImpl;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {
    // A jukebox itemstack
    private static ItemStack stack = new ItemStack(Delinium.DELINIUM, 1);

    public DeliniumCrucibleLootableContainerBlockEntityRenderer(
            BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
        System.out.println(ModelLoader.LAVA_FLOW.toString());
    }

    public float cosAngle(int angle) {
        return (float) (Math.cos((angle) * Math.PI / 180));
    }

    public float sinAngle(int angle) {
        return (float) (Math.sin((angle) * Math.PI / 180));
    }

    @Override
    public void render(DeliniumCrucibleLootableContainerBlockEntity blockEntity, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        boolean melting = DeliniumCrucible.getMeltingFromBlockState(blockEntity.getCachedState());
        int percentage = DeliniumCrucible.getPercentageFromBlockState(blockEntity.getCachedState());
        
        matrices.push();
        // // RENDER ITEM ABOVE BLOCK // //

        // Calculate the current offset in the y value
        double offset =
                Math.abs(Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
        // Move the item
        matrices.translate(.5, 1.1 + offset, .5);
        // Rotate the item
        matrices.multiply(Vector3f.POSITIVE_Y
                .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));

        // get the correct light above the block
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(),
                blockEntity.getPos().up());

        // render stack above
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack,
                ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices,
                vertexConsumers);
        matrices.pop();

        matrices.push();
        // // END RENDER ITEM ABOVE BLOCK // //
        // matrices.pop();


        // matrices.push();
        // render lava based on blockEntity state?


        // System.out.println(percentage);
        matrices.translate(.5, ((.005 + offset/2) * percentage/100), .5);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime()
        + tickDelta) * 4));
        // get vertex consumer from buffer builders
        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getSolid());

        // create new mesh for lava
        MeshBuilder meshBuilder = RendererAccessImpl.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();

        float cosx, sinx, height = 0F;
        float radius = 2.9f;
        for (int x = 0; x < 360; x++) {
            // draw cylinder;
            cosx = cosAngle(x)/radius;
            sinx = sinAngle(x)/radius;
            emitter.material(RendererAccessImpl.INSTANCE.getRenderer().materialFinder()
                    .blendMode(0,
                            BlendMode.fromRenderLayer(
                                    RenderLayers.getBlockLayer(blockEntity.getCachedState())))
                    .find());
            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.colorIndex(0);
            emitter.pos(0, cosx, height, sinx);
            emitter.pos(1, cosx, (float) (height + (.1 + (.1 * percentage))), sinx);
            x++;
            cosx = cosAngle(x)/radius;
            sinx = sinAngle(x)/radius;
            emitter.pos(2, cosx, (float) (height + (.1 + (.1 * percentage))), sinx);
            emitter.pos(3, cosx, height, sinx);
            x--;
            emitter.emit();

            // draw bottom
            cosx = cosAngle(x)/radius;
            sinx = sinAngle(x)/radius;
            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.colorIndex(0);
            emitter.pos(0, cosx, height, sinx);
            emitter.pos(1, -cosx, height, sinx);
            emitter.pos(2, -cosx, height, -sinx);
            emitter.pos(3, cosx, height, -sinx);
            emitter.emit();

            // draw top
            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.colorIndex(0);
            emitter.pos(0, cosx, (float) (height + (.1 + (.1 * percentage))), sinx);
            emitter.pos(1, -cosx, (float) (height + (.1 + (.1 * percentage))), sinx);
            emitter.pos(2, -cosx, (float) (height + (.1 + (.1 * percentage))), -sinx);
            emitter.pos(3, cosx, (float) (height + (.1 + (.1 * percentage))), -sinx);
            emitter.emit();
        }
        Mesh m = meshBuilder.build();
        List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
        // System.out.println(quadList.length);
        for (int x = 0; x < quadList.length; x++) {
            for (BakedQuad bq : quadList[x]) {
                vc.quad(matrices.peek(), bq, 1f, 1f, 1f, lightAbove, OverlayTexture.DEFAULT_UV);
            }
        }
        matrices.pop();
    }

}
