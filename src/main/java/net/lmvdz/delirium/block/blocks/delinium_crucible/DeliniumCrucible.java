package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
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
    public static IntProperty PERCENTAGE = IntProperty.of("percentage", 0, 10);

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
        // setup material and render layer
        super(FabricBlockSettings.of(Delinium.MAP_MATERIAL).nonOpaque().build(),
                RenderLayer.getSolid());

        if (DELINIUM_CRUCIBLE_BLOCK == null) {

            DELINIUM_CRUCIBLE_BLOCK = this;

            setDefaultState(getStateManager().getDefaultState().with(MELTING, false)
                    .with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(PERCENTAGE, 0));

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

            DELINIUM_CRUCIBLE_BLOCK_ENTITY =
                    new Identifier(DeliriumMod.MODID, "delinium_crucible_block_entity");

            DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE =
                    Registry.register(Registry.BLOCK_ENTITY_TYPE, DELINIUM_CRUCIBLE_BLOCK_ENTITY,
                            BlockEntityType.Builder
                                    .create(DeliniumCrucibleLootableContainerBlockEntity::new,
                                            DELINIUM_CRUCIBLE_BLOCK)
                                    .build(null));
        }
    }

    @Override
    public void appendProperties(final StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MELTING, Properties.HORIZONTAL_FACING, PERCENTAGE);
    }

    public static boolean getMeltingFromBlockState(final BlockState blockState) {
        return blockState.get(MELTING);
    }

    public static int getPercentageFromBlockState(final BlockState blockState) {
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
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                ContainerProviderRegistry.INSTANCE.openContainer(getIdentifier(this), player,
                        buf -> buf.writeBlockPos(pos));
            }
        }
        return ActionResult.SUCCESS;
    }

    // Scatter the items in the chest when it is removed.
    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState,
            boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                ItemScatterer.spawn(world, (BlockPos) pos,
                        (Inventory) ((DeliniumCrucibleLootableContainerBlockEntity) blockEntity));
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

}
