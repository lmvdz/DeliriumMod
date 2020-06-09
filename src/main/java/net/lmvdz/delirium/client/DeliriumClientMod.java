package net.lmvdz.delirium.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLootableContainerBlockEntityRenderer;
import net.lmvdz.delirium.font.CustomFontTextRenderer;
import net.lmvdz.delirium.item.CustomItemTooltip;
import net.lmvdz.delirium.api.event.ItemTooltipCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

//import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.shader.ShaderEffectRenderLayer;
import net.lmvdz.delirium.shader.ShaderProgramRenderLayer;

/**
 * FabricRPG
 */
public class DeliriumClientMod implements ClientModInitializer {

    public final static String MODID = "delirium";
    public static ShaderEffectRenderLayer ExampleShaderEffectRenderLayer;

    public static ShaderProgramRenderLayer FBM = new ShaderProgramRenderLayer(MODID, "test", (program) -> {
        System.out.println("ShaderProgram -- test -- FBM");
    });

    public static final Identifier fontIdentifier = new Identifier("delirium", "test");
//    public static final Identifier fontIdentifier2 = new Identifier("delirium", "test2");

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
//        MinecraftClient client = MinecraftClient.getInstance();
//        FontManager fontManager = ((MinecraftClientAccess)client).getFontManager();
//        System.out.println(((FontManagerAccess)fontManager).getFontStorages().size());
        // ReadableDepthFramebuffer.useFeature();

        CustomFontTextRenderer.of(fontIdentifier, true);

        // PostWorldRenderCallback.EVENT.register(DepthFx.INSTANCE);
        // PostWorldRenderLayerCallback.EVENT.register(ShaderRenderLayerFx.INSTANCE);
        // the render method of the shader will be called after the game
        // has drawn the world on the main framebuffer, when it renders
        // vanilla post process shaders
        // ShaderEffectRenderCallback.EVENT.register(TEST::render);

        // Delirium Items Tooltip Callback
        ItemTooltipCallback.EVENT.register((stack, player, tooltipContext, components) -> {
            if (stack != null && tooltipContext != null && components != null) {
                if (player.world.isChunkLoaded(player.getBlockPos())) {
                    // boolean isDeliriumItem = (stack.getItem() instanceof DeliriumItem);
                    if (stack.getMaxDamage() > 0) {
                        components.addAll(CustomItemTooltip
                                .addDurabilityOfItemStackToTooltip(new ArrayList<Text>(), stack));
                    }
                }
            }
        });
//        ExampleShaderEffectRenderLayer =
//                new ShaderEffectRenderLayer(MODID, "illusion", effect -> {
//                    System.out.println("Example Shader Effect Render Layer");
//                });


        // Register DeliniumCrucibleBlock Models
        // ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager ->
        // DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK);
        // DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK.registerModels((blockState) -> {
        // return new DeliniumCrucibleModel(blockState);
        // });
        // ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> provider);

//        EntityRendererRegistry.INSTANCE.register(RotatingPortal.entityType, (entityRenderDispatcher,
//                context) -> new PortalEntityRenderer<RotatingPortal>(entityRenderDispatcher));

        // Register Block Entity Renderer - Delinium Crucible Block Entity Renderer
        BlockEntityRendererRegistry.INSTANCE.register(
                DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE,
                DeliniumCrucibleLootableContainerBlockEntityRenderer::new);
        // BlockRenderLayerMap.INSTANCE.putBlock(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK,
        // RenderLayer.getCutout());
        // Register Screen Provider - Delinium Crucible Screen
//        ScreenProviderRegistry.INSTANCE.<DeliniumCrucibleContainer>registerFactory(
//                DeliniumCrucible.getIdentifier(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK),
//                (DeliniumCrucibleContainer container) -> new DeliniumCrucibleContainerScreen(container,
//                        MinecraftClient.getInstance().player,
//                        FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText(
//                                DeliniumCrucible.DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY))));

        // ClientSidePacketRegistry.INSTANCE.register(DeliniumCrucibleLootableContainerBlockEntity.SET_CLIENT_IMMERSIVE_PORTAL_PACKET,
        // (packetContext, attachedData) -> {
        // BlockPos pos = attachedData.readBlockPos();
        // double axisH_X = attachedData.readDouble();
        // double axisH_Y = attachedData.readDouble();
        // double axisH_Z = attachedData.readDouble();

        // double axisW_X = attachedData.readDouble();
        // double axisW_Y = attachedData.readDouble();
        // double axisW_Z = attachedData.readDouble();
        // packetContext.getTaskQueue().execute(() -> {
        // World w = packetContext.getPlayer().world;
        // BlockEntity be = w.getBlockEntity(pos);
        // Vec3d axisH = new Vec3d(axisH_X,axisH_Y,axisH_Z);
        // Vec3d axisW = new Vec3d(axisW_X,axisW_Y,axisW_Z);
        // if (be instanceof DeliniumCrucibleLootableContainerBlockEntity) {
        // DeliniumCrucibleLootableContainerBlockEntity de_be =
        // (DeliniumCrucibleLootableContainerBlockEntity)be;
        // Box b = packetContext.getPlayer().getBoundingBox().expand(500);
        // w.getEntities(RotatingPortal.entityType, b, p -> {
        // return p instanceof RotatingPortal;
        // }).forEach(portal -> {
        // // portal.axisH = axisH;
        // // portal.axisW = axisW;
        // });
        // }


        // });
        // });
    }
}
