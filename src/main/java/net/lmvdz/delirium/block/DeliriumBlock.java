package net.lmvdz.delirium.block;

import java.util.HashMap;
import com.google.common.base.CaseFormat;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.lmvdz.delirium.DeliriumMod;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * DeliriumBlock
 */
public class DeliriumBlock extends Block {
    public static HashMap<Identifier, DeliriumBlock> BLOCKS = new HashMap<>();
    
    private String name = "";
    private Identifier identifier;


    public DeliriumBlock(Block.Settings settings, RenderLayer renderLayer) {
        super(settings);
        BlockRenderLayerMap.INSTANCE.putBlock(this, renderLayer);
    }

    public static void registerDeliriumBlock(DeliriumBlock block) {
        setBlockName(block, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                block.getClass().getSimpleName()));
        setIdentifier(block);
        BLOCKS.put(getIdentifier(block), Registry.register(Registry.BLOCK, getIdentifier(block), block));
        System.out.println("Registered Block: " + block.getTranslationKey());
    }

    public static void setIdentifier(DeliriumBlock block) {
        block.identifier = new Identifier(DeliriumMod.MODID, getBlockName(block));
    }

    public static Identifier getIdentifier(DeliriumBlock block) {
        return block.identifier;
    }

    public static String getBlockName(DeliriumBlock block) {
        return block.name;
    }

    public static void setBlockName(DeliriumBlock block, String name) {
        block.name = name;
    }

    @Override
    public String getTranslationKey() {
        return getBlockName(this).toLowerCase().replaceAll(" ", "_");
    }

    
}