package net.lmvdz.delirium.block.blocks.delinium_crucible;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.lmvdz.delirium.model.DynamicModel;
import net.minecraft.client.render.RenderLayer;

public class DeliniumCruciblePortalModel extends DynamicModel {
    
	public DeliniumCruciblePortalModel() {
        super(RenderLayer::getEntityTranslucent);
		models = new ObjectArrayList<>();
		models.add(new DeliniumCruciblePortalLavaModel());
        models.add(new DeliniumCruciblePortalTransparentModel());
		// models.add(new DeliniumCruciblePortalCrucibleModel());
		this.withModels(models).buildUsingSeeds();
        // this.with(ObjectArrayList.wrap(new DynamicModelPart[] {
        //     this.lava,
        //     this.transparent,
        //     this.crucible
        // })).buildUsingSeeds();
    }
}