package net.lmvdz.delirium.block.blocks.delinium_crucible;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.impl.renderer.RendererAccessImpl;
import net.lmvdz.delirium.client.DeliriumClientMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DeliniumCrucibleLootableContainerBlockEntityRenderer
        extends BlockEntityRenderer<DeliniumCrucibleLootableContainerBlockEntity> {

    // Constants
//    private static final float X = .525731112119133606f;
//    private static final float Z = .850650808352039932f;
//    private static final float N = 0.f;
//
//    public static List<Vector3f> vertices = new ArrayList<>();
//    public static List<Vector3f> normals = new ArrayList<>();
//    public static List<Vector3f> uvs = new ArrayList<>();
//    public static Vector3f[] points = new Vector3f[]{
//            new Vector3f(-X, N, Z),
//            new Vector3f(X, N, Z),
//            new Vector3f(-X, N, -Z),
//            new Vector3f(X, N, -Z),
//
//            new Vector3f(N, Z, X),
//            new Vector3f(N, Z, -X),
//            new Vector3f(N, -Z, X),
//            new Vector3f(N, -Z, -X),
//
//            new Vector3f(Z, X, N),
//            new Vector3f(-Z, X, N),
//            new Vector3f(Z, -X, N),
//            new Vector3f(-Z, -X, N)
//    };
//    public static int[][] faces = new int[][]{
//            {0, 4, 1}, {0, 9, 4}, {9, 5, 4}, {4, 5, 8}, {4, 8, 1},
//            {8, 10, 1}, {8, 3, 10}, {5, 3, 8}, {5, 2, 3}, {2, 7, 3},
//            {7, 10, 3}, {7, 6, 10}, {7, 11, 6}, {11, 0, 6}, {0, 1, 6},
//            {6, 1, 10}, {9, 0, 11}, {9, 11, 2}, {9, 2, 5}, {7, 2, 11}
//    };
//
//    public static void calcVerticesIcoSphere(int sectorCount, int stackCount) {
//        vertices.clear();
//        normals.clear();
//        uvs.clear();
//
//        // Creates vertrices for faces
//        for (int[] triangle : faces) {
//            for (int i = 0; i < 3; i++) {
//                vertices.add(points[triangle[i]]);
//                Vector3f normalized = points[triangle[i]].copy();
//                normalized.normalize();
//                normals.add(points[triangle[i]]);
//                // Get the needed UV.
//                switch (i) {
//                    case 0:
//                        uvs.add(new Vector3f(0, 0, 0));
//                        break;
//                    case 1:
//                        uvs.add(new Vector3f(0, 1, 0));
//                        break;
//                    case 2:
//                        uvs.add(new Vector3f(1, 1, 0));
//                        break;
//                }
//            }
//        }
//    }


    private float tick = 0;


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
     * 
     * @param matrices
     * @param vertexConsumers
     * @param scale                          -- @Nullable
     * @param translation                    -- @Nullable
     * @param matrixMultiplicationQuaternion -- @Nullable
     */
    public void renderCube(MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            Vector3f scale, Vector3f translation, Quaternion matrixMultiplicationQuaternion) {
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
        SpriteIdentifier spriteId = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX,
                new Identifier("block/lava_still"));
//        VertexConsumer vc = spriteId.getVertexConsumer(vertexConsumers, layerFactory -> {
//            return DeliriumClientMod.ExampleShaderEffectRenderLayer
//                    .getRenderLayer(RenderLayer.getTranslucent());
            VertexConsumer vc = spriteId.getVertexConsumer(vertexConsumers, layerFactory -> {
                    return RenderLayer.getTranslucent();
            });

            // return
            // ShaderRenderLayer.computeShaderRenderLayerIfAbsent(RenderLayer.getTranslucent(),
            // "delirium:illusion", ShaderRenderLayer.ExampleShaderRenderLayer.shaderTarget);
//        });
        // VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MeshBuilder meshBuilder = renderer.meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        emitter.pos(0, 0f, 1f, 0f).pos(1, 0f, 1f, 1f).pos(2, 1f, 1f, 1f).pos(3, 1f, 1f, 0f).emit(); // top
                                                                                                    // quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 1f, 0f, 0f).pos(2, 1f, 0f, 1f).pos(3, 0f, 0f, 1f).emit(); // bottom
                                                                                                    // quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 1f, 0f).pos(2, 1f, 1f, 0f).pos(3, 1f, 0f, 0f).emit(); // right
                                                                                                    // quad

        emitter.pos(0, 1f, 1f, 1f).pos(1, 0f, 1f, 1f).pos(2, 0f, 0f, 1f).pos(3, 1f, 0f, 1f).emit(); // left
                                                                                                    // quad

        emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 0f, 1f).pos(2, 0f, 1f, 1f).pos(3, 0f, 1f, 0f).emit(); // front
                                                                                                    // quad

        emitter.pos(0, 1f, 1f, 0f).pos(1, 1f, 1f, 1f).pos(2, 1f, 0f, 1f).pos(3, 1f, 0f, 0f).emit(); // back
                                                                                                    // quad
        emitter.material(renderer.materialFinder().find());
        Mesh m = meshBuilder.build();
        List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
        for (int x = 0; x < quadList.length; x++) {
            for (BakedQuad bq : quadList[x]) {
                float[] brightness = new float[] {1f, 1f, 1f, 1f};
                int[] lights = new int[] {15728880, 15728880, 15728880, 15728880};
                vc.quad(matrices.peek(), bq, brightness, 1f, 1f, 1f, lights,
                        OverlayTexture.DEFAULT_UV, true);
            }
        }
        matrices.pop();
    }

    public void renderFluid(BlockPos blockPos, BlockRenderView blockRenderView,
            VertexConsumer vertexConsumer, FluidState fluidState) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getBlockRenderManager().renderFluid(blockPos, blockRenderView, vertexConsumer,
                fluidState);
    }

    /**
     * 
     * @param blockEntity            -- BlockEntity, used to check if the distance from the player
     *                               to the block is out of text render range
     * @param text                   -- String, the text to render
     * @param matrices               -- MatrixStack, the matrices from the
     *                               BlockEntityRenderer.render()
     * @param vertexConsumerProvider -- VertexConsumerProvider, the vertexConsumerProvider from the
     *                               BlockEntityRenderer.render()
     * @param offset         -- float, move the text vertically
     * @param light                  -- integer, light
     */
    public void renderTextAboveBlockEntity(double distanceToCamera, BlockEntity blockEntity,
            String text, double textRenderRange, MatrixStack matrices,
            VertexConsumerProvider vertexConsumerProvider, float[] offset, int light) {
        MinecraftClient client = MinecraftClient.getInstance();
        // distance between client's camera and the blockEntity
        if (distanceToCamera <= textRenderRange) {
            matrices.push();
            // center and move vertical
            matrices.translate(.5f + offset[0], 1.5 + offset[1], .5f + offset[2]);
            // scale down
            float scale = Float.min((float) distanceToCamera, .025F);
            // (float)Math.log((double));
            matrices.scale(scale, scale, scale);
            // rotate based on camera rotation
            matrices.multiply(client.gameRenderer.getCamera().getRotation());
            // flip x-axis
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(180));
            // flip y-axis
            matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(180));
            // render text
            client.textRenderer.draw(text, -client.textRenderer.getWidth(text) / 2, 0f,
                    0xffffff, false, matrices.peek().getModel(), vertexConsumerProvider, false,
                    (int) (client.options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24, light);
            matrices.pop();
        }
    }

    public List<String> getItemTooltip(ItemStack i) {
        MinecraftClient c = MinecraftClient.getInstance();
        List<Text> list = i.getTooltip(c.player,
                c.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED
                        : TooltipContext.Default.NORMAL);
        List<String> list2 = Lists.newArrayList();
        Iterator<Text> var4 = list.iterator();

        while (var4.hasNext()) {
            Text text = var4.next();
            list2.add(text.asString());
        }
        return list2;
    }

    public void fillGradient(int top, int left, int right, int bottom, int color1, int color2,
            int blitOffset) {
        float f = (float) (color1 >> 24 & 255) / 255.0F;
        float g = (float) (color1 >> 16 & 255) / 255.0F;
        float h = (float) (color1 >> 8 & 255) / 255.0F;
        float i = (float) (color1 & 255) / 255.0F;
        float j = (float) (color2 >> 24 & 255) / 255.0F;
        float k = (float) (color2 >> 16 & 255) / 255.0F;
        float l = (float) (color2 >> 8 & 255) / 255.0F;
        float m = (float) (color2 & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex((double) right, (double) left, (double) blitOffset).color(g, h, i, f)
                .next();
        bufferBuilder.vertex((double) top, (double) left, (double) blitOffset).color(g, h, i, f)
                .next();
        bufferBuilder.vertex((double) top, (double) bottom, (double) blitOffset).color(k, l, m, j)
                .next();
        bufferBuilder.vertex((double) right, (double) bottom, (double) blitOffset).color(k, l, m, j)
                .next();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public void renderTooltip(List<String> text, int x, int y, MatrixStack matrices,
            VertexConsumerProvider vcp) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (!text.isEmpty()) {
            matrices.push();

            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int i = 0;
            Iterator<String> tooltipIterator = text.iterator();

            while (tooltipIterator.hasNext()) {
                String string = tooltipIterator.next();
                int stringWidth = mc.textRenderer.getWidth(string);
                if (stringWidth > i) {
                    i = stringWidth;
                }
            }

            int k = x + 12;
            int l = y - 12;
            int n = 8;
            if (text.size() > 1) {
                n += 2 + (text.size() - 1) * 10;
            }

            if (k + i > mc.getWindow().getWidth()) {
                k -= 28 + i;
            }

            if (l + n + 6 > mc.getWindow().getHeight()) {
                l = mc.getWindow().getHeight() - n - 6;
            }

            // mc.getItemRenderer().zOffset = 300.0F;
            int o = -267386864;
            int p = 1347420415;
            int q = 1344798847;
            fillGradient(k - 3, l - 4, k + i + 3, l - 3, o, o, 300);
            fillGradient(k - 3, l + n + 3, k + i + 3, l + n + 4, o, o, 300);
            fillGradient(k - 3, l - 3, k + i + 3, l + n + 3, o, o, 300);
            fillGradient(k - 4, l - 3, k - 3, l + n + 3, o, o, 300);
            fillGradient(k + i + 3, l - 3, k + i + 4, l + n + 3, o, o, 300);
            fillGradient(k - 3, l - 3 + 1, k - 3 + 1, l + n + 3 - 1, p, q, 300);
            fillGradient(k + i + 2, l - 3 + 1, k + i + 3, l + n + 3 - 1, p, q, 300);
            fillGradient(k - 3, l - 3, k + i + 3, l - 3 + 1, p, p, 300);
            fillGradient(k - 3, l + n + 2, k + i + 3, l + n + 3, q, q, 300);

            for (int r = 0; r < text.size(); ++r) {
                String string2 = (String) text.get(r);
                if (string2 != null) {
                    mc.textRenderer.draw(string2, (float) k, (float) l, -1, true,
                            matrices.peek().getModel(), vcp, false, 0, 15728880);
                }

                if (r == 0) {
                    l += 2;
                }

                l += 10;
            }
            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
            matrices.pop();
        }
    }


    @Override
    public void render(DeliniumCrucibleLootableContainerBlockEntity blockEntity, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

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
        // Sprite sprite =
        // client.getBlockRenderManager().getModel(blockEntity.getCachedState()).getSprite();

        boolean ticked = this.tick > tickDelta;
        this.tick = tickDelta;
        double offset =
                Math.abs(Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 16.0);
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(),
                blockEntity.getPos().up());
        double distanceToCamera = client.cameraEntity.getPos()
                .squaredDistanceTo(new Vec3d(blockEntity.getPos().getX() + .5f,
                        blockEntity.getPos().getY() + .5f, blockEntity.getPos().getZ() + .5f));
        double renderDistance = 4000D;
        boolean inRenderRange = distanceToCamera < renderDistance;
        boolean renderDynamic = (!client.isPaused() && inRenderRange);
        // renderCube(matrices, vertexConsumers, new Vector3f(.1F, .1F, .1F), new Vector3f(.5f,
        // 1.1f, .5f), null);
        // renderFluid(blockEntity.getPos().up(), (BlockRenderView)client.world,
        // vertexConsumers.getBuffer(RenderLayers.getFluidLayer(Fluids.FLOWING_LAVA.getDefaultState().with(LavaFluid.LEVEL,
        // 8))), Fluids.FLOWING_LAVA.getDefaultState().with(LavaFluid.LEVEL, 8));
        if (renderDynamic && (melting || primed)) {
            // renderTooltip(getItemTooltip(new ItemStack(Items.ENDER_EYE)), 20, 20, matrices,
            // vertexConsumers);
            // renderTextAboveBlockEntity(distanceToCamera, blockEntity, String.join(" ",
            // DeliniumBlock.getBlockName(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK).split("_")),
            // renderDistance, matrices, vertexConsumers, new float[] { 0f, (float)offset+.1f, 0f },
            // 0xF000F0);
            // int shiftUVTicks = (int)(640 / ((Math.random() * 12) + 10) / ((percentage * .28) +
            // 1));
            matrices.push();
            matrices.translate(0f, .5f, 1f);
            matrices.scale(1f, -1f, 1f);
            blockEntity.getLavaModel().renderDynamic(ticked, blockEntity.ticks, matrices,
                    vertexConsumers, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
            /**
             * 
             * spawn portal blocks
             * 
             */
            // matrices.push();

            // // matrices.translate(.5f + (cameraFacing.toString() == "west" ? -1f :
            // cameraFacing.toString() == "east" ? 1f : 0), 1f, .5f + (cameraFacing.toString() ==
            // "north" ? -1f : cameraFacing.toString() == "south" ? 1f : 0));
            // // if (cameraFacing.toString() == "north" || cameraFacing.toString() == "south") {
            // // matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(90));
            // // }
            // matrices.translate(.5f, .75f, .5f);
            // matrices.scale(2.75f, 3.25f, 2.75f);
            // Camera c = client.gameRenderer.getCamera();
            // Quaternion q = c.getVerticalPlane().getDegreesQuaternion(180);
            // q.hamiltonProduct(c.getHorizontalPlane().getDegreesQuaternion(180));
            // q.hamiltonProduct(client.gameRenderer.getCamera().getRotation());
            // q.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            // matrices.multiply(q);

            // blockEntity.getPortalModel().renderDynamic(ticked, blockEntity.ticks, matrices,
            // vertexConsumers, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

            // matrices.pop();


            // rotate portal?
            // PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            // buf.writeBlockPos(blockEntity.getPos());
            // buf.writeFloat(q.getA());
            // buf.writeFloat(q.getB());
            // buf.writeFloat(q.getC());
            // buf.writeFloat(q.getD());
            // ClientSidePacketRegistry.INSTANCE.sendToServer(DeliniumCrucibleLootableContainerBlockEntity.SET_IMMERSIVE_PORTAL_ROTATION,
            // buf);

            if (melting) {
                // System.out.println(blockEntity.smeltableStackIndex);
                if (blockEntity.smeltableStackIndex >= 0
                        && blockEntity.smeltableStackIndex < blockEntity.inventory.size()) {
                    ItemStack stack = blockEntity.getStack(blockEntity.smeltableStackIndex);
                    matrices.push();
                    if (distanceToCamera < 9D) {
                        // System.out.println(distanceToCamera);
                        if (distanceToCamera < 3D) {
                            distanceToCamera = 3D;
                        }
                        matrices.translate(.5,
                                1.75 + (offset - ((float) (10D - distanceToCamera) * .01)), .5);

                        float scale = 1F - ((float) (10D - distanceToCamera) / 10F);
                        matrices.scale(scale, scale, scale);
                    } else {
                        matrices.translate(.5, 1.65 + offset, .5);
                        matrices.scale(1f, 1f, 1f);
                    }
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(
                            (blockEntity.getWorld().getTime() + tickDelta) * 4));
                    client.getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND,
                            0xF000F0, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
                    matrices.pop();
                    DeliniumCrucibleConversion conversion =
                            DeliniumCrucibleConversion.smeltConversions.get(stack.getItem());
                    if (conversion != null) {
                        matrices.push();
                        String text = stack.getName().asString() + " (" + stack.getCount() + ")";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text,
                                renderDistance, matrices, vertexConsumers,
                                new float[] {0f, (float) offset + .1f, 0f}, 0xF000F0);
                        text = (new ItemStack(conversion.product)).getName().asString() + " ("
                                + (stack.getCount() / conversion.per) * conversion.count + ")";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text,
                                renderDistance, matrices, vertexConsumers,
                                new float[] {0f, (float) (offset - .15f), 0f}, 0xF000F0);
                        text = percentage + "/10";
                        renderTextAboveBlockEntity(distanceToCamera, blockEntity, text,
                                renderDistance, matrices, vertexConsumers,
                                new float[] {0f, (float) (offset - .4f), 0f}, 0xF000F0);
                        matrices.pop();
                    }
                }
            }
        }



//        matrices.push();

//        Vec3d pos = client.gameRenderer.getCamera().getPos();
//        float relativeX = (float) (blockEntity.getPos().getX() - pos.x);
//        float relativeY = (float) (blockEntity.getPos().getY() - pos.y);
//        float relativeZ = (float) (blockEntity.getPos().getZ() - pos.z);
//        matrices.translate(0, 3, 0);
//        matrices.scale(0.5f, 0.5f, 0.5f);
//        matrices.scale(10f, 10f, 10f);
//        float angle = ((Objects.requireNonNull(blockEntity.getWorld()).getTime()) / 4f) % 360f;
//        matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(angle));
//        matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(90));

        // Generates vertrices
//        calcVerticesIcoSphere(20, 10);

//        Renderer renderer = RendererAccessImpl.INSTANCE.getRenderer();
//        MeshBuilder meshBuilder = renderer.meshBuilder();
//        QuadEmitter emitter = meshBuilder.getEmitter();
//        Sprite sprite = DeliniumCrucibleLavaModel.sprite.getSprite();
//        for (int i = 0; i < vertices.size(); i+=3) {
//            for (int j = 0; j < 3; j++) {
//                Vector3f vec = vertices.get(i+j);
//                Vector3f uv = uvs.get(i+j);
//                emitter.pos(j, vec.getX(), vec.getY(), vec.getZ())
//                        .cullFace(Direction.UP)
//                        .sprite(j, 0, ((sprite.getMaxU() - sprite.getMinU()) * uv.getX()) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * uv.getY()) + sprite.getMinV());
//            }
//            Vector3f vec = vertices.get(i);
//            Vector3f uv = uvs.get(i);
//            emitter.pos(3, vec.getX(), vec.getY(), vec.getZ())
//                    .sprite(3, 0, ((sprite.getMaxU() - sprite.getMinU()) * uv.getX()) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * uv.getY()) + sprite.getMinV());
//            emitter.emit(); // to add the vertexs
//        }
//        emitter.pos(0, 0, 0, 0).sprite(0, 0, ((sprite.getMaxU() - sprite.getMinU()) * 0) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * 0) + sprite.getMinV()); // vertex index, xcoord, ycoord, zcoord (index can only be from 0-4)
//        emitter.pos(1, 1, 0, 0).sprite(0, 0, ((sprite.getMaxU() - sprite.getMinU()) * 0) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * 0) + sprite.getMinV());
//        emitter.pos(2, 1, 1, 0).sprite(0, 0, ((sprite.getMaxU() - sprite.getMinU()) * 0) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * 0) + sprite.getMinV());
//        emitter.pos(3, 0, 1, 0).sprite(0, 0, ((sprite.getMaxU() - sprite.getMinU()) * 0) + sprite.getMinU(), ((sprite.getMaxV() - sprite.getMinV()) * 0) + sprite.getMinV());
//        emitter.pos(0, 0, 0, 0).spriteBake(0, sprite, 0); // vertex index, xcoord, ycoord, zcoord (index can only be from 0-4)
//        emitter.pos(1, 1, 0, 0).spriteBake(0, sprite, 0);
//        emitter.pos(2, 1, 1, 0).spriteBake(0, sprite, 0);
//        emitter.pos(3, 0, 1, 0).spriteBake(0, sprite, 0);


//        emitter.emit(); // to add the vertex
//        Mesh m = meshBuilder.build();
//        List<BakedQuad>[] quadList = ModelHelper.toQuadLists(m);
//        VertexConsumer vc = DeliniumCrucibleLavaModel.sprite.getVertexConsumer(vertexConsumers, (id) -> {
//            RenderLayer r = RenderLayer.getEntityTranslucent(id);
////            return r;
//            return DeliriumClientMod.FBM.getRenderLayer(r); // satin render layer
//        });
//        for (int x = 0; x < quadList.length; x++) {
//            for (BakedQuad bq : quadList[x]) {
//                vc.quad(matrices.peek(), bq, .5f, .5f, .5f, 0x00F000B0, OverlayTexture.DEFAULT_UV); // light = 0x00F000B0 (emissive)
//            }
//        }
//
//        matrices.pop();

        // if ((melting)) {

        // String text = "x"+stack.getCount();
        // System.out.println();

        // matrices.multiply(Vector3f.POSITIVE_Y
        // .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
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
        // matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime()
        // + tickDelta) * 4));
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
        // emitter.pos(0, 0f, 0f, 0f).pos(1, 0f, 0f, 1f).pos(2, 1f, 0f, 1f).pos(3, 1f, 0f,
        // 0f).emit(); //top quad
        // emitter.pos(0, 0f, 0f, 0f).pos(1, 1f, 0f, 0f).pos(2, 1f, 0f, 1f).pos(3, 0f, 0f,
        // 1f).emit(); //bottom quad
        // float cosx, sinx, height = 0F;
        // float radius = 2.89f;
        // for (int x = 0; x < 90; x++) {
        // draw cylinder;
        // cosx = cosAngle(x)/radius;
        // sinx = sinAngle(x)/radius;
        // emitter.material(renderer.materialFinder()
        // .blendMode(0,
        // BlendMode.fromRenderLayer(
        // RenderLayers.getBlockLayer(blockEntity.getCachedState())))
        // .find());
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
        // for (BakedQuad bq : quadList[x]) {
        // // vc.quad(matrices.peek(), bq, .5f, .5f, .5f, lightAbove, OverlayTexture.DEFAULT_UV);
        // vc.quad(matrices.peek(), bq, .5f, .5f, .5f, 0x00F000B0, OverlayTexture.DEFAULT_UV);
        // }
        // }
        // matrices.pop();
        // matrices.push();
        // matrices.translate(.5, 1.1 + offset, .5);
        // matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
        // matrices.multiply(Vector3f.NEGATIVE_Y
        // .getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
        // DeliniumCrucibleModel.MODEL.render(matrices, new
        // SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(DeliriumMod.MODID,
        // "block/delinium_crucible")).getVertexConsumer(vertexConsumers,
        // RenderLayer::getEntitySolid), 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F,
        // 1.0F);
        // matrices.pop();
        // }

    }

}
