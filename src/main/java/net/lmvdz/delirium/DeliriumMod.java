package net.lmvdz.delirium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.lmvdz.delirium.api.event.MinecraftServerInitCallback;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLava;
import net.lmvdz.delirium.blockitem.DeliriumBlockItem;
import net.lmvdz.delirium.blockitem.blockitems.DeliniumCrucibleBlockItem;
import net.lmvdz.delirium.item.*;
import net.lmvdz.delirium.warp.WarpManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

// world seed -334447148958399075
// world spawn point 185, 80, 164
public class DeliriumMod implements ModInitializer, MinecraftServerInitCallback {



	public final static String MODID = "delirium";
	public static MinecraftServer server;
	public static Logger LOGGER = LogManager.getLogger();
	public static WarpManager WarpManager = new WarpManager();
	public final static ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "items")).icon(() -> new ItemStack(Registry.ITEM.get(new Identifier(MODID, "delinium")))).build();
	public static HashMap<Identifier, DeliriumBlock> BLOCKS = new HashMap<>();
	public static HashMap<Identifier, DeliriumBlockItem> BLOCK_ITEMS = new HashMap<>();




	public static void log(Level level, String string) {
		LOGGER.log(level, "[" + MODID + "] " + string);
	}

	@Override
	public void onInitialize() {
		log(Level.INFO, "Initialization");
		// .obj model - FOML - nerdhub.foml.obj.OBJLoader
		// https://github.com/OnyxStudios/FOML
		// OBJLoader.INSTANCE.registerDomain(MODID); --- is not working

		// create delinium items
		// -- items
		ItemArmorToolMaterial.build(MODID, "Delinium", 2.5f, 1000, 25, 1, 5f, 1f, new HashMap<>(), new HashMap<>(), 1, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 10, 5);
		ItemArmorToolMaterial.build(MODID, "DeliniumIngot", 5f, 1500, 25, 2, 5f, 2f, new HashMap<>(), new HashMap<>(), 1, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 15, 10);
		
		// -- blocks
		new DeliniumCrucible(); // DeliriumBlock + BlockEntityProvider
		new DeliniumCrucibleLava(); // Needs to be registered to be able to render a DeliniumCrucibleLavaModel
		// new DeliniumCruciblePortalLava();
		// new DeliniumCruciblePortalCrucible();
		// new DeliniumCruciblePortalTransparent();

		// -- block items
		new DeliniumCrucibleBlockItem(); //DeliriumBlockItem
		
//		RotatingPortal.entityType = Registry.register(
//			Registry.ENTITY_TYPE,
//			new Identifier(MODID, "rotating_portal"),
//			FabricEntityTypeBuilder.create( SpawnGroup.MISC, (EntityType<RotatingPortal> type, World world1) -> new RotatingPortal(type, world1)).dimensions(new EntityDimensions(1, 1, true)).fireImmune().build()
//		);
	}

	@Override
	public void onInitMinecraftServer(MinecraftServer server) {
		DeliriumMod.server = server;
	}

}
