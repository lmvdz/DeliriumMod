package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class DeliniumCrucibleContainer extends Container {
            
    public static final int INVENTORY_SIZE = 54; // 6 rows * 9 cols
    
    public DeliniumCrucibleInventory crucibleInventory;

    protected DeliniumCrucibleContainer(int syncId, PlayerInventory playerInventory, DeliniumCrucibleInventory crucibleInventory) {
        super(null, syncId);

        this.crucibleInventory = crucibleInventory;
        checkContainerSize(crucibleInventory, INVENTORY_SIZE);
        this.crucibleInventory.onInvOpen(playerInventory.player);


        int o;
        int n;

        // Chest Inventory 
        for (o = 0; o < 6; ++o) {
            for (n = 0; n < 9; ++n) {
                this.addSlot(new DeliniumCrucibleSlot(this.crucibleInventory, o * 9 + n, 8 + n * 18, 18 + o * 18));
            }
        }
        

        // Player Inventory (27 storage + 9 hotbar)
        for (o = 0; o < 3; ++o) {
            for (n = 0; n < 9; ++n) {
                this.addSlot(new Slot(playerInventory, o * 9 + n + 9, 8 + n * 18, 18 + o * 18 + 103 + 18));
            }
        }
        // hotbar
        for (o = 0; o < 9; ++o) {
            this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 18 + 161 + 18));
        }
    }


    @Override
    public boolean canUse(final PlayerEntity player) {
        return this.crucibleInventory.canPlayerUseInv(player);
    }


    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot < this.crucibleInventory.getInvSize()) {
                if (!this.insertItem(itemStack2, this.crucibleInventory.getInvSize(),
                        this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.crucibleInventory.getInvSize(),
                    false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void close(final PlayerEntity player) {
        super.close(player);
        this.crucibleInventory.onInvClose(player);
    }

}