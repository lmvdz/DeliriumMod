package net.lmvdz.delirium.portal;

import java.util.HashMap;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class PlatonicSolidPortal {

    public static enum PlatonicSolidEnum {
        TETRAHEDRON,
        CUBE,
        OCTAHEDRON,
        DODECAHEDRON,
        ICOSAHEDRON,
    }
    public static HashMap<Integer, PlatonicSolidPortal> PLATONIC_SOLID_PORTALS = new HashMap<Integer, PlatonicSolidPortal>();

    public static PlatonicSolidPortal TETRAHEDRON = new PlatonicSolidPortal(PlatonicSolidEnum.TETRAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal CUBE = new PlatonicSolidPortal(PlatonicSolidEnum.CUBE, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal OCTAHEDRON = new PlatonicSolidPortal(PlatonicSolidEnum.OCTAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal DODECAHEDRON = new PlatonicSolidPortal(PlatonicSolidEnum.DODECAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal ICOSAHEDRON = new PlatonicSolidPortal(PlatonicSolidEnum.ICOSAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);

    public RotatingPortal[] portals;
    public boolean removed;
    public PlatonicSolidEnum psEnum;

    PlatonicSolidPortal(PlatonicSolidEnum psEnum, DimensionType dimensionTo, Vec3d destination, double scale) {
        this.psEnum = psEnum;
        this.generatePortals(dimensionTo, destination, scale);
        PLATONIC_SOLID_PORTALS.put(psEnum.ordinal(), this);
    }

    public static PlatonicSolidPortal cloneFromEnum(PlatonicSolidEnum psEnum) {
        return PlatonicSolidPortal.clone(PLATONIC_SOLID_PORTALS.get(psEnum.ordinal()));
    }

    public static PlatonicSolidPortal clone(PlatonicSolidPortal portal) {
        return new PlatonicSolidPortal(portal);
    }

    public PlatonicSolidPortal(PlatonicSolidPortal portal) {
        this.portals = portal.portals;
        this.psEnum = portal.psEnum;
        this.removed = portal.removed;
    }

    public PlatonicSolidPortal setCenter(Vec3d center) {
        for (int x = 0; x < this.portals.length; x++) {
            this.portals[x].setCenter(center);
        }
        return this;
    }

    public PlatonicSolidPortal setCenters(Vec3d[] centers) {
        for (int x = 0; x < this.portals.length; x++) {
            this.portals[x].setCenter(centers[x]);
        }
        return this;
    }

    public PlatonicSolidPortal setDimensionType(DimensionType dimensionTo) {
        for (int x = 0; x < this.portals.length; x++) {
            this.portals[x].dimensionTo = dimensionTo;
        }
        return this;
    }

    public PlatonicSolidPortal setDimensionTypes(DimensionType[] dimensionsTo) {
        if (dimensionsTo.length == this.portals.length) {
            for (int x = 0; x < this.portals.length; x++) {
                this.portals[x].dimensionTo = dimensionsTo[x];
            }
        } else {
            System.out.println("ERROR: PlatonicSolidPortal::setDimensionTypes argument 'dimensionsTo' needs to have the same number of elements as the number of portals there are in this platonic solid portal.");
        }
        return this;
        
    }
    public PlatonicSolidPortal setDestination(Vec3d destination) {
        for (int x = 0; x < this.portals.length; x++) {
            this.portals[x].destination = destination;
        }
        return this;
    }
    public PlatonicSolidPortal setDestinations(Vec3d[] destinations) {
        if (destinations.length == this.portals.length) {
            for (int x = 0; x < this.portals.length; x++) {
                this.portals[x].destination = destinations[x];
            }
        } else {
            System.out.println("ERROR: PlatonicSolidPortal::setDestinations argument 'destinations' needs to have the same number of elements as the number of portals there are in this platonic solid portal.");
        }
        return this;
    }

    public PlatonicSolidPortal setScale(double scale) {
        for(int x = 0; x < this.portals.length; x++) {
            this.portals[x].width = scale;
            this.portals[x].height = scale;
        }
        return this;
    }

    public PlatonicSolidPortal setScales(double[] scales) {
        if (scales.length == this.portals.length) {
            for(int x = 0; x < this.portals.length; x++) {
                this.portals[x].width = scales[x];
                this.portals[x].height = scales[x];
            }
            
        } else {
            System.out.println("ERROR: PlatonicSolidPortal::setScales argument 'scales' needs to have the same number of elements as the number of portals there are in this platonic solid portal.");
        }
        return this;
    }

    /**
     * Generates the portals for the platonic solid portal all pointing to the same dimension/destination
     * @param dimensionTo DimensionType, which dimension the portal should teleport the player to
     * @param destination Vec3d, where the portal will teleport the player to
     * @param scale double, scale of the portal (width & height)
     * @return PlatonicSolidPortal instance
     */
    public PlatonicSolidPortal generatePortals(DimensionType dimensionTo, Vec3d destination, double scale) {
        this.portals = new RotatingPortal[PlatonicSolid.fromEnum(this.psEnum).faces];
        PreFoldedPlatonicSolidShape folded = PreFoldedPlatonicSolidShape.fromEnum(this.psEnum);
        for (int x = 0; x < this.portals.length; x++) {
            this.portals[x] = new RotatingPortal(folded.foldedShapes[x].axisH, folded.foldedShapes[x].axisW, dimensionTo, destination, scale, scale, false, PlatonicSolid.fromEnum(this.psEnum).shape.sides, false, true).offsetCenter(folded.foldedShapes[x].offset);
        }
        return this;
    }
    /**
     * Spawns the portal in the world
     * @param world World, world in which to spawn the RotatingPortal
     * @return RotatingPortal instance
     */
    public PlatonicSolidPortal spawn(World world) {
        PreFoldedPlatonicSolidShape folded = PreFoldedPlatonicSolidShape.fromEnum(this.psEnum).clone().regenerate();
        System.out.println("spawning " + this.portals.length + " portals");
        for(int x = 0; x < this.portals.length; x++) {
            if (this.portals[x].removed) {
                this.portals[x] = new RotatingPortal(folded.foldedShapes[x].axisH, folded.foldedShapes[x].axisW, this.portals[x].dimensionTo, this.portals[x].destination, this.portals[x].width, this.portals[x].height, this.portals[x].enablePortalRotate, this.portals[x].sides, this.portals[x].enableInterpolateSides, this.portals[x].generatePortalShapeFromSides).setCenter(this.portals[x].getPos()).offsetCenter(folded.foldedShapes[x].offset);
            }
            if (this.portals[x].world == null) {
                this.portals[x].attachWorld(world);
            }
            this.portals[x] = this.portals[x].spawn(world);
        }
        this.removed = false;
        return this;
    
    }

    /**
     * removes each connected portal
     * @return RotatingPortal instance
     */
    public PlatonicSolidPortal remove() {
        System.out.println("removing " + this.portals.length + " portals");
        for(int x = 0; x < this.portals.length; x++) {
            PortalManipulation.removeConnectedPortals(RotatingPortal.entityType, this.portals[x], p -> {
                System.out.println(p + " removed.");
            });
        }
        for(int x = 0; x < this.portals.length; x++) {
            if (!this.portals[x].removed) {
                System.out.println(this.portals[x] + " removed.");
                this.portals[x].remove();
            }
        }
        this.removed = true;
        return this;
    }
}