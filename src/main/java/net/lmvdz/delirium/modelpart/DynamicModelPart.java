package net.lmvdz.delirium.modelpart;

import java.util.Random;
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

    private float textureWidth;
    private float textureHeight;
    private int textureOffsetU;
    private int textureOffsetV;
    private final ObjectList<DynamicModelPart.DynamicCuboid> cuboids;
    private final ObjectList<DynamicModelPart.DynamicPart> dynamicCuboidParts;
    private final ObjectList<DynamicModelPart> children;
    private DynamicModel dynamicModel;


    public DynamicModelPart(DynamicModel dynamicModel) {
        super(dynamicModel);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.dynamicCuboidParts = new ObjectArrayList<DynamicModelPart.DynamicPart>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicModel = dynamicModel;
    }

    public DynamicModelPart(DynamicModel dynamicModel, ObjectList<DynamicModelPart.DynamicPart> dynamicCuboidParts) {
        super(dynamicModel);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.dynamicCuboidParts = new ObjectArrayList<DynamicModelPart.DynamicPart>();
        this.dynamicCuboidParts.addAll(dynamicCuboidParts);
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicModel = dynamicModel;
    }


    public DynamicModelPart(DynamicModel dynamicModel, int textureOffsetU, int textureOffsetV) {
        super(dynamicModel, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.dynamicCuboidParts = new ObjectArrayList<DynamicModelPart.DynamicPart>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicModel = dynamicModel;
    }

    public DynamicModelPart(int textureWidth, int textureHeight, int textureOffsetU, int textureOffsetV) {
        super(textureWidth, textureHeight, textureOffsetU, textureOffsetV);
        this.cuboids = new ObjectArrayList<DynamicModelPart.DynamicCuboid>();
        this.children = new ObjectArrayList<DynamicModelPart>();
        this.dynamicCuboidParts = new ObjectArrayList<DynamicModelPart.DynamicPart>();
    }

    public ObjectList<DynamicModelPart.DynamicPart> getDynamicCuboidParts() {
        return this.dynamicCuboidParts;
    }

    enum DYNAMIC_ENUM {
        X,
        Y,
        Z,
        RED,
        GREEN,
        BLUE,
        ALPHA
    }

    @Environment(EnvType.CLIENT)
    public class DynamicPart {

        private float[] dynamicsMaxDefault = new float[] {.5F, .5F, .5F, .00001F, .00001F, .00001F, -10F};
        public boolean[] dynamicsEnabled = new boolean[DYNAMIC_ENUM.values().length];
        public float[] dynamicValues = new float[DYNAMIC_ENUM.values().length];
        public float[] dynamicsMin = new float[DYNAMIC_ENUM.values().length];
        public float[] dynamicsMax = new float[DYNAMIC_ENUM.values().length];

        public DynamicPart() {
            dynamicsMax = dynamicsMaxDefault;
        }

        public DynamicPart(boolean b) {
            for(int i = 0; i < this.dynamicsEnabled.length; i++) {
                this.state(DYNAMIC_ENUM.values()[i], b);
            }
            dynamicsMax = dynamicsMaxDefault;
        }


        public DynamicPart dynamic(DYNAMIC_ENUM dEnum, boolean state, float min, float max, float value) {
            return this.state(dEnum, state).minMax(dEnum, min, max).value(dEnum, value);
        }

        public DynamicPart dynamic(DYNAMIC_ENUM dEnum, boolean state, float min, float max) {
            return this.state(dEnum, state).minMax(dEnum, min, max);
        }

        public DynamicPart minMax(DYNAMIC_ENUM dEnum, float min, float max) {
            return this.min(dEnum, min).max(dEnum, min);
        }

        public DynamicPart state(DYNAMIC_ENUM dEnum, boolean state) {
            this.dynamicsEnabled[dEnum.ordinal()] = state;
            return this;
        }
        
        public DynamicPart disable(DYNAMIC_ENUM dEnum) {
            this.dynamicsEnabled[dEnum.ordinal()] = true;
            return this;
        }

        public DynamicPart enable(DYNAMIC_ENUM dEnum) {
            this.dynamicsEnabled[dEnum.ordinal()] = true;
            return this;
        }

        public DynamicPart min(DYNAMIC_ENUM dEnum, float min) {
            this.dynamicsMin[dEnum.ordinal()] = min;
            return this;
        }

        public DynamicPart max(DYNAMIC_ENUM dEnum, float max) {
            this.dynamicsMax[dEnum.ordinal()] = max;
            return this;
        }

        public DynamicPart value(DYNAMIC_ENUM dEnum, float value) {
            this.dynamicValues[dEnum.ordinal()] = value;
            return this;
        }


        public DynamicPart apply() {
            float r = (float)(Math.random()  * .015F);
            for (int x = 0; x < DYNAMIC_ENUM.values().length; x++) {
                this.dynamicValues[x] = (this.dynamicsEnabled[x] ? (float)(((r * this.dynamicsMax[x]) + this.dynamicsMin[x])) : 0f);
            }
            return this;
        }

        @Override
        public String toString() {
            String s = "";
            for (int x = 0; x < DYNAMIC_ENUM.values().length; x++) {
                s += this.dynamicValues[x] + " ";
            }
            return s;
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
    
        public void consume(DynamicModelPart.DynamicPart dynamicCuboidPart, VertexConsumer vertexConsumer, float x, float y, float z, float red,
                float green, float blue, float alpha, float u2, float v2, int overlay, int light,
                float normalX, float normalY, float normalZ) {
            vertexConsumer.vertex(x+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("X").ordinal()], y+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("Y").ordinal()], z+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("Z").ordinal()], red+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("RED").ordinal()], green+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("GREEN").ordinal()], blue+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("BLUE").ordinal()], alpha+dynamicCuboidPart.dynamicValues[DYNAMIC_ENUM.valueOf("ALPHA").ordinal()], u, v, overlay, light, normalX, normalY, normalZ);
        }
    }
 
    @Environment(EnvType.CLIENT)
    static class DynamicQuad {

        public final DynamicModelPart.DynamicVertex[] vertices;
        public final Vector3f direction;
        public final DynamicModelPart.DynamicCuboid parent;
        public DynamicQuad(DynamicModelPart.DynamicCuboid parent, DynamicModelPart.DynamicVertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
            this.parent = parent;  
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
        public DynamicModelPart parent;
        private DynamicModelPart.DynamicQuad[] sides;
        private final float x;
        private final float y;
        private final float z;
        private final float sizeX;
        private final float sizeY;
        private final float sizeZ;
        private final float extraX;
        private final float extraY;
        private final float extraZ;
        private final float u;
        private final float v;
        private boolean mirror;
        private float textureWidth;
        private float textureHeight;


        
        public DynamicCuboid(DynamicModelPart parent, int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight) {
            super(u, v, x, y, z, sizeX, sizeY, extraX, sizeZ, extraY, extraZ, mirror, textureWidth, textureHeight);
            
            parent.dynamicCuboidParts.add(new DynamicPart(true));
            
            
            this.parent = parent;
            this.u = u;
            this.v = v;
            this.x = x;
            this.y = y;
            this.z = z;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.sizeZ = sizeZ;
            this.extraX = extraX;
            this.extraY = extraY;
            this.extraZ = extraZ;
            this.mirror = mirror;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;

            this.sides = buildSides();
        }

        public DynamicModelPart.DynamicQuad[] buildSides() {
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
            return new DynamicModelPart.DynamicQuad[] {
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[5], vertexs[1], vertexs[2], vertexs[6]}, l, q, n, r, this.textureWidth, this.textureHeight, this.mirror, Direction.EAST),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[0], vertexs[4], vertexs[7], vertexs[3]}, j, q, k, r, this.textureWidth, this.textureHeight, this.mirror, Direction.WEST),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[5], vertexs[4], vertexs[0], vertexs[1]}, k, p, l, q, this.textureWidth, this.textureHeight, this.mirror, Direction.DOWN),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[2], vertexs[3], vertexs[7], vertexs[6]}, l, q, m, p, this.textureWidth, this.textureHeight, this.mirror, Direction.UP),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[1], vertexs[0], vertexs[3], vertexs[2]}, k, q, l, r, this.textureWidth, this.textureHeight, this.mirror, Direction.NORTH),
                new DynamicModelPart.DynamicQuad(this, new DynamicModelPart.DynamicVertex[]{vertexs[4], vertexs[5], vertexs[6], vertexs[7]}, n, q, o, r, this.textureWidth, this.textureHeight, this.mirror, Direction.SOUTH)
            };
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
        this.renderDynamic(new MutablePair<Boolean, Integer>(false, 0), 0, 200, matrices, vertexConsumer, light, overlay);
    }

    public void renderDynamic(MutablePair<Boolean, Integer> shiftUV, int tick, int updatePerTicks, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        this.renderDynamic(shiftUV, tick, updatePerTicks, matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.renderDynamic(new MutablePair<Boolean, Integer>(false, 0), 0, 200, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderDynamic(MutablePair<Boolean, Integer> shiftUV, int tick, int updatePerTicks, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboidsDynamic(shiftUV, tick, updatePerTicks, matrices.peek(), vertexConsumer, light, overlay, red, green, blue, alpha);
                ObjectListIterator<DynamicModelPart> var9 = this.children.iterator();

                while(var9.hasNext()) {
                    DynamicModelPart modelPart = (DynamicModelPart)var9.next();
                    modelPart.renderDynamic(shiftUV, tick, updatePerTicks, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
                }

                matrices.pop();
            }
        }
    }

    
    private void renderCuboidsDynamic(MutablePair<Boolean, Integer> shiftUV, int tick, int updatePerTicks, MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        boolean updateDynamicCuboidParts = ((tick % updatePerTicks) == 0);
        if (updateDynamicCuboidParts) {
            if (shiftUV.getLeft()) {
                dynamicModel.shiftUV(shiftUV.getRight()).rebuild();;
            }
        }
        
        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();
        ObjectListIterator<DynamicModelPart.DynamicCuboid> cubiodsIterator = this.cuboids.iterator();
        while(cubiodsIterator.hasNext()) {
            int index = cubiodsIterator.nextIndex();
            DynamicModelPart.DynamicCuboid cuboid = (DynamicModelPart.DynamicCuboid)cubiodsIterator.next();
            if(updateDynamicCuboidParts) {
                dynamicCuboidParts.get(index).apply();
            }
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
                    vertex.consume(dynamicCuboidParts.get(index), vertexConsumer, vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, vertex.u, vertex.v, overlay, light, normalX, normalY, normalZ);
                }
            }
        }
    }

    // private void renderCuboids(MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
    //     this.renderCuboidsDynamic(false, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    // }

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
}