package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.ItemStack;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {
    // A jukebox itemstack
    private static ItemStack stack = new ItemStack(Delinium.DELINIUM, 1);

    public DeliniumCrucibleLootableContainerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(DeliniumCrucibleLootableContainerBlockEntity blockEntity, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
                matrices.push();
                
                // RENDER ITEM ABOVE BLOCK
                
                // Calculate the current offset in the y value
                double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
                // Move the item
                matrices.translate(0.5, 1.25 + offset, 0.5);
                // Rotate the item
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
                
                int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
                
                // render stack above
                MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

                // END RENDER ITEM ABOVE BLOCK


                // render lava based on blockEntity state?
                
                boolean melting = DeliniumCrucible.getMeltingFromBlockState(blockEntity.getCachedState());
                int percentage = DeliniumCrucible.getPercentageFromBlockState(blockEntity.getCachedState());

                // MinecraftClient.getInstance().getBlockRenderManager().renderFluid(blockEntity.getPos(), blockEntity.getWorld(), vertexConsumers, )

                matrices.pop();
    }
}