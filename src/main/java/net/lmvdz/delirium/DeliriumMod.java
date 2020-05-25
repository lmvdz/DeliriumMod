package net.lmvdz.delirium;

import java.util.HashMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.lmvdz.delirium.block.DeliriumBlock;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLava;
import net.lmvdz.delirium.blockitem.DeliriumBlockItem;
import net.lmvdz.delirium.blockitem.blockitems.DeliniumCrucibleBlockItem;
import net.lmvdz.delirium.item.DeliriumAxeItem;
import net.lmvdz.delirium.item.DeliriumHoeItem;
import net.lmvdz.delirium.item.DeliriumItem;
import net.lmvdz.delirium.item.DeliriumPickaxeItem;
import net.lmvdz.delirium.item.DeliriumShovelItem;
import net.lmvdz.delirium.item.DeliriumSwordItem;
import net.lmvdz.delirium.item.DeliriumToolItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
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
	public static Logger LOGGER = LogManager.getLogger();
	public static WarpManager WarpManager = new WarpManager();
	public final static ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MODID, "items")).icon(() -> new ItemStack(Registry.ITEM.get(new Identifier(MODID, "delinium")))).build();
	public static HashMap<Identifier, DeliriumBlock> BLOCKS = new HashMap<>();
	public static HashMap<Identifier, DeliriumBlockItem> BLOCK_ITEMS = new HashMap<>();
	public static HashMap<Identifier, DeliriumToolItem> TOOLS = new HashMap<>();
    public static HashMap<Identifier, DeliriumSwordItem> SWORDS = new HashMap<>();
    public static HashMap<Identifier, DeliriumShovelItem> SHOVELS = new HashMap<>();
    public static HashMap<Identifier, DeliriumPickaxeItem> PICKAXES = new HashMap<>();
    public static HashMap<Identifier, DeliriumHoeItem> HOES = new HashMap<>();
    public static HashMap<Identifier, DeliriumAxeItem> AXES = new HashMap<>();
	public static HashMap<Identifier, DeliriumItem> ITEMS = new HashMap<>();

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
		new Delinium(); // ItemToolMaterial
		new DeliniumIngot(); // ItemToolMaterial
		
		// -- blocks
		new DeliniumCrucible(); // DeliriumBlock + BlockEntityProvider
		new DeliniumCrucibleLava(); // Needs to be registered to be able to render a DeliniumCrucibleLavaModel
		// new DeliniumCruciblePortalLava();
		// new DeliniumCruciblePortalCrucible();
		// new DeliniumCruciblePortalTransparent();
		
		// -- block items
		new DeliniumCrucibleBlockItem(); //DeliriumBlockItem
		
		RotatingPortal.entityType = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MODID, "rotating_portal"),
			FabricEntityTypeBuilder.create( EntityCategory.MISC, (EntityType<RotatingPortal> type, World world1) -> new RotatingPortal(type, world1)).dimensions(new EntityDimensions(1, 1, true)).fireImmune().build()
		);
	}
}
