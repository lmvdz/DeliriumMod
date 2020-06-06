package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class DeliniumCrucibleContainer extends GenericContainerScreenHandler {

    public DeliniumCrucibleContainer(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
        super(type, syncId, playerInventory, inventory, rows);
    }

}