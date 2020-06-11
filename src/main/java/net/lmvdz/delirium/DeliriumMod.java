package net.lmvdz.delirium;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.lmvdz.delirium.api.event.MinecraftServerInitCallback;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLava;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLavaModel;
import net.lmvdz.delirium.blockitem.DeliriumBlockItem;
import net.lmvdz.delirium.blockitem.blockitems.DeliniumCrucibleBlockItem;
import net.lmvdz.delirium.item.*;
import net.lmvdz.delirium.ppag.ProceduralPixelArtGenerator;
import net.lmvdz.delirium.warp.WarpManager;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
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


//		EquipmentMaterial.builder(new Identifier(MODID, "Delinium") -- alternative
		EquipmentMaterial.builder(MODID, "Delinium")
				.attackDamage(2.5f)
				.durability(1000)
				.enchantability(25)
				.miningLevel(1)
				.miningSpeed(5f)
				.attackSpeed(1f)
				.armorDurabilities(1000, 1000, 1000, 1000)
//				.armorDurability(EquipmentSlot.HEAD, 10000) -- alternative
				.armorProtectionAmounts(10, 10, 10, 10)
//				.armorProtectionAmount(EquipmentSlot.FEET, 20) -- alternative
				.armorEnchantability(1)
				.equipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC)
				.toughness(10)
				.knockbackResistance(5)
//				.buildArmor() -- only create the Armor Material --- won't be able to create tools
//				.buildTool() -- only create the Tool Material --- wont't be able to create armor
				.build()
//				.armorMaterial(CustomArmorMaterial customArmorMaterial) --- set the custom armor material -- useful if you only buildToolMaterial() and later on need to create armor
//				.toolMaterial(CustomToolMaterial customToolMaterial) --- set the custom tool material -- useful if you only buildArmorMaterial() and later on need to create tools
//				.createArmor() -- create all armor
//				.createArmor(new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.FEET}) --- alternative
//				.createArmor(EquipmentSlot.HEAD) --- alternative
				.createArmor(true, false, true, false)
//				.createTools() -- create all tools
//				.createTools(new EquipmentMaterial.ToolType[] {EquipmentMaterial.ToolType.PICKAXE})  --- alternative
//				.createTool(EquipmentMaterial.ToolType.PICKAXE) --- alternative
				.createTools(false, true, false, false, true, false);


		ProceduralPixelArtGenerator ppag = new ProceduralPixelArtGenerator(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX,
				new Identifier(DeliriumMod.MODID, "item/delinium_sword")));







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
