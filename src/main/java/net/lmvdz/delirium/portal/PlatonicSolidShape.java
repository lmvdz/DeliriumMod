package net.lmvdz.delirium.portal;

import java.util.HashMap;
import net.lmvdz.delirium.portal.PlatonicSolidPortal.PlatonicSolidEnum;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public class PlatonicSolidShape {

    public static HashMap<Integer, PlatonicSolidShape> SHAPES = new HashMap<>();
    
    public static PlatonicSolidShape TETRAHEDRON = new PlatonicSolidShape(3, new Vec3d(1, 0, 0), new Vec3d(0, 1, 0), PlatonicSolidEnum.TETRAHEDRON);
    public static PlatonicSolidShape OCTAHEDRON = new PlatonicSolidShape(3, new Vec3d(1, 0, 0), new Vec3d(0, 1, 0), PlatonicSolidEnum.OCTAHEDRON);
    public static PlatonicSolidShape ICOSAHEDRON = new PlatonicSolidShape(3, new Vec3d(1, 0, 0), new Vec3d(0, 1, 0), PlatonicSolidEnum.ICOSAHEDRON);
    public static PlatonicSolidShape CUBE = new PlatonicSolidShape(4, new Vec3d(1, 0, 0), new Vec3d(0, 1, 0), PlatonicSolidEnum.CUBE);
    public static PlatonicSolidShape DODECAHEDRON = new PlatonicSolidShape(5, new Vec3d(1, 0, 0), new Vec3d(0, 1, 0), PlatonicSolidEnum.DODECAHEDRON);

    public int sides;
    public Vec3d axisW;
    public Vec3d axisH;
    public Vec3d offset = new Vec3d(0, 0, 0);
    public PlatonicSolidEnum psEnum;


    public PlatonicSolidShape(int sides, Vec3d axisW, Vec3d axisH, PlatonicSolidEnum psEnum) {
        this.sides = sides;
        this.axisW = axisW;
        this.axisH = axisH;
        this.psEnum = psEnum;
        SHAPES.put(this.psEnum.ordinal(), this);
    }

    private PlatonicSolidShape(PlatonicSolidShape x) {
        this.sides = x.sides;
        this.axisW = x.axisW;
        this.axisH = x.axisH;
        this.psEnum = x.psEnum;
    }

    public static PlatonicSolidShape clone(PlatonicSolidShape psShape) {
        return new PlatonicSolidShape(psShape);
    }

    public static PlatonicSolidShape fromEnum(PlatonicSolidEnum psEnum) {
        return SHAPES.get(psEnum.ordinal());
    }

    public float interiorAngle() {
        return (this.sides-2) * 180 / this.sides;
    }

    public float exteriorAngle() {
        return 180 - interiorAngle();
    }

    public float sideLength() {
        return (float)(2 * Math.sin(180/this.sides));
    }

    public PlatonicSolidShape rotateAxis(Quaternion q) {

        Vector3f fH = new Vector3f(this.axisH);
        fH.rotate(q);
        this.axisH = new Vec3d(fH);

        Vector3f fW = new Vector3f(this.axisW);
        fW.rotate(q);
        this.axisW = new Vec3d(fW);

        return this;
    }

    public PlatonicSolidShape setAxis(Vec3d axisW, Vec3d axisH) {
        this.axisW = axisW;
        this.axisH = axisH;
        return this;
    }

}