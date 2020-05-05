package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.List;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.impl.renderer.RendererAccessImpl;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {

    private float tick = 0;
    private int shiftDeltaTickCounter = 0;
    private int dynamicsDeltaTickCounter = 0;

    public DeliniumCrucibleLootableContainerBlockEntityRenderer(
            BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
        
    }

    public float cosAngle(int angle) {
        return (float) (Math.cos((angle) * Math.PI / 180));
    }

    public float sinAngle(int angle) {
        return (float) (Math.sin((angle) * Math.PI / 180));
    }

    /**
     * Render a black cube
     * @param matrices
     * @param vertexConsumers
     * @param scale -- @Nullable
     * @param translation -- @Nullable
     * @param matrixMultiplicationQuaternion -- @Nullable
     */
    public void renderCube(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Vector3f scale, Vector3f translation, Quaternion matrixMultiplicationQuaternion) {
        matrices.push();
        if (translation != null) {
            matrices.translate(translation.getX(), translation.getY(), translation.getZ());
        }
        if (scale != null) {
            matrices.scale(scale.getX(), scale.getY(), scale.getZ());
        }
        if (matrixMultiplicationQuaternion != null) {
            matrices.multiply(matrixMultiplicationQuaternion);
        }
        Renderer renderer = RendererAccessImpl.INSTANCE.getRenderer();
        SpriteIdentifier spriteId = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier("block/lava_still"));
        VertexConsumer vc = spriteId.getVertexConsumer(vertexConsumers, layerFactory -> { return RenderLayer.getTranslucent(); });
        // VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MeshBuilder meshBuilder = renderer.meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        emitter.pos(0, 0f, 1f, 0f).pos(1, 0f, 1f, 1f).pos(2, 1f, 1f, 1f).pos(3, 1f, 1f, 0f).emit(); //top quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 1f, 0f, 0f).pos(2, 1f, 0f, 1f).pos(3, 0f, 0f, 1f).emit(); //bottom quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 1f, 0f).pos(2, 1f, 1f, 0f).pos(3, 1f, 0f, 0f).emit(); //right quad

        emitter.pos(0, 1f, 1f, 1f).pos(1, 0f, 1f, 1f).pos(2, 0f, 0f, 1f).pos(3, 1f, 0f, 1f).emit(); //left quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 0f, 1f).pos(2, 0f, 1f, 1f).pos(3, 0f, 1f, 0f).emit(); //front quad

        emitter.pos(0, 1f, 1f, 0f).pos(1, 1f, 1f, 1f).pos(2, 1f, 0f, 1f).pos(3, 1f, 0f, 0f).emit(); //back quad
        emitter.material(renderer.materialFinder().find());
        Mesh m = meshBuilder.build();
        List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
        for (int x = 0; x < quadList.length; x++) {
            for (BakedQuad bq : quadList[x]) {
                float[] brightness = new float[] {1f, 1f, 1f, 1f};
                int[] lights = new int[] {15728880, 15728880, 15728880, 15728880};
                vc.quad(matrices.peek(), bq, brightness, 1f, 1f, 1f, lights, OverlayTexture.DEFAULT_UV, true);
            }
        }
        matrices.pop();
    }

    public void renderFluid(BlockPos blockPos, BlockRenderView blockRenderView, VertexConsumer vertexConsumer, FluidState fluidState) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getBlockRenderManager().renderFluid(blockPos, blockRenderView, vertexConsumer, fluidState);
    }

    /**
     * 
     * @param blockEntity -- BlockEntity, used to check if the distance from the player to the block is out of text render range
     * @param text -- String, the text to render
     * @param matrices -- MatrixStack, the matrices from the BlockEntityRenderer.render()
     * @param vertexConsumerProvider -- VertexConsumerProvider, the vertexConsumerProvider from the BlockEntityRenderer.render()
     * @param verticalOffset -- float, move the text vertically
     * @param light -- integer, light
     */
    public void renderTextAboveBlockEntity(double distanceToCamera, BlockEntity blockEntity, String text, double textRenderRange, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, float[] offset, int light) {
        MinecraftClient client = MinecraftClient.getInstance();
        // distance between client's camera and the blockEntity
        if (distanceToCamera <= textRenderRange) {
            matrices.push();
            // center and move vertical
            matrices.translate(.5f + offset[0], 1.5 + offset[1], .5f + offset[2]); 
            // scale down
            matrices.scale(Float.min(0.0025F * (float)distanceToCamera, .025F), Float.min(0.0025F * (float)distanceToCamera, .025F), Float.min(0.0025F * (float)distanceToCamera, .025F)); 
            // rotate based on camera rotation
            matrices.multiply(client.gameRenderer.getCamera().getRotation()); 
            // flip x-axis
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(180)); 
            // flip y-axis
            matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(180)); 
            // render text
            client.textRenderer.draw(text, -client.textRenderer.getStringWidth(text)/2, 0f, 0xffffff, false, matrices.peek().getModel(), vertexConsumerProvider, false, (int)(client.options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24, light);
            matrices.pop();
        }
    }


    @Override
    public void render(DeliniumCrucibleLootableContainerBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        MinecraftClient client = MinecraftClient.getInstance();
        BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
        if (!state.getBlock().equals(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK)) {
            return;
        }
        boolean melting = DeliniumCrucible.getMeltingFromBlockState(state);
        boolean primed = DeliniumCrucible.getCanMeltFromBlockState(state);
        int percentage = DeliniumCrucible.getPercentageFromBlockState(state);
        Direction facing = DeliniumCrucible.getHorizontalFacingFromBlockState(state);
        Direction cameraFacing = client.cameraEntity.getHorizontalFacing();
        // Renderer renderer = RendererAccessImpl.INSTANCE.getRenderer();
        // Sprite sprite = client.getBlockRenderManager().getModel(blockEntity.getCachedState()).getSprite();
        


        double offset = Math.abs(Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        double distanceToCamera = client.cameraEntity.getPos().squaredDistanceTo(new Vec3d(blockEntity.getPos().getX()+.5f, blockEntity.getPos().getY()+.5f, blockEntity.getPos().getZ()+.5f));
        double renderDistance = 4000D;
        boolean inRenderRange = distanceToCamera < renderDistance;
        boolean renderDynamic = (!client.isPaused() && inRenderRange); 
        // renderCube(matrices, vertexConsumers, new Vector3f(.1F, .1F, .1F), new Vector3f(.5f, 1.1f, .5f), null);
        // renderFluid(blockEntity.getPos().up(), (BlockRenderView)client.world, vertexConsumers.getBuffer(RenderLayers.getFluidLayer(Fluids.FLOWING_LAVA.getDefaultState().with(LavaFluid.LEVEL, 8))), Fluids.FLOWING_LAVA.getDefaultState().with(LavaFluid.LEVEL, 8));
        if (renderDynamic && (melting || primed)) {

            
            // int shiftUVTicks = (int)(640 / ((Math.random() * 12) + 10) / ((percentage * .28) + 1));

            boolean ticked = this.tick > tickDelta;
            this.tick = tickDelta;

            matrices.push();
            matrices.translate(0f, .5f, 1f);
            matrices.scale(1f, -1f, 1f);
            blockEntity.getLavaModel().renderDynamic(ticked, blockEntity.ticks, matrices, vertexConsumers, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
            /**
             * 
             * spawn portal blocks
             *  
             */
            // matrices.push();

            // // matrices.translate(.5f + (cameraFacing.toString() == "west" ? -1f : cameraFacing.toString() == "east" ? 1f : 0), 1f, .5f + (cameraFacing.toString() == "north" ? -1f : cameraFacing.toString() == "south" ? 1f : 0));
            // // if (cameraFacing.toString() == "north" || cameraFacing.toString() == "south") {
            // //     matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            // // }
            // matrices.translate(.5f, .75f, .5f);
            // matrices.scale(2.75f, 3.25f, 2.75f);
            // Camera c = client.gameRenderer.getCamera();
            // Quaternion q = c.getVerticalPlane().getDegreesQuaternion(180);
            // q.hamiltonProduct(c.getHorizontalPlane().getDegreesQuaternion(180));
            // q.hamiltonProduct(client.gameRenderer.getCamera().getRotation());
            // q.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            // matrices.multiply(q);

            // blockEntity.getPortalModel().renderDynamic(ticked, blockEntity.ticks, matrices, vertexConsumers, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            
            // matrices.pop();


            // rotate portal?
            // PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            // buf.writeBlockPos(blockEntity.getPos());
            // buf.writeFloat(q.getA());
            // buf.writeFloat(q.getB());
            // buf.writeFloat(q.getC());
            // buf.writeFloat(q.getD());
            // ClientSidePacketRegistry.INSTANCE.sendToServer(DeliniumCrucibleLootableContainerBlockEntity.SET_IMMERSIVE_PORTAL_ROTATION, buf);

            if (melting) {
                // System.out.println(blockEntity.smeltableStackIndex);
                if (blockEntity.smeltableStackIndex >= 0 && blockEntity.smeltableStackIndex < blockEntity.inventory.getInvSize()) {
                    ItemStack stack = blockEntity.getInvStack(blockEntity.smeltableStackIndex);
                    matrices.push();
                    if (distanceToCamera < 9D)  {
                        // System.out.println(distanceToCamera);
                        if (distanceToCamera < 3D) {
                            distanceToCamera = 3D;
                        }
                        matrices.translate(.5, 1.75 + (offset - ((float)(10D-distanceToCamera) * .01)), .5);
        
                        float scale = 1F - ((float)(10D-distanceToCamera)/10F);
                        matrices.scale(scale, scale, scale);
                    } else {
                        matrices.translate(.5, 1.65 + offset, .5);
                        matrices.scale(1f, 1f, 1f);
                    }
                    matrices.multiply(Vector3f.POSITIVE_Y
                        .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
                    client.getItemRenderer().renderItem(stack,
                        ModelTransformation.Mode.GROUND, 0xF000F0, OverlayTexture.DEFAULT_UV, matrices,
                        vertexConsumers);
                    matrices.pop();
                    DeliniumCrucibleConversion conversion = DeliniumCrucibleConversion.smeltConversions.get(stack.getItem());
                    if (conversion != null) {
                        matrices.push();
                        String text = stack.getName().asString() + " (" + stack.getCount()  + ")";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text, renderDistance, matrices, vertexConsumers, new float[] { 0f, (float)offset+.1f, 0f }, 0xF000F0);
                        text = (new ItemStack(conversion.product)).getName().asString() + " (" + (stack.getCount() / conversion.per) * conversion.count  + ")";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text, renderDistance, matrices, vertexConsumers, new float[] { 0f, (float)(offset-.15f), 0f }, 0xF000F0);
                        text = percentage + "/10";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text, renderDistance, matrices, vertexConsumers, new float[] { 0f, (float)(offset-.4f), 0f }, 0xF000F0);
                        matrices.pop();
                    }
                }
            }
        }

        // if ((melting)) {
            
            // String text = "x"+stack.getCount();
            // System.out.println();
            
            // matrices.multiply(Vector3f.POSITIVE_Y
            //         .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
            // matrices.multiply(Vector3f.NEGATIVE_Z.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(client.getCameraEntity().getHeadYaw()));
            // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float)client.getCameraEntity().getEyeY()));
            // matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(client.cameraEntity.getTargetingMargin()));
            
            // System.out.println(cameraPos.toString());
            // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(client.cameraEntity.pitch));
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(client.cameraEntity.yaw));
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(cameraPos.dot(blockpos)));
            
            
            
            

            // matrices.push();
            // // END RENDER ITEM ABOVE BLOCK // //
            // matrices.pop();


            // matrices.push();
            // render lava based on blockEntity state?


            // System.out.println(percentage);
            // matrices.translate(.5f, (float)1.1f+((.005 + offset/2) * percentage/100), .5f);
            // renderLabel(blockEntity, "Delinium Crucible", matrices, vertexConsumers, 0x00F000B0);
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            // get vertex consumer from buffer builders
            // VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getSolid());

            // create new mesh for lava
            // MeshBuilder meshBuilder = renderer.meshBuilder();
            // QuadEmitter emitter = meshBuilder.getEmitter();
            // matrices.scale(.1f, .1f, .1f); // 1/10th scale
            // matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            // float halfDistance = (float)(cameraPos.distanceTo(blockpos) / 2f);

            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(45));
            // matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
            // matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(45));
            // emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 0f, 1f).pos(2, 1f, 0f, 1f).pos(3, 1f, 0f, 0f).emit(); //top quad
            // emitter.pos(0, 0f, 0f, 0f).pos(1, 1f, 0f, 0f).pos(2, 1f, 0f, 1f).pos(3, 0f, 0f, 1f).emit(); //bottom quad
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
            // Mesh m = meshBuilder.build();
            // List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
            // // System.out.println(quadList.length);
            // for (int x = 0; x < quadList.length; x++) {
            //     for (BakedQuad bq : quadList[x]) {
            //         // vc.quad(matrices.peek(), bq, .5f, .5f, .5f, lightAbove, OverlayTexture.DEFAULT_UV);
            //         vc.quad(matrices.peek(), bq, .5f, .5f, .5f, 0x00F000B0, OverlayTexture.DEFAULT_UV);
            //     }
            // }
            // matrices.pop();
            // matrices.push();
            // matrices.translate(.5, 1.1 + offset, .5);
            // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
            // matrices.multiply(Vector3f.NEGATIVE_Y
            //         .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            // DeliniumCrucibleModel.MODEL.render(matrices, new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID, "block/delinium_crucible")).getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            // matrices.pop();
        // }
        
    }

}
