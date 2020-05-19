package net.lmvdz.delirium.portal;

import java.util.HashMap;
import net.lmvdz.delirium.portal.PlatonicSolidPortal.PlatonicSolidEnum;

public class PlatonicSolid {
    
    public static HashMap<Integer, PlatonicSolid> PLATONIC_SOLIDS = new HashMap<Integer, PlatonicSolid>();

    public static PlatonicSolid TETRAHEDRON = new PlatonicSolid(4, 6, 4, 3, (float)70.53, PlatonicSolidEnum.TETRAHEDRON);
    public static PlatonicSolid CUBE = new PlatonicSolid(8, 12, 6, 3, (float)90, PlatonicSolidEnum.CUBE);
    public static PlatonicSolid OCTAHEDRON = new PlatonicSolid(6, 12, 8, 4, (float)109.47, PlatonicSolidEnum.OCTAHEDRON);
    public static PlatonicSolid DODECAHEDRON = new PlatonicSolid(20, 30, 12, 3, (float)116.57, PlatonicSolidEnum.DODECAHEDRON);
    public static PlatonicSolid ICOSAHEDRON = new PlatonicSolid(12, 30, 20, 5, (float)138.19, PlatonicSolidEnum.ICOSAHEDRON);

    public final int vertices;
    public final int edges;
    public final int faces;
    public final int facesMeetingAtEachVertex;
    public final float dihedralAngle;
    public final PlatonicSolidEnum psEnum;
    public final PlatonicSolidShape shape;
    // public final PlatonicSolidShape[] folded;


    PlatonicSolid(int vertices, int edges, int faces, int facesMeetingAtEachVertex, float dihedralAngle, PlatonicSolidEnum psEnum) {
        this.vertices = vertices;
        this.edges = edges;
        this.faces = faces;
        this.facesMeetingAtEachVertex = facesMeetingAtEachVertex;
        this.dihedralAngle = dihedralAngle;
        this.psEnum = psEnum;
        this.shape = PlatonicSolidShape.fromEnum(this.psEnum);
        // this.folded = PreFoldedPlatonicSolidShape.fromEnum(this.psEnum);
        PLATONIC_SOLIDS.put(psEnum.ordinal(), this);
    }

    public static PlatonicSolid fromEnum(PlatonicSolidEnum psEnum) {
        return PLATONIC_SOLIDS.get(psEnum.ordinal());
    }

}