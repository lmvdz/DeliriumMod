package net.lmvdz.delirium.client;

import java.util.ArrayList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleContainer;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLootableContainerBlockEntityRenderer;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleScreen;
import net.lmvdz.delirium.item.DeliriumItemTooltip;
import net.lmvdz.delirium.item.DeliriumItemTooltipCallback;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * FabricRPG
 */
public class DeliriumClientMod implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        // Delirium Items Tooltip Callback
        DeliriumItemTooltipCallback.EVENT.register((stack, player, tooltipContext, components) -> {
            if (stack != null && tooltipContext != null && components != null) {
                if (player.world.isChunkLoaded(player.getBlockPos())) {
                    // boolean isDeliriumItem = (stack.getItem() instanceof DeliriumItem);
                    if (stack.getMaxDamage() > 0) {
                        components.addAll(DeliriumItemTooltip
                                .addDurabilityOfItemStackToTooltip(new ArrayList<Text>(), stack));
                    }
                }
            }
        });
        
        
        // Register DeliniumCrucibleBlock Models
        // ModelLoadingRegistry.INSTANCE.registerVariantProvider(manager -> DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK);
        // DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK.registerModels((blockState) -> {
        //     return new DeliniumCrucibleModel(blockState);
        // });
        // ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> provider);
        
        
        
        // Register Block Entity Renderer - Delinium Crucible Block Entity Renderer
        BlockEntityRendererRegistry.INSTANCE.register(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE, DeliniumCrucibleLootableContainerBlockEntityRenderer::new);
        // Register Screen Provider - Delinium Crucible Screen
        ScreenProviderRegistry.INSTANCE.<DeliniumCrucibleContainer>registerFactory
            (DeliniumCrucible.getIdentifier(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK), 
            (container) -> new DeliniumCrucibleScreen(
                container, 
                MinecraftClient.getInstance().player.inventory, 
                FormattingEngine.replaceColorCodeInTranslatableText(
                    new TranslatableText(
                        DeliniumCrucible.DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY
                    )
                )
            )
        );
    }
}