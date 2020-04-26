package net.lmvdz.delirium.block.blocks.delinium_crucible;

import java.util.HashMap;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


/**
 * DeliniumCrucible
 * 
 * Used to melt Delinium
 */
// public class DeliniumCrucible extends DeliriumBlock implements BlockEntityProvider,
// ModelVariantProvider {
public class DeliniumCrucible extends DeliriumBlock implements BlockEntityProvider {
    public static DeliniumCrucible DELINIUM_CRUCIBLE_BLOCK;
    public static String DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY;
    public static Identifier DELINIUM_CRUCIBLE_BLOCK_ENTITY;
    public static BlockEntityType<DeliniumCrucibleLootableContainerBlockEntity> DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE;
    public static BooleanProperty MELTING = BooleanProperty.of("melting");
    public static BooleanProperty PRIMED = BooleanProperty.of("primed");
    public static IntProperty PERCENTAGE = IntProperty.of("percentage", 0, 10);

    public static HashMap<Item, DeliniumCrucibleConversion> smeltConversions = new HashMap<>();

    public static void registerSmeltConversion(Item from, int fromCount, Item product, int productCount) {
        smeltConversions.put(from, new DeliniumCrucibleConversion(product, productCount, fromCount));
    }

    // private final HashMap<ModelIdentifier, UnbakedModel> variants = new HashMap<>();



    // @Override
    // public UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context)
    // throws ModelProviderException {
    // if (modelId.getNamespace().equals("delirium") &&
    // modelId.getPath().equals("delinium_crucible")) {
    // UnbakedModel m = variants.get(modelId);
    // return m;
    // } else{
    // return null;
    // }

    // }
    // public void registerModels(Function<BlockState, DeliniumCrucibleModel> model) {
    // for (BlockState state : DELINIUM_CRUCIBLE_BLOCK.getStateManager().getStates()) {
    // ModelIdentifier modelId = BlockModels.getModelId(state);
    // variants.put(modelId, (DeliriumUnbakedModel)() -> model.apply(state));
    // }
    // variants.put(new ModelIdentifier(Registry.ITEM.getId(DELINIUM_CRUCIBLE_BLOCK.asItem()),
    // "inventory"), (DeliriumUnbakedModel)() ->
    // model.apply(DELINIUM_CRUCIBLE_BLOCK.getDefaultState()));
    // }

    public DeliniumCrucible() {
        // setup map material and render layer
        super(FabricBlockSettings.of(Delinium.MAP_MATERIAL).nonOpaque().build(), RenderLayer.getTranslucent());

        if (DELINIUM_CRUCIBLE_BLOCK == null) {

            DELINIUM_CRUCIBLE_BLOCK = this;

            setDefaultState(getStateManager().getDefaultState().with(MELTING, false)
                    .with(PRIMED, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH)
                    .with(PERCENTAGE, 0));

            registerSmeltConversion(Delinium.DELINIUM, 4, DeliniumIngot.DELINIUM_INGOT, 1);
            registerSmeltConversion(Items.IRON_ORE, 1, Items.IRON_INGOT, 1);
            registerSmeltConversion(Items.GOLD_ORE, 1, Items.GOLD_INGOT, 1);

            registerDeliriumBlock(DELINIUM_CRUCIBLE_BLOCK);

            ContainerProviderRegistry.INSTANCE.registerFactory(
                    getIdentifier(DELINIUM_CRUCIBLE_BLOCK), (syncId, identifier, player, buf) -> {
                        final BlockEntity blockEntity =
                                player.world.getBlockEntity(buf.readBlockPos());
                        return ((DeliniumCrucibleLootableContainerBlockEntity) blockEntity)
                                .createContainer(syncId, player.inventory);
                    });

            DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY =
                    Util.createTranslationKey("container", getIdentifier(DELINIUM_CRUCIBLE_BLOCK));

            DELINIUM_CRUCIBLE_BLOCK_ENTITY = new Identifier(DeliriumMod.MODID, getBlockName(DELINIUM_CRUCIBLE_BLOCK)+"_block_entity");

            DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE =
                    Registry.register(Registry.BLOCK_ENTITY_TYPE, DELINIUM_CRUCIBLE_BLOCK_ENTITY,
                            BlockEntityType.Builder
                                    .create(DeliniumCrucibleLootableContainerBlockEntity::new,
                                            DELINIUM_CRUCIBLE_BLOCK)
                                    .build(null));
        }
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MELTING, PRIMED, Properties.HORIZONTAL_FACING, PERCENTAGE);
    }

    public static boolean getMeltingFromBlockState(BlockState blockState) {
        return blockState.get(MELTING);
    }

    public static boolean getCanMeltFromBlockState(BlockState blockState) {
        return blockState.get(PRIMED);
    }

    public static int getPercentageFromBlockState(BlockState blockState) {
        return blockState.get(PERCENTAGE);
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            Hand hand, BlockHitResult hit) {
        // if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                DeliniumCrucibleLootableContainerBlockEntity de_blockEntity = ((DeliniumCrucibleLootableContainerBlockEntity) blockEntity);
                boolean usedItemOnCrucible = false;
                ItemStack itemStack = player.inventory.getInvStack(player.inventory.selectedSlot);
                Item inHand = itemStack.getItem();
                boolean primed = getCanMeltFromBlockState(state);
                boolean melting = getMeltingFromBlockState(state);
                if (!primed && !melting && inHand == Items.LAVA_BUCKET) {
                    if (itemStack.getCount() == 1) {
                        player.inventory.setInvStack(
                            player.inventory.selectedSlot,
                                new ItemStack(Items.BUCKET, 1));
                    } else {
                        player.inventory.setInvStack(
                            player.inventory.selectedSlot,
                                new ItemStack(Items.LAVA_BUCKET, itemStack.getCount() - 1));
                        player.inventory.offerOrDrop(world, new ItemStack(Items.BUCKET, 1));
                    }
                    world.setBlockState(pos, state.with(PRIMED, true));
                    usedItemOnCrucible = true;
                } else if (primed && smeltConversions.get(inHand) != null) {
                    int emptySlot = de_blockEntity.inventory.getFirstEmptySlot();
                    if (emptySlot != -1) {
                        de_blockEntity.inventory.setInvStack(emptySlot, player.inventory.takeInvStack(player.inventory.selectedSlot, itemStack.getCount()));
                        usedItemOnCrucible = true;
                        de_blockEntity.tryToSmelt(world);
                    }
                } else if (primed && inHand == Items.BUCKET){
                    if (itemStack.getCount() == 1) {
                        player.inventory.setInvStack(
                                player.inventory.selectedSlot,
                                new ItemStack(Items.LAVA_BUCKET, 1));
                    } else {
                        player.inventory.setInvStack(
                                player.inventory.selectedSlot,
                                new ItemStack(Items.BUCKET, itemStack.getCount() - 1));
                        player.inventory.offerOrDrop(world, new ItemStack(Items.LAVA_BUCKET, 1));
                    }
                    world.setBlockState(pos, state.with(PRIMED, false));
                    usedItemOnCrucible = true;
                } else if (!usedItemOnCrucible && !melting) {
                    // ContainerProviderRegistry.INSTANCE.openContainer(getIdentifier(this),
                    //     player, buf -> buf.writeBlockPos(pos));
                    
                }
            }
        // }
        return ActionResult.SUCCESS;
    }

    // Scatter the items in the chest when it is removed.
    // spawn lava
    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState,
            boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                DeliniumCrucibleLootableContainerBlockEntity dc_blockEntity = (DeliniumCrucibleLootableContainerBlockEntity)blockEntity;
                ItemScatterer.spawn(world, (BlockPos) pos,
                        (Inventory) (dc_blockEntity));
                world.updateHorizontalAdjacent(pos, this);
                if (DeliniumCrucible.getCanMeltFromBlockState(state)) {
                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(3.5, 0, 3.5, 12.5, 10, 12.5);
        // return super.getOutlineShape(state, view, pos, context);
    }


    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }

    @Override
    public int getLuminance(BlockState state) {
        if (getMeltingFromBlockState(state) || getCanMeltFromBlockState(state)) {
            return getPercentageFromBlockState(state) > 0 ? 1 + getPercentageFromBlockState(state) : 1;
        } else {
            return 0;
        }
    }

}
