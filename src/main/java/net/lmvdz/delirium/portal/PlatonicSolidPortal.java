package net.lmvdz.delirium.portal;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class PlatonicSolidPortal {

    public enum PlatonicSolidEnum {
        TETRAHEDRON,
        CUBE,
        OCTAHEDRON,
        DODECAHEDRON,
        ICOSAHEDRON,
    }

    public static final class PlatonicSolid {

        public static PlatonicSolid TETRAHEDRON = new PlatonicSolid(3, 4, 6, 4, 3, (float)70.53, PlatonicSolidEnum.TETRAHEDRON);
        public static PlatonicSolid CUBE = new PlatonicSolid(4, 8, 12, 6, 3, (float)90, PlatonicSolidEnum.CUBE);
        public static PlatonicSolid OCTAHEDRON = new PlatonicSolid(3, 6, 12, 8, 4, (float)109.47, PlatonicSolidEnum.OCTAHEDRON);
        public static PlatonicSolid DODECAHEDRON = new PlatonicSolid(5, 20, 30, 12, 3, (float)116.57, PlatonicSolidEnum.DODECAHEDRON);
        public static PlatonicSolid ICOSAHEDRON = new PlatonicSolid(3, 12, 30, 20, 5, (float)138.19, PlatonicSolidEnum.ICOSAHEDRON);

        public final int polygonSides;
        public final int vertices;
        public final int edges;
        public final int faces;
        public final int facesMeetingAtEachVertex;
        public final float dihedralAngle;
        public final PlatonicSolidEnum psEnum;
        public final Vec3d[] shape;

        PlatonicSolid(int polygonSides, int vertices, int edges, int faces, int facesMeetingAtEachVertex, float dihedralAngle, PlatonicSolidEnum psEnum) {
            this.polygonSides = polygonSides;
            this.vertices = vertices;
            this.edges = edges;
            this.faces = faces;
            this.facesMeetingAtEachVertex = facesMeetingAtEachVertex;
            this.dihedralAngle = dihedralAngle;
            this.psEnum = psEnum;
            this.shape = buildShape();
        }

        public float interiorAngle() {
            return (this.polygonSides-2) * 180 / this.polygonSides;
        }

        public float exteriorAngle() {
            return 180 - interiorAngle();
        }

        public float sideLength() {
            return (float)(2 * Math.sin(180/this.polygonSides));
        }

        public Vec3d[] buildShape() {
            Vec3d[] shape = new Vec3d[this.vertices];
            AtomicInteger i = new AtomicInteger();
            float interiorAngle = interiorAngle();
            int indx;
            do {
                indx = i.getAndIncrement();
                Vector3f f = new Vector3f(new Vec3d(0, 0, 0));
                f.add((float)Math.cos(indx*interiorAngle), 0F, (float)Math.sin(indx*interiorAngle));
                shape[indx] = new Vec3d(f);
            } while(indx < shape.length - 1);
            return shape;
        }

    }

    public static final class FoldedPlatonicSolidArray {

        public final Vec3d[] axisH;
        public final Vec3d[] axisW;
        public final PlatonicSolidEnum psEnum;

        public static FoldedPlatonicSolidArray getFromEnum(PlatonicSolidEnum psEnum) {
            return SOLIDS.get(psEnum.ordinal());
        }

        public static HashMap<Integer, FoldedPlatonicSolidArray> SOLIDS;

        public static final FoldedPlatonicSolidArray TETRAHEDRON = new FoldedPlatonicSolidArray(PlatonicSolidEnum.TETRAHEDRON,
            (Vec3d[])ObjectArrayList.wrap(PlatonicSolidNet.getNet(PlatonicSolidEnum.TETRAHEDRON).shapeNet.clone()).stream().map((Vec3d[] shape) -> {
                return shape;
            }).collect(Collectors.toList()).toArray(),
            new Vec3d[] {}
        );
        public static final FoldedPlatonicSolidArray CUBE = new FoldedPlatonicSolidArray(PlatonicSolidEnum.CUBE,
            new Vec3d[] {} ,
            new Vec3d[] {}

        );
        public static final FoldedPlatonicSolidArray OCTAHEDRON = new FoldedPlatonicSolidArray(PlatonicSolidEnum.OCTAHEDRON,
            new Vec3d[] {} ,
            new Vec3d[] {}
        );
        public static final FoldedPlatonicSolidArray DODECAHEDRON = new FoldedPlatonicSolidArray(PlatonicSolidEnum.DODECAHEDRON,
            new Vec3d[] {} ,
            new Vec3d[] {}
        );
        public static final FoldedPlatonicSolidArray ICOSAHEDRON = new FoldedPlatonicSolidArray(PlatonicSolidEnum.ICOSAHEDRON,
            new Vec3d[] {} ,
            new Vec3d[] {}
        );

        FoldedPlatonicSolidArray(PlatonicSolidEnum psEnum, Vec3d[] axisH, Vec3d[] axisW) {
            this.axisH = axisH;
            this.axisW = axisW;
            this.psEnum = psEnum;
            SOLIDS.put(psEnum.ordinal(), this);
        }
    }

    public static PlatonicSolidPortal TETRAHEDRON = generateAPlatonicSolidPortal(PlatonicSolid.TETRAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal CUBE = generateAPlatonicSolidPortal(PlatonicSolid.CUBE, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal OCTAHEDRON = generateAPlatonicSolidPortal(PlatonicSolid.OCTAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal DODECAHEDRON = generateAPlatonicSolidPortal(PlatonicSolid.DODECAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);
    public static PlatonicSolidPortal ICOSAHEDRON = generateAPlatonicSolidPortal(PlatonicSolid.ICOSAHEDRON, DimensionType.THE_NETHER, new Vec3d(0, 120, 0), 1);

    public static class PlatonicSolidNet {
        private Vec3d[][] shapeNet;

        public static PlatonicSolidNet getNet(PlatonicSolidEnum psEnum) {
            return NETS.get(psEnum.ordinal());
        }

        private static HashMap<Integer, PlatonicSolidNet> NETS;
        private static PlatonicSolidNet TETRAHEDRON = new PlatonicSolidNet(PlatonicSolid.TETRAHEDRON);
        private static PlatonicSolidNet CUBE = new PlatonicSolidNet(PlatonicSolid.CUBE);
        private static PlatonicSolidNet OCTAHEDRON = new PlatonicSolidNet(PlatonicSolid.OCTAHEDRON);
        private static PlatonicSolidNet DODECAHEDRON = new PlatonicSolidNet(PlatonicSolid.DODECAHEDRON);
        private static PlatonicSolidNet ICOSAHEDRON = new PlatonicSolidNet(PlatonicSolid.ICOSAHEDRON);

        PlatonicSolidNet(PlatonicSolid solid) {
            this.shapeNet = buildNet(solid);
        }

        public static PlatonicSolidNet generateAPlatonicSolidNet(PlatonicSolid solid) {
            PlatonicSolidNet net;
            if (getNet(solid.psEnum) == null) {
                net = new PlatonicSolidNet(solid);
                NETS.put(solid.psEnum.ordinal(), net);
            }  else {
                net = getNet(solid.psEnum);
            }
            return net;
        }

        /**
         * Fold the net. Check if each vertex has solid.facesMeetingAtEachVertex at each vertex.
         * @param net
         * @return
         */
        private static boolean isValidNet(Vec3d[][] net) {
            boolean isValid = true;
            for(int x = 0; x < net.length; x++) {
                for(int y = 0; y < net[x].length; y++) {

                }
            }
            return isValid;
        }

        private static boolean isValidSpotforShapeInNet(Vec3d[][] net, int indexOfShape) {
            boolean isValid = true;
            // loop through shapes in net
            int numberOfFacesMeetingAtVertex;
            Vec3d[] shape = net[indexOfShape];
            for(int x = 0; x < net.length; x++) {
                if (x != indexOfShape) {
                    Vec3d[] shapeInNetThatIsNotTheSameShape = net[x];
                    // if any vertex of the shape we're checking for validity of lands within one of the net's shapes its invalid;
                    for(int y = 0; y < shape.length; y++) {

                    }
                }
                
            }
            
            
            return isValid;
        }

        /**
         * Given a Platonic Solid find a net (arrangement of sides on a 2D plane). 
         * First shape (A shape is a Vec3d[] containing vertex locations) is the base.
         * Second shape is a clone of the first and rotated at the first vertex, by the interior angle of the shape (solid.interiorAngle) which corresponds to that platonic solid.
         * Repeat until each vertex has been used as a rotation point for the cloned shape.
         * Use the first cloned-base as the new base and repeat, until all faces have been used. Fold and check that the platonic solid is valid.
         * Remember the order of operations to create a system of replication and a system of keeping track of which order produces a valid or invalid net.
         * @param solid PlatonicSolid which to build the net for. PlatonicSolid has the information needed (how many faces are connected to a vertex) to confirm whether or not the net will be invalid or valid.
         * @return Vec3d[][] array of shapes. 
         */
        private static Vec3d[][] buildNet(PlatonicSolid solid) {
            Vec3d[][] net = new Vec3d[solid.faces][solid.shape.length];
            // keep generating nets until a valid one is found.
            do {
                AtomicInteger i = new AtomicInteger();
                int indx;
                Vec3d[] shape;
                // add shapes until all faces have been created
                do {
                    indx = i.getAndIncrement();
                    // if 
                    if (indx % solid.shape.length == 0 && net[indx-(solid.shape.length-1)] != null) {
                        shape = net[indx-(solid.shape.length-1)].clone();
                    } else {
                        shape = solid.shape.clone();
                    }
                    // rotate each vertex of the shape
                    do {
                        AtomicInteger i2 = new AtomicInteger();
                        int innerIndx;
                        Vec3d vertex;
                        // for each vertex (shape vertices)
                        // rotate Y axis by interior angle
                        do {



                            innerIndx = i2.getAndIncrement();

                            //rotate vertex by interior angle around Y axis
                            vertex = shape[innerIndx];
                            Vector3f f = new Vector3f(vertex);
                            f.rotate(Vector3f.POSITIVE_Y.getDegreesQuaternion(solid.interiorAngle()));
                            vertex = new Vec3d(f);

                            shape[innerIndx] = vertex;



                        } while (innerIndx < solid.shape.length);

                    } while(!isValidSpotforShapeInNet(net, indx));

                    net[indx] = shape;
                } while(indx < solid.faces);

            } while(!isValidNet(net));
            return net;
        }

    }

    public RotatingPortal[] portals;

    public static HashMap<Integer, PlatonicSolidPortal> PLATONIC_SOLID_PORTALS;

    public static PlatonicSolidPortal generateAPlatonicSolidPortal(PlatonicSolid solid, DimensionType dimensionTo, Vec3d destination, double scale) {
        PlatonicSolidPortal portal;
        if (PLATONIC_SOLID_PORTALS.get(solid.psEnum.ordinal()) == null) {
            portal = new PlatonicSolidPortal();
            AtomicInteger index = new AtomicInteger();
            portal.portals = new RotatingPortal[solid.faces];
            FoldedPlatonicSolidArray array = FoldedPlatonicSolidArray.getFromEnum(solid.psEnum);
            int indx;
            do {
                indx = index.getAndIncrement();
                portal.portals[indx] = new RotatingPortal(array.axisH[indx].multiply(scale), array.axisW[indx].multiply(scale), dimensionTo, destination, scale, scale, false, solid.polygonSides);
            } while(indx < portal.portals.length);
        } else {
            portal = PLATONIC_SOLID_PORTALS.get(solid.psEnum.ordinal()).setDimensionType(dimensionTo).setDestination(destination).setScale(scale);
        }
        return portal;
    }
    
    public PlatonicSolidPortal setDimensionType(DimensionType dimensionTo) {
        AtomicInteger index = new AtomicInteger();
        int indx;
        do {
            indx = index.getAndIncrement();
            this.portals[indx].dimensionTo = dimensionTo;
        } while(indx < this.portals.length);
        return this;
    }
    public PlatonicSolidPortal setDestination(Vec3d destination) {
        AtomicInteger index = new AtomicInteger();
        int indx;
        do {
            indx = index.getAndIncrement();
            this.portals[indx].destination = destination;
        } while(indx < this.portals.length);
        return this;
    }
    public PlatonicSolidPortal setScale(double scale) {
        AtomicInteger index = new AtomicInteger();
        int indx;
        do {
            indx = index.getAndIncrement();
            this.portals[indx].axisH = this.portals[indx].axisH.multiply(scale);
            this.portals[indx].axisW = this.portals[indx].axisW.multiply(scale);
            this.portals[indx].setInitialAxis();
        } while(indx < this.portals.length);
        return this;
    }
}