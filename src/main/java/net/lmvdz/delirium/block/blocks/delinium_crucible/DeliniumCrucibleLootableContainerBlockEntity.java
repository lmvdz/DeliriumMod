package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.util.FormattingEngine;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;

/**
 * BlockEntity
 */
public class DeliniumCrucibleLootableContainerBlockEntity extends LootableContainerBlockEntity implements Tickable {
    private int ticks = 0;
    public int smeltableStackIndex = -1;
    private int rank = 1;


    public DeliniumCrucibleInventory inventory;

    public DeliniumCrucibleLootableContainerBlockEntity() {
        super(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE);
        this.inventory = new DeliniumCrucibleInventory();
    }


    // Serialize the BlockEntity
    @Override
    public CompoundTag toTag(final CompoundTag tag) {
        super.toTag(tag);
        // Save the current value of the number to the tag
        tag.putInt("rank", rank);
        BlockState state = this.world.getBlockState(this.getPos());
        tag.putBoolean("melting", DeliniumCrucible.getMeltingFromBlockState(state));
        tag.putBoolean("primed", DeliniumCrucible.getCanMeltFromBlockState(state));
        tag.putInt("percentage", DeliniumCrucible.getPercentageFromBlockState(state));
        return this.serializeInventory(tag);
    }

    public CompoundTag serializeInventory(CompoundTag tag) {
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory.getItems());
        }
        return tag;
    }

    // Deserialize the BlockEntity
    @Override
    public void fromTag(final CompoundTag tag) {
        super.fromTag(tag);
        rank = tag.getInt("rank");
        this.world.setBlockState(this.getPos(), this.world.getBlockState(this.getPos()).with(DeliniumCrucible.MELTING, tag.getBoolean("melting")).with(DeliniumCrucible.PERCENTAGE, tag.getInt("percentage")).with(DeliniumCrucible.PRIMED, tag.getBoolean("primed")));
        this.deserializeInventory(tag);
    }

    public void deserializeInventory(CompoundTag tag) {
        this.inventory = new DeliniumCrucibleInventory();
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, inventory.getItems());
        }
    }

    @Override
    public int getInvSize() {
        return inventory.getInvSize();
    }

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory.getItems();
    }

    @Override
    public void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory.setItems(list);
    }

    @Override
    public Text getContainerName() {
        return FormattingEngine.replaceColorCodeInTranslatableText(new TranslatableText("container."
                + DeliriumMod.MODID + "." + DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK.getTranslationKey()));
    }

    @Override
    public Container createContainer(int i, PlayerInventory playerInventory) {
        return new DeliniumCrucibleContainer(i, playerInventory, this.inventory);
    }


    public void addToInvOrDrop(ItemStack stack) {
        int[] firstLastDontFit = inventory.firstIndexOrLastIndexAndHowManyDontFit(stack);
        if (firstLastDontFit[0] != -1) {
            ItemStack invSlot = getInvStack(firstLastDontFit[0]);
            stack.setCount(invSlot.getCount() + stack.getCount());
            if (firstLastDontFit[1] != -1) {
                stack.setCount(stack.getMaxCount());
                addToInvOrDrop(new ItemStack(stack.getItem(), firstLastDontFit[1]));
            }
            setInvStack(firstLastDontFit[0], stack);
        } else {
            this.world.spawnEntity(new ItemEntity(this.world, this.getPos().getX(),
                    this.getPos().getY(), this.getPos().getZ(), stack));
        }
    }

    public void tryToSmelt() {
        if (!this.isInvEmpty()) {
            this.smeltableStackIndex = -1;
            DeliniumCrucibleConversion conversion = null;
            findSmeltableStack: for (int i = 0; i < this.getInvSize(); i++) {
                ItemStack stack = this.getInvStack(i);
                if (!stack.isEmpty()) {
                    conversion = DeliniumCrucible.smeltConversions.get(stack.getItem());
                    System.out.println(conversion.product.toString());
                    if (conversion != null) {
                        this.smeltableStackIndex = i;
                        break findSmeltableStack;
                    }
                }
            }
            if (this.smeltableStackIndex != -1 && conversion != null) {
                ItemStack smeltableStack = getInvStack(this.smeltableStackIndex);
                if (smeltableStack.getCount() >= (int) (conversion.per / rank)) {
                    this.ticks = 0;
                    this.world.setBlockState(this.getPos(), this.world.getBlockState(this.getPos()).with(DeliniumCrucible.MELTING, true).with(DeliniumCrucible.PRIMED, false));
                }
            }
        }
    }

    public void finishSmelt() {
        ItemStack smeltableStack = getInvStack(this.smeltableStackIndex);
        DeliniumCrucibleConversion conversion = DeliniumCrucible.smeltConversions.get(smeltableStack.getItem());
        int numberOfProducts = (int) Math.floor((smeltableStack.getCount() / (conversion.per / rank)));
        // create converted delinium stack
        ItemStack productStack = new ItemStack(conversion.product, numberOfProducts);
        // remove (conversion / rank) amount of delinium
        takeInvStack(smeltableStackIndex, numberOfProducts * (int) (conversion.per / rank));
        if (getInvStack(smeltableStackIndex).isEmpty()
                || getInvStack(smeltableStackIndex).getCount() < (int) (conversion.per / rank)) {
            smeltableStackIndex = -1;
        }
        // place ingots into crucible or drop it to the ground
        addToInvOrDrop(productStack);
        this.markDirty();
        this.world.setBlockState(this.getPos(),
                this.world.getBlockState(this.getPos()).with(DeliniumCrucible.MELTING, false));
        this.world.setBlockState(this.getPos(),
                this.world.getBlockState(this.getPos()).with(DeliniumCrucible.PERCENTAGE, 0));
    }

    @Override
    public void tick() {
        if (this.world.isChunkLoaded(this.getPos())) {
            BlockState state = this.world.getBlockState(this.getPos());
            if (!DeliniumCrucible.getMeltingFromBlockState(state)) {
                if (DeliniumCrucible.getCanMeltFromBlockState(state)) {
                    this.tryToSmelt();
                }
            } else {
                this.ticks++;
                if (this.ticks <= 1280) {
                    this.world.setBlockState(this.getPos(), state.with(DeliniumCrucible.PERCENTAGE, (this.ticks / 128)));
                }
                if (DeliniumCrucible.getPercentageFromBlockState(state) >= 10) {
                    this.ticks = 0;
                    this.finishSmelt();
                }
            }
        }
    }

}