package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.portal.PlatonicSolidPortal;
import net.lmvdz.delirium.portal.RotatingPortal;
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
    private DeliniumCruciblePortalModel PORTAL_MODEL;
    public RotatingPortal IMMERSIVE_PORTAL;
    public PlatonicSolidPortal PLATONIC_PORTAL;

    // public static final Identifier SPAWN_IMMERSIVE_PORTAL_PACKET_ID =
    //         new Identifier(DeliriumMod.MODID, "SPAWN_IMMERSIVE_PORTAL");
    // public static final Identifier SET_CLIENT_IMMERSIVE_PORTAL_PACKET =
    //         new Identifier(DeliriumMod.MODID, "SET_CLIENT_IMMERSIVE_PORTAL");
    // public static final Identifier SET_IMMERSIVE_PORTAL_ROTATION =
    //         new Identifier(DeliriumMod.MODID, "SET_IMMERSIVE_PORTAL_ROTATION");

    public DeliniumCrucibleInventory inventory;

    public DeliniumCrucibleLootableContainerBlockEntity() {
        super(DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE);
        this.inventory = new DeliniumCrucibleInventory();
        LAVA_MODEL = new DeliniumCrucibleLavaModel();
        PORTAL_MODEL = new DeliniumCruciblePortalModel();
    }


    public DeliniumCruciblePortalModel getPortalModel() {
        return PORTAL_MODEL;
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
        tag.putInt("smeltableStackIndex", this.smeltableStackIndex);
        tag.putInt("ticks", this.ticks);
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
        return FormattingEngine.replaceColorCodeInTranslatableText(
                new TranslatableText("container." + DeliriumMod.MODID + "."
                        + DeliniumCrucible.DELINIUM_CRUCIBLE_BLOCK.getTranslationKey()));
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
                    DeliniumCrucibleConversion conversion =
                            DeliniumCrucibleConversion.smeltConversions.get(stack.getItem());
                    if (conversion != null) {
                        if (stack.getCount() >= (int) (conversion.per / rank)) {
                            this.smeltableStackIndex = i;
                            world.setBlockState(this.getPos(),
                                    world.getBlockState(this.getPos())
                                            .with(DeliniumCrucible.MELTING, true)
                                            .with(DeliniumCrucible.PRIMED, false));
                            this.ticks = 0;
                            System.out.println("SMELTING");
                        }
                    }
                }
            }
            if (this.smeltableStackIndex == -2) {
                this.smeltableStackIndex = -1;
            }
        }
    }

    public void finishSmelt(BlockState state) {
        if (this.smeltableStackIndex > -1 && this.smeltableStackIndex < inventory.getInvSize()) {
            ItemStack smeltableStack = getInvStack(this.smeltableStackIndex);
            DeliniumCrucibleConversion conversion =
            DeliniumCrucibleConversion.smeltConversions.get(smeltableStack.getItem());
            if (smeltableStack != null && conversion != null && rank != 0 && conversion.per != 0) {
                int numberOfProducts =
                        (int) Math.floor((smeltableStack.getCount() / (conversion.per / rank)));
                // create converted delinium stack
                ItemStack productStack = new ItemStack(conversion.product, numberOfProducts);
                // remove (conversion / rank) amount of delinium
                takeInvStack(this.smeltableStackIndex,
                        numberOfProducts * (int) (conversion.per / rank));
                if (getInvStack(this.smeltableStackIndex).isEmpty()
                        || getInvStack(this.smeltableStackIndex)
                                .getCount() < (int) (conversion.per / rank)) {
                    this.smeltableStackIndex = -1;
                }
                // place ingots into crucible or drop it to the ground
                drop(productStack);
            }
            this.ticks = -1;
        }
    }

    @Override
    public void tick() {
        BlockState state = this.world.getBlockState(this.getPos());
        if (this.world.isChunkLoaded(this.getPos())) {
            boolean melting = DeliniumCrucible.getMeltingFromBlockState(state);
            boolean primed = DeliniumCrucible.getCanMeltFromBlockState(state);
            // System.out.println(melting + " " + primed);
            if (primed || melting) {
                if (this.ticks >= 0 && this.ticks <= 1280) {
                    this.ticks++;
                    if (melting) {
                        this.world.setBlockState(this.getPos(),
                                state.with(DeliniumCrucible.PERCENTAGE, (int) (this.ticks / 128)));
                        int percentage = DeliniumCrucible.getPercentageFromBlockState(state);
                        if (percentage == 10) {
                            this.ticks = -2;
                            this.world.setBlockState(this.getPos(),
                                    state.with(DeliniumCrucible.MELTING, false)
                                            .with(DeliniumCrucible.PRIMED, true)
                                            .with(DeliniumCrucible.PERCENTAGE, 0));
                            this.finishSmelt(state);
                        }
                    }
                } else if (this.ticks == -1 || this.ticks > 1280) {
                    this.ticks = 0;
                }
            }
        }
    }

}