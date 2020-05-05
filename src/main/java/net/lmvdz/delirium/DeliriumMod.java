package net.lmvdz.delirium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLava;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCruciblePortalCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCruciblePortalLava;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCruciblePortalTransparent;
import net.lmvdz.delirium.blockitem.blockitems.DeliniumCrucibleBlockItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.lmvdz.delirium.item.delinium.tools.axe.DeliniumAxe;
import net.lmvdz.delirium.item.delinium.tools.axe.DeliniumIngotAxe;
import net.lmvdz.delirium.item.delinium.tools.hoe.DeliniumHoe;
import net.lmvdz.delirium.item.delinium.tools.hoe.DeliniumIngotHoe;
import net.lmvdz.delirium.item.delinium.tools.pickaxe.DeliniumIngotPickaxe;
import net.lmvdz.delirium.item.delinium.tools.pickaxe.DeliniumPickaxe;
import net.lmvdz.delirium.item.delinium.tools.shovel.DeliniumIngotShovel;
import net.lmvdz.delirium.item.delinium.tools.shovel.DeliniumShovel;
import net.lmvdz.delirium.item.delinium.tools.swords.DeliniumIngotSword;
import net.lmvdz.delirium.item.delinium.tools.swords.DeliniumSword;
import net.lmvdz.delirium.portal.RotatingPortal;
import net.lmvdz.delirium.warp.WarpManager;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

// world seed -334447148958399075
// world spawn point 185, 80, 164
public class DeliriumMod implements ModInitializer {

	public final static String MODID = "delirium";
	public static WarpManager WarpManager = new WarpManager();
	public final static ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "items")).icon(() -> new ItemStack(Registry.ITEM.get(new Identifier(MODID, "delinium")))).build();


	@Override
	public void onInitialize() {
		System.out.println("DeliriumMod - Init");
		
		// .obj model - FOML - nerdhub.foml.obj.OBJLoader
		// https://github.com/OnyxStudios/FOML
		// OBJLoader.INSTANCE.registerDomain(MODID); --- is not working

		// create delinium items
		
		// -- items
		new Delinium(); // DeliriumItemToolMaterial
		new DeliniumIngot(); // DeliriumItemToolMaterial
		
		// -- tools
		
		// -- -- pickaxes
		new DeliniumPickaxe(); // DeliriumPickaxeItem
		new DeliniumIngotPickaxe(); // DeliriumPickaxeItem
		// -- -- axes
		new DeliniumAxe(); // DeliriumAxeItem
		new DeliniumIngotAxe(); // DeliriumAxeItem
		// -- -- hoes
		new DeliniumHoe(); // DeliriumHoeItem
		new DeliniumIngotHoe(); // DeliriumHoeItem
		// -- -- shovels
		new DeliniumShovel(); // DeliriumShovelItem
		new DeliniumIngotShovel(); // DeliriumShovelItem
		// -- -- swords
		new DeliniumSword(); // DeliriumSwordItem
		new DeliniumIngotSword(); // DeliriumSwordItem
		
		// -- blocks
		new DeliniumCrucible(); // DeliriumBlock + BlockEntityProvider
		new DeliniumCrucibleLava();
		new DeliniumCruciblePortalLava();
		new DeliniumCruciblePortalCrucible();
		new DeliniumCruciblePortalTransparent();
		
		// -- block items
		new DeliniumCrucibleBlockItem(); //DeliriumBlockItem
		
		RotatingPortal.entityType = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "rotating_portal"),
			FabricEntityTypeBuilder.create( EntityCategory.MISC, (EntityType<RotatingPortal> type, World world1) -> new RotatingPortal(type, world1)).size(new EntityDimensions(1, 1, true)).setImmuneToFire().build()
		);

		// ServerSidePacketRegistry.INSTANCE.register(DeliniumCrucibleLootableContainerBlockEntity.SET_IMMERSIVE_PORTAL_ROTATION, (packetContext, attachedData) -> {
		// 	BlockPos pos = attachedData.readBlockPos();
		// 	float qA = attachedData.readFloat();
		// 	float qB = attachedData.readFloat();
		// 	float qC = attachedData.readFloat();
		// 	float qD = attachedData.readFloat();
			
        //     packetContext.getTaskQueue().execute(() -> {
		// 		World w = packetContext.getPlayer().world;
		// 		BlockEntity be = w.getBlockEntity(pos);
		// 		Quaternion q = new Quaternion(qA, qB, qC, qD);
		// 		if (be instanceof DeliniumCrucibleLootableContainerBlockEntity) {
		// 			DeliniumCrucibleLootableContainerBlockEntity de_blockEntity = ((DeliniumCrucibleLootableContainerBlockEntity)be);
					
		// 			// Vector3f rotatingAxisH = new Vector3f(de_blockEntity.IMMERSIVE_PORTAL.axisH);
		// 			// rotatingAxisH.rotate(q);
		// 			// de_blockEntity.IMMERSIVE_PORTAL.axisH = new Vec3d(de_blockEntity.IMMERSIVE_PORTAL.axisH.x, rotatingAxisH.getY(), de_blockEntity.IMMERSIVE_PORTAL.axisH.z);
					
		// 			// Vector3f rotatingAxisW = new Vector3f(de_blockEntity.IMMERSIVE_PORTAL.axisW);
		// 			// rotatingAxisW.rotate(q);
		// 			// de_blockEntity.IMMERSIVE_PORTAL.axisW = new Vec3d(rotatingAxisW.getX(), de_blockEntity.IMMERSIVE_PORTAL.axisW.y,  rotatingAxisW.getZ());
					
		// 			// System.out.println(de_blockEntity.IMMERSIVE_PORTAL.axisH + " " + de_blockEntity.IMMERSIVE_PORTAL.axisW);
					
		// 			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		// 			buf.writeBlockPos(pos);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisH.x);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisH.y);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisH.z);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisW.x);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisW.y);
		// 			buf.writeDouble(de_blockEntity.IMMERSIVE_PORTAL.axisW.z);
		// 			ServerSidePacketRegistry.INSTANCE.sendToPlayer(packetContext.getPlayer(), DeliniumCrucibleLootableContainerBlockEntity.SET_CLIENT_IMMERSIVE_PORTAL_PACKET, buf);
		// 		}
		// 	});
        // });
	}
}
