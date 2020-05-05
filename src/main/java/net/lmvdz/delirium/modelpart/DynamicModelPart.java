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

public class DynamicModelPart extends ModelPart {


    public static enum DYNAMIC_ENUM {
        X,
        Y,
        Z,
        RED,
        GREEN,
        BLUE,
        ALPHA,
        LIGHT
    }

    public static final int DYNAMIC_ENUM_LENGTH = DYNAMIC_ENUM.values().length;
    public static final boolean[] DEFAULT_ALL_STATE = new boolean[]{true, true, true, true, true, true, true, true};

    public static final float[] DEFAULT_MIN = new float[] {-.0075F, -.0075F, -.0075F, -.0001F, -.0001F, -.0001F, -.0001F, -5F};
    public static final float[] DEFAULT_MAX = new float[] {.0075F, .0075F, .0075F, .0001F, .0001F, .0001F, .0001F, 5F};
    public static final float[] DEFAULT_LERP_PERCENT = new float[]{.15F, .15F, .15F, .15F, .15F, .15F, .15F, .15F};
    public static final float[] DEFAULT_APPLY_RANDOM_MULTIPLIER = new float[]{1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
    public static final float DEFAULT_APPLY_RANDOM_MAX = .005F;
    public static final float DEFAULT_APPLY_RANDOM_MIN = 0F;

    public boolean UV_SHIFT_APPLY_SYNC;
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


    public float[] x;
	public float[] y;
	public float[] z;
	public int[] sizeX;
	public int[] sizeY;
	public int[] sizeZ;
	public float[] extra;
	public int[] u;
    public int[] v;
    public float[] rotation;
    public ObjectList<DynamicPart[]> seeds;
    public SpriteIdentifier spriteId;
    public Function<Identifier, RenderLayer> layerFactory;
    private float textureWidth;
    private float textureHeight;
    private int textureOffsetU;
    private int textureOffsetV;
    private ObjectList<DynamicModelPart.DynamicCuboid> cuboids;
    private ObjectList<DynamicModelPart> children;
    public static final DynamicModelPart EMPTY = new DynamicModelPart(0, 0, 0, 0);

    public DynamicModelPart setU(int[] u) {
        this.u = u;
        return this;
    }
    public DynamicModelPart setV(int[] v) {
        this.v = v;
        return this;
    }

    public DynamicModelPart setX(float[] x) {
        this.x = x;
        return this;
    }
    public DynamicModelPart setY(float[] y) {
        this.y = y;
        return this;
    }
    public DynamicModelPart setZ(float[] z) {
        this.z = z;
        return this;
    }

    public DynamicModelPart setSizeX(int[] sizeX) {
        this.sizeX = sizeX;
        return this;
    }
    public DynamicModelPart setSizeY(int[] sizeY) {
        this.sizeY = sizeY;
        return this;
    }
    public DynamicModelPart setSizeZ(int[] sizeZ) {
        this.sizeZ = sizeZ;
        return this;
    }

    public DynamicModelPart setExtra(float[] extra) {
        this.extra = extra;
        return this;
    }

    public DynamicModelPart setRotation(float[] rotation){
        this.rotation = rotation;
        return this;
    }

    public DynamicModelPart spriteId(SpriteIdentifier spriteId) {
        this.spriteId = spriteId;
        return this;
    }

    public DynamicModelPart layerFactory(Function<Identifier, RenderLayer> layerFactory) {
        this.layerFactory = layerFactory;
        return this;
    }

    public DynamicModelPart seeds(ObjectList<DynamicPart[]> seeds) {
        this.seeds = seeds;
        return this;
    }

    public DynamicModelPart rotateModelPart(float[] rotation) {
        MatrixStack rotationStack = new MatrixStack();
        rotationStack.translate(rotation[0], rotation[1], rotation[2]);
        this.rotate(rotationStack);
        return this;
    }

    public DynamicModelPart build() {
        return this.rotateModelPart(this.rotation).addCuboids().buildChildren();
    }

    public DynamicModelPart buildChildren() {
        this.children.forEach(child -> {
            child.build();
        });
        return this;
    }

    public DynamicModelPart buildUsingSeeds() {
        return this.rotateModelPart(this.rotation).addCuboidsUsingSeeds().buildChildrenUsingSeeds();
    }

    public DynamicModelPart buildChildrenUsingSeeds() {
        this.children.forEach(child -> {
            child.buildUsingSeeds();
        });
        return this;
    }

    public DynamicModelPart addCuboids() {
        for(int i = 0; i < this.x.length; i++) {
			this.addCuboid(this.x[i], this.y[i], this.z[i], this.sizeX[i], this.sizeY[i], this.sizeZ[i], this.extra[i], this.u[i], this.v[i]);
        }
        return this;
    }
    public DynamicModelPart addCuboidsUsingSeeds() {
        for(int i = 0; i < this.x.length; i++) {
			this.addCuboid(this.x[i], this.y[i], this.z[i], this.sizeX[i], this.sizeY[i], this.sizeZ[i], this.extra[i], this.u[i], this.v[i], this.seeds.get(i));
        }
        return this;
    }

	public DynamicModelPart rebuild() {
        return this.seeds(this.getSeeds()).buildUsingSeeds();
    }


	public DynamicModelPart shiftU(int shift) {
        this.u = shiftIntArray(this.u, shift);
        return this;
	}

	public DynamicModelPart shiftV(int shift) {
        this.v = shiftIntArray(this.v, shift);
        return this;
	}

	public DynamicModelPart shiftUV(int shift) {
        return this.shiftU(shift).shiftV(shift);
	}


    private int[] shiftIntArray(int[] array, int shift) {
		int shiftCounter = 0;
		while(shiftCounter < Math.abs(shift)) {
			int temp = array[array.length-1]; // last number
			for (int i = array.length-1; i > 0; i--) {
				array[i] = array[i-1];
			}
			array[0] = temp;
			shiftCounter++;
		}
		return array;
    }
    
    public boolean hasSeeds() {
        return this.seeds != null && this.seeds.size() > 0;
    }

    public DynamicModelPart(DynamicModel dynamicModel, float[] x, float[] y, float[] z, int[] sizeX, int[] sizeY, int[] sizeZ, float[] extra, int[] u, int[] v, float[] rotation, ObjectList<DynamicModelPart.DynamicPart[]> seeds, SpriteIdentifier spriteId, Function<Identifier, RenderLayer> layerFactory) {
        super(dynamicModel);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.UV_SHIFTABLE = true;
        this.UV_SHIFT_APPLY_SYNC  = true;
        this.UV_SHIFT_EVERY_TICK = false;
        this.UV_SHIFT_EVERY_X_TICK = false;
        this.UV_SHIFT_EVERY_X_TICKS = 0;
        this.UV_SHIFT_EVERY_DELTA_TICK = false;
        this.UV_SHIFT_EVERY_X_DELTA_TICK = false;
        this.UV_SHIFT_EVERY_X_DELTA_TICKS = 0;
        this.UPDATE_DYNAMICS_EVERY_TICK = true;
        this.UPDATE_DYNAMICS_EVERY_X_TICK = false;
        this.UPDATE_DYNAMICS_EVERY_X_TICKS = 0;
        this.UPDATE_DYNAMICS_EVERY_DELTA_TICK = false;
        this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICK = false;
        this.UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS = 0;
        this.setX(x)
            .setY(y)
            .setZ(z)
            .setSizeX(sizeX)
            .setSizeY(sizeY)
            .setSizeZ(sizeZ)
            .setExtra(extra)
            .setU(u)
            .setV(v)
            .setRotation(rotation)
            .seeds(seeds)
            .spriteId(spriteId)
            .layerFactory(layerFactory)
            .buildUsingSeeds();
        
    }


    public DynamicModelPart(DynamicModel dynamicModel, int textureOffsetU, int textureOffsetV) {
        super(dynamicModel, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
    }
    public DynamicModelPart with(boolean UV_SHIFT_APPLY_SYNC, boolean UV_SHIFTABLE, boolean UV_SHIFT_EVERY_X_TICK, boolean UV_SHIFT_EVERY_TICK, int UV_SHIFT_EVERY_X_TICKS, boolean UV_SHIFT_EVERY_DELTA_TICK, boolean UV_SHIFT_EVERY_X_DELTA_TICK, int UV_SHIFT_EVERY_X_DELTA_TICKS, boolean UPDATE_DYNAMICS_EVERY_TICK, boolean UPDATE_DYNAMICS_EVERY_X_TICK, int UPDATE_DYNAMICS_EVERY_X_TICKS, boolean UPDATE_DYNAMICS_EVERY_DELTA_TICK, boolean UPDATE_DYNAMICS_EVERY_X_DELTA_TICK, int UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS) {
        this.UV_SHIFTABLE = UV_SHIFTABLE;
        this.UV_SHIFT_APPLY_SYNC = UV_SHIFT_APPLY_SYNC;
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

    public DynamicModelPart(int textureWidth, int textureHeight, int textureOffsetU, int textureOffsetV) {
        super(textureWidth, textureHeight, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
    }

    public DynamicModelPart addDynamicChild(DynamicModelPart child) {
        this.children.add(child);
        return this;
    }
    public DynamicModelPart addDynamicChildren(ObjectList<DynamicModelPart> children) {
        this.children.addAll(children);
        return this;
    }

    
    @Environment(EnvType.CLIENT)
    public class DynamicPart {
        
        
        public DYNAMIC_ENUM dynamic;
        public boolean state;
        public float value;
        public float newValue;
        public float min;
        public float max;
        public float lerpPercent;
        public float applyRandomMax;
        public float applyRandomMin;
        public float applyRandomMultiplier;

        public DynamicPart(DYNAMIC_ENUM dynamic) {
            this.dynamic(dynamic, true, DEFAULT_MIN[dynamic.ordinal()], DEFAULT_MAX[dynamic.ordinal()], 0F, (float)Math.random(), DEFAULT_APPLY_RANDOM_MAX, DEFAULT_APPLY_RANDOM_MIN, DEFAULT_APPLY_RANDOM_MULTIPLIER[dynamic.ordinal()]).apply(true);
        }

        public DynamicPart(DYNAMIC_ENUM dynamic, boolean state) {
            this.dynamic(dynamic, state, DEFAULT_MIN[dynamic.ordinal()], DEFAULT_MAX[dynamic.ordinal()], 0F, (float)Math.random(), DEFAULT_APPLY_RANDOM_MAX, DEFAULT_APPLY_RANDOM_MIN, DEFAULT_APPLY_RANDOM_MULTIPLIER[dynamic.ordinal()]).apply(true);
        }

        public DynamicPart(DYNAMIC_ENUM dynamic, boolean state, float min, float max, float value, float lerpPercent, float applyRandomMax, float applyRandomMin, float applyRandomMultiplier) {
            this.dynamic(dynamic, state, min, max, value, lerpPercent, applyRandomMax, applyRandomMin, applyRandomMultiplier).apply(true);
        }


        public DynamicPart dynamic(DYNAMIC_ENUM dEnum, boolean state, float min, float max, float value,  float lerpPercent, float applyRandomMax, float applyRandomMin, float applyRandomMultiplier) {
            return this.setEnum(dEnum).state(state).minMax(min, max).value(value).lerpPercent(lerpPercent).applyRandomMax(applyRandomMax).applyRandomMin(applyRandomMin).applyRandomMultiplier(applyRandomMultiplier);
        }

        public DynamicPart setEnum(DYNAMIC_ENUM dEnum) {
            this.dynamic = dEnum;
            return this;
        }

        public DynamicPart minMax(float min, float max) {
            return this.min(min).max(max);
        }

        public DynamicPart state(boolean state) {
            this.state = state;
            return this;
        }
        
        public DynamicPart disable() {
            this.state = true;
            return this;
        }

        public DynamicPart enable() {
            this.state = true;
            return this;
        }

        public DynamicPart min(float min) {
            this.min = min;
            return this;
        }

        public DynamicPart max(float max) {
            this.max = max;
            return this;
        }

        public DynamicPart value(float value) {
            this.value = value;
            return this;
        }

        
        public DynamicPart lerpPercent(float lerpPercent) {
            this.lerpPercent = lerpPercent;
            return this;
        }
        
        public DynamicPart applyRandomMax(float applyRandomMax) {
            this.applyRandomMax = applyRandomMax;
            return this;
        }

        public DynamicPart applyRandomMin(float applyRandomMin) {
            this.applyRandomMin = applyRandomMin;
            return this;
        }
        
        public DynamicPart applyRandomMultiplier(float applyRandomMultiplier) {
            this.applyRandomMultiplier = applyRandomMultiplier;
            return this;
        }
        
        public DynamicPart apply(boolean shouldCalculateNewValue) {
            if (this.state) {
                if (shouldCalculateNewValue || round(this.value, 3) == round(this.newValue, 3)) {
                    float r = (float)(((Math.random() * this.applyRandomMax) + this.applyRandomMin) * this.applyRandomMultiplier);
                    this.newValue = (float)((((r * this.max) + this.min)));
                    if (this.newValue + this.value < 0 || this.newValue - this.value > 0) {
                        this.newValue *= -1;
                    }
                }
                this.value = (float)Interpolation.LinearInterpolate(this.value, this.newValue, this.lerpPercent);
            }
            return this;
        }

        private double round (double value, int precision) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        }


        @Override
        public String toString() {
            return "ENUM: " + DYNAMIC_ENUM.values()[dynamic.ordinal()] + "\nVALUE: " + this.value;
        }
        

    }

    @Environment(EnvType.CLIENT)
    static class DynamicVertex {
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
 
    @Environment(EnvType.CLIENT)
    static class DynamicQuad {

        public final DynamicModelPart.DynamicVertex[] vertices;
        public final Vector3f direction;
        public final DynamicModelPart.DynamicCuboid parentCuboid;
        public DynamicQuad(DynamicModelPart.DynamicCuboid parentCuboid, DynamicModelPart.DynamicVertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
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
 
                for(int j = 0; j < i / 2; ++j) {
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

        public DynamicModelPart parentModelPart;
        private DynamicPart[] parts;
        private DynamicModelPart.DynamicQuad[] sides;
        private float x;
        private float y;
        private float z;
        private float sizeX;
        private float sizeY;
        private float sizeZ;
        private float extraX;
        private float extraY;
        private float extraZ;
        private float u;
        private float v;
        private boolean mirror;
        private float textureWidth;
        private float textureHeight;
        
        
        public DynamicCuboid(DynamicModelPart parentModelPart, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight, DynamicPart[] seed) {
            super(u, v, x, y, z, sizeX, sizeY, extraX, sizeZ, extraY, extraZ, mirror, textureWidth, textureHeight);
            this.set(parentModelPart, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, textureWidth, textureHeight, seed).build();
        }

        public DynamicCuboid(DynamicModelPart parentModelPart, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight) {
            super(u, v, x, y, z, sizeX, sizeY, extraX, sizeZ, extraY, extraZ, mirror, textureWidth, textureHeight);
            
            DynamicPart[] parts = new DynamicPart[DYNAMIC_ENUM_LENGTH];
            for(int i = 0; i < DYNAMIC_ENUM_LENGTH; i++) {
                parts[i] = new DynamicPart(DYNAMIC_ENUM.values()[i], true);
            }
            
            this.set(parentModelPart, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, textureWidth, textureHeight, parts).build();
        }

        public DynamicCuboid parts(DynamicPart[] parts) {
            this.parts = validateParts(parts);
            return this;
        }

        public DynamicPart[] validateParts(DynamicPart[] parts) {
            Arrays.sort(parts, (DynamicPart a, DynamicPart b) -> {
                return a.dynamic.ordinal() - b.dynamic.ordinal();
            });
            return parts;
        }

        public DynamicCuboid set(DynamicModelPart parentModelPart, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight, DynamicPart[] seed) {
            return this.setParentModelPart(parentModelPart)
            .setMirror(mirror)
            .setTextureWidth(textureWidth)
            .setTextureHeight(textureHeight)
            .setUV(u, v)
            .setExtra(extraX, extraY, extraZ)
            .setSize(sizeX, sizeY, sizeZ)
            .setXYZ(x, y, z)
            .parts(seed);
        }

        public DynamicCuboid setParentModelPart(DynamicModelPart parentModelPart) {
            this.parentModelPart = parentModelPart;
            return this;
        }
        public DynamicCuboid setMirror(boolean mirror) {
            this.mirror = mirror;
            return this;
        }
        public DynamicCuboid setTextureWidth(float width) {
            this.textureWidth = width;
            return this;
        }
        public DynamicCuboid setTextureHeight(float height) {
            this.textureHeight = height;
            return this;
        }

        public DynamicCuboid setU(float u) {
            this.u = u;
            return this;
        }

        public DynamicCuboid setV(float v) {
            this.v = v;
            return this;
        }

        public DynamicCuboid setUV(float u, float v) {
            return this.setU(u).setV(v);
        }
        

        public DynamicCuboid setX(float x) {
            this.x = x;
            return this;
        }
        public DynamicCuboid setY(float y) {
            this.y = y;
            return this;
        }
        public DynamicCuboid setZ(float z) {
            this.z = z;
            return this;
        }

        public DynamicCuboid setSizeX(float x) {
            this.sizeX = x;
            return this;
        }
        public DynamicCuboid setSizeY(float y) {
            this.sizeY = y;
            return this;
        }
        public DynamicCuboid setSizeZ(float z) {
            this.sizeZ = z;
            return this;
        }
        
        public DynamicCuboid setExtraX(float x) {
            this.extraX = x;
            return this;
        }
        public DynamicCuboid setExtraY(float y) {
            this.extraY = y;
            return this;
        }
        public DynamicCuboid setExtraZ(float z) {
            this.extraZ = z;
            return this;
        }

        public DynamicCuboid setExtra(float x, float y, float z) {
            return this.setExtraX(x).setExtraY(y).setExtraZ(z);
        }

        public DynamicCuboid setSize(float x, float y, float z) {
            return this.setSizeX(x).setSizeY(y).setSizeZ(z);
        }

        public DynamicCuboid setXYZ(float x, float y, float z) {
            return this.setX(x).setY(y).setZ(z);
        }
        
        

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


            float j = (float)this.u;
            float k = (float)this.u + this.sizeZ;
            float l = (float)this.u + this.sizeZ + this.sizeX;
            float m = (float)this.u + this.sizeZ + this.sizeX + this.sizeX;
            float n = (float)this.u + this.sizeZ + this.sizeX + this.sizeZ;
            float o = (float)this.u + this.sizeZ + this.sizeX + this.sizeZ + this.sizeX;
            float p = (float)this.v;
            float q = (float)this.v + this.sizeZ;
            float r = (float)this.v + this.sizeZ + this.sizeY;

            DynamicModelPart.DynamicVertex[] vertexs = buildVertexs(x, y, z, f, g, h);
            this.sides = new DynamicModelPart.DynamicQuad[] {
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[5], vertexs[1], vertexs[2], vertexs[6]}, l, q, n, r, this.textureWidth, this.textureHeight, this.mirror, Direction.EAST),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[0], vertexs[4], vertexs[7], vertexs[3]}, j, q, k, r, this.textureWidth, this.textureHeight, this.mirror, Direction.WEST),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[5], vertexs[4], vertexs[0], vertexs[1]}, k, p, l, q, this.textureWidth, this.textureHeight, this.mirror, Direction.DOWN),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[2], vertexs[3], vertexs[7], vertexs[6]}, l, q, m, p, this.textureWidth, this.textureHeight, this.mirror, Direction.UP),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[1], vertexs[0], vertexs[3], vertexs[2]}, k, q, l, r, this.textureWidth, this.textureHeight, this.mirror, Direction.NORTH),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[4], vertexs[5], vertexs[6], vertexs[7]}, n, q, o, r, this.textureWidth, this.textureHeight, this.mirror, Direction.SOUTH)
            };
            return this;
        }
        
        public DynamicModelPart.DynamicVertex[] buildVertexs(float x, float y, float z, float f, float g, float h) {
            DynamicModelPart.DynamicVertex vertex0 = new DynamicModelPart.DynamicVertex(x, y, z, 0.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex1 = new DynamicModelPart.DynamicVertex(f, y, z, 0.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex2 = new DynamicModelPart.DynamicVertex(f, g, z, 8.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex3 = new DynamicModelPart.DynamicVertex(x, g, z, 8.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex4 = new DynamicModelPart.DynamicVertex(x, y, h, 0.0F, 0.0F);
            DynamicModelPart.DynamicVertex vertex5 = new DynamicModelPart.DynamicVertex(f, y, h, 0.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex6 = new DynamicModelPart.DynamicVertex(f, g, h, 8.0F, 8.0F);
            DynamicModelPart.DynamicVertex vertex7 = new DynamicModelPart.DynamicVertex(x, g, h, 8.0F, 0.0F);
            return new DynamicModelPart.DynamicVertex[] { vertex0, vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7 };
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        this.renderDynamic(false, 0, matrices, vertexConsumer, light, overlay);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.renderDynamic(false, 0, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }


    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        this.renderDynamic(ticked, tick, matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    

    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(ticked, tick, matrices.peek(), vertexConsumer, light, overlay, red, green, blue, alpha);
                ObjectListIterator<DynamicModelPart> var9 = this.children.iterator();

                while(var9.hasNext()) {
                    DynamicModelPart modelPart = (DynamicModelPart)var9.next();
                    modelPart.renderDynamic(ticked, tick, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
                }

                matrices.pop();
            }
        }
    }

    public void renderDynamic(boolean ticked, int tick, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(ticked, tick, matrices.peek(), vertexConsumers, light, overlay, red, green, blue, alpha);
                ObjectListIterator<DynamicModelPart> var9 = this.children.iterator();

                while(var9.hasNext()) {
                    DynamicModelPart modelPart = (DynamicModelPart)var9.next();
                    modelPart.renderDynamic(ticked, tick, matrices, vertexConsumers, light, overlay, red, green, blue, alpha);
                }

                matrices.pop();
            }
        }
    }
    private void renderCuboidsDynamic(boolean ticked, int tick, MatrixStack.Entry matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green, float blue, float alpha) {
        renderCuboidsDynamic(ticked, tick, matrices, spriteId.getVertexConsumer(vertexConsumers, layerFactory), light, overlay, red, green, blue, alpha);
    }

    private void renderCuboidsDynamic(boolean ticked, int tick, MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        
        boolean shouldApplyDynamics = false;

        if (shiftDeltaTickCounter > UV_SHIFT_EVERY_X_DELTA_TICKS) {
            shiftDeltaTickCounter = 0;
        }

        if (dynamicsDeltaTickCounter > UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS) {
            dynamicsDeltaTickCounter = 0;
        }

        if (UV_SHIFTABLE && (((UV_SHIFT_EVERY_X_TICK && ((int)tick % UV_SHIFT_EVERY_X_TICKS == 0) || UV_SHIFT_EVERY_TICK) && ticked) || UV_SHIFT_EVERY_DELTA_TICK || (UV_SHIFT_EVERY_X_DELTA_TICK && UV_SHIFT_EVERY_X_DELTA_TICKS == ++shiftDeltaTickCounter))) {
            System.out.println("Rebuilding Dynamic Model - UV Shifted");
            if (UV_SHIFT_APPLY_SYNC) {
                shouldApplyDynamics = true;
            }
            shiftUV(1).rebuild();
        }

        if ((UPDATE_DYNAMICS_EVERY_TICK && ticked) ||(UPDATE_DYNAMICS_EVERY_X_TICK  && ((int)tick % UPDATE_DYNAMICS_EVERY_X_TICKS == 0)) || UPDATE_DYNAMICS_EVERY_DELTA_TICK || (UPDATE_DYNAMICS_EVERY_X_DELTA_TICK && UPDATE_DYNAMICS_EVERY_X_DELTA_TICKS == ++dynamicsDeltaTickCounter)) {
            shouldApplyDynamics = true;
        }
        
        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();
        ObjectListIterator<DynamicModelPart.DynamicCuboid> cubiodsIterator = this.cuboids.iterator();

        float dx = 0.0F;
        float dy = 0.0F;
        float dz = 0.0F;
        float dRed = 0.0F;
        float dGreen = 0.0F;
        float dBlue = 0.0F;
        float dAlpha = 0.0F;
        float dLight = 0.0F;


        while(cubiodsIterator.hasNext()) {

            DynamicModelPart.DynamicCuboid cuboid = (DynamicModelPart.DynamicCuboid)cubiodsIterator.next();
            
            dx = cuboid.parts[DYNAMIC_ENUM.X.ordinal()].apply(shouldApplyDynamics).value;
            dy = cuboid.parts[DYNAMIC_ENUM.Y.ordinal()].apply(shouldApplyDynamics).value;
            dz = cuboid.parts[DYNAMIC_ENUM.Z.ordinal()].apply(shouldApplyDynamics).value;
            dRed = cuboid.parts[DYNAMIC_ENUM.RED.ordinal()].apply(shouldApplyDynamics).value;
            dGreen = cuboid.parts[DYNAMIC_ENUM.GREEN.ordinal()].apply(shouldApplyDynamics).value;
            dBlue = cuboid.parts[DYNAMIC_ENUM.BLUE.ordinal()].apply(shouldApplyDynamics).value;
            dAlpha = cuboid.parts[DYNAMIC_ENUM.ALPHA.ordinal()].apply(shouldApplyDynamics).value;
            dLight = cuboid.parts[DYNAMIC_ENUM.LIGHT.ordinal()].apply(shouldApplyDynamics).value;
        

            // System.out.println(dx + " " + dy+ " " +dz+ " " +(red+dRed)+ " " +(green+dGreen)+ " " +(blue+dBlue)+ " " + (alpha+dAlpha)+ " " + (int)(light+dLight));            
            // System.out.println(dx);            
            DynamicModelPart.DynamicQuad[] cuboidSidesQuadArray = cuboid.sides;
            int cuboidSidesLength = cuboidSidesQuadArray.length;

            for(int x = 0; x < cuboidSidesLength; ++x) {
                DynamicModelPart.DynamicQuad quad = cuboidSidesQuadArray[x];
                Vector3f vector3f = quad.direction.copy();
                vector3f.transform(matrix3f);
                float normalX = vector3f.getX();
                float normalY = vector3f.getY();
                float normalZ = vector3f.getZ();

                for(int i = 0; i < 4; ++i) {
                    DynamicModelPart.DynamicVertex vertex = quad.vertices[i];

                    float j = vertex.pos.getX() / 16.0F;
                    float k = vertex.pos.getY() / 16.0F;
                    float l = vertex.pos.getZ() / 16.0F;
                    
                    Vector4f vector4f = new Vector4f(j, k, l, 1.0F);
                    vector4f.transform(matrix4f);
                    vertexConsumer.vertex(vector4f.getX()+dx, vector4f.getY()+dy, vector4f.getZ()+dz, red+dRed, green+dGreen, blue+dBlue, alpha+dAlpha, vertex.u, vertex.v, overlay, (int)(light+dLight), normalX, normalY, normalZ);
                }
            }
        }
    }
    

    public void addChild(DynamicModelPart part) {
        this.children.add(part);
    }

    public DynamicModelPart setTextureSize(int width, int height) {
        this.textureWidth = (float)width;
        this.textureHeight = (float)height;
        return this;
     }

    @Override
    public DynamicModelPart.DynamicCuboid getRandomCuboid(Random random) {
        return (DynamicModelPart.DynamicCuboid)this.cuboids.get(random.nextInt(this.cuboids.size()));
    }

    @Override
    public DynamicModelPart setTextureOffset(int textureOffsetU, int textureOffsetV) {
        this.textureOffsetU = textureOffsetU;
        this.textureOffsetV = textureOffsetV;
        return this;
    }

    @Override
    public DynamicModelPart addCuboid(String name, float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int textureOffsetU, int textureOffsetV) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, (float)sizeX, (float)sizeY, (float)sizeZ, extra, extra, extra, this.mirror, false);
        return this;
    }

    public DynamicModelPart addCuboid(float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int textureOffsetU, int textureOffsetV) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, (float)sizeX, (float)sizeY, (float)sizeZ, extra, extra, extra, this.mirror, false);
        return this;
    }

    public DynamicModelPart addCuboid(float x, float y, float z, int sizeX, int sizeY, int sizeZ, float extra, int textureOffsetU, int textureOffsetV, DynamicPart[] seed) {
        this.setTextureOffset(textureOffsetU, textureOffsetV);
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, (float)sizeX, (float)sizeY, (float)sizeZ, extra, extra, extra, this.mirror, false, seed);
        return this;
    }

     @Override
     public DynamicModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0F, 0.0F, 0.0F, this.mirror, false);
        return this;
     }

     @Override
     public DynamicModelPart addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, boolean mirror) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, 0.0F, 0.0F, 0.0F, mirror, false);
        return this;
     }

     @Override
     public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, this.mirror, false);
     }

     @Override
     public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, this.mirror, false);
     }

     @Override
     public void addCuboid(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra, boolean mirror) {
        this.addDynamicCuboid(this, this.textureOffsetU, this.textureOffsetV, x, y, z, sizeX, sizeY, sizeZ, extra, extra, extra, mirror, false);
     }

    public void addDynamicCuboid(DynamicModelPart parent, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, boolean bl) {
        this.cuboids.add(new DynamicCuboid(parent, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, this.textureWidth, this.textureHeight));
    }

    public void addDynamicCuboid(DynamicModelPart parent, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, boolean bl, DynamicPart[] seed) {
        this.cuboids.add(new DynamicCuboid(parent, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, this.textureWidth, this.textureHeight, seed));
    }

    public ObjectList<DynamicModelPart> getChildren() {
        return this.children;
    }

    public ObjectList<DynamicPart[]> getSeeds() {
        ObjectList<DynamicPart[]> seeds = new ObjectArrayList<DynamicPart[]>();
        seeds.addAll(this.cuboids.stream().map(cuboid -> cuboid.parts).collect(Collectors.toList()));
        return seeds;
    }

    public ObjectList<DynamicCuboid> getCuboids() {
        return this.cuboids;
    }

}