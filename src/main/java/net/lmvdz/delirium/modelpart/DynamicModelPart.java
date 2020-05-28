package net.lmvdz.delirium.modelpart;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.model.DynamicModel;
import net.lmvdz.delirium.util.Interpolation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.Matrix3f;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

/**
 * Each DynamicModelPart has DynamicCuboid(s) Each DynamicCuboid has a DynamicPart[] (one
 * DynamicPart for each DYNAMIC_ENUM) Each DynamicCuboid can calculate new offsets for each
 * DynamicPart Each DynamicCuboid can shift it's texture UV array
 */
public class DynamicModelPart extends ModelPart {

    /**
     * Current supported dynamic fields
     */
    public static enum DYNAMIC_ENUM {
        X, // x coord of the cuboid
        Y, // y coord of the cuboid
        Z, // z coord of the cuboid
        RED, // red color of the cuboid
        GREEN, // green color of the cuboid
        BLUE, // blue color of the cuboid
        ALPHA, // alpha color of the cuboid
        LIGHT // light value of the cuboid
    }

    public static final int DYNAMIC_ENUM_LENGTH = DYNAMIC_ENUM.values().length;
    public static final boolean[] DEFAULT_STATE =
            new boolean[] {true, true, true, true, true, true, true, true};

    public static final float[] DEFAULT_MIN =
            new float[] {-.0075F, -.0075F, -.0075F, -.0001F, -.0001F, -.0001F, -.0001F, -5F};
    public static final float[] DEFAULT_MAX =
            new float[] {.0075F, .0075F, .0075F, .0001F, .0001F, .0001F, .0001F, 5F};
    public static final float[] DEFAULT_LERP_PERCENT =
            new float[] {.15F, .15F, .15F, .15F, .15F, .15F, .15F, .15F};
    public static final float[] DEFAULT_APPLY_RANDOM_MULTIPLIER =
            new float[] {1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
    public static final float[] DEFAULT_APPLY_RANDOM_MAX =
            new float[] {.005F, .005F, .005F, .005F, .005F, .005F, .005F, .005F};
    public static final float[] DEFAULT_APPLY_RANDOM_MIN =
            new float[] {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};

    // UV mixing? --- instead of shifting the array, mix the array of UV
    // public boolean UV_MIX_APPLY_SYNC;
    // public boolean UV_MIXABLE;

    public boolean UV_SHIFT_APPLY_SYNC;
    public int UV_SHIFT_AMOUNT;
    public boolean UV_SHIFTABLE;
    public boolean UV_SHIFT_EVERY_X_TICK;
    public boolean UV_SHIFT_EVERY_TICK;
    public int UV_SHIFT_EVERY_X_TICKS;
    public boolean UV_SHIFT_EVERY_DELTA_TICK;
    public boolean UV_SHIFT_EVERY_X_DELTA_TICK;
    public int UV_SHIFT_EVERY_X_DELTA_TICKS;
    public boolean UPDATE_DYNAMICS_EVERY_TICK;
    public boolean UPDATE_DYNAMICS_EVERY_X_TICK;
    public int UPDATE_DYNAMICS_EVERY_X_TICKS;
    public boolean UPDATE_DYNAMICS_EVERY_DELTA_TICK;
    public boolean UPDATE_DYNAMICS_EVERY_X_DELTA_TICK;
    public int UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS;

    public int shiftDeltaTickCounter = 0;
    public int dynamicsDeltaTickCounter = 0;


    public float[] x; // array of each cuboid's x
    public float[] y; // array of each cuboid's y
    public float[] z; // array of each cuboid's z
    public int[] sizeX; // array of each cuboid's sizeX
    public int[] sizeY; // array of each cuboid's sizeY
    public int[] sizeZ; // array of each cuboid's sizeZ
    public int[] u; // array of each cuboid's texture u
    public int[] v; // array of each cuboid's texture v
    public float[] extra; // float[3] extra (x, y, z)
    public float[] rotation; // float[3] rotation (x, y, z)
    public ObjectList<DynamicPart[]> seeds; // array of each cuboid's DynamicPart[]
    public SpriteIdentifier spriteId; // sprite identifier to use for each cuboid
    public Function<Identifier, RenderLayer> layerFactory; // render layer factory function
    protected float textureWidth; // texture width
    protected float textureHeight; // texture height
    protected int textureOffsetU; // texture offset u
    protected int textureOffsetV; // texture offset v
    protected ObjectList<DynamicModelPart.DynamicCuboid> cuboids; // array of cuboids attached to
                                                                  // this
                                                                  // DynamicModelPart
    protected ObjectList<DynamicModelPart> children; // array of children DynamicModelPart(s)
                                                     // attached
                                                     // to this DynamicModelPart
    public static final DynamicModelPart EMPTY = new DynamicModelPart(0, 0, 0, 0); // EMPTY
                                                                                   // DynamicModelPart
    public DynamicModel dynamicModel;


    /**
     * Set the u array corresponding to each cuboid
     * 
     * @param u int[], contains each u for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setU(int[] u) {
        this.u = u;
        return this;
    }

    /**
     * Set the v array corresponding to each cuboid
     * 
     * @param v int[], contains each v for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setV(int[] v) {
        this.v = v;
        return this;
    }

    /**
     * Set the x array corresponding to each cuboid
     * 
     * @param x float[], contains each x for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setX(float[] x) {
        this.x = x;
        return this;
    }

    /**
     * Set the y array corresponding to each cuboid
     * 
     * @param y float[], contains each y for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setY(float[] y) {
        this.y = y;
        return this;
    }

    /**
     * Set the z array corresponding to each cuboid
     * 
     * @param z float[], contains each z for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setZ(float[] z) {
        this.z = z;
        return this;
    }

    /**
     * Set the sizeX array corresponding to each cuboid
     * 
     * @param sizeX int[], contains each sizeX for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setSizeX(int[] sizeX) {
        this.sizeX = sizeX;
        return this;
    }

    /**
     * Set the sizeY array corresponding to each cuboid
     * 
     * @param sizeY int[], contains each sizeY for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setSizeY(int[] sizeY) {
        this.sizeY = sizeY;
        return this;
    }

    /**
     * Set the sizeZ array corresponding to each cuboid
     * 
     * @param sizeZ int[], contains each sizeZ for each cuboid of this dynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setSizeZ(int[] sizeZ) {
        this.sizeZ = sizeZ;
        return this;
    }


    /**
     * Set the extra[3] (extraX, extraY, extraZ)
     * 
     * @param extra float[3] (x, y, z)
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setExtra(float[] extra) {
        this.extra = extra;
        return this;
    }

    /**
     * Set the rotation float array for this DynamicModelPart
     * 
     * @param rotation float[3] (x, y, z)
     * @return DynamicModelPart instance
     */
    public DynamicModelPart setRotation(float[] rotation) {
        this.rotation = rotation;
        return this;
    }

    /**
     * set the sprite identifier for the texture to use
     * 
     * @param spriteId SpriteIdentifier, used to get the correct texture when rendering
     * @return DynamicModelPart instance
     */
    public DynamicModelPart spriteId(SpriteIdentifier spriteId) {
        this.spriteId = spriteId;
        return this;
    }

    /**
     * set the render layer factory function
     * 
     * @param layerFactory Function<Identifier, RenderLayer>
     * @return DynamicModelPart instance
     */
    public DynamicModelPart layerFactory(Function<Identifier, RenderLayer> layerFactory) {
        this.layerFactory = layerFactory;
        return this;
    }

    /**
     * set the seeds to use
     * 
     * @param seeds ObjectList<DynamicPart[]>, List of DyanmicPart arrays corresponding to each
     *              DynamicCuboid
     * @return DynamicModelPart instance
     */
    public DynamicModelPart seeds(ObjectList<DynamicPart[]> seeds) {
        this.seeds = seeds;
        return this;
    }

    /**
     * Rotates this DynamicModelPart given a float[3] (x, y, z) rotation vector.
     * 
     * @param rotation float[3] (x, y, z)
     * @return DynamicModelPart instance
     */
    public DynamicModelPart rotateModelPart(float[] rotation) {
        MatrixStack rotationStack = new MatrixStack();
        rotationStack.translate(rotation[0], rotation[1], rotation[2]);
        this.rotate(rotationStack);
        return this;
    }

    /**
     * Rotates the model based on the rotation given. Adds cuboids to the current list of cuboids
     * builds each child DynamicModelPart
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart build() {
        return this.rotateModelPart(this.rotation).addCuboids();
    }

    /**
     * Calls each child's .build() function
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart buildChildren() {
        this.children.parallelStream().forEach(child -> {
            child.build();
        });
        return this;
    }

    /**
     * Same as build, but with the seeds provided
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart buildUsingSeeds() {
        return this.rotateModelPart(this.rotation).addCuboidsUsingSeeds();
    }

    /**
     * Same as buildChildren, but with the seeds provided
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart buildChildrenUsingSeeds() {
        this.children.parallelStream().forEach(child -> {
            child.buildUsingSeeds();
        });
        return this;
    }

    /**
     * Adds each cuboid to the list of cuboids
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart addCuboids() {
        for (int i = 0; i < this.x.length; i++) {
            this.addCuboid(this.x[i], this.y[i], this.z[i], this.sizeX[i], this.sizeY[i],
                    this.sizeZ[i], this.extra[i], this.u[i], this.v[i]);
        }
        return this;
    }

    /**
     * Same as addCuboids but with seeds for DynamicPart
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart addCuboidsUsingSeeds() {
        for (int i = 0; i < this.x.length; i++) {
            this.addCuboid(this.x[i], this.y[i], this.z[i], this.sizeX[i], this.sizeY[i],
                    this.sizeZ[i], this.extra[i], this.u[i], this.v[i], this.seeds.get(i));
        }
        return this;
    }

    /**
     * Sets the seeds as the current seeds and builds using them
     * 
     * @return DynamicModelPart instance
     */
    public DynamicModelPart rebuild() {
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        return addCuboidsUsingSeeds();
    }

    /**
     * Shifts the array of u values by the integer number `shift`
     * 
     * @param shift number of index places to shift each element in the array by.
     * @return DynamicModelPart instance
     */
    public DynamicModelPart shiftU(int shift) {
        this.u = shiftIntArray(this.u, shift);
        return this;
    }

    /**
     * Shifts the array of v values by the integer number `shift`
     * 
     * @param shift number of index places to shift each element in the array by.
     * @return DynamicModelPart instance
     */
    public DynamicModelPart shiftV(int shift) {
        this.v = shiftIntArray(this.v, shift);
        return this;
    }

    /**
     * Shifts the arrays of u and v values by the integer number `shift`
     * 
     * @param shift number of index places to shift each element in the array by.
     * @return DynamicModelPart instance
     */
    public DynamicModelPart shiftUV(int shift) {
        return this.shiftU(shift).shiftV(shift);
    }

    /**
     * Helper function to shift an array of integers by a certain number of index places
     * 
     * @param array the array to shift
     * @param shift the number of index places to shift by
     * @return int[] shifted
     */
    protected int[] shiftIntArray(int[] array, int shift) {
        int shiftCounter = 0;
        while (shiftCounter < Math.abs(shift)) {
            int temp = array[array.length - 1]; // last number
            for (int i = array.length - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = temp;
            shiftCounter++;
        }
        return array;
    }

    /**
     * Boolean to check if this DynamicModelPart has seeds
     * 
     * @return boolean
     */
    public boolean hasSeeds() {
        return this.seeds != null && this.seeds.size() > 0;
    }

    /**
     * Creates a new DynamicModelPart given basic ModelPart data along with seeds, spriteId, and
     * layerFactory
     * 
     * @param dynamicModel
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extra
     * @param u
     * @param v
     * @param rotation
     * @param seeds
     * @param spriteId
     * @param layerFactory
     */
    public DynamicModelPart(DynamicModel dynamicModel, float[] x, float[] y, float[] z, int[] sizeX,
            int[] sizeY, int[] sizeZ, float[] extra, int[] u, int[] v, float[] rotation,
            ObjectList<DynamicModelPart.DynamicPart[]> seeds, SpriteIdentifier spriteId,
            Function<Identifier, RenderLayer> layerFactory) {
        super(dynamicModel);
        this.dynamicModel = dynamicModel;
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.with(false, true, 1, true, false, 50, false, false, 0, true, false, 0, false, false, 0)
                .setX(x).setY(y).setZ(z).setSizeX(sizeX).setSizeY(sizeY).setSizeZ(sizeZ)
                .setExtra(extra).setU(u).setV(v).setRotation(rotation).seeds(seeds)
                .spriteId(spriteId).layerFactory(layerFactory).buildUsingSeeds()
                .buildChildrenUsingSeeds();

    }

    /**
     * Creates a new DynamicModelPart with basic ModelPart data along with booleans for UV shifting
     * and updating dynamics
     * 
     * @param dynamicModel
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extra
     * @param u
     * @param v
     * @param rotation
     * @param seeds
     * @param spriteId
     * @param layerFactory
     * @param UV_SHIFT_APPLY_SYNC
     * @param UV_SHIFTABLE
     * @param UV_SHIFT_AMOUNT
     * @param UV_SHIFT_EVERY_X_TICK
     * @param UV_SHIFT_EVERY_TICK
     * @param UV_SHIFT_EVERY_X_TICKS
     * @param UV_SHIFT_EVERY_DELTA_TICK
     * @param UV_SHIFT_EVERY_X_DELTA_TICK
     * @param UV_SHIFT_EVERY_X_DELTA_TICKS
     * @param UPDATE_DYNAMICS_EVERY_TICK
     * @param UPDATE_DYNAMICS_EVERY_X_TICK
     * @param UPDATE_DYNAMICS_EVERY_X_TICKS
     * @param UPDATE_DYNAMICS_EVERY_DELTA_TICK
     * @param UPDATE_DYNAMICS_EVERY_X_DELTA_TICK
     * @param UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS
     */
    public DynamicModelPart(DynamicModel dynamicModel, float[] x, float[] y, float[] z, int[] sizeX,
            int[] sizeY, int[] sizeZ, float[] extra, int[] u, int[] v, float[] rotation,
            ObjectList<DynamicModelPart.DynamicPart[]> seeds, SpriteIdentifier spriteId,
            Function<Identifier, RenderLayer> layerFactory, boolean UV_SHIFT_APPLY_SYNC,
            boolean UV_SHIFTABLE, int UV_SHIFT_AMOUNT, boolean UV_SHIFT_EVERY_X_TICK,
            boolean UV_SHIFT_EVERY_TICK, int UV_SHIFT_EVERY_X_TICKS,
            boolean UV_SHIFT_EVERY_DELTA_TICK, boolean UV_SHIFT_EVERY_X_DELTA_TICK,
            int UV_SHIFT_EVERY_X_DELTA_TICKS, boolean UPDATE_DYNAMICS_EVERY_TICK,
            boolean UPDATE_DYNAMICS_EVERY_X_TICK, int UPDATE_DYNAMICS_EVERY_X_TICKS,
            boolean UPDATE_DYNAMICS_EVERY_DELTA_TICK, boolean UPDATE_DYNAMICS_EVERY_X_DELTA_TICK,
            int UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS) {
        super(dynamicModel);
        this.dynamicModel = dynamicModel;
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.with(UV_SHIFT_APPLY_SYNC, UV_SHIFTABLE, UV_SHIFT_AMOUNT, UV_SHIFT_EVERY_X_TICK,
                UV_SHIFT_EVERY_TICK, UV_SHIFT_EVERY_X_TICKS, UV_SHIFT_EVERY_DELTA_TICK,
                UV_SHIFT_EVERY_X_DELTA_TICK, UV_SHIFT_EVERY_X_DELTA_TICKS,
                UPDATE_DYNAMICS_EVERY_TICK, UPDATE_DYNAMICS_EVERY_X_TICK,
                UPDATE_DYNAMICS_EVERY_X_TICKS, UPDATE_DYNAMICS_EVERY_DELTA_TICK,
                UPDATE_DYNAMICS_EVERY_X_DELTA_TICK, UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS).setX(x)
                .setY(y).setZ(z).setSizeX(sizeX).setSizeY(sizeY).setSizeZ(sizeZ).setExtra(extra)
                .setU(u).setV(v).setRotation(rotation).seeds(seeds).spriteId(spriteId)
                .layerFactory(layerFactory).buildUsingSeeds().buildChildrenUsingSeeds();
    }

    /**
     * Creates a blank DynamicModelPart with empty cuboids and empty children
     * 
     * @param dynamicModel   -- parent DynamicModel
     * @param textureOffsetU -- textureOffsetU
     * @param textureOffsetV -- texyureOffsetV
     */
    public DynamicModelPart(DynamicModel dynamicModel, int textureOffsetU, int textureOffsetV) {
        super(dynamicModel, textureOffsetU, textureOffsetV);
        this.dynamicModel = dynamicModel;
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
    }

    /**
     * Helper function to enable/disable and set values for dynamics
     * 
     * @param UV_SHIFT_APPLY_SYNC                 boolean, Should the UV shift be applied when
     *                                            dynamics are applied?
     * @param UV_SHIFTABLE                        boolean, Is the UV even shiftable?
     * @param UV_SHIFT_AMOUNT                     int, number of indexes to shift the UV array by
     * @param UV_SHIFT_EVERY_X_TICK               boolean, Should the UV shift every X amount of
     *                                            ticks?
     * @param UV_SHIFT_EVERY_TICK                 boolean, Should the UV shift every tick?
     * @param UV_SHIFT_EVERY_X_TICKS              int, number of ticks between each UV shift
     * @param UV_SHIFT_EVERY_DELTA_TICK           boolean, Should the UV shift every deltaTick?
     * @param UV_SHIFT_EVERY_X_DELTA_TICK         boolean, Should the UV shift evert X amount of
     *                                            deltaTicks?
     * @param UV_SHIFT_EVERY_X_DELTA_TICKS        int, number of deltaTicks between each UV shift
     * @param UPDATE_DYNAMICS_EVERY_TICK          boolean, should DynamicPart applyDynamics every
     *                                            tick?
     * @param UPDATE_DYNAMICS_EVERY_X_TICK        boolean, should DynamicPart applyDynamics every x
     *                                            amount of ticks?
     * @param UPDATE_DYNAMICS_EVERY_X_TICKS       int, number of ticks between each applyDynamics
     * @param UPDATE_DYNAMICS_EVERY_DELTA_TICK    boolean, should DynamicPart applyDynamics every
     *                                            delta tick?
     * @param UPDATE_DYNAMICS_EVERY_X_DELTA_TICK  boolean, should DynamicPart applyDynamics every x
     *                                            amount of delta ticks?
     * @param UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS int, number of deltaTicks between each
     *                                            applyDynamics
     * @return DynamicModelPart instance
     */
    public DynamicModelPart with(boolean UV_SHIFT_APPLY_SYNC, boolean UV_SHIFTABLE,
            int UV_SHIFT_AMOUNT, boolean UV_SHIFT_EVERY_X_TICK, boolean UV_SHIFT_EVERY_TICK,
            int UV_SHIFT_EVERY_X_TICKS, boolean UV_SHIFT_EVERY_DELTA_TICK,
            boolean UV_SHIFT_EVERY_X_DELTA_TICK, int UV_SHIFT_EVERY_X_DELTA_TICKS,
            boolean UPDATE_DYNAMICS_EVERY_TICK, boolean UPDATE_DYNAMICS_EVERY_X_TICK,
            int UPDATE_DYNAMICS_EVERY_X_TICKS, boolean UPDATE_DYNAMICS_EVERY_DELTA_TICK,
            boolean UPDATE_DYNAMICS_EVERY_X_DELTA_TICK, int UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS) {
        this.UV_SHIFT_APPLY_SYNC = UV_SHIFT_APPLY_SYNC;
        this.UV_SHIFTABLE = UV_SHIFTABLE;
        this.UV_SHIFT_AMOUNT = UV_SHIFT_AMOUNT;
        this.UV_SHIFT_EVERY_TICK = UV_SHIFT_EVERY_TICK;
        this.UV_SHIFT_EVERY_X_TICK = UV_SHIFT_EVERY_X_TICK;
        this.UV_SHIFT_EVERY_X_TICKS = UV_SHIFT_EVERY_X_TICKS;
        this.UV_SHIFT_EVERY_DELTA_TICK = UV_SHIFT_EVERY_DELTA_TICK;
        this.UV_SHIFT_EVERY_X_DELTA_TICK = UV_SHIFT_EVERY_X_DELTA_TICK;
        this.UV_SHIFT_EVERY_X_DELTA_TICKS = UV_SHIFT_EVERY_X_DELTA_TICKS;
        this.UPDATE_DYNAMICS_EVERY_TICK = UPDATE_DYNAMICS_EVERY_TICK;
        this.UPDATE_DYNAMICS_EVERY_X_TICK = UPDATE_DYNAMICS_EVERY_X_TICK;
        this.UPDATE_DYNAMICS_EVERY_X_TICKS = UPDATE_DYNAMICS_EVERY_X_TICKS;
        this.UPDATE_DYNAMICS_EVERY_DELTA_TICK = UPDATE_DYNAMICS_EVERY_DELTA_TICK;
        this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICK = UPDATE_DYNAMICS_EVERY_X_DELTA_TICK;
        this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS = UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS;
        return this;
    }

    /**
     * Creates a blank DynamicModelPart with empty cuboids and empty children
     * 
     * @param textureWidth   int, textureWidth
     * @param textureHeight  int, textureHeight
     * @param textureOffsetU int, textureOffsetU
     * @param textureOffsetV int, textureOffsetV
     */
    public DynamicModelPart(int textureWidth, int textureHeight, int textureOffsetU,
            int textureOffsetV) {
        super(textureWidth, textureHeight, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
    }

    /**
     * Adds a DynamicModelPart to the children array
     * 
     * @param child DynamicModelPart, child to add to this instance of DynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart addDynamicChild(DynamicModelPart child) {
        this.children.add(child);
        return this;
    }

    /**
     * Adds all DynamicModelPart(s) to the children array
     * 
     * @param children ObjectList<DynamicModelPart>, array of DynamicModelPart(s) to add to the
     *                 children of this instance of DynamicModelPart
     * @return DynamicModelPart instance
     */
    public DynamicModelPart addDynamicChildren(ObjectList<DynamicModelPart> children) {
        this.children.addAll(children);
        return this;
    }

    /**
     * The actual 'dynamic' part
     */
    @Environment(EnvType.CLIENT)
    public class DynamicPart {


        public DYNAMIC_ENUM dynamic; // key
        public boolean state; // enabled/disabled
        public float value; // current value
        public float newValue; // value to lerp to
        public float min; // min value
        public float max; // max value
        public float lerpPercent; // lerp percentage
        public float applyRandomMax; // apply random max
        public float applyRandomMin; // apply random min
        public float applyRandomMultiplier; // apply random multiplier

        /**
         * Creates a new DynamicPart
         * 
         * @param dynamic DYNAMIC_ENUM key
         */
        public DynamicPart(DYNAMIC_ENUM dynamic) {
            this.dynamic(dynamic, true, DEFAULT_MIN.clone()[dynamic.ordinal()],
                    DEFAULT_MAX.clone()[dynamic.ordinal()], 0F, (float) Math.random(),
                    DEFAULT_APPLY_RANDOM_MAX.clone()[dynamic.ordinal()],
                    DEFAULT_APPLY_RANDOM_MIN.clone()[dynamic.ordinal()],
                    DEFAULT_APPLY_RANDOM_MULTIPLIER.clone()[dynamic.ordinal()]).apply(true);
        }

        /**
         * Creates a new DynamicPart and it's state (enabled/disabled)
         * 
         * @param dynamic DYNAMIC_ENUM key
         * @param state   boolean, on/off (true/false)
         */
        public DynamicPart(DYNAMIC_ENUM dynamic, boolean state) {
            this.dynamic(dynamic, state, DEFAULT_MIN.clone()[dynamic.ordinal()],
                    DEFAULT_MAX.clone()[dynamic.ordinal()], 0F, (float) Math.random(),
                    DEFAULT_APPLY_RANDOM_MAX.clone()[dynamic.ordinal()],
                    DEFAULT_APPLY_RANDOM_MIN.clone()[dynamic.ordinal()],
                    DEFAULT_APPLY_RANDOM_MULTIPLIER.clone()[dynamic.ordinal()]).apply(true);
        }

        /**
         * Creates a new DynamicPart and sets all fields
         * 
         * @param dynamic               DYNAMIC_ENUM key
         * @param state                 boolean, on/off
         * @param min                   float, minimum value allowed
         * @param max                   float, maximum value allowed
         * @param value                 float, current value
         * @param lerpPercent           float, lerp percentage
         * @param applyRandomMax        float, apply random max
         * @param applyRandomMin        float, apply random min
         * @param applyRandomMultiplier float, apply random multiplier
         */
        public DynamicPart(DYNAMIC_ENUM dynamic, boolean state, float min, float max, float value,
                float lerpPercent, float applyRandomMax, float applyRandomMin,
                float applyRandomMultiplier) {
            this.dynamic(dynamic, state, min, max, value, lerpPercent, applyRandomMax,
                    applyRandomMin, applyRandomMultiplier).apply(true);
        }

        /**
         * Helper function to set all fields
         * 
         * @param dEnum
         * @param state
         * @param min
         * @param max
         * @param value
         * @param lerpPercent
         * @param applyRandomMax
         * @param applyRandomMin
         * @param applyRandomMultiplier
         * @return DynamicPart instance
         */
        public DynamicPart dynamic(DYNAMIC_ENUM dEnum, boolean state, float min, float max,
                float value, float lerpPercent, float applyRandomMax, float applyRandomMin,
                float applyRandomMultiplier) {
            return this.setEnum(dEnum).state(state).minMax(min, max).value(value)
                    .lerpPercent(lerpPercent).applyRandomMax(applyRandomMax)
                    .applyRandomMin(applyRandomMin).applyRandomMultiplier(applyRandomMultiplier);
        }

        /**
         * Set the key
         * 
         * @param dEnum DYNAMIC_ENUM key
         * @return DynamicPart instance
         */
        public DynamicPart setEnum(DYNAMIC_ENUM dEnum) {
            this.dynamic = dEnum;
            return this;
        }

        /**
         * Set the min and max allowed values
         * 
         * @param min float, minimum allowed value
         * @param max float, maximum allowed value
         * @return DynamicPart instance
         */
        public DynamicPart minMax(float min, float max) {
            return this.min(min).max(max);
        }

        /**
         * Set the state (enable/disable) (on/off)
         * 
         * @param state boolean
         * @return DynamicPart instance
         */
        public DynamicPart state(boolean state) {
            this.state = state;
            return this;
        }

        /**
         * Disables the DynamicPart
         * 
         * @return DynamicPart instance
         */
        public DynamicPart disable() {
            this.state = false;
            return this;
        }

        /**
         * Enables the DynamicPart
         * 
         * @return DynamicPart instance
         */
        public DynamicPart enable() {
            this.state = true;
            return this;
        }

        /**
         * Sets the minimum allowed value
         * 
         * @param min float, minimum allowed value
         * @return DynamicPart
         */
        public DynamicPart min(float min) {
            this.min = min;
            return this;
        }

        /**
         * Sets the maximum allowed value
         * 
         * @param max float, maximum allowed value
         * @return DynamicPart
         */
        public DynamicPart max(float max) {
            this.max = max;
            return this;
        }

        /**
         * Sets the current value
         * 
         * @param value float, current value
         * @return DynamicPart
         */
        public DynamicPart value(float value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the lerp percentage
         * 
         * @param lerpPercent float, percent to lerp
         * @return DynamicPart
         */
        public DynamicPart lerpPercent(float lerpPercent) {
            this.lerpPercent = lerpPercent;
            return this;
        }

        /**
         * Sets the random maximum value used during apply
         * 
         * @param applyRandomMax
         * @return DynamicPart instance
         */
        public DynamicPart applyRandomMax(float applyRandomMax) {
            this.applyRandomMax = applyRandomMax;
            return this;
        }

        /**
         * Sets the random minimum value used during apply
         * 
         * @param applyRandomMin
         * @return
         */
        public DynamicPart applyRandomMin(float applyRandomMin) {
            this.applyRandomMin = applyRandomMin;
            return this;
        }

        /**
         * Sets the random multiplier value used during apply
         * 
         * @param applyRandomMultiplier
         * @return
         */
        public DynamicPart applyRandomMultiplier(float applyRandomMultiplier) {
            this.applyRandomMultiplier = applyRandomMultiplier;
            return this;
        }

        /**
         * Causes the DynamicPart to calculate new values if the dynamicPart is enabled (state)
         * 
         * @return DynamicPart instance
         */
        public DynamicPart apply(boolean shouldApplyDynamics) {
            if (this.state) {
                // if the values are equal to 3 decimal places calculate a new value
                Random random = new Random();
                if (shouldApplyDynamics || round(this.value, 3) == round(this.newValue, 3)) {
                    float r = (float) (((random.nextGaussian() * this.applyRandomMax)
                            + this.applyRandomMin) * this.applyRandomMultiplier);
                    this.newValue = (float) ((((r * this.max) + this.min)));
                    if (this.newValue + this.value < 0 || this.newValue - this.value > 0) {
                        this.newValue *= -1;
                    }
                }
                // CosineInterpolate
                this.value = (float) Interpolation.CosineInterpolate(this.value, this.newValue,
                        this.lerpPercent);
            }
            return this;
        }

        /**
         * Rounding helper function. Rounds to decimal places
         * 
         * @param value     number to round
         * @param precision the number of decimal places
         * @return double, rounded to percision
         */
        protected double round(double value, int precision) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        }

        @Override
        public String toString() {
            return "ENUM: " + DYNAMIC_ENUM.values()[dynamic.ordinal()] + "\nVALUE: " + this.value;
        }


    }

    @Environment(EnvType.CLIENT)
    /** DynamicModelPart.DynamicVertex == ModelPart.Vertex */
    public class DynamicVertex {
        public final Vector3f pos;
        public final float u;
        public final float v;


        public DynamicVertex(Vector3f vector3f, float u, float v) {
            this.pos = vector3f;
            this.u = u;
            this.v = v;
        }

        public DynamicVertex(float x, float y, float z, float u, float v) {
            this(new Vector3f(x, y, z), u, v);
        }

        public DynamicModelPart.DynamicVertex remap(float u, float v) {
            return new DynamicModelPart.DynamicVertex(this.pos, u, v);
        }
    }
    /** DynamicModelPart.DynamicQuad == ModelPart.Quad */
    @Environment(EnvType.CLIENT)
    public class DynamicQuad {

        public final DynamicModelPart.DynamicVertex[] vertices;
        public final Vector3f direction;
        public final DynamicModelPart.DynamicCuboid parentCuboid;

        public DynamicQuad(DynamicModelPart.DynamicCuboid parentCuboid,
                DynamicModelPart.DynamicVertex[] vertices, float u1, float v1, float u2, float v2,
                float squishU, float squishV, boolean flip, Direction direction) {
            this.parentCuboid = parentCuboid;
            this.vertices = vertices;
            float f = 0.0F / squishU;
            float g = 0.0F / squishV;
            vertices[0] = vertices[0].remap(u2 / squishU - f, v1 / squishV + g);
            vertices[1] = vertices[1].remap(u1 / squishU + f, v1 / squishV + g);
            vertices[2] = vertices[2].remap(u1 / squishU + f, v2 / squishV - g);
            vertices[3] = vertices[3].remap(u2 / squishU - f, v2 / squishV - g);
            if (flip) {
                int i = vertices.length;

                for (int j = 0; j < i / 2; ++j) {
                    DynamicModelPart.DynamicVertex vertex = vertices[j];
                    vertices[j] = vertices[i - 1 - j];
                    vertices[i - 1 - j] = vertex;
                }
            }

            this.direction = direction.getUnitVector();
            if (flip) {
                this.direction.multiplyComponentwise(-1.0F, 1.0F, 1.0F);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public class DynamicCuboid extends Cuboid {

        public DynamicModelPart parentModelPart; // parent dynamic model part
        protected DynamicPart[] parts; // DynamicParts attached to this cuboid
        protected DynamicModelPart.DynamicQuad[] sides; // each face of the cuboid
        protected float x; // x coord
        protected float y; // y coord
        protected float z; // z coord
        protected float sizeX; // size in x direction
        protected float sizeY; // size in y direction
        protected float sizeZ; // size in z direction
        protected float extraX; // extra X
        protected float extraY; // extra Y
        protected float extraZ; // extra Z
        protected float u; // U (texture U)
        protected float v; // V (texture V)
        protected boolean mirror; // boolean
        protected float textureWidth; // textureWidth
        protected float textureHeight; // textureHeight

        /**
         * Creates a new DynamicCuboid with a seed DynamicPart[]
         * 
         * @param parentModelPart // parent
         * @param u               float, u (texture u)
         * @param v               float, v (texture v)
         * @param x               float, x coord
         * @param y               float, y coord
         * @param z               float, z coord
         * @param sizeX           float, size in x direction
         * @param sizeY           float, size in y direction
         * @param sizeZ           float, size in z direction
         * @param extraX          float extra X
         * @param extraY          float, extra Y
         * @param extraZ          float, extra Z
         * @param mirror          boolean
         * @param textureWidth    float, texture width
         * @param textureHeight   float, texture height
         * @param seed            DynamicPart[], seed DynamicPart array for this cuboid
         */
        public DynamicCuboid(DynamicModelPart parentModelPart, int u, int v, float x, float y,
                float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY,
                float extraZ, boolean mirror, float textureWidth, float textureHeight,
                DynamicPart[] seed) {
            super(u, v, x, y, z, sizeX, sizeY, extraX, sizeZ, extraY, extraZ, mirror, textureWidth,
                    textureHeight);
            this.set(parentModelPart, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ,
                    mirror, textureWidth, textureHeight, seed).build();
        }

        /**
         * Same as previous function, except seed DynamicPart[] is defaulted
         * 
         * @param parentModelPart
         * @param u
         * @param v
         * @param x
         * @param y
         * @param z
         * @param sizeX
         * @param sizeY
         * @param sizeZ
         * @param extraX
         * @param extraY
         * @param extraZ
         * @param mirror
         * @param textureWidth
         * @param textureHeight
         */
        public DynamicCuboid(DynamicModelPart parentModelPart, int u, int v, float x, float y,
                float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY,
                float extraZ, boolean mirror, float textureWidth, float textureHeight) {
            super(u, v, x, y, z, sizeX, sizeY, extraX, sizeZ, extraY, extraZ, mirror, textureWidth,
                    textureHeight);

            DynamicPart[] parts = new DynamicPart[DYNAMIC_ENUM_LENGTH];
            for (int i = 0; i < DYNAMIC_ENUM_LENGTH; i++) {
                parts[i] = new DynamicPart(DYNAMIC_ENUM.values()[i], true);
            }

            this.set(parentModelPart, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ,
                    mirror, textureWidth, textureHeight, parts).build();
        }

        /**
         * Sets DynamicPart[] parts to this cuboid
         * 
         * @param parts DynamicPart[] parts
         * @return DynamicCuboid instance
         */
        public DynamicCuboid parts(DynamicPart[] parts) {
            this.parts = sortParts(parts);
            return this;
        }

        /**
         * Sorts parts into the correct order based on DYNAMIC_ENUM ordinal
         * 
         * @param parts DynamicPart[] unsorted
         * @return DynamicPart[] sorted
         */
        public DynamicPart[] sortParts(DynamicPart[] parts) {
            Arrays.sort(parts, (DynamicPart a, DynamicPart b) -> {
                return a.dynamic.ordinal() - b.dynamic.ordinal();
            });
            return parts;
        }

        /**
         * Helper function to set all fields
         * 
         * @param parentModelPart
         * @param u
         * @param v
         * @param x
         * @param y
         * @param z
         * @param sizeX
         * @param sizeY
         * @param sizeZ
         * @param extraX
         * @param extraY
         * @param extraZ
         * @param mirror
         * @param textureWidth
         * @param textureHeight
         * @param seed
         * @return DynamicCuboid instance
         */
        public DynamicCuboid set(DynamicModelPart parentModelPart, int u, int v, float x, float y,
                float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY,
                float extraZ, boolean mirror, float textureWidth, float textureHeight,
                DynamicPart[] seed) {
            return this.setParentModelPart(parentModelPart).setMirror(mirror)
                    .setTextureWidth(textureWidth).setTextureHeight(textureHeight).setUV(u, v)
                    .setExtra(extraX, extraY, extraZ).setSize(sizeX, sizeY, sizeZ).setXYZ(x, y, z)
                    .parts(seed);
        }

        /**
         * Set the parent DynamicModelPart
         * 
         * @param parentModelPart DynamicModelPart parent
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setParentModelPart(DynamicModelPart parentModelPart) {
            this.parentModelPart = parentModelPart;
            return this;
        }

        /**
         * Set the mirror boolean for this DynamicCuboid
         * 
         * @param mirror boolean, mirror enabled/disabled
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setMirror(boolean mirror) {
            this.mirror = mirror;
            return this;
        }

        /**
         * Sets the texture width for this DynamicCuboid
         * 
         * @param width float, texture width
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setTextureWidth(float width) {
            this.textureWidth = width;
            return this;
        }

        /**
         * Sets the texture height for this DynamicCuboid
         * 
         * @param height float, texture height
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setTextureHeight(float height) {
            this.textureHeight = height;
            return this;
        }

        /**
         * Sets the texture U location for this DynamicCuboid
         * 
         * @param u float, texture u
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setU(float u) {
            this.u = u;
            return this;
        }

        /**
         * Sets the texture V location for this DynamicCuboid
         * 
         * @param v float, texture v
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setV(float v) {
            this.v = v;
            return this;
        }

        /**
         * Sets the texture UV location for this DynamicCuboid
         * 
         * @param u float, texture u
         * @param v float, texture v
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setUV(float u, float v) {
            return this.setU(u).setV(v);
        }

        /**
         * Sets the x location for this DynamicCuboid
         * 
         * @param x float, texture x
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setX(float x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the y location for this DynamicCuboid
         * 
         * @param y float, texture y
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setY(float y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the z location for this DynamicCuboid
         * 
         * @param z float, texture z
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setZ(float z) {
            this.z = z;
            return this;
        }

        /**
         * Sets the size in the x direction for this DynamicCuboid
         * 
         * @param x float, size in x direction
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setSizeX(float x) {
            this.sizeX = x;
            return this;
        }

        /**
         * Sets the size in the y direction for this DynamicCuboid
         * 
         * @param y float, size in y direction
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setSizeY(float y) {
            this.sizeY = y;
            return this;
        }

        /**
         * Sets the size in the z direction for this DynamicCuboid
         * 
         * @param z float, size in z direction
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setSizeZ(float z) {
            this.sizeZ = z;
            return this;
        }

        /**
         * Sets the extra X for this DynamicCuboid
         * 
         * @param x float, extra X
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setExtraX(float x) {
            this.extraX = x;
            return this;
        }

        /**
         * Sets the extra Y for this DynamicCuboid
         * 
         * @param y float, extra Y
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setExtraY(float y) {
            this.extraY = y;
            return this;
        }

        /**
         * Sets the extra Z for this DynamicCuboid
         * 
         * @param z float, extra Z
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setExtraZ(float z) {
            this.extraZ = z;
            return this;
        }

        /**
         * Sets the extra X Y and Z
         * 
         * @param x float, extra X
         * @param y float, extra Y
         * @param z float, extra Z
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setExtra(float x, float y, float z) {
            return this.setExtraX(x).setExtraY(y).setExtraZ(z);
        }

        /**
         * Sets the size X Y and Z
         * 
         * @param x float, size X
         * @param y float, size Y
         * @param z float, size Z
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setSize(float x, float y, float z) {
            return this.setSizeX(x).setSizeY(y).setSizeZ(z);
        }

        /**
         * Sets the X Y and Z
         * 
         * @param x float, x
         * @param y float, y
         * @param z float, z
         * @return DynamicCuboid instance
         */
        public DynamicCuboid setXYZ(float x, float y, float z) {
            return this.setX(x).setY(y).setZ(z);
        }


        /**
         * builds the DynamicCuboid
         */
        public DynamicModelPart.DynamicCuboid build() {
            float x = this.x;
            float y = this.y;
            float z = this.z;
            float f = this.x + this.sizeX;
            float g = this.y + this.sizeY;
            float h = this.z + this.sizeZ;

            x -= this.extraX;
            y -= this.extraY;
            z -= this.extraZ;

            f += this.extraX;
            g += this.extraY;
            h += this.extraZ;


            if (this.mirror) {
                float i = f;
                f = x;
                x = i;
            }


            float j = (float) this.u;
            float k = (float) this.u + this.sizeZ;
            float l = (float) this.u + this.sizeZ + this.sizeX;
            float m = (float) this.u + this.sizeZ + this.sizeX + this.sizeX;
            float n = (float) this.u + this.sizeZ + this.sizeX + this.sizeZ;
            float o = (float) this.u + this.sizeZ + this.sizeX + this.sizeZ + this.sizeX;
            float p = (float) this.v;
            float q = (float) this.v + this.sizeZ;
            float r = (float) this.v + this.sizeZ + this.sizeY;

            DynamicModelPart.DynamicVertex[] vertexs = buildVertexs(x, y, z, f, g, h);
            this.sides = new DynamicModelPart.DynamicQuad[] {
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[5], vertexs[1],
                                    vertexs[2], vertexs[6]},
                            l, q, n, r, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.EAST),
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[0], vertexs[4],
                                    vertexs[7], vertexs[3]},
                            j, q, k, r, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.WEST),
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[5], vertexs[4],
                                    vertexs[0], vertexs[1]},
                            k, p, l, q, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.DOWN),
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[2], vertexs[3],
                                    vertexs[7], vertexs[6]},
                            l, q, m, p, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.UP),
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[1], vertexs[0],
                                    vertexs[3], vertexs[2]},
                            k, q, l, r, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.NORTH),
                    new DynamicModelPart.DynamicQuad(this,
                            new DynamicModelPart.DynamicVertex[] {vertexs[4], vertexs[5],
                                    vertexs[6], vertexs[7]},
                            n, q, o, r, this.textureWidth, this.textureHeight, this.mirror,
                            Direction.SOUTH)};
            return this;
        }

        /**
         * builds the vertices of a quad
         * 
         * @param x float, x coord
         * @param y float, y coord
         * @param z float, z coord
         * @param f float, opposite x coord
         * @param g float, opposite y coord
         * @param h float, opposite z coord
         * @return DynamicVertex[], array of DynamicVertex
         */
        public DynamicModelPart.DynamicVertex[] buildVertexs(float x, float y, float z, float f,
                float g, float h) {
            DynamicModelPart.DynamicVertex vertex0 =
                    new DynamicModelPart.DynamicVertex(x, y, z, 0.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex1 =
                    new DynamicModelPart.DynamicVertex(f, y, z, 0.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex2 =
                    new DynamicModelPart.DynamicVertex(f, g, z, 8.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex3 =
                    new DynamicModelPart.DynamicVertex(x, g, z, 8.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex4 =
                    new DynamicModelPart.DynamicVertex(x, y, h, 0.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex5 =
                    new DynamicModelPart.DynamicVertex(f, y, h, 0.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex6 =
                    new DynamicModelPart.DynamicVertex(f, g, h, 8.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex7 =
                    new DynamicModelPart.DynamicVertex(x, g, h, 8.0F, 0.0F);
            return new DynamicModelPart.DynamicVertex[] {vertex0, vertex1, vertex2, vertex3,
                    vertex4, vertex5, vertex6, vertex7};
        }

        public void apply(boolean shouldApplyDynamics) {
            ObjectArrayList.wrap(this.parts).parallelStream().forEach(part -> {
                part.apply(shouldApplyDynamics);
            });
        }

        public float getPartValue(DYNAMIC_ENUM dEnum) {
            return this.parts[dEnum.ordinal()].value;
        }
    }

    /**
     * Overrides default render function and sends to dynamic render... but will not render
     * dynamically
     */
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light,
            int overlay) {
        this.renderDynamic(false, 0, matrices, vertexConsumer, light, overlay);
    }

    /**
     * Overrides default render function with RGBA and sends to dynamic render... but will not
     * render dynamically
     */
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay,
            float red, float green, float blue, float alpha) {
        this.renderDynamic(false, 0, matrices, vertexConsumer, light, overlay, red, green, blue,
                alpha);
    }

    /**
     * renderDynamic helper function (VertexConsumer)
     * 
     * @param ticked
     * @param tick
     * @param matrices
     * @param vertexConsumer
     * @param light
     * @param overlay
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
            VertexConsumer vertexConsumer, int light, int overlay) {
        this.renderDynamic(ticked, tick, matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F,
                1.0F);
    }

    /**
     * renderDynamic helper function (VertexConsumerProvider)
     * 
     * @param ticked
     * @param tick
     * @param matrices
     * @param vertexConsumers
     * @param light
     * @param overlay
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, 1.0F, 1.0F,
                1.0F, 1.0F);
    }

    /**
     * render dynamic using VertexConsumer
     * 
     * @param ticked         boolean, whether or not a full tick occured
     * @param tick           int, the current tick
     * @param matrices       MatrixStack
     * @param vertexConsumer VertexConsumer
     * @param light          int
     * @param overlay        int
     * @param red            float
     * @param green          float
     * @param blue           float
     * @param alpha          float
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
            VertexConsumer vertexConsumer, int light, int overlay, float red, float green,
            float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(ticked, tick, matrices.peek(), vertexConsumer, light,
                        overlay, red, green, blue, alpha);
                matrices.pop();
            }
            if (!this.children.isEmpty()) {

                this.children.parallelStream().forEach(child -> {
                    child.renderDynamic(ticked, tick, matrices, vertexConsumer, light, overlay, red,
                            green, blue, alpha);
                });
            }
        }
    }

    /**
     * render dynamic using VertexConsumerProvider
     * 
     * @param ticked          boolean, whether or not a full tick occured
     * @param tick            int, the current tick
     * @param matrices        MatrixStack
     * @param vertexConsumers VertexConsumerProvider
     * @param light           int
     * @param overlay         int
     * @param red             float
     * @param green           float
     * @param blue            float
     * @param alpha           float
     */
    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green,
            float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(ticked, tick, matrices.peek(), vertexConsumers, light,
                        overlay, red, green, blue, alpha);
                matrices.pop();
            }
            if (!this.children.isEmpty()) {
                this.children.parallelStream().forEach(child -> {
                    child.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay,
                            red, green, blue, alpha);
                });
            }
        }
    }

    /**
     * render cuboids dynamic using VertexConsumerProvider by calling next function with
     * vertexConsumer from spriteId
     * 
     * @param ticked
     * @param tick
     * @param matrices
     * @param vertexConsumers
     * @param light
     * @param overlay
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    protected void renderCuboidsDynamic(boolean ticked, int tick, MatrixStack.Entry matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green,
            float blue, float alpha) {
        renderCuboidsDynamic(ticked, tick, matrices,
                spriteId.getVertexConsumer(vertexConsumers, layerFactory), light, overlay, red,
                green, blue, alpha);
    }

    /**
     * renders each cuboid using dynamicPart values as offsets
     * 
     * @param ticked
     * @param tick
     * @param matrices
     * @param vertexConsumer
     * @param light
     * @param overlay
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    protected void renderCuboidsDynamic(boolean ticked, int tick, MatrixStack.Entry matrices,
            VertexConsumer vertexConsumer, int light, int overlay, float red, float green,
            float blue, float alpha) {

        boolean shouldApplyDynamics = false;

        if (shiftDeltaTickCounter > UV_SHIFT_EVERY_X_DELTA_TICKS) {
            shiftDeltaTickCounter = 0;
        }

        if (dynamicsDeltaTickCounter > UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS) {
            dynamicsDeltaTickCounter = 0;
        }

        if (UV_SHIFTABLE && (((UV_SHIFT_EVERY_X_TICK && ((int) tick % UV_SHIFT_EVERY_X_TICKS == 0)
                || UV_SHIFT_EVERY_TICK) && ticked) || UV_SHIFT_EVERY_DELTA_TICK
                || (UV_SHIFT_EVERY_X_DELTA_TICK
                        && UV_SHIFT_EVERY_X_DELTA_TICKS == ++shiftDeltaTickCounter))) {
            // System.out.println("RCD - Shifting UV");
            if (UV_SHIFT_APPLY_SYNC) {
                shouldApplyDynamics = true;
            }
            shiftUV(UV_SHIFT_AMOUNT).rebuild();
        }

        if ((UPDATE_DYNAMICS_EVERY_TICK && ticked)
                || (UPDATE_DYNAMICS_EVERY_X_TICK
                        && ((int) tick % UPDATE_DYNAMICS_EVERY_X_TICKS == 0))
                || UPDATE_DYNAMICS_EVERY_DELTA_TICK || (UPDATE_DYNAMICS_EVERY_X_DELTA_TICK
                        && UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS == ++dynamicsDeltaTickCounter)) {
            shouldApplyDynamics = true;
        }

        // if (shouldApplyDynamics) {
        // System.out.println("RCD applying dynamics");
        // }


        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();
        ObjectListIterator<DynamicModelPart.DynamicCuboid> cubiodsIterator =
                this.cuboids.iterator();

        float dx = 0.0F; // x value offset
        float dy = 0.0F; // y value offset
        float dz = 0.0F; // z value offset
        float dRed = 0.0F; // red value offset
        float dGreen = 0.0F; // green value offset
        float dBlue = 0.0F; // blue value offset
        float dAlpha = 0.0F; // alpha value offset
        float dLight = 0.0F; // light value offset


        while (cubiodsIterator.hasNext()) {

            DynamicModelPart.DynamicCuboid cuboid =
                    (DynamicModelPart.DynamicCuboid) cubiodsIterator.next();
            cuboid.apply(shouldApplyDynamics);

            // get each dynamicPart value
            dx = cuboid.getPartValue(DYNAMIC_ENUM.X);
            dy = cuboid.getPartValue(DYNAMIC_ENUM.Y);
            dz = cuboid.getPartValue(DYNAMIC_ENUM.Z);
            dRed = cuboid.getPartValue(DYNAMIC_ENUM.RED);
            dGreen = cuboid.getPartValue(DYNAMIC_ENUM.GREEN);
            dBlue = cuboid.getPartValue(DYNAMIC_ENUM.BLUE);
            dAlpha = cuboid.getPartValue(DYNAMIC_ENUM.ALPHA);
            dLight = cuboid.getPartValue(DYNAMIC_ENUM.LIGHT);


            // System.out.println(dx + " " + dy+ " " +dz+ " " +(red+dRed)+ " " +(green+dGreen)+ " "
            // +(blue+dBlue)+ " " + (alpha+dAlpha)+ " " + (int)(light+dLight));
            // System.out.println(dx);
            DynamicModelPart.DynamicQuad[] cuboidSidesQuadArray = cuboid.sides;
            int cuboidSidesLength = cuboidSidesQuadArray.length;

            for (int x = 0; x < cuboidSidesLength; ++x) {
                DynamicModelPart.DynamicQuad quad = cuboidSidesQuadArray[x];
                Vector3f vector3f = quad.direction.copy();
                vector3f.transform(matrix3f);
                float normalX = vector3f.getX();
                float normalY = vector3f.getY();
                float normalZ = vector3f.getZ();

                for (int i = 0; i < 4; ++i) {
                    DynamicModelPart.DynamicVertex vertex = quad.vertices[i];

                    float j = vertex.pos.getX() / 16.0F;
                    float k = vertex.pos.getY() / 16.0F;
                    float l = vertex.pos.getZ() / 16.0F;

                    Vector4f vector4f = new Vector4f(j, k, l, 1.0F);
                    vector4f.transform(matrix4f);
                    vertexConsumer.vertex(vector4f.getX() + dx, vector4f.getY() + dy,
                            vector4f.getZ() + dz, red + dRed, green + dGreen, blue + dBlue,
                            alpha + dAlpha, vertex.u, vertex.v, overlay, (int) (light + dLight),
                            normalX, normalY, normalZ);
                }
            }
        }
    }

    /**
     * Texture Size setter
     * 
     * @param width  int
     * @param height int
     * @return DynamicModelPart
     */
    public DynamicModelPart setTextureSize(int width, int height) {
        this.textureWidth = (float) width;
        this.textureHeight = (float) height;
        return this;
    }

    /**
     * Override getRandomCuboid (ModelPart.Cuboid)
     */
    @Override
    public DynamicModelPart.DynamicCuboid getRandomCuboid(Random random) {
        return (DynamicModelPart.DynamicCuboid) this.cuboids
                .get(random.nextInt(this.cuboids.size()));
    }

    /**
     * Override setTextureOffset (ModelPart.Cuboid)
     */
    @Override
    public DynamicModelPart setTextureOffset(int textureOffsetU, int textureOffsetV) {
        this.textureOffsetU = textureOffsetU;
        this.textureOffsetV = textureOffsetV;
        return this;
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public DynamicModelPart addCuboid(String name, float x, float y, float z, int sizeX, int sizeY,
            int sizeZ, float extra, int textureOffsetU, int textureOffsetV) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z,
                (float) sizeX, (float) sizeY, (float) sizeZ, extra, extra, extra, this.mirror,
                false);
        return this;
    }

    /**
     * set the texture UV offsets and addDynamicCuboid
     * 
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extra
     * @param textureOffsetU
     * @param textureOffsetV
     * @return
     */
    public DynamicModelPart addCuboid(float x, float y, float z, int sizeX, int sizeY, int sizeZ,
            float extra, int textureOffsetU, int textureOffsetV) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z,
                (float) sizeX, (float) sizeY, (float) sizeZ, extra, extra, extra, this.mirror,
                false);
        return this;
    }

    /**
     * set the texture UV offsets and addDynamicCudoid (with seed)
     * 
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extra
     * @param textureOffsetU
     * @param textureOffsetV
     * @param seed
     * @return
     */
    public DynamicModelPart addCuboid(float x, float y, float z, int sizeX, int sizeY, int sizeZ,
            float extra, int textureOffsetU, int textureOffsetV, DynamicPart[] seed) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z,
                (float) sizeX, (float) sizeY, (float) sizeZ, extra, extra, extra, this.mirror,
                false, seed);
        return this;
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public DynamicModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY,
            float sizeZ) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY,
                sizeZ, 0.0F, 0.0F, 0.0F, this.mirror, false);
        return this;
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public DynamicModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY,
            float sizeZ, boolean mirror) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY,
                sizeZ, 0.0F, 0.0F, 0.0F, mirror, false);
        return this;
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ,
            float extra) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY,
                sizeZ, extra, extra, extra, this.mirror, false);
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ,
            float extraX, float extraY, float extraZ) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY,
                sizeZ, extraX, extraY, extraZ, this.mirror, false);
    }

    /**
     * Override addCuboid (ModelPart.Cuboid)
     */
    @Override
    public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ,
            float extra, boolean mirror) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY,
                sizeZ, extra, extra, extra, mirror, false);
    }

    /**
     * add DynamicCuboid to cuboids array (without seed)
     * 
     * @param parent
     * @param u
     * @param v
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extraX
     * @param extraY
     * @param extraZ
     * @param mirror
     * @param bl
     */
    public void addDynamicCuboid(DynamicModelPart parent, int u, int v, float x, float y, float z,
            float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ,
            boolean mirror, boolean bl) {
        this.cuboids.add(new DynamicCuboid(parent, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX,
                extraY, extraZ, mirror, this.textureWidth, this.textureHeight));
    }

    /**
     * add DynamicCuboid to cuboids array (with seed)
     * 
     * @param parent
     * @param u
     * @param v
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extraX
     * @param extraY
     * @param extraZ
     * @param mirror
     * @param bl
     * @param seed
     */
    public void addDynamicCuboid(DynamicModelPart parent, int u, int v, float x, float y, float z,
            float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ,
            boolean mirror, boolean bl, DynamicPart[] seed) {
        this.cuboids.add(new DynamicCuboid(parent, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX,
                extraY, extraZ, mirror, this.textureWidth, this.textureHeight, seed));
    }

    /**
     * Gets the current children array
     * 
     * @return ObjectList<DynamicModelPart> children
     */
    public ObjectList<DynamicModelPart> getChildren() {
        return this.children;
    }

    /**
     * gets each cuboid's DynamicPart[]
     * 
     * @return ObjectList<DynamicPart[]> containing each DynamicCuboid's DynamicPart[]
     */
    public ObjectList<DynamicPart[]> getSeeds() {
        ObjectList<DynamicPart[]> seeds = new ObjectArrayList<DynamicPart[]>();
        seeds.addAll(
                getCuboids().stream().map(cuboid -> cuboid.parts).collect(Collectors.toList()));
        return seeds;
    }

    /**
     * Gets the cuboids attached to this DynamicModelPart
     * 
     * @return ObjectList<DynamicCuboid> array of DynamicCuboids
     */
    public ObjectList<DynamicCuboid> getCuboids() {
        return this.cuboids;
    }

    @Override
    public String toString() {
        return super.toString();
        // String s = "UV_SHIFTABLE: " + this.UV_SHIFTABLE + "\n";
        // s += "UV_SHIFT_APPLY_SYNC: " + this.UV_SHIFT_APPLY_SYNC + "\n";
        // s += "UV_SHIFT_AMOUNT: " + this.UV_SHIFT_AMOUNT + "\n";
        // s += "UV_SHIFTABLE: " + this.UV_SHIFTABLE + "\n";
        // s += "UV_SHIFT_EVERY_X_TICK: " + this.UV_SHIFT_EVERY_X_TICK + "\n";
        // s += "UV_SHIFT_EVERY_TICK: " + this.UV_SHIFT_EVERY_TICK + "\n";
        // s += "UV_SHIFT_EVERY_X_TICKS: " + this.UV_SHIFT_EVERY_X_TICKS + "\n";
        // s += "UV_SHIFT_EVERY_DELTA_TICK: " + this.UV_SHIFT_EVERY_DELTA_TICK + "\n";
        // s += "UV_SHIFT_EVERY_X_DELTA_TICK: " + this.UV_SHIFT_EVERY_X_DELTA_TICK + "\n";
        // s += "UV_SHIFT_EVERY_X_DELTA_TICKS: " + this.UV_SHIFT_EVERY_X_DELTA_TICKS + "\n";
        // s += "UPDATE_DYNAMICS_EVERY_TICK: " + this.UPDATE_DYNAMICS_EVERY_TICK + "\n";
        // s += "UPDATE_DYNAMICS_EVERY_X_TICK: " + this.UPDATE_DYNAMICS_EVERY_X_TICK + "\n";
        // s += "UPDATE_DYNAMICS_EVERY_X_TICKS: " + this.UPDATE_DYNAMICS_EVERY_X_TICKS + "\n";
        // s += "UPDATE_DYNAMICS_EVERY_DELTA_TICK: " + this.UPDATE_DYNAMICS_EVERY_DELTA_TICK + "\n";
        // s += "UPDATE_DYNAMICS_EVERY_X_DELTA_TICK: " + this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICK +
        // "\n";
        // s += "UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS: " + this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS +
        // "\n";
        // return s;
    }

    /**
     * same as next function but is a loop
     * 
     * @param dynamicModel
     * @param x
     * @param y
     * @param z
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     * @param extra
     * @param u
     * @param v
     * @param rotation
     * @param sprite
     * @param layerFactory
     * @return
     */
    public static ObjectList<DynamicModelPart> generateModelParts(DynamicModel dynamicModel,
            float[][] x, float[][] y, float[][] z, int[][] sizeX, int[][] sizeY, int[][] sizeZ,
            float[][] extra, int[][] u, int[][] v, float[][] rotation, SpriteIdentifier[] sprite,
            Function<Identifier, RenderLayer> layerFactory) {
        ObjectList<DynamicModelPart> MODEL_PARTS = new ObjectArrayList<DynamicModelPart>();
        for (int i = 0; i < x.length; i++) {
            MODEL_PARTS.add(generateModelPart(dynamicModel, u[i], v[i], x[i], y[i], z[i], sizeX[i],
                    sizeY[i], sizeZ[i], extra[i], rotation[i], sprite[i], defaultSeeds(x.length),
                    layerFactory));
        }
        return MODEL_PARTS;
    }

    /**
     * generates a DynamicModelPart
     * 
     * @param dynamicModel -- the parent DynamicModel
     * @param u            -- array of each U value
     * @param v            -- array of each V value
     * @param x            -- array of each X value
     * @param y            -- array of each Y value
     * @param z            -- array of each Z value
     * @param sizeX        -- array of each sizeX value
     * @param sizeY        -- array of each sizeY value
     * @param sizeZ        -- array of each sizeZ value
     * @param extra        -- array of each extra value
     * @param rotation     -- array of rotation (x, y, z)
     * @param seeds        -- ObjectList of seed DynamicPart arrays for each cuboid
     * @param spriteId     -- SpriteIdentifier for texture
     * @param layerFactory -- RenderLayer Factory Function
     * @return DyanmicModelPart
     */
    public static DynamicModelPart generateModelPart(DynamicModel dynamicModel, int[] u, int[] v,
            float[] x, float[] y, float[] z, int[] sizeX, int[] sizeY, int[] sizeZ, float[] extra,
            float[] rotation, SpriteIdentifier spriteId, ObjectList<DynamicPart[]> seeds,
            Function<Identifier, RenderLayer> layerFactory) {
        return new DynamicModelPart(dynamicModel, x, y, z, sizeX, sizeY, sizeZ, extra, u, v,
                rotation, seeds, spriteId, layerFactory);
    }

    /**
     * generates a DynamicModelPart given the same data as the previous function but all (u, v, x,
     * y, z, sizeX, sizeY, sizeZ, extra) are placed in one array
     * 
     * @param dynamicModel -- the parent DynamicModel
     * @param allCuboids   -- contains each cuboid's (u, v, x, y, z, etc)
     * @param rotation     -- array of rotation (x, y, z)
     * @param spriteId     -- SpriteIdentifier for texture
     * @param seeds        -- ObjectList of seed DynamicPart arrays for each cuboid
     * @param layerFactory -- RenderLayer Factory Function
     * @return DynamicModelPart
     */
    public static DynamicModelPart generateModelPart(DynamicModel dynamicModel, float[] allCuboids,
            float[] rotation, SpriteIdentifier spriteId, ObjectList<DynamicPart[]> seeds,
            Function<Identifier, RenderLayer> layerFactory) {
        int count = allCuboids.length / 9;
        int[] u = new int[count];
        int[] v = new int[count];
        float[] x = new float[count];
        float[] y = new float[count];
        float[] z = new float[count];
        int[] sizeX = new int[count];
        int[] sizeY = new int[count];
        int[] sizeZ = new int[count];
        float[] extra = new float[count];
        for (int i = 0; i < count; i++) {
            int index = (i * 9);
            u[i] = (int) allCuboids[index];
            v[i] = (int) allCuboids[index + 1];

            x[i] = allCuboids[index + 2];
            y[i] = allCuboids[index + 3];
            z[i] = allCuboids[index + 4];

            sizeX[i] = (int) allCuboids[index + 5];
            sizeY[i] = (int) allCuboids[index + 6];
            sizeZ[i] = (int) allCuboids[index + 7];

            extra[i] = allCuboids[index + 8];
        }
        return generateModelPart(dynamicModel, u, v, x, y, z, sizeX, sizeY, sizeZ, extra, rotation,
                spriteId, seeds, layerFactory);
    }

    /**
     * Creates default seeds for a specific number of cuboids
     * 
     * @param numberOfCuboids int, number of DynamicPart arrays to create
     * @return ObjectList<DynamicPart[]>
     */
    public static ObjectList<DynamicPart[]> defaultSeeds(int numberOfCuboids) {
        DynamicModelPart temp = new DynamicModelPart(0, 0, 0, 0);
        ObjectList<DynamicPart[]> SEEDS = new ObjectArrayList<DynamicPart[]>();
        for (int index = 0; index < numberOfCuboids; index++) {
            DynamicPart[] parts = new DynamicPart[DynamicModelPart.DYNAMIC_ENUM_LENGTH];
            for (int dEnumIndex =
                    0; dEnumIndex < DynamicModelPart.DYNAMIC_ENUM_LENGTH; dEnumIndex++) {
                DYNAMIC_ENUM dEnum = DYNAMIC_ENUM.values()[dEnumIndex];
                DynamicPart part = temp.new DynamicPart(dEnum,
                        (DEFAULT_STATE.length - 1 < dEnumIndex)
                                ? DEFAULT_STATE[DEFAULT_STATE.length - 1]
                                : DEFAULT_STATE[dEnumIndex],
                        (DEFAULT_MIN.length - 1 < dEnumIndex) ? DEFAULT_MIN[DEFAULT_MIN.length - 1]
                                : DEFAULT_MIN[dEnumIndex],
                        (DEFAULT_MAX.length - 1 < dEnumIndex) ? DEFAULT_MAX[DEFAULT_MAX.length - 1]
                                : DEFAULT_MAX[dEnumIndex],
                        0F, // value
                        (DEFAULT_LERP_PERCENT.length - 1 < dEnumIndex)
                                ? DEFAULT_LERP_PERCENT[DEFAULT_LERP_PERCENT.length - 1]
                                : DEFAULT_LERP_PERCENT[dEnumIndex],
                        (DEFAULT_APPLY_RANDOM_MAX.length - 1 < dEnumIndex)
                                ? DEFAULT_APPLY_RANDOM_MAX[DEFAULT_APPLY_RANDOM_MAX.length - 1]
                                : DEFAULT_APPLY_RANDOM_MAX[dEnumIndex],
                        (DEFAULT_APPLY_RANDOM_MIN.length - 1 < dEnumIndex)
                                ? DEFAULT_APPLY_RANDOM_MIN[DEFAULT_APPLY_RANDOM_MIN.length - 1]
                                : DEFAULT_APPLY_RANDOM_MIN[dEnumIndex],
                        (DEFAULT_APPLY_RANDOM_MULTIPLIER.length - 1 < dEnumIndex)
                                ? DEFAULT_APPLY_RANDOM_MULTIPLIER[DEFAULT_APPLY_RANDOM_MULTIPLIER.length
                                        - 1]
                                : DEFAULT_APPLY_RANDOM_MULTIPLIER[dEnumIndex]);
                parts[dEnumIndex] = part;
            }
            SEEDS.add(index, parts);
        }
        return SEEDS;
    }
}
