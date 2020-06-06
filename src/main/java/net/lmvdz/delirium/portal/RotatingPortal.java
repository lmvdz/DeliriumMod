package net.lmvdz.delirium.portal;

//import com.qouteall.immersive_portals.portal.Portal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

/**
 * RotatingPortal mainly allows for the rotation of the axisW and axisH
 * apart from a normal Portal a RotatingPortal has:
 * - initial facing direction (Used to rotate the portal in accordance to the way it should be facing)
 * - intial axisW and axisH (Used to calculate rotation based off the initial axis instead of the current axis)
 * - references to the connected portals (biFacingPortal, biWayPortal, biWayBiFacingPortal)
 */
public class RotatingPortal extends Portal {
    
    // EntityType
    public static EntityType<RotatingPortal> entityType;
    // whether or not the portal should rotate
    public boolean enablePortalRotate;
    // whether or not the portal should generate it's shape based on the number of sides
    public boolean generatePortalShapeFromSides;

    // the initial portal horizontal axis
    public Vec3d initialAxisW;
    // the initial portal vertical axis
    public Vec3d initialAxisH;

    public RegistryKey<World> dimensionTo;
    public RegistryKey<World> dimension;
    
    // number of sides the portal should have
    public int sides = 3;
    // maximum number of sides allowed for the specialShape
    public static final int MAX_SIDES = 50;
    // minimum number of sides allowed for the specialShape
    public static final int MIN_SIDES = 3;

    // whether or not the portal should increase to the MAX_SIDES and then decrease to the MIN_SIDES going back and forth endlessly
    public boolean enableInterpolateSides;
    // number of maximum sides the portal can have
    public int INTERPOLATE_MAX_SIDES = 50;
    // number of minimum sides the portal can have
    public int INTERPOLATE_MIN_SIDES = 3;
    // how many sides to increase/decrease by every frame 
    public int SIDE_INCREMENT = 1;

    // Whether or not the portal is bi-facing
    public boolean biFacing;
    // link to the bi-facing portal
    public RotatingPortal biFacingPortal;

    // Whether or not the portal is bi-way
    public boolean biWay;
    // link to the bi-way portal
    public RotatingPortal biWayPortal;

    // Whether or not the portal is bi-way-bi-facing
    public boolean biWayBiFacing;
    // link to the bi-way-bi-facing
    public RotatingPortal biWayBiFacingPortal;
    public Vec3d axisH;
    public Vec3d axisW;
    public double height;
    public Vec3d destination;
    public double width;

    /**
     * Default constructor, enables rotation, disables interactability
     * @param entityType 
     * @param world
     */
    public RotatingPortal(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.enablePortalRotate = false; 
        this.setInteractable(false); 
    }

    /**
     * 
     * @param axisH
     * @param axisW
     * @param dimensionTo
     * @param destination
     * @param width
     * @param height
     * @param enablePortalRotate
     * @param sides
     * @param enableInterpolateSides
     * @param generatePortalShapeFromSides
     */
    public RotatingPortal(Vec3d axisH, Vec3d axisW, RegistryKey<World> dimensionTo, Vec3d destination, double width, double height, boolean enablePortalRotate, int sides, boolean enableInterpolateSides, boolean generatePortalShapeFromSides) {
        super(RotatingPortal.entityType, null); 
        this.setInteractable(false); 
        this.setPortal(axisH, axisW, dimensionTo, destination, width, height).setRotatePortalAxis(enablePortalRotate).setSides(sides).setInterpolateSides(enableInterpolateSides).setGeneratePortalShapeFromSides(generatePortalShapeFromSides);
    }

    public RotatingPortal setGeneratePortalShapeFromSides(boolean generatePortalShapeFromSides) {
        this.generatePortalShapeFromSides = generatePortalShapeFromSides;
        return this.generateShapeFromSides();
    }

    /**
     * Attach a world to the entity to allow for spawning
     * @param world World, pass a ServerWorld instance to allow for spawning
     * @return RotatingPortal instance
     */
    public RotatingPortal attachWorld(World world) {
        this.world = world;
        if (this.world != null) {
            this.dimension = world.getRegistryKey();
        }
        return this;
    }

    public RotatingPortal clone() {
        return new RotatingPortal(entityType, world, axisH, axisW, dimensionTo, destination, width, height, enablePortalRotate, new Vec3d(this.getX(), this.getY(), this.getZ()), sides, enableInterpolateSides);
    }

    /**s
     * Required Fields Constructor, enables rotation, disables interactability
     * @param entityType
     * @param world
     * (Portal fields)
     * @param axisH
     * @param axisW
     * @param dimensionTo
     * @param destination
     * @param width
     * @param height
     * (RotatingPortal fields)
     * @param enablePortalRotate
     * @param center
     */
    public RotatingPortal(EntityType<?> entityType, World world, Vec3d axisH, Vec3d axisW, RegistryKey<World> dimensionTo, Vec3d destination, double width, double height, boolean enablePortalRotate, Vec3d center, int sides, boolean enableInterpolateSides) {
        super(entityType, world);
        // waiting for newest release 
        // this.setInteractable(false);
        this.setPortal(axisH, axisW, dimensionTo, destination, width, height).setRotatingPortal(enablePortalRotate, center).setSides(sides).setInterpolateSides(enableInterpolateSides).generateShapeFromSides();
    }
    /**
     * Set the current number of sides the portal should have.
     * @param sides int, number of sides, between 3 and 50 inclusive
     * @return
     */
    public RotatingPortal setSides(int sides) {
        if (sides < MIN_SIDES) {
            this.sides = MIN_SIDES;
            System.out.printf("RotatingPortal - # of sides must be >= %d, you entered %d setting to %d.", MIN_SIDES, sides, MIN_SIDES);
        } else if (sides > MAX_SIDES) {
            this.sides = MAX_SIDES;
            System.out.printf("RotatingPortal - # of sides must be <= %d, you entered %d setting to %d.", MAX_SIDES, sides, MAX_SIDES);
        } else {
            this.sides = sides;
        }
        return this;
    }
    /**
     * Enable or Disable the portal shape sides interpolation 
     * @param enableInterpolateSides
     * @return Rotating Portal instance
     */
    public RotatingPortal setInterpolateSides(boolean enableInterpolateSides) {
        this.enableInterpolateSides = enableInterpolateSides;
        return this;
    }

    /**
     * Set the max number of sides for interpolating the number of sides the portal has.
     * @param maxSides
     * @return Rotating Portal Instance
     */
    public RotatingPortal setMaxSidesInterpolation(int maxSides) {
        if (maxSides <= MAX_SIDES && maxSides >= MIN_SIDES) {
            this.INTERPOLATE_MAX_SIDES = maxSides;
        } else {
            System.out.printf("RotatingPortal - max interpolate sides must be between %d and %d inclusive. Setting to %d.", MIN_SIDES, MAX_SIDES, MAX_SIDES);
            this.INTERPOLATE_MAX_SIDES = MAX_SIDES;
        }
        return this;
    } 
    /**
     * Set the min number of sides for interpolating the number of sides the portal has.
     * @param minSides
     * @return Rotating Portal Instance
     */
    public RotatingPortal setMinSidesInterpolation(int minSides) {
        if (minSides <= MAX_SIDES && minSides >= MIN_SIDES) {
            this.INTERPOLATE_MIN_SIDES = minSides;
        } else {
            System.out.printf("RotatingPortal - min interpolate sides must be between %d and %d inclusive. Setting to %d.", MIN_SIDES, MAX_SIDES, MAX_SIDES);
            this.INTERPOLATE_MIN_SIDES = MIN_SIDES;
        }
        return this;
    } 
    /**
     * Sets the max and min number of sides for interpolating the number of sides the portal has.
     * @param maxSides int, between 3 and 50 inclusive
     * @param minSides int, between 3 and 50 inclusive
     * @return Rotating Portal instance
     */
    public RotatingPortal setMinMaxSidesInterpolation(int maxSides, int minSides) {
        return this.setMaxSidesInterpolation(maxSides).setMinSidesInterpolation(minSides);
    }
    /**
     * Uses the PortalManipulation.generatePolygonPortalShape() function
     * @return RotatingPortal instance
     */
    public RotatingPortal generateShapeFromSides() {
        if (this.generatePortalShapeFromSides) {
//            PortalManipulation.generatePolygonPortalShape(this, this.sides);
        }
        return this;
    }

    /**
     * Rotate vec3d given quaternion
     * 
     * @param q    Quaternion (rotation)
     * @param axis Vec3d to rotate
     * @return Rotated Vec3d
     */
    @Environment(EnvType.CLIENT)
    public Vec3d rotateAxis(Quaternion q, Vec3d axis) {
        Vector3f v = new Vector3f(axis);
        q.copy().conjugate();
        v.rotate(q);
        return new Vec3d(v);
    }
    /**
     * Rotate 'initialAxisW' given Quaternion
     * @param q Quaternion
     * @return Rotated Vec3d
     */
    @Environment(EnvType.CLIENT)
    public Vec3d rotatePortalWidthInitialAxis(Quaternion q) {
        final Vec3d vec3d = rotateAxis(q, this.initialAxisW);
        return vec3d;
    }
    /**
     * Rotate 'initialAxisH' given Quaternion
     * @param q Quaternion
     * @return Rotated Vec3d
     */
    @Environment(EnvType.CLIENT)
    public Vec3d rotatePortalHeightInitialAxis(Quaternion q) {
        final Vec3d vec3d = rotateAxis(q, this.initialAxisH);
        return vec3d;
    }
    /**
     * Rotate both portal axis' (axisH and axisW)
     * @param qH Height Quaternion
     * @param qW Width Quaternion
     */
    @Environment(EnvType.CLIENT)
    public void rotatePortalAxis(Quaternion qH, Quaternion qW) {
        // only rotate height axis if quaternion isn't null
        if (qH != null) {
            // setup the initial height axis if needed
            if(this.initialAxisH == null) {
                this.initialAxisH = this.axisH;
            }
            // set the current height axis
            this.axisH = rotatePortalHeightInitialAxis(qH);
        }
        // only rotate width axis if quaternion isn't null
        if (qW != null) {
            // setup the initial width axis if needed
            if(this.initialAxisW == null) {
                this.initialAxisW = this.axisW;
            }
            // set the current width axis
            this.axisW = rotatePortalWidthInitialAxis(qW);
        }
    }
    /**
     * Set all the initial (Portal) fields
     * @param axisH Vec3d, Height (Vertical) component of the portal
     * @param axisW Vec3d, Width (Horizontal) component of the portal
     * @param dimensionTo DimensionType, destination dimension
     * @param destination Vec3d, destination coordinates
     * @param width double, width of the portal
     * @param height height, height of the portal
     * @return RotatingPortal instance
     */
    public RotatingPortal setPortal(Vec3d axisH, Vec3d axisW, RegistryKey<World> dimensionTo, Vec3d destination, double width, double height) {
        return this.setAxisH(axisH).setAxisW(axisW).setDimensionTo(dimensionTo).setDestination(destination).setWidth(width).setHeight(height);
    }
    
    /**
     * Set all the initial (RotatingPortal) fields
     * @param enablePortalRotate boolean, should the portal rotate
     * @param center Vec3d, where the center of the portal should be
     * @return RotatingPortal instance
     */
    public RotatingPortal setRotatingPortal(boolean enablePortalRotate, Vec3d center) {
        return this.setRotatePortalAxis(enablePortalRotate).setInitialAxis().setCenter(center);
    }

    /**
     * width setter
     * @param width double, width of the portal in blocks
     * @return RotatingPortal instance
     */
    public RotatingPortal setWidth(double width) {
        this.width = width;
        return this;
    }
    /**
     * height setter
     * @param height
     * @return RotatingPortal instance
     */
    public RotatingPortal setHeight(double height) {
        this.height = height;
        return this;
    }
    /**
     * destination setter
     * @param destination
     * @return RotatingPortal instance
     */
    public RotatingPortal setDestination(Vec3d destination) {
        this.destination = destination;
        return this;
    }
    /**
     * dimensionTo setter
     * @param dimensionTo DimensionType
     * @return RotatingPortal instance
     */
    public RotatingPortal setDimensionTo(RegistryKey<World> dimensionTo) {
        this.dimensionTo = dimensionTo;
        return this;
    }
    /**
     * axisH setter
     * @param axisH Vec3d, vertical portal axis
     * @return RotatingPortal instance
     */
    public RotatingPortal setAxisH(Vec3d axisH) {
        this.axisH = axisH;
        return this;
    }
    /**
     * axisW setter
     * @param axisW Vec3d, horizontal portal axis
     * @return  RotatingPortal instance
     */
    public RotatingPortal setAxisW(Vec3d axisW) {
        this.axisW = axisW;
        return this;
    }
    
    /**
     * enablePortalRotate setter
     * @param enablePortalRotate boolean, whether or not the portal axis should rotate
     * @return RotatingPortal instance
     */
    public RotatingPortal setRotatePortalAxis(boolean enablePortalRotate) {
        this.enablePortalRotate = enablePortalRotate;
        return this;
    }

    public RotatingPortal offsetCenter(Vec3d offset) {
        Vec3d newCenter = this.getPos().add(offset);
        return this.setCenter(newCenter.x, newCenter.y, newCenter.z);
    }

    /**
     * Sets the center of the portal using the facing of the portal
     * @param center Vec3d, where the center should be
     * @return RotatingPortal instance
     */
    public RotatingPortal setCenter(Vec3d center) {
        center = center.add(0, 2, 0);
        return this.setCenter(center.getX(), center.getY(), center.getZ());
    }

    /**
     * Updates the portal's center position
     * @param x double, X coordinate of the portal's center
     * @param y double, Y coordinate of the portal's center
     * @param z double, Z coordinate of the portal's center
     * @return RotatingPortal instance
     */
    public RotatingPortal setCenter(double x, double y, double z) {
        super.updatePosition(x, y, z);
        return this;
    }

    /**
     * Sets the initialAxisW and initialAxisH fields equal to the current axisW and axisH respectively
     * @return RotatingPortal instance
     */
    public RotatingPortal setInitialAxis() {
        this.initialAxisH = this.axisH;
        this.initialAxisW = this.axisW;
        return this;
    }

    /**
     * Swaps the portal axis'
     * @return RotatingPortal instance
     */
    public RotatingPortal swapAxis() {
        Vec3d temp = this.axisH;
        this.axisH = this.axisW;
        this.axisW = temp;

        temp = this.initialAxisH;
        this.initialAxisH = this.initialAxisW;
        this.initialAxisW = temp;

        return this;
    }

    /**
     * Used to rotate the portal
     */
    @Override
    @Environment(EnvType.CLIENT)
    public void tick() {
        if (this.world.isClient) {
            if (this.enableInterpolateSides) {
                this.sides += SIDE_INCREMENT;
                if (this.sides >= INTERPOLATE_MAX_SIDES) {
                    if (SIDE_INCREMENT > 0) {
                        SIDE_INCREMENT *= -1;
                    }
                } else if (this.sides <= INTERPOLATE_MIN_SIDES) {
                    if (SIDE_INCREMENT < 0) {
                        SIDE_INCREMENT *= -1;
                    }
                }
                this.generateShapeFromSides();
            }
            if (this.enablePortalRotate) {
                // System.out.println("enablePortalRotate");
                MinecraftClient client = MinecraftClient.getInstance();
                Camera c = client.gameRenderer.getCamera();
                Quaternion q = c.getVerticalPlane().getDegreesQuaternion(180);
                q.hamiltonProduct(c.getHorizontalPlane().getDegreesQuaternion(180));
                q.hamiltonProduct(c.getRotation());
                q.hamiltonProduct(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
                rotatePortalAxis(q, q);
                this.updateCache();
            }
            
        }
    }

    /**
     * Spawns the portal in the world
     * @param world World, world in which to spawn the RotatingPortal
     * @return RotatingPortal instance
     */
    public RotatingPortal spawn(World world) {
        System.out.println(this + " spawned.");
        world.spawnEntity(this);
        return this;
    }

    /**
     * Makes this RotatingPortal bi-way if not already
     * @return RotatingPortal instance
     */
    public RotatingPortal makeBiWay() {
        if (!this.biWay && this.biWayPortal == null) {
            this.biWay = true;
//            this.biWayPortal = PortalManipulation.completeBiWayPortal(this, RotatingPortal.entityType);
        } else {
            System.out.println("This RotatingPortal already has a biWayPortal attached");
        }
        return this;
    }
    /**
     * Makes this RotatingPortal bi-facing if not already
     * @return RotatingPortal instance
     */
    public RotatingPortal makeBiFacing() {
        if (!this.biFacing && this.biFacingPortal == null) {
            this.biFacing = true;
//            this.biFacingPortal = PortalManipulation.completeBiFacedPortal(this, RotatingPortal.entityType);
        } else {
            System.out.println("This RotatingPortal already has a biFacingPortal attached");
        }
        return this;
    }
    /**
     * Makes this RotatingPortal's bi-facing RotaingPortal into a bi-facing-bi-way RotatingPortal
     * @return RotatingPortal instance
     */
    public RotatingPortal makeBiFacingBiWay() {
        if (this.biFacing && this.biFacingPortal != null && !this.biWayBiFacing && this.biWayBiFacingPortal == null) {
            this.biWayBiFacing = true;
//            this.biWayBiFacingPortal = PortalManipulation.completeBiWayPortal(this.biFacingPortal, RotatingPortal.entityType);
        } else {
            System.out.println("Error: cannot makeBiFacingBiWay() from a null biFacing portal, call rotatingPortal.makeBiFacing() first to create the biFacing portal, then call makeBiFacingBiWay().");
        }
        return this;
    }
    /**
     * Makes this RotatingPortal into a bi-way-bi-facing-bi-way RotatingPortal
     * @return RotatingPortal instance
     */
    public RotatingPortal makeBiWayBiFacingBiWay() {
        return this.makeBiFacing().makeBiWay().makeBiFacingBiWay();
    }
}