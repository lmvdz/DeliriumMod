package net.lmvdz.delirium.portal;

//import com.qouteall.immersive_portals.CGlobal;
//import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PortalEntityRenderer<T extends Portal> extends EntityRenderer<T> {
    public PortalEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
    
    @Override
    public void render(
        T portal,
        float float_1,
        float float_2,
        MatrixStack matrixStack_1,
        VertexConsumerProvider vertexConsumerProvider_1,
        int int_1
    ) {
        super.render(portal, float_1, float_2, matrixStack_1, vertexConsumerProvider_1, int_1);
//        CGlobal.renderer.renderPortalInEntityRenderer(portal);
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }
}