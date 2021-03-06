package net.lmvdz.delirium.block.blocks.delinium_crucible;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.client.DeliriumClientMod;
import net.lmvdz.delirium.item.CustomItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
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
    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public final static Material MAP_MATERIAL = new FabricMaterialBuilder(MaterialColor.GREEN).allowsMovement().build();

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
    @Environment(EnvType.CLIENT)
    public DeliniumCrucible() {
        // setup map material and render layer
        super(FabricBlockSettings.of(MAP_MATERIAL).nonOpaque(), RenderLayer.getTranslucent());

        if (DELINIUM_CRUCIBLE_BLOCK == null) {

            DELINIUM_CRUCIBLE_BLOCK = this;

            setDefaultState(getStateManager().getDefaultState().with(MELTING, false)
                    .with(PRIMED, false).with(FACING, Direction.NORTH).with(PERCENTAGE, 0));

            DeliniumCrucibleConversion.registerSmeltConversion(CustomItem.ITEMS.get(new Identifier(DeliriumMod.MODID, "delinium")), 4,
                    CustomItem.ITEMS.get(new Identifier(DeliriumMod.MODID, "delinium_ingot")), 1);
            DeliniumCrucibleConversion.registerSmeltConversion(Items.IRON_ORE, 1, Items.IRON_INGOT,
                    1);
            DeliniumCrucibleConversion.registerSmeltConversion(Items.GOLD_ORE, 1, Items.GOLD_INGOT,
                    1);

            registerDeliriumBlock(DELINIUM_CRUCIBLE_BLOCK);

            ContainerProviderRegistry.INSTANCE.registerFactory(
                    getIdentifier(DELINIUM_CRUCIBLE_BLOCK), (syncId, identifier, player, buf) -> {
                        final BlockEntity blockEntity = player.world.getBlockEntity(buf.readBlockPos());
                        return ((DeliniumCrucibleLootableContainerBlockEntity) blockEntity).createMenu(syncId, player.inventory, player);
                    });

            DELINIUM_CRUCIBLE_CONTAINER_TRANSLATION_KEY =
                    Util.createTranslationKey("container", getIdentifier(DELINIUM_CRUCIBLE_BLOCK));

            DELINIUM_CRUCIBLE_BLOCK_ENTITY = new Identifier(DeliriumClientMod.MODID,
                    getBlockName(DELINIUM_CRUCIBLE_BLOCK) + "_block_entity");

            DELINIUM_CRUCIBLE_BLOCK_ENTITY_TYPE =
                    Registry.register(Registry.BLOCK_ENTITY_TYPE, DELINIUM_CRUCIBLE_BLOCK_ENTITY,
                            BlockEntityType.Builder
                                    .create(DeliniumCrucibleLootableContainerBlockEntity::new,
                                            DELINIUM_CRUCIBLE_BLOCK)
                                    .build(null));
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        // damage player if blockstate is primed/melting
        super.onEntityLand(world, entity);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MELTING, PRIMED, FACING, PERCENTAGE);
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

    public static Direction getHorizontalFacingFromBlockState(BlockState blockState) {
        return blockState.get(FACING);
    }

    public BlockState getPlacementState(final ItemPlacementContext ctx) {
        return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public BlockEntity createBlockEntity(final BlockView view) {
        return new DeliniumCrucibleLootableContainerBlockEntity();
    }

    // Open Container
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
                DeliniumCrucibleLootableContainerBlockEntity de_blockEntity =
                        ((DeliniumCrucibleLootableContainerBlockEntity) blockEntity);
                boolean usedItemOnCrucible = false;
                ItemStack itemStack = player.inventory.getStack(player.inventory.selectedSlot);
                Item inHand = itemStack.getItem();
                boolean primed = getCanMeltFromBlockState(state);
                boolean melting = getMeltingFromBlockState(state);
                Direction facing = getHorizontalFacingFromBlockState(state);
                if (!primed && !melting && inHand == Items.LAVA_BUCKET) {
                    if (itemStack.getCount() == 1) {
                        player.inventory.setStack(player.inventory.selectedSlot,
                                new ItemStack(Items.BUCKET, 1));
                    } else {
                        player.inventory.setStack(player.inventory.selectedSlot,
                                new ItemStack(Items.LAVA_BUCKET, itemStack.getCount() - 1));
                        player.inventory.offerOrDrop(world, new ItemStack(Items.BUCKET, 1));
                    }
                    world.setBlockState(pos, state.with(PRIMED, true));
                    usedItemOnCrucible = true;
                } else if (primed
                        && DeliniumCrucibleConversion.smeltConversions.get(inHand) != null) {
                    int emptySlot = de_blockEntity.inventory.getFirstEmptySlot();
                    if (emptySlot != -1) {
                        de_blockEntity.inventory.setStack(emptySlot, player.inventory.removeStack(player.inventory.selectedSlot, itemStack.getCount()));
                        usedItemOnCrucible = true;
                        de_blockEntity.tryToSmelt(world);
                    }
                } else if (primed && inHand == Items.BUCKET) {
                    if (itemStack.getCount() == 1) {
                        player.inventory.setStack(player.inventory.selectedSlot,
                                new ItemStack(Items.LAVA_BUCKET, 1));
                    } else {
                        player.inventory.setStack(player.inventory.selectedSlot,
                                new ItemStack(Items.BUCKET, itemStack.getCount() - 1));
                        player.inventory.offerOrDrop(world, new ItemStack(Items.LAVA_BUCKET, 1));
                    }
                    world.setBlockState(pos, state.with(PRIMED, false));
                    usedItemOnCrucible = true;
                }
                if (!usedItemOnCrucible && !melting && !primed) {
                    // OPEN CONTAINER
                    ContainerProviderRegistry.INSTANCE.openContainer(getIdentifier(this), player,
                            buf -> buf.writeBlockPos(pos));

                }
                if (usedItemOnCrucible) {
                    primed = getCanMeltFromBlockState(world.getBlockState(pos));
                    melting = getMeltingFromBlockState(world.getBlockState(pos));
                    state = world.getBlockState(pos);
                    facing = getHorizontalFacingFromBlockState(state);
                    // if ((de_blockEntity.PLATONIC_PORTAL == null ||
                    // de_blockEntity.PLATONIC_PORTAL.removed) && (primed || melting)) {
                    // de_blockEntity.PLATONIC_PORTAL =
                    // PlatonicSolidPortal.cloneFromEnum(PlatonicSolidEnum.TETRAHEDRON).setDimensionType(DimensionType.THE_NETHER).setCenter(new
                    // Vec3d(pos.getX(), pos.getY(), pos.getZ()));
                    // de_blockEntity.PLATONIC_PORTAL =
                    // de_blockEntity.PLATONIC_PORTAL.setDestination(new Vec3d(pos.getX()*8,
                    // pos.getY(), pos.getZ()*8)).setScale(4);
                    // de_blockEntity.PLATONIC_PORTAL = de_blockEntity.PLATONIC_PORTAL.spawn(world);
                    // } else if (de_blockEntity.PLATONIC_PORTAL != null &&
                    // !de_blockEntity.PLATONIC_PORTAL.removed && !primed && !melting) {
                    // de_blockEntity.PLATONIC_PORTAL.remove();
                    // }
                    // if ((de_blockEntity.IMMERSIVE_PORTAL == null ||
                    // de_blockEntity.IMMERSIVE_PORTAL.removed) && (primed || melting)) {
                    // Vec3d axisW = new Vec3d(facing.getVector());
                    // if (facing.toString() == "east" || facing.toString() == "west") {
                    // Vector3f f = new Vector3f(axisW);
                    // f.rotate(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
                    // axisW = new Vec3d(f);
                    // }
                    // System.out.println("adding portals");
                    // de_blockEntity.IMMERSIVE_PORTAL = new
                    // RotatingPortal(RotatingPortal.entityType, world, new
                    // Vec3d(Direction.UP.getVector()), axisW, DimensionType.THE_NETHER, new
                    // Vec3d(pos.getX()*8, pos.getY(), pos.getZ()*8), 5, 6, true, new
                    // Vec3d(pos.getX(), pos.getY(), pos.getZ()), 3, true);
                    // de_blockEntity.IMMERSIVE_PORTAL =
                    // de_blockEntity.IMMERSIVE_PORTAL.spawn(world).makeBiWayBiFacingBiWay();
                    // } else if (de_blockEntity.IMMERSIVE_PORTAL != null &&
                    // !de_blockEntity.IMMERSIVE_PORTAL.removed && !primed && !melting) {
                    // System.out.println("removing portals");
                    // PortalManipulation.removeConnectedPortals(RotatingPortal.entityType,
                    // de_blockEntity.IMMERSIVE_PORTAL, p -> {
                    // System.out.println(p + " removed.");
                    // });
                    // de_blockEntity.IMMERSIVE_PORTAL.remove();
                    // }
                }
            }

        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
            DeliniumCrucibleLootableContainerBlockEntity dc_blockEntity =
                    (DeliniumCrucibleLootableContainerBlockEntity) blockEntity;
            ItemScatterer.spawn(world, (BlockPos) pos, (Inventory) (dc_blockEntity));
//            world.updateHorizontalAdjacent(pos, this);
            if (DeliniumCrucible.getCanMeltFromBlockState(state)) {
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }
            if (dc_blockEntity.IMMERSIVE_PORTAL != null
                    && !dc_blockEntity.IMMERSIVE_PORTAL.removed) {
//                PortalManipulation.removeConnectedPortals(RotatingPortal.entityType,
//                        dc_blockEntity.IMMERSIVE_PORTAL, p -> {
//                            System.out.println(p + " removed.");
//                        });
                dc_blockEntity.IMMERSIVE_PORTAL.remove();
            }
        }
        super.onBreak(world, pos, state, player);
    }

    // Scatter the items in the chest when it is removed.
    // spawn lava
    //    @Override
    //    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState,
    //            boolean moved) {
    //        if (state.getBlock() != newState.getBlock()) {
    //            BlockEntity blockEntity = world.getBlockEntity(pos);
    //            if (blockEntity instanceof DeliniumCrucibleLootableContainerBlockEntity) {
    //                DeliniumCrucibleLootableContainerBlockEntity dc_blockEntity =
    //                        (DeliniumCrucibleLootableContainerBlockEntity) blockEntity;
    //                ItemScatterer.spawn(world, (BlockPos) pos, (Inventory) (dc_blockEntity));
    //                world.updateHorizontalAdjacent(pos, this);
    //                if (DeliniumCrucible.getCanMeltFromBlockState(state)) {
    //                    world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    //                }
    //                if (dc_blockEntity.IMMERSIVE_PORTAL != null
    //                        && !dc_blockEntity.IMMERSIVE_PORTAL.removed) {
    //                    PortalManipulation.removeConnectedPortals(RotatingPortal.entityType,
    //                            dc_blockEntity.IMMERSIVE_PORTAL, p -> {
    //                                System.out.println(p + " removed.");
    //                            });
    //                    dc_blockEntity.IMMERSIVE_PORTAL.remove();
    //                }
    //            }
    //            super.onBlockRemoved(state, world, pos, newState, moved);
    //        }
    //    }



    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(3.5, 0, 3.5, 12.5, 9, 12.5);
    }


    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }



//
//    @Override
//    public int getLuminance(BlockState state) {
//        if (getMeltingFromBlockState(state) || getCanMeltFromBlockState(state)) {
//            return getPercentageFromBlockState(state) > 0 ? 1 + getPercentageFromBlockState(state)
//                    : 1;
//        } else {
//            return 0;
//        }
//    }

}
