package net.lmvdz.delirium.client;

import java.util.ArrayList;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.lmvdz.delirium.block.blocks.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.DeliniumCrucible.DeliniumCrucibleLootableContainerBlockEntity.DeliniumCrucibleContainer;
import net.lmvdz.delirium.block.blocks.DeliniumCrucible.DeliniumCrucibleLootableContainerBlockEntity.DeliniumCrucibleScreen;
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
        DeliriumItemTooltipCallback.EVENT.register((stack, player, tooltipContext, components) -> {
            if (stack != null && tooltipContext != null && components != null) {
                if (player.world.isChunkLoaded(player.getBlockPos())) {
                    // boolean isDeliriumItem = (stack.getItem() instanceof DeliriumItem);
                    if (stack.getMaxDamage() > 0) {
                        components.addAll(DeliriumItemTooltip.addDurabilityOfItemStackToTooltip(new ArrayList<Text>(), stack));
                    }
                }
            }
        });

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