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
import net.minecraft.world.World;

/**
 * BlockEntity
 */
public class DeliniumCrucibleLootableContainerBlockEntity extends LootableContainerBlockEntity implements Tickable {
    public int ticks = 0;
    private int rank = 1;
    public int smeltableStackIndex = -1;
    private DeliniumCrucibleLavaModel LAVA_MODEL;

    public DeliniumCrucibleInventory inventory;

    public DeliniumCrucibleLootableContainerBlockEntity() {
        super(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE);
        this.inventory = new DeliniumCrucibleInventory();
        LAVA_MODEL = new DeliniumCrucibleLavaModel();
    }

    public DeliniumCrucibleLavaModel getLavaModel() {
        return LAVA_MODEL;
    }


    // Serialize the BlockEntity
    @Override
    public CompoundTag toTag(final CompoundTag tag) {
        super.toTag(tag);
        // Save the current value of the number to the tag
        tag.putInt("rank", this.rank);
        // BlockState state = this.world.getBlockState(this.getPos());
        tag.putInt("smeltableStackIndex", this.smeltableStackIndex);
        tag.putInt("ticks", this.ticks);
        // tag.putBoolean("melting", DeliniumCrucible.getMeltingFromBlockState(state));
        // tag.putBoolean("primed", DeliniumCrucible.getCanMeltFromBlockState(state));
        // tag.putInt("percentage", DeliniumCrucible.getPercentageFromBlockState(state));
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
        this.rank = tag.getInt("rank");
        this.smeltableStackIndex = tag.getInt("smeltableStackIndex");
        this.ticks = tag.getInt("ticks");
        // BlockPos pos = this.getPos();
        // System.out.println(pos);
        // BlockState blockState = DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK.getDefaultState().with(DeliniumCrucible.MELTING, tag.getBoolean("melting")).with(DeliniumCrucible.PERCENTAGE, tag.getInt("percentage")).with(DeliniumCrucible.PRIMED, tag.getBoolean("primed"));
        // System.out.println(blockState);
        // this.world.setBlockState(pos, blockState);
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
            drop(stack);
        }
    }

    public void drop(ItemStack stack) {
        this.world.spawnEntity(new ItemEntity(this.world, this.getPos().getX(),
                    this.getPos().getY(), this.getPos().getZ(), stack));
    }

    public void tryToSmelt(World world) {
        if (!this.isInvEmpty() && this.smeltableStackIndex == -1) {
            this.smeltableStackIndex = -2;
            for (int i = 0; i < this.getInvSize(); i++) {
                ItemStack stack = this.getInvStack(i);
                if (!stack.isEmpty() && this.smeltableStackIndex == -2) {
                    DeliniumCrucibleConversion conversion = DeliniumCrucible.smeltConversions.get(stack.getItem());
                    if (conversion != null) {
                        if (stack.getCount() >= (int) (conversion.per / rank)) {
                            this.smeltableStackIndex = i;
                            world.setBlockState(this.getPos(), world.getBlockState(this.getPos()).with(DeliniumCrucible.MELTING, true).with(DeliniumCrucible.PRIMED, false));
                            this.ticks = 0;
                            System.out.println("SMELTING");
                        }
                    }
                }
            }
            System.out.println(this.smeltableStackIndex);
            if (this.smeltableStackIndex == -2) {
                this.smeltableStackIndex = -1;
            }
        }
    }

    public void finishSmelt() {
        if (this.smeltableStackIndex > -1 && this.smeltableStackIndex < inventory.getInvSize()) {
            ItemStack smeltableStack = getInvStack(this.smeltableStackIndex);
            DeliniumCrucibleConversion conversion = DeliniumCrucible.smeltConversions.get(smeltableStack.getItem());
            if (smeltableStack != null && conversion != null) {
                int numberOfProducts = (int) Math.floor((smeltableStack.getCount() / (conversion.per / rank)));
                // create converted delinium stack
                ItemStack productStack = new ItemStack(conversion.product, numberOfProducts);
                // remove (conversion / rank) amount of delinium
                takeInvStack(this.smeltableStackIndex, numberOfProducts * (int) (conversion.per / rank));
                if (getInvStack(this.smeltableStackIndex).isEmpty() || getInvStack(this.smeltableStackIndex).getCount() < (int) (conversion.per / rank)) {
                            this.smeltableStackIndex = -1;
                }
                // place ingots into crucible or drop it to the ground
                drop(productStack);
                this.world.setBlockState(this.getPos(), this.world.getBlockState(this.getPos()).with(DeliniumCrucible.MELTING, false));
                this.world.setBlockState(this.getPos(), this.world.getBlockState(this.getPos()).with(DeliniumCrucible.PRIMED, true));
                this.world.setBlockState(this.getPos(), this.world.getBlockState(this.getPos()).with(DeliniumCrucible.PERCENTAGE, 0));
            }
        }
    }

    @Override
    public void tick() {
        BlockState state = this.world.getBlockState(this.getPos());
        if (this.world.isChunkLoaded(this.getPos())) {
            boolean melting = DeliniumCrucible.getMeltingFromBlockState(state);
            boolean primed = DeliniumCrucible.getCanMeltFromBlockState(state);
            if (primed || melting) {
                if (primed && !melting && this.ticks == -1) {
                    this.ticks = 0;
                }
                if (this.ticks >= 0 && this.ticks <= 1280) {
                    this.ticks++;
                    if (melting) {
                        this.world.setBlockState(this.getPos(), state.with(DeliniumCrucible.PERCENTAGE, (int)(this.ticks / 128)));
                        int percentage = DeliniumCrucible.getPercentageFromBlockState(state);
                        if (percentage == 10) {
                            this.finishSmelt();
                            this.ticks = -1;
                        }
                    }
                } else if (this.ticks != -1){
                    this.ticks = 0;
                }
            }
        }
    }

}