package net.lmvdz.delirium;


import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucible;
import net.lmvdz.delirium.block.blocks.delinium_crucible.DeliniumCrucibleLava;
import net.lmvdz.delirium.blockitem.blockitems.DeliniumCrucibleBlockItem;
import net.lmvdz.delirium.item.delinium.items.Delinium;
import net.lmvdz.delirium.item.delinium.items.DeliniumIngot;
import net.lmvdz.delirium.item.delinium.tools.swords.DeliniumIngotSword;
import net.lmvdz.delirium.item.delinium.tools.swords.DeliniumSword;
import net.lmvdz.delirium.item.delinium.tools.axe.DeliniumAxe;
import net.lmvdz.delirium.item.delinium.tools.axe.DeliniumIngotAxe;
import net.lmvdz.delirium.item.delinium.tools.hoe.DeliniumHoe;
import net.lmvdz.delirium.item.delinium.tools.hoe.DeliniumIngotHoe;
import net.lmvdz.delirium.item.delinium.tools.pickaxe.DeliniumIngotPickaxe;
import net.lmvdz.delirium.item.delinium.tools.pickaxe.DeliniumPickaxe;
import net.lmvdz.delirium.item.delinium.tools.shovel.DeliniumIngotShovel;
import net.lmvdz.delirium.item.delinium.tools.shovel.DeliniumShovel;
import net.lmvdz.delirium.warp.WarpManager;
// import nerdhub.foml.obj.OBJLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.util.Identifier;

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
		
		// -- block items
		new DeliniumCrucibleBlockItem(); //DeliriumBlockItem
	}
}
