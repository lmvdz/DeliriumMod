package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import org.w3c.dom.css.RGBColor;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
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
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {

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
        MinecraftClient client = MinecraftClient.getInstance();
        Renderer renderer = RendererAccessImpl.INSTANCE.getRenderer();
        
        // Sprite sprite = client.getBlockRenderManager().getModel(blockEntity.getCachedState()).getSprite();
        
        // if ((melting && blockEntity.smeltableStackIndex != -1 )) {
            // ItemStack stack = blockEntity.getInvStack(blockEntity.smeltableStackIndex);
            // String text = "x"+stack.getCount();
            String text = "test";
            double offset =
                    Math.abs(Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
            matrices.push();
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            Vector3f cameraPos = new Vector3f(client.cameraEntity.getCameraPosVec(tickDelta));
            Vector3f blockpos = new Vector3f(new Vec3d(blockEntity.getPos()));
            // System.out.println();
            
            matrices.translate(.5-(client.textRenderer.getStringWidth(text)/2*0.025F), 1.1 + offset, .5);
            matrices.scale(0.025F, 0.025F, 0.025F);
            // matrices.multiply(Vector3f.POSITIVE_Y
            //         .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(180));
            // matrices.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(client.getCameraEntity().getHeadYaw()));
            // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float)client.getCameraEntity().getEyeY()));
            // matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(client.cameraEntity.getTargetingMargin()));
            
            // System.out.println(cameraPos.toString());
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(client.cameraEntity.pitch));
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(client.cameraEntity.yaw));
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(cameraPos.dot(blockpos)));

            client.textRenderer.draw(text, 0f, 0f, 0xffffff, true, matrices.peek().getModel(), vertexConsumers, false, 0xffffff, lightAbove);
            matrices.pop();
            
            // matrices.push();
            // matrices.translate(.5, 1.1 + offset, .5);
            // matrices.multiply(Vector3f.POSITIVE_Y
            //         .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        

            // // render stack above
            // client.getItemRenderer().renderItem(stack,
            //         ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices,
            //         vertexConsumers);
            
            // matrices.pop();

            matrices.push();
            // // END RENDER ITEM ABOVE BLOCK // //
            // matrices.pop();


            // matrices.push();
            // render lava based on blockEntity state?


            // System.out.println(percentage);
            matrices.translate(.5, 1.1+((.005 + offset/2) * percentage/100), .5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            // get vertex consumer from buffer builders
            VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getSolid());

            // create new mesh for lava
            MeshBuilder meshBuilder = renderer.meshBuilder();
            QuadEmitter emitter = meshBuilder.getEmitter();
            emitter.pos(0, 0f, 0f, 0f).pos(1, 1f, 0f, 0f).pos(2, 1f, 0f, 1f).pos(3, 0f, 0f, 1f).emit();
            emitter.pos(0, 1f, 1f, 1f).pos(1, 2f, 1f, 1f).pos(2, 2f, 1f, 2f).pos(3, 1f, 1f, 2f).emit();
            // float cosx, sinx, height = 0F;
            // float radius = 2.89f;
            // for (int x = 0; x < 90; x++) {
                // draw cylinder;
                // cosx = cosAngle(x)/radius;
                // sinx = sinAngle(x)/radius;
                // emitter.material(renderer.materialFinder()
                //         .blendMode(0,
                //                 BlendMode.fromRenderLayer(
                //                         RenderLayers.getBlockLayer(blockEntity.getCachedState())))
                //         .find());
                // emitter.spriteBake(spriteIndex, sprite, bakeFlags)
                // emitter.spriteColor(0, 0xffffff, 0xffffff, 0xffffff, 0xffffff);
                // emitter.colorIndex(0);
                // emitter.pos(0, cosx, height, sinx);
                // emitter.lightmap(0, lightAbove);
                // emitter.pos(1, cosx, (float) (height + (.1 + (.05 * percentage))), sinx);
                // emitter.lightmap(1, lightAbove);
                // x++;
                // cosx = cosAngle(x)/radius;
                // sinx = sinAngle(x)/radius;
                // emitter.pos(2, cosx, (float) (height + (.1 + (.05 * percentage))), sinx);
                // emitter.lightmap(2, lightAbove);
                // emitter.pos(3, cosx, height, sinx);
                // emitter.lightmap(3, lightAbove);
                // x--;
                // emitter.emit();

                // draw bottom
                // cosx = cosAngle(x)/radius;
                // sinx = sinAngle(x)/radius;
                // emitter.spriteColor(0, 0xffffff, 0xffffff, 0xffffff, 0xffffff);
                // emitter.colorIndex(0);
                // emitter.pos(0, cosx, height, sinx);
                // emitter.lightmap(0, lightAbove);
                // emitter.pos(1, -cosx, height, sinx);
                // emitter.lightmap(1, lightAbove);
                // emitter.pos(2, -cosx, height, -sinx);
                // emitter.lightmap(2, lightAbove);
                // emitter.pos(3, cosx, height, -sinx);
                // emitter.lightmap(3, lightAbove);
                // emitter.emit();

                // draw top
                // emitter.spriteColor(0, 0xffffff, 0xffffff, 0xffffff, 0xffffff);
                // emitter.colorIndex(0);
                // emitter.pos(0, cosx, (float) (height + (.1 + (.05 * percentage))), sinx);
                // emitter.lightmap(0, lightAbove); 
                // emitter.pos(1, -cosx, (float) (height + (.1 + (.05 * percentage))), sinx);
                // emitter.lightmap(1, lightAbove);
                // emitter.pos(2, -cosx, (float) (height + (.1 + (.05 * percentage))), -sinx);
                // emitter.lightmap(2, lightAbove);
                // emitter.pos(3, cosx, (float) (height + (.1 + (.05 * percentage))), -sinx);
                // emitter.lightmap(3, lightAbove);
                // emitter.spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED);
                // emitter.emit();
            // }
            Mesh m = meshBuilder.build();
            List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
            // System.out.println(quadList.length);
            for (int x = 0; x < quadList.length; x++) {
                for (BakedQuad bq : quadList[x]) {
                    // vc.quad(matrices.peek(), bq, .5f, .5f, .5f, lightAbove, OverlayTexture.DEFAULT_UV);
                    vc.quad(matrices.peek(), bq, .5f, .5f, .5f, 0x00F000B0, OverlayTexture.DEFAULT_UV);
                }
            }
            matrices.pop();
        // }
        
    }

}
