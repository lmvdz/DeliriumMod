package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

import java.util.stream.IntStream;

public class DeliniumCrucibleInventory implements SidedInventory {
    private static final int INVENTORY_SIZE = 54; // 9 * 6 = 54
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, INVENTORY_SIZE).toArray();
    private DefaultedList<ItemStack> items;
    public DeliniumCrucibleInventory() {
        this.items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    }


    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public void setItems(DefaultedList<ItemStack> items) {
        this.items = items;
        this.markDirty();
    }

    @Override
    public void clear() {
        getItems().clear();
    }

    // smelt when inventory is closed.

    @Override
    public void onClose(PlayerEntity player) {
        SidedInventory.super.onClose(player);
    }

    @Override
    public int size() {
        return getItems().size();
    }



    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            final ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public int getFirstEmptySlot() {
        for (int i = 0; i < size(); i++) {
            final ItemStack stack = getStack(i);
            if (stack.isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public ItemStack getStack(final int slot) {
        return getItems().get(slot);
    }

    @Override
    public ItemStack removeStack(final int slot, final int amount) {
        final ItemStack result = Inventories.splitStack(getItems(), slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(final int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    public int[] firstIndexOrLastIndexAndHowManyDontFit(final ItemStack stack) {
        int[] firstLastDontFit = {-1, -1};
        for (int i = 0; i < size(); i++) {
            final ItemStack invStk = getStack(i);
            if ((invStk.getItem().equals(DeliniumIngot.DELINIUM_INGOT)
                    && invStk.getCount() < stack.getMaxCount()) || invStk.isEmpty()) {
                if (invStk.getCount() + stack.getCount() <= stack.getMaxCount()) {
                    firstLastDontFit[0] = i; // index of non full stack
                    return firstLastDontFit;
                } else {
                    firstLastDontFit[0] = i; // index of non full stack
                    firstLastDontFit[1] = invStk.getCount() - stack.getMaxCount() + stack.getCount(); // how many don't fit
                    return firstLastDontFit;
                }
            }
        }
        return firstLastDontFit;
    }

    @Override
    public void setStack(final int slot, final ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        this.markDirty();
    }

    @Override
    public boolean canPlayerUse(final PlayerEntity player) {
        return true;
    }

    @Override
    public void markDirty() {
    }


    @Override
    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }


    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return true;
    }


    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

}