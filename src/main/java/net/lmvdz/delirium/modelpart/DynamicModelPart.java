package net.lmvdz.delirium.modelpart;

import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.MutablePair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lmvdz.delirium.model.DynamicModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.Matrix3f;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Direction;

public class DynamicModelPart extends ModelPart {

    public static final DynamicModelPart EMPTY;

    static {
        EMPTY = new DynamicModelPart(0, 0, 0, 0);
    }

    private float textureWidth;
    private float textureHeight;
    private int textureOffsetU;
    private int textureOffsetV;
    private ObjectList<DynamicModelPart.DynamicCuboid> cuboids;
    private ObjectList<DynamicModelPart> children;
    private DynamicModel dynamicModel;
    private float lastTick = 0;


    public DynamicModelPart(DynamicModel dynamicModel) {
        super(dynamicModel);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicModel = dynamicModel;
    }

    public DynamicModelPart(DynamicModel dynamicModel, DynamicModelPart main) {
        super(dynamicModel);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children.addAll(main.children);
        this.lastTick = main.lastTick;
        this.dynamicModel = dynamicModel;
        main = null;
    }


    public DynamicModelPart(DynamicModel dynamicModel, int textureOffsetU, int textureOffsetV) {
        super(dynamicModel, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicModel = dynamicModel;
    }

    public DynamicModelPart(int textureWidth, int textureHeight, int textureOffsetU, int textureOffsetV) {
        super(textureWidth, textureHeight, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
    }

    public enum DYNAMIC_ENUM {
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
            return this.state(dEnum, state).minMax(dEnum, min, max).value(dEnum, value).lerpPercent(dEnum, lerpPercent).applyRandomMax(dEnum, applyRandomMax).applyRandomMin(dEnum, applyRandomMin).applyRandomMultiplier(dEnum, applyRandomMultiplier);
        }

        public DynamicPart minMax(DYNAMIC_ENUM dEnum, float min, float max) {
            return this.min(dEnum, min).max(dEnum, max);
        }

        public DynamicPart state(DYNAMIC_ENUM dEnum, boolean state) {
            this.state = state;
            return this;
        }
        
        public DynamicPart disable(DYNAMIC_ENUM dEnum) {
            this.state = true;
            return this;
        }

        public DynamicPart enable(DYNAMIC_ENUM dEnum) {
            this.state = true;
            return this;
        }

        public DynamicPart min(DYNAMIC_ENUM dEnum, float min) {
            this.min = min;
            return this;
        }

        public DynamicPart max(DYNAMIC_ENUM dEnum, float max) {
            this.max = max;
            return this;
        }

        public DynamicPart value(DYNAMIC_ENUM dEnum, float value) {
            this.value = value;
            return this;
        }

        
        public DynamicPart lerpPercent(DYNAMIC_ENUM dEnum, float lerpPercent) {
            this.lerpPercent = lerpPercent;
            return this;
        }
        
        public DynamicPart applyRandomMax(DYNAMIC_ENUM dEnum, float applyRandomMax) {
            this.applyRandomMax = applyRandomMax;
            return this;
        }

        public DynamicPart applyRandomMin(DYNAMIC_ENUM dEnum, float applyRandomMin) {
            this.applyRandomMin = applyRandomMin;
            return this;
        }
        
        public DynamicPart applyRandomMultiplier(DYNAMIC_ENUM dEnum, float applyRandomMultiplier) {
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
                this.value = this.lerp(this.value, this.newValue, this.lerpPercent);
                // System.out.println((Math.abs(this.value) +  Math.abs(this.newValue)) / 2);
                
            }
            return this;
        }

        private double round (double value, int precision) {
            int scale = (int) Math.pow(10, precision);
            return (double) Math.round(value * scale) / scale;
        }

        public float lerp(float start, float end, float percent) {
            return (start + percent*(end - start));
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
            
            this.parts = new DynamicPart[DYNAMIC_ENUM_LENGTH];
            for(int i = 0; i < DYNAMIC_ENUM_LENGTH; i++) {
                parts[i] = new DynamicPart(DYNAMIC_ENUM.values()[i], true);
            }
            
            this.set(parentModelPart, u, v, x, y, z, sizeX, sizeY, sizeZ, extraX, extraY, extraZ, mirror, textureWidth, textureHeight, parts).build();
        }

        public DynamicCuboid parts(DynamicPart[] parts) {
            this.parts = parts;
            return this;
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
        this.renderDynamic(new MutableTriple<MutablePair<Boolean, Boolean>, Integer, Integer>(new MutablePair<Boolean,Boolean>(false, false), 0, 0),0, matrices, vertexConsumer, light, overlay);
    }

    public void renderDynamic(MutableTriple<MutablePair<Boolean, Boolean>, Integer, Integer> shiftUV, int tick, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        this.renderDynamic(shiftUV, tick, matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.renderDynamic(new MutableTriple<MutablePair<Boolean, Boolean>, Integer, Integer>(new MutablePair<Boolean,Boolean>(false, false), 0, 0), 0, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderDynamic(MutableTriple<MutablePair<Boolean, Boolean>, Integer, Integer> shiftUV, float tick, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(shiftUV, tick, matrices.peek(), vertexConsumer, light, overlay, red, green, blue, alpha);
                ObjectListIterator<DynamicModelPart> var9 = this.children.iterator();

                while(var9.hasNext()) {
                    DynamicModelPart modelPart = (DynamicModelPart)var9.next();
                    modelPart.renderDynamic(shiftUV, tick, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
                }

                matrices.pop();
            }
        }
    }

    
    private void renderCuboidsDynamic(MutableTriple<MutablePair<Boolean, Boolean>, Integer, Integer> shiftUV, float tick, MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        
        boolean shiftingUV = ((int)tick % shiftUV.getMiddle() == 0);
        boolean shiftEnabledModel = shiftUV.getLeft().getRight();
        boolean shouldApplyDynamics = (shiftUV.getLeft().getLeft() && shiftingUV);

        if (shiftEnabledModel && shiftingUV && lastTick != tick) {
            lastTick = tick;
            dynamicModel.shiftUV(shiftUV.getRight()).rebuild();
        }

        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();
        ObjectListIterator<DynamicModelPart.DynamicCuboid> cubiodsIterator = this.cuboids.iterator();

        float dx = 0;
        float dy = 0;
        float dz = 0;
        float dRed = 0;
        float dGreen = 0;
        float dBlue = 0;
        float dAlpha = 0;
        float dLight = 0;

        DynamicPart[] parts;

        DynamicPart dxPart;
        DynamicPart dyPart;
        DynamicPart dzPart;
        DynamicPart dRedPart;
        DynamicPart dGreenPart;
        DynamicPart dBluePart;
        DynamicPart dAlphaPart;
        DynamicPart dLightPart;

        while(cubiodsIterator.hasNext()) {

            DynamicModelPart.DynamicCuboid cuboid = (DynamicModelPart.DynamicCuboid)cubiodsIterator.next();

            parts = cuboid.parts;

            dxPart = parts[DYNAMIC_ENUM.X.ordinal()];
            dyPart = parts[DYNAMIC_ENUM.Y.ordinal()];
            dzPart = parts[DYNAMIC_ENUM.Z.ordinal()];
            dRedPart = parts[DYNAMIC_ENUM.RED.ordinal()];
            dGreenPart = parts[DYNAMIC_ENUM.GREEN.ordinal()];
            dBluePart = parts[DYNAMIC_ENUM.BLUE.ordinal()];
            dAlphaPart = parts[DYNAMIC_ENUM.ALPHA.ordinal()];
            dLightPart = parts[DYNAMIC_ENUM.LIGHT.ordinal()];
            

            dx = dxPart != null ? dxPart.apply(shouldApplyDynamics).value : 0F;
            dy = dyPart != null  ?  dyPart.apply(shouldApplyDynamics).value : 0F;
            dz = dzPart != null ? dzPart.apply(shouldApplyDynamics).value : 0F;
            dRed = dRedPart != null ? dRedPart.apply(shouldApplyDynamics).value : 0F;
            dGreen = dGreenPart != null ? dGreenPart.apply(shouldApplyDynamics).value : 0F;
            dBlue = dBluePart != null ? dBluePart.apply(shouldApplyDynamics).value  : 0F;
            dAlpha = dAlphaPart != null ? dAlphaPart.apply(shouldApplyDynamics).value  : 0F;
            dLight = dLightPart != null ? dLightPart.apply(shouldApplyDynamics).value  : 0F;
            // System.out.println(dx+" "+dy+" "+dz+" "+dRed+" "+dGreen+" "+dBlue+" "+dAlpha+" "+dLight);

            
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
                    // System.out.println(dLight);
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

    public ObjectList<DynamicPart[]> getSeeds() {
        ObjectList<DynamicPart[]> seeds = new ObjectArrayList<DynamicPart[]>();
        seeds.addAll(this.cuboids.stream().map(cuboid -> cuboid.parts).collect(Collectors.toList()));
        return seeds;
    }

    public ObjectList<DynamicCuboid> getCuboids() {
        return this.cuboids;
    }

}