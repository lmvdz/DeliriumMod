package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.impl.renderer.RendererAccessImpl;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.mixin.QuadInvoker;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.WorldView;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {
    // A jukebox itemstack
    private static ItemStack stack = new ItemStack(Delinium.DELINIUM, 1);

    public DeliniumCrucibleLootableContainerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(DeliniumCrucibleLootableContainerBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        
        // // RENDER ITEM ABOVE BLOCK // //
        
        // Calculate the current offset in the y value
        double offset = Math.abs(Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
        // Move the item
        matrices.translate(.5, 1.1 + offset, .5);
        
        // Rotate the item
        // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        
        // get the correct light above the block
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        
        // render stack above
        // MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

        // // END RENDER ITEM ABOVE BLOCK // //
        // matrices.pop();


        // matrices.push();
        // render lava based on blockEntity state?
        
        boolean melting = DeliniumCrucible.getMeltingFromBlockState(blockEntity.getCachedState());
        int percentage = DeliniumCrucible.getPercentageFromBlockState(blockEntity.getCachedState());
        
        
        // get vertex consumer from buffer builders
        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayers.getBlockLayer(blockEntity.getCachedState()));
        
        // create new mesh for lava
        MeshBuilder meshBuilder = RendererAccessImpl.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        Mesh m;
        int x;
        float cosx, sinx, height;
        height = 0F;
        for(x = 0; x < 360; x++) {
            cosx = (float)(Math.cos((x)*Math.PI/180));
            sinx = (float)(Math.sin((x)*Math.PI/180));
            emitter.pos(0, cosx, height, sinx);
            
            emitter.pos(1, -cosx, height, sinx);
            
            
            emitter.pos(2, -cosx, height, -sinx);
            
            emitter.pos(3, cosx, height, -sinx);
            
            emitter.spriteColor(0, 0xffffff, 0xffffff, 0xffffff, 0xffffff);
            
            emitter.emit();
        }
        
        // emitter.pos(0, .1F * x, .01F * y, 0F).pos(1, 0F, .01F, .1F).pos(2, .1F, .01F, .1F).pos(3, .1F, .0F, 0F).emit(); // bottom
        
        // emitter.pos(0, .1F, 0F, 0F).pos(1, .1F, 0F, .1F).pos(2, .2F, 0F, .1F).pos(3, .2F, 0F, 0F).emit(); // bottom
        // emitter.pos(0, 0F, 1F, 0F).pos(1, 0F, 1F, 1F).pos(2, 1F, 1F, 1F).pos(3, 1F, 1F, 0F).emit(); // top
        // emitter.pos(0, 0F, 0F, 0F).pos(1, 1F, 0F, 1F).pos(2, 0F, 1F, 0F).pos(3, 1F, 1F, 1F).emit(); // left
        // emitter.pos(0, 1F, 0F, 0F).pos(1, 1F, 1F, 0F).pos(2, 1F, 1F, 1F).pos(3, 1F, 0F, 1F).emit(); // right
        // emitter.pos(0, 0F, 0F, 1F).pos(1, 1F, 0F, 1F).pos(2, 1F, 1F, 1F).pos(3, 0F, 1F, 1F).emit(); // front
        // emitter.pos(0, 1F, 0F, 0F).pos(1, 1F, 1F, 0F).pos(2, 0F, 1F, 0F).pos(3, 0F, 0F, 0F).emit(); // back
        // build mesh
        m = meshBuilder.build();
        // get array of quad lists from built mesh
        List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
        // render each quad
        for(x = 0; x < quadList.length; x++) {
            QuadInvoker.renderQuad(matrices.peek(), vc, 1F, 1F, 1F, quadList[x], lightAbove, OverlayTexture.DEFAULT_UV);
        }
        matrices.pop();
    }
}