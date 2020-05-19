package net.lmvdz.delirium.portal;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.qouteall.immersive_portals.Helper;
import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.GeometryPortalShape;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.GeometryPortalShape.TriangleInPlane;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
/**
 * 
 */
public class PortalManipulation {
    /**
     * 
     * @param <T>
     * @param type
     * @param portal
     * @param removalInformer
     */
    public static <T extends Portal> void removeConnectedPortals(EntityType<T> type, T portal, Consumer<T> removalInformer) {
        removeOverlappedPortals(
            type,
            portal.world,
            portal.getPos(),
            portal.getNormal().multiply(-1),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
        ServerWorld toWorld = McHelper.getServer().getWorld(portal.dimensionTo);
        removeOverlappedPortals(
            type,
            toWorld,
            portal.destination,
            portal.transformLocalVec(portal.getNormal().multiply(-1)),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
        removeOverlappedPortals(
            type,
            toWorld,
            portal.destination,
            portal.transformLocalVec(portal.getNormal()),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
    }
    /**
     * 
     * @param <T>
     * @param portal
     * @param entityType
     * @return
     */
    public static <T extends Portal> T completeBiWayPortal(T portal, EntityType<T> entityType) {
        ServerWorld world = McHelper.getServer().getWorld(portal.dimensionTo);
        
        T newPortal = entityType.create(world);
        newPortal.dimensionTo = portal.dimension;
        newPortal.updatePosition(portal.destination.x, portal.destination.y, portal.destination.z);
        newPortal.destination = portal.getPos();
        newPortal.specificPlayerId = portal.specificPlayerId;
        
        newPortal.width = portal.width;
        newPortal.height = portal.height;
        newPortal.axisW = portal.axisW;
        newPortal.axisH = portal.axisH.multiply(-1);

        if (portal.specialShape != null) {
            newPortal.specialShape = new GeometryPortalShape();
            initFlippedShape(newPortal, portal.specialShape);
        }
        
        newPortal.initCullableRange(
            portal.cullableXStart,
            portal.cullableXEnd,
            -portal.cullableYStart,
            -portal.cullableYEnd
        );
        
        if (portal.rotation != null) {
            rotatePortalBody(newPortal, portal.rotation);
            
            newPortal.rotation = new Quaternion(portal.rotation);
            newPortal.rotation.conjugate();
        }
        
        newPortal.motionAffinity = portal.motionAffinity;
        
        newPortal.specificPlayerId = portal.specificPlayerId;
        
        world.spawnEntity(newPortal);
        
        return newPortal;
    }
    /**
     * 
     * @param <T>
     * @param newPortal
     * @param rotation
     */
    public static <T extends Portal> void rotatePortalBody(T newPortal, Quaternion rotation) {
        newPortal.axisW = Helper.getRotated(rotation, newPortal.axisW);
        newPortal.axisH = Helper.getRotated(rotation, newPortal.axisH);
    }
    /**
     * 
     * @param <T>
     * @param portal
     * @param entityType
     * @return
     */
    public static <T extends Portal> T completeBiFacedPortal(T portal, EntityType<T> entityType) {
        ServerWorld world = (ServerWorld) portal.world;
        T newPortal = entityType.create(world);
        newPortal.dimensionTo = portal.dimensionTo;
        newPortal.updatePosition(portal.getX(), portal.getY(), portal.getZ());
        newPortal.destination = portal.destination;
        newPortal.specificPlayerId = portal.specificPlayerId;
        
        newPortal.width = portal.width;
        newPortal.height = portal.height;
        newPortal.axisW = portal.axisW;
        newPortal.axisH = portal.axisH.multiply(-1);
        
        if (portal.specialShape != null) {
            newPortal.specialShape = new GeometryPortalShape();
            initFlippedShape(newPortal, portal.specialShape);
        }
        
        newPortal.initCullableRange(
            portal.cullableXStart,
            portal.cullableXEnd,
            -portal.cullableYStart,
            -portal.cullableYEnd
        );
        
        newPortal.rotation = portal.rotation;
        
        newPortal.motionAffinity = portal.motionAffinity;
    
        newPortal.specificPlayerId = portal.specificPlayerId;
        
        world.spawnEntity(newPortal);
        
        return newPortal;
    }
    /**
     * 
     * the new portal will not be added into worl
     * 
     * @param <T> 
     * @param portal
     * @param entityType
     * @return
     */
    public static <T extends Portal> T copyPortal(T portal, EntityType<T> entityType) {
        ServerWorld world = (ServerWorld) portal.world;
        T newPortal = entityType.create(world);
        newPortal.dimensionTo = portal.dimensionTo;
        newPortal.updatePosition(portal.getX(), portal.getY(), portal.getZ());
        newPortal.destination = portal.destination;
        newPortal.specificPlayerId = portal.specificPlayerId;
    
        newPortal.width = portal.width;
        newPortal.height = portal.height;
        newPortal.axisW = portal.axisW;
        newPortal.axisH = portal.axisH;
        if (newPortal instanceof RotatingPortal) {
            ((RotatingPortal)newPortal).setInitialAxis();
        }
    
        newPortal.specialShape = portal.specialShape;
    
        newPortal.initCullableRange(
            portal.cullableXStart,
            portal.cullableXEnd,
            portal.cullableYStart,
            portal.cullableYEnd
        );
    
        newPortal.rotation = portal.rotation;
    
        newPortal.motionAffinity = portal.motionAffinity;
    
        newPortal.specificPlayerId = portal.specificPlayerId;
    
        return newPortal;
    }
    /**
     * 
     * @param <T>
     * @param newPortal
     * @param specialShape
     */
    private static <T extends Portal> void initFlippedShape(T newPortal, GeometryPortalShape specialShape) {
        newPortal.specialShape.triangles = specialShape.triangles.stream()
            .map(triangle -> new GeometryPortalShape.TriangleInPlane(
                triangle.x1,
                -triangle.y1,
                triangle.x2,
                -triangle.y2,
                triangle.x3,
                -triangle.y3
            )).collect(Collectors.toList());
    }
    /**
     * 
     * @param <T>
     * @param portal
     * @param removalInformer
     * @param addingInformer
     * @param entityType
     */
    public static <T extends Portal> void completeBiWayBiFacedPortal(
        T portal, Consumer<T> removalInformer,
        Consumer<T> addingInformer, EntityType<T> entityType
    ) {
        removeOverlappedPortals(
            entityType,
            ((ServerWorld) portal.world),
            portal.getPos(),
            portal.getNormal().multiply(-1),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
        
        T oppositeFacedPortal = completeBiFacedPortal(portal, entityType);
        removeOverlappedPortals(
            entityType,
            McHelper.getServer().getWorld(portal.dimensionTo),
            portal.destination,
            portal.transformLocalVec(portal.getNormal().multiply(-1)),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
        
        T r1 = completeBiWayPortal(portal, entityType);
        removeOverlappedPortals(
            entityType,
            McHelper.getServer().getWorld(oppositeFacedPortal.dimensionTo),
            oppositeFacedPortal.destination,
            oppositeFacedPortal.transformLocalVec(oppositeFacedPortal.getNormal().multiply(-1)),
            p -> Objects.equals(p.specificPlayerId, portal.specificPlayerId),
            removalInformer
        );
        
        T r2 = completeBiWayPortal(oppositeFacedPortal, entityType);
        addingInformer.accept(oppositeFacedPortal);
        addingInformer.accept(r1);
        addingInformer.accept(r2);
    }
    /**
     * 
     * @param <T>
     * @param type
     * @param world
     * @param pos
     * @param normal
     * @param predicate
     * @param informer
     */
    public static <T extends Portal> void removeOverlappedPortals(
        EntityType<T> type,
        World world,
        Vec3d pos,
        Vec3d normal,
        Predicate<T> predicate,
        Consumer<T> informer
    ) {
        getPortalClutter(type, world, pos, normal, predicate).forEach(e -> {
            e.remove();
            informer.accept(e);
        });
    }
    /**
     * 
     * @param <T>
     * @param type
     * @param world
     * @param pos
     * @param normal
     * @param predicate
     * @return
     */
    public static <T extends Portal> List<T> getPortalClutter(
        EntityType<T> type,
        World world,
        Vec3d pos,
        Vec3d normal,
        Predicate<T> predicate
    ) {
        return world.getEntities(
            type,
            new Box(
                pos.add(0.5, 0.5, 0.5),
                pos.subtract(0.5, 0.5, 0.5)
            ),
            p -> {
                return p.getNormal().dotProduct(normal) > 0.5 && predicate.test(p);
            }
        );
    }

    /**
     * Generates a GeometryPortalShape for Portal.specialShape based on the parameter number of sides
     * @param <T> T extends Portal
     * @param portal Portal instance
     * @param sides number of sides the polygon portal shape should have
     */
    public static <T extends Portal> void generatePolygonPortalShape(T portal, int sides) {
        portal.specialShape = new GeometryPortalShape();
        double theta = 2 * Math.PI / sides;
        for (int i = 0; i < sides; ++i) {
            double x2 = Math.cos(theta * i);
            double y2 = Math.sin(theta * i);
            double x3 = Math.cos(theta * (i+1));
            double y3 = Math.sin(theta * (i+1));
            // portal.specialShape.triangles.add(new TriangleInPlane(0, portal.width/2, y2 * portal.height/2, x2 * portal.width/2, y3 * portal.height/2, x3 * portal.width/2));
            portal.specialShape.triangles.add(new TriangleInPlane(0, 0, x2 * portal.width/2, y2 * portal.height/2, x3 * portal.width/2, y3 * portal.height/2));
        }
        if (sides <= 3 && sides >= 50) {
            System.out.println("PortalManipulation - Number of sides used to generate polygon portal shape exceeds suggested bounds.");
            // portal.specialShape.triangles.forEach(triangle -> {
            //     System.out.println(triangle.x1 + " " + triangle.y1 + " " + triangle.x2 + " " + triangle.y2 + " " + triangle.x3 + " " +triangle.y3);
            // });
        } 
    }
}