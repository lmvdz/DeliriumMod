package net.lmvdz.delirium.portal;

import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

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
    
    // the initial portal horizontal axis
    protected Vec3d initialAxisW;
    // the initial portal vertical axis
    protected Vec3d initialAxisH;

    // whether or not the portal should increase to the MAX_SIDES and then decrease to the MIN_SIDES going back and forth endlessly
    public boolean interpolateSides;
    // number of sides the portal should have
    protected int sides = 3;
    // number of maximum sides the portal can have
    protected int MAX_SIDES = 50;
    // number of minimum sides the portal can have
    protected int MIN_SIDES = 3;
    // how many sides to increase/decrease by every frame 
    protected int SIDE_INCREMENT = 1;

    // Whether or not the portal is bi-facing
    private boolean biFacing;
    // link to the bi-facing portal
    private RotatingPortal biFacingPortal;

    // Whether or not the portal is bi-way
    private boolean biWay;
    // link to the bi-way portal
    private RotatingPortal biWayPortal;
    
    // Whether or not the portal is bi-way-bi-facing
    private boolean biWayBiFacing;
    // link to the bi-way-bi-facing
    private RotatingPortal biWayBiFacingPortal;

    /**
     * Default constructor, enables rotation, disables interactability
     * @param entityType 
     * @param world
     */
    public RotatingPortal(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.enablePortalRotate = true;
        // waiting for newest release 
        // this.setInteractable(false); 
    }

    /**
     * NULL WORLD CONSTRUCTOR
     * @param entityType 
     * @param world
     */
    public RotatingPortal(Vec3d axisH, Vec3d axisW, DimensionType dimensionTo, Vec3d destination, double width, double height, boolean enablePortalRotate, int sides) {
        super(RotatingPortal.entityType, null);
        // waiting for newest release 
        // this.setInteractable(false); 
        this.setPortal(axisH, axisW, dimensionTo, destination, width, height).setRotatePortalAxis(enablePortalRotate).setSides(sides);
    }

    public RotatingPortal attachWorld(World w) {
        this.world = w;
        if (this.world != null) {
            this.dimension = world.dimension.getType();
        }
        return this;
    }

    public RotatingPortal clone() {
        return new RotatingPortal(entityType, world, axisH, axisW, dimensionTo, destination, width, height, enablePortalRotate, new Vec3d(this.getX(), this.getY(), this.getZ()), sides);
    }

    /**
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
    public RotatingPortal(EntityType<?> entityType, World world, Vec3d axisH, Vec3d axisW, DimensionType dimensionTo, Vec3d destination, double width, double height, boolean enablePortalRotate, Vec3d center, int sides) {
        super(entityType, world);
        // waiting for newest release 
        // this.setInteractable(false);
        this.setPortal(axisH, axisW, dimensionTo, destination, width, height).setRotatingPortal(enablePortalRotate, center).setSides(sides).generateShapeFromSides();
    }

    public RotatingPortal setSides(int sides) {
        this.sides = sides;
        return this;
    }

    public RotatingPortal generateShapeFromSides() {
        PortalManipulation.generatePolygonPortalShape(this, this.sides);
        return this;
    }

    /**
     * Rotate vec3d given quaternion
     * 
     * @param q    Quaternion (rotation)
     * @param axis Vec3d to rotate
     * @return Rotated Vec3d
     */
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
    public Vec3d rotatePortalWidthInitialAxis(Quaternion q) {
        return rotateAxis(q, this.initialAxisW);
    }
    /**
     * Rotate 'initialAxisH' given Quaternion
     * @param q Quaternion
     * @return Rotated Vec3d
     */
    public Vec3d rotatePortalHeightInitialAxis(Quaternion q) {
        return rotateAxis(q, this.initialAxisH);
    }
    /**
     * Rotate both portal axis' (axisH and axisW)
     * @param qH Height Quaternion
     * @param qW Width Quaternion
     */
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
    public RotatingPortal setPortal(Vec3d axisH, Vec3d axisW, DimensionType dimensionTo, Vec3d destination, double width, double height) {
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
    public RotatingPortal setDimensionTo(DimensionType dimensionTo) {
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

    /**
     * Sets the center of the portal using the facing of the portal
     * @param center Vec3d, where the center should be
     * @return RotatingPortal instance
     */
    public RotatingPortal setCenter(Vec3d center) {
        center = center.add((.5 * width/2) - .75, 4.1, .5);
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
    public void tick() {
        if (this.world.isClient && enablePortalRotate) {
            if (interpolateSides) {
                this.sides += SIDE_INCREMENT;
                if (this.sides >= MAX_SIDES) {
                    if (SIDE_INCREMENT > 0) {
                        SIDE_INCREMENT *= -1;
                    }
                } else if (this.sides <= MIN_SIDES) {
                    if (SIDE_INCREMENT < 0) {
                        SIDE_INCREMENT *= -1;
                    }
                }
                this.generateShapeFromSides();
            }
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

    /**
     * Spawns the portal in the world
     * @param world World, world in which to spawn the RotatingPortal
     * @return RotatingPortal instance
     */
    public RotatingPortal spawn(World world) {
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
            this.biWayPortal = PortalManipulation.completeBiWayPortal(this, RotatingPortal.entityType);
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
            this.biFacingPortal = PortalManipulation.completeBiFacedPortal(this, RotatingPortal.entityType);
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
            this.biWayBiFacingPortal = PortalManipulation.completeBiWayPortal(this.biFacingPortal, RotatingPortal.entityType);
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