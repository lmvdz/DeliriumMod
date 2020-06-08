package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.DeliriumMod;
import net.lmvdz.delirium.client.DeliriumClientMod;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.model.DynamicShaderModel;
import net.lmvdz.delirium.modelpart.DynamicModelPart;
import net.lmvdz.delirium.shader.ManagedUniformType;
import net.lmvdz.delirium.shader.ShaderProgramRenderLayer;
import net.lmvdz.delirium.shader.ShaderProgramRenderLayer.EmissiveLightmap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DeliniumCrucibleLavaModel extends DynamicShaderModel {

	public static final SpriteIdentifier sprite =
			new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX,
					new Identifier(DeliriumMod.MODID, "block/delinium_crucible_lava"));
	private static final float[] rotation = new float[] {-8.0F, 16.0F, 8.0F};

	private static final float[] cuboids = new float[] {0, 0, 10.5F, 6.0F, -12.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 0, 0, 10.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 0, 11.5F, 6.0F, -11.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 1, 0, 11.5F, 2.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 0, 11.5F,
			4.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 0, 11.5F, 0.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			3, 0, 11.5F, 4.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 0, 11.5F, 0.0F, -9.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 4, 0, 11.5F, 4.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 0, 11.5F, 0.0F, -7.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 5, 0, 11.5F, 4.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 0, 11.5F,
			0.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 0, 11.5F, 5.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			6, 0, 11.5F, 1.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 0, 11.5F, 5.0F, -9.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 7, 0, 11.5F, 1.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 0, 11.5F, 6.0F, -9.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 8, 0, 11.5F, 2.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 0, 11.5F,
			6.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 0, 11.5F, 2.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			10, 0, 11.5F, 6.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 0, 11.5F, 2.0F, -7.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 11, 0, 11.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 0, 11.5F, 1.0F,
			-7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 0, 8.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 0,
			8.5F, 1.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 2, 8.5F, 4.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 0, 2, 8.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 2, 7.5F, 4.0F, -4.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 1, 2, 7.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 2, 6.5F, 4.0F,
			-4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 2, 6.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 2,
			6.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 2, 6.5F, 1.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 4, 2, 7.5F, 5.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 2, 7.5F, 1.0F, -4.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 5, 2, 7.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 2, 7.5F, 2.0F,
			-4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 2, 8.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 2,
			8.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 2, 6.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 7, 2, 6.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 2, 6.5F, 6.0F, -12.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 8, 2, 6.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 2, 7.5F, 6.0F,
			-12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 2, 7.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 2,
			8.5F, 5.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 2, 8.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 10, 2, 8.5F, 6.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 2, 8.5F, 2.0F, -12.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 12, 2, 8.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 2, 8.5F,
			0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 4, 7.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			0, 4, 7.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 4, 6.5F, 4.0F, -12.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 1, 4, 6.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 4, 6.5F, 5.0F, -12.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 2, 4, 6.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 4, 7.5F,
			5.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 4, 7.5F, 1.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			4, 4, 3.5F, 5.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 4, 3.5F, 1.0F, -7.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 5, 4, 3.5F, 4.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 4, 3.5F, 0.0F, -7.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 6, 4, 3.5F, 4.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 4, 3.5F,
			0.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 4, 3.5F, 4.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7,
			4, 3.5F, 0.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 4, 3.5F, 5.0F, -9.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 8, 4, 3.5F, 1.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 4, 3.5F, 6.0F, -9.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 9, 4, 3.5F, 2.0F, -9.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 4, 3.5F, 6.0F,
			-8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 4, 3.5F, 2.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 4,
			3.5F, 5.0F, -8.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 4, 3.5F, 1.0F, -8.5F, 1.0F, 1.0F, 1.0F,
			0.0F, 12, 4, 3.5F, 6.0F, -7.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 4, 3.5F, 2.0F, -7.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 12, 8, 10.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 12, 8, 10.5F,
			0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0, 10, 4.5F, 4.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			0, 10, 4.5F, 0.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 1, 10, 4.5F, 6.0F, -12.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 1, 10, 4.5F, 2.0F, -12.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 10, 3.5F, 6.0F,
			-11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 2, 10, 3.5F, 2.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3,
			10, 3.5F, 4.0F, -11.5F, 1.0F, 1.0F, 1.0F, 0.0F, 3, 10, 3.5F, 0.0F, -11.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 4, 10, 3.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 4, 10, 3.5F, 0.0F, -5.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 5, 10, 3.5F, 6.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 5, 10, 3.5F,
			2.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 6, 10, 4.5F, 6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			6, 10, 4.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 7, 10, 4.5F, 4.0F, -4.5F, 1.0F, 1.0F,
			1.0F, 0.0F, 7, 10, 4.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 8, 10, 10.5F, 4.0F, -4.5F,
			1.0F, 1.0F, 1.0F, 0.0F, 8, 10, 10.5F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 10, 10.5F,
			6.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F, 9, 10, 10.5F, 2.0F, -4.5F, 1.0F, 1.0F, 1.0F, 0.0F,
			10, 10, 11.5F, 6.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 10, 10, 11.5F, 2.0F, -5.5F, 1.0F,
			1.0F, 1.0F, 0.0F, 11, 10, 11.5F, 4.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F, 11, 10, 11.5F,
			0.0F, -5.5F, 1.0F, 1.0F, 1.0F, 0.0F};

	@Environment(EnvType.CLIENT)
	public DeliniumCrucibleLavaModel() {
//		BASIC
//		super(new EmissiveLightmap(DeliriumMod.MODID, "test", new Identifier(DeliriumMod.MODID, "block/delinium_crucible_lava"), program -> {
//			System.out.println("Example -- Emissive/Lightmap Sampler -- Shader Program Render Layer");
//		}), RenderLayer::getEntityTranslucent);
		super(DeliriumClientMod.FBM, RenderLayer::getEntityTranslucent);

//		List<Pair<ManagedUniformType, String>> uniforms = new ArrayList<>();
//		uniforms.add(new Pair<>(ManagedUniformType.vec4, "Viewport"));
//		program.putUniforms(uniforms);
//		MinecraftClient client = MinecraftClient.getInstance();
//		program.managedUniforms.setUniform(ManagedUniformType.vec4, "Viewport", new Vector4f(0, 0, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight()));

//		SATIN
//		super((Identifier id) -> {
//
//			ShaderProgramRenderLayer.getRenderLayer(EMISSIVE_LIGHTMAP_SPRL, RenderLayer.getEntityTranslucent(id));
//		});
		this.withParts(ObjectArrayList.wrap(new DynamicModelPart[] {
				DynamicModelPart.generateModelPart(this, cuboids.clone(), rotation.clone(), sprite,
						DynamicModelPart.defaultSeeds(cuboids.length / 9), layerFactory)}));
	}

}
