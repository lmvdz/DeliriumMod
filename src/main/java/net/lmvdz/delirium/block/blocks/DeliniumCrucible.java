package net.lmvdz.delirium.block.blocks;

import java.util.ArrayList;
import java.util.stream.IntStream;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.registry.Registry;


/**
 * DeliniumCrucible
 * 
 * Used to melt Delinium
*/
public class DeliniumCrucible extends DeliriumBlock implements BlockEntityProvider {
    public static DeliniumCrucible DELINIUM_CRUCIBLE_BLOCK;
    public static String DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY; 
    public static Identifier DELINIUM_CRUCIBLE_BLOCK_ENTITY;
    public static BlockEntityType<DeliniumCrucibleLootableContainerBlockEntity> DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE;
    public static BooleanProperty MELTING = BooleanProperty.of("melting");

    public DeliniumCrucible() {
        // setup material and render layer
        super(FabricBlockSettings.of(Delinium.MAP_MATERIAL).nonOpaque().build(),
                RenderLayer.getSolid());

        if (DELINIUM_CRUCIBLE_BLOCK == null) {
            DELINIUM_CRUCIBLE_BLOCK = this;
            setDefaultState(getStateManager().getDefaultState().with(MELTING, false)
                .with(Properties.HORIZONTAL_FACING, Direction.NORTH));
            registerDeliriumBlock(DELINIUM_CRUCIBLE_BLOCK);
            ContainerProviderRegistry.INSTANCE.registerFactory(getIdentifier(DELINIUM_CRUCIBLE_BLOCK), (syncId, identifier, player, buf) -> {
                final BlockEntity blockEntity = player.world.getBlockEntity(buf.readBlockPos());
                return((DeliniumCrucibleLootableContainerBlockEntity) blockEntity).createContainer(syncId, player.inventory);
            });
            DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY = Util.createTranslationKey("container", getIdentifier(DELINIUM_CRUCIBLE_BLOCK));
            DELINIUM_CRUCIBLE_BLOCK_ENTITY = new Identifier(DeliriumMod.MODID, "delinium_crucible_block_entity");
            DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE =
                Registry.register(Registry.BLOCK_ENTITY_TYPE, DELINIUM_CRUCIBLE_BLOCK_ENTITY,
                        BlockEntityType.Builder.create(DeliniumCrucibleLootableContainerBlockEntity::new,
                                DELINIUM_CRUCIBLE_BLOCK).build(null));
        }
    }

    @Override
    public void appendProperties(final StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MELTING);
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    public static boolean getMeltingFromBlockState(final BlockState blockState) {
        return blockState.get(MELTING);
    }

    public BlockState getPlacementState(final ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(Properties.HORIZONTAL_FACING,
                ctx.getPlayerFacing());
    }

    @Override
    public BlockEntity createBlockEntity(final BlockView view) {
        return new DeliniumCrucibleLootableContainerBlockEntity();
    }

    // Open Container
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                ContainerProviderRegistry.INSTANCE.openContainer(getIdentifier(this), player, buf -> buf.writeBlockPos(pos));
            }
        }
        return ActionResult.SUCCESS;
    }
    
    // Scatter the items in the chest when it is removed.
    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((DeliniumCrucibleLootableContainerBlockEntity)blockEntity));
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }


    /**
     * BlockEntity
     */
    public static class DeliniumCrucibleLootableContainerBlockEntity extends LootableContainerBlockEntity {

        public static class DeliniumCrucibleSlot extends Slot {

            public DeliniumCrucibleSlot(Inventory inventory, int invSlot, int xPosition,
                    int yPosition) {
                super(inventory, invSlot, xPosition, yPosition);
            }
    
        }
        

        public static class DeliniumCrucibleContainer extends Container {  
            
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
                        this.addSlot(new DeliniumCrucibleSlot(this.crucibleInventory,
                                n + o * 9, 8 + n * 18,
                                18 + o * 18));
                    }
                }
                

                // Player Inventory (27 storage + 9 hotbar)
                for (o = 0; o < 3; ++o) {
                    for (n = 0; n < 9; ++n) {
                        this.addSlot(new Slot(playerInventory, n + o * 9 + 9, 8 + n * 18, 84 + o * 18));
                    }
                }
    
                for (o = 0; o < 9; ++o) {
                    this.addSlot(new Slot(playerInventory, o, 8 + o * 18, 142));
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
        

        public static class DeliniumCrucibleScreen extends ContainerScreen<DeliniumCrucibleContainer> {


            public DeliniumCrucibleScreen(DeliniumCrucibleContainer container, PlayerInventory playerInventory, Text title) {
                super(container, playerInventory, title);
                this.containerHeight = 114 + 6 * 18;
            }

            private static final Identifier TEXTURE = new Identifier(DeliriumMod.MODID, "textures/gui/container/delinium_crucible.png");

            @Override
            protected void drawForeground(int mouseX, int mouseY) {
                this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
                this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.containerHeight - 96 + 2), 4210752);
            }

            @Override
            protected void drawBackground(float delta, int mouseX, int mouseY) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bindTexture(TEXTURE);
                int i = (this.width - this.containerWidth) / 2;
                int j = (this.height - this.containerHeight) / 2;
                this.blit(i, j, 0, 0, this.containerWidth, 6 * 18 + 17);
                this.blit(i, j + 6 * 18 + 17, 0, 126, this.containerWidth, 96);
            }
            
        }


        public static class DeliniumCrucibleInventory implements SidedInventory, Tickable {
            private static final int INVENTORY_SIZE = 54; // 9 * 6 = 54
            private static final int[] AVAILABLE_SLOTS = IntStream.range(0, INVENTORY_SIZE).toArray();
            private final int conversion = 4;
            private int rank = 1;
            private boolean isSmelting = false;
            private int ticks = 0;
            private DefaultedList<ItemStack> items;

            public DeliniumCrucibleInventory() {
                this.items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
            }


            public DefaultedList<ItemStack> getItems() {
                return items;
            }

            public void setItems(final DefaultedList<ItemStack> items) {
                this.items = items;
            }

            @Override
            public void clear() {
                getItems().clear();
            }

            // smelt when inventory is closed.

            @Override
            public void onInvClose(final PlayerEntity player) {
                SidedInventory.super.onInvClose(player);
                if (!isSmelting) {
                    this.isSmelting = true;
                    this.markDirty();
                    final ArrayList<Integer> smeltableStackIndexes = new ArrayList<>();
                    final ArrayList<Integer> lavaBucketStackIndexes = new ArrayList<>();

                    for (int i = 0; i < getInvSize(); i++) {
                        final ItemStack stack = getInvStack(i);
                        if (!stack.isEmpty()) {
                            if (stack.getItem().equals(Delinium.DELINIUM)) {
                                smeltableStackIndexes.add(i);
                            } else if (stack.getItem().equals(Items.LAVA_BUCKET)) {
                                lavaBucketStackIndexes.add(i);
                            }
                        }
                    }

                    while (smeltableStackIndexes.size() > 0 && lavaBucketStackIndexes.size() > 0) {
                        final ItemStack deliniumStack = getInvStack(smeltableStackIndexes.get(0));
                        if (deliniumStack.getCount() >= (int) (conversion / rank)) {
                            // create converted delinium stack
                            final ItemStack deliniumIngots =
                                    new ItemStack(DeliniumIngot.DELINIUM_INGOT, 1);
                            // remove 1 lava bucket and x amount of delinium
                            takeInvStack(smeltableStackIndexes.get(0), (int) (conversion / rank));
                            if (getInvStack(smeltableStackIndexes.get(0)).isEmpty()
                                    || getInvStack(smeltableStackIndexes.get(0))
                                            .getCount() < (int) (conversion / rank)) {
                                smeltableStackIndexes.remove(0);
                            }
                            takeInvStack(lavaBucketStackIndexes.get(0), 1);
                            // create empty bucket
                            final ItemStack emptyBucket = new ItemStack(Items.BUCKET, 1);
                            int firstOpenSlot = -1;
                            // place empty bucket into crucible or drop it to the player
                            if (getInvStack(lavaBucketStackIndexes.get(0)).isEmpty()) {
                                setInvStack(lavaBucketStackIndexes.remove(0), emptyBucket);
                            } else {
                                firstOpenSlot = firstOpenSlot(emptyBucket);
                                if (firstOpenSlot != -1) {
                                    final ItemStack invSlot = getInvStack(firstOpenSlot);
                                    if (!invSlot.isEmpty()) {
                                        emptyBucket.setCount(
                                                invSlot.getCount() + emptyBucket.getCount());
                                    }
                                    setInvStack(firstOpenSlot, emptyBucket);
                                } else {
                                    player.dropStack(emptyBucket);
                                }
                            }
                            // place ingots into crucible or drop it to the player
                            firstOpenSlot = firstOpenSlot(deliniumIngots);
                            if (firstOpenSlot != -1) {
                                final ItemStack invSlot = getInvStack(firstOpenSlot);
                                if (!invSlot.isEmpty()) {
                                    deliniumIngots.setCount(
                                            invSlot.getCount() + deliniumIngots.getCount());
                                }
                                setInvStack(firstOpenSlot, deliniumIngots);
                            } else {
                                player.dropStack(deliniumIngots);
                            }
                        }
                    }
                }
            }

            @Override
            public int getInvSize() {
                return getItems().size();
            }

            @Override
            public boolean isInvEmpty() {
                for (int i = 0; i < getInvSize(); i++) {
                    final ItemStack stack = getInvStack(i);
                    if (!stack.isEmpty()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public ItemStack getInvStack(final int slot) {
                return getItems().get(slot);
            }

            @Override
            public ItemStack takeInvStack(final int slot, final int amount) {
                final ItemStack result = Inventories.splitStack(getItems(), slot, amount);
                if (!result.isEmpty()) {
                    markDirty();
                }
                return result;
            }

            @Override
            public ItemStack removeInvStack(final int slot) {
                return Inventories.removeStack(getItems(), slot);
            }

            public int firstOpenSlot(final ItemStack stack) {
                int firstOpenSlot = -1;
                for (int i = 0; i < getInvSize(); i++) {
                    final ItemStack stk = getInvStack(i);
                    if (stk.getItem().equals(DeliniumIngot.DELINIUM_INGOT)
                            && stk.getCount() < stack.getMaxCount()
                            && stk.getCount() + stack.getCount() <= stack.getMaxCount()) {
                        firstOpenSlot = i;
                    }
                }
                if (firstOpenSlot == -1) {
                    for (int i = 0; i < getInvSize(); i++) {
                        final ItemStack stk = getInvStack(i);
                        if (stk.isEmpty()) {
                            firstOpenSlot = i;
                        }
                    }
                }
                return firstOpenSlot;
            }

            @Override
            public void setInvStack(final int slot, final ItemStack stack) {
                getItems().set(slot, stack);
                if (stack.getCount() > getInvMaxStackAmount()) {
                    stack.setCount(getInvMaxStackAmount());
                }

            }

            @Override
            public boolean canPlayerUseInv(final PlayerEntity player) {
                return true;
            }

            @Override
            public void markDirty() {

            }

            @Override
            public void tick() {
                ++this.ticks;
                if (this.ticks > 2000) {
                    this.ticks = 0;
                }
            }


            @Override
            public int[] getInvAvailableSlots(Direction side) {
                return AVAILABLE_SLOTS;
            }


            @Override
            public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
                return true;
            }


            @Override
            public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
                return true;
            }

        }
        

        public DeliniumCrucibleInventory inventory;

        public DeliniumCrucibleLootableContainerBlockEntity() {
            super(DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE);
            this.inventory = new DeliniumCrucibleInventory();
        }


        // Serialize the BlockEntity
        @Override
        public CompoundTag toTag(final CompoundTag tag) {
            super.toTag(tag);
            // Save the current value of the number to the tag
            tag.putInt("rank", inventory.rank);
            tag.putBoolean("isSmelting", inventory.isSmelting);
            return this.serializeInventory(tag);
        }
        public CompoundTag serializeInventory(CompoundTag tag) {
            if (!this.serializeLootTable(tag)) {
                Inventories.toTag(tag, this.inventory.items, false);
            }
            return tag;
        }
        
        // Deserialize the BlockEntity
        @Override
        public void fromTag(final CompoundTag tag) {
            super.fromTag(tag);
            inventory.rank = tag.getInt("rank");
            inventory.isSmelting = tag.getBoolean("isSmelting");
            this.deserializeInventory(tag);
        }
        public void deserializeInventory(CompoundTag tag) {
            this.inventory = new DeliniumCrucibleInventory();
            if (!this.deserializeLootTable(tag) && tag.contains("Items", 9)) {
               Inventories.fromTag(tag, this.inventory.items);
            }
        }

        @Override
        public int getInvSize() {
            return inventory.getInvSize();
        }
        @Override
        public DefaultedList<ItemStack> getInvStackList() {
            return this.inventory.items;
        }
        @Override
        public void setInvStackList(DefaultedList<ItemStack> list) {
            this.inventory.items = list;

        }
        @Override
        public Text getContainerName() {
            return new TranslatableText("container.shulkerBox", new Object[0]);
        }
        @Override
        public Container createContainer(int i, PlayerInventory playerInventory) {
            return new DeliniumCrucibleContainer(i, playerInventory, this.inventory);
        }
    }

}