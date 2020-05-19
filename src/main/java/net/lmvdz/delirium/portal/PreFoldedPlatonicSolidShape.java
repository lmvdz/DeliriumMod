package net.lmvdz.delirium.portal;

import java.util.HashMap;
import net.lmvdz.delirium.portal.PlatonicSolidPortal.PlatonicSolidEnum;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Vec3d;

public class PreFoldedPlatonicSolidShape {

    public PlatonicSolidShape[] foldedShapes;
    public PlatonicSolidEnum psEnum;
    public PlatonicSolid solid;
    public static HashMap<Integer, PreFoldedPlatonicSolidShape> FOLDED = new HashMap<Integer, PreFoldedPlatonicSolidShape>();

    public static PreFoldedPlatonicSolidShape TETRAHEDRON = new PreFoldedPlatonicSolidShape(PlatonicSolidEnum.TETRAHEDRON);
    public static PreFoldedPlatonicSolidShape CUBE = new PreFoldedPlatonicSolidShape(PlatonicSolidEnum.CUBE);
    public static PreFoldedPlatonicSolidShape OCTAHEDRON = new PreFoldedPlatonicSolidShape(PlatonicSolidEnum.OCTAHEDRON);
    public static PreFoldedPlatonicSolidShape DODECAHEDRON = new PreFoldedPlatonicSolidShape(PlatonicSolidEnum.DODECAHEDRON);
    public static PreFoldedPlatonicSolidShape ICOSAHEDRON = new PreFoldedPlatonicSolidShape(PlatonicSolidEnum.ICOSAHEDRON);


    PreFoldedPlatonicSolidShape(PlatonicSolidEnum psEnum) {
        this.psEnum =  psEnum;
        this.solid = PlatonicSolid.fromEnum(psEnum);
        this.foldedShapes = new PlatonicSolidShape[this.solid.faces];
        this.regenerate();
        FOLDED.put(this.psEnum.ordinal(), this);
    }

    public PreFoldedPlatonicSolidShape() {
    }

    public static PreFoldedPlatonicSolidShape fromEnum(PlatonicSolidEnum psEnum) {
        return FOLDED.get(psEnum.ordinal());
    }

    public PreFoldedPlatonicSolidShape clone() {
        PreFoldedPlatonicSolidShape folded = new PreFoldedPlatonicSolidShape();
        folded.psEnum = this.psEnum;
        folded.solid = this.solid;
        folded.foldedShapes = this.foldedShapes.clone();
        return folded;
    }

    public PreFoldedPlatonicSolidShape regenerate() {
        PlatonicSolidShape shapeClone;
        for (int x = 0; x < foldedShapes.length; x++) {
            shapeClone = PlatonicSolidShape.clone(PlatonicSolidShape.fromEnum(this.psEnum));
            this.foldedShapes[x] = shapeClone;
        }
        if (this.psEnum.ordinal() == PlatonicSolidEnum.TETRAHEDRON.ordinal()) {
            Vector3f temp;
            shapeClone = PlatonicSolidShape.clone(PlatonicSolidShape.fromEnum(this.psEnum));
            
            
            PlatonicSolidShape shape1 = this.foldedShapes[1];
            temp = new Vector3f(shape1.axisH);
            temp.rotate(Vector3f.POSITIVE_Y.getDegreesQuaternion(180-this.solid.dihedralAngle));
            temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
            shape1.axisH = new Vec3d(temp);
            
            temp = new Vector3f(shape1.axisW);
            temp.rotate(Vector3f.POSITIVE_Y.getDegreesQuaternion(180-this.solid.dihedralAngle));
            temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
            shape1.axisW = new Vec3d(temp);
            shape1.offset = shape1.offset.add(-1*shapeClone.axisW.subtract(shape1.axisW).getX(), 0, -1*shapeClone.axisW.subtract(shape1.axisW).getZ());
            
            this.foldedShapes[1] = shape1;
            
            PlatonicSolidShape shape2 = foldedShapes[2];
            // shape2.offset = shape2.offset.add(1.733, 1, 0);
            temp = new Vector3f(shape2.axisH);
            temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shapeClone.interiorAngle()*2));
            temp.rotate(Vector3f.NEGATIVE_X.getDegreesQuaternion(this.solid.dihedralAngle));
            // temp.rotate(Vector3f.NEGATIVE_Y.getDegreesQuaternion(45));
            shape2.axisH = new Vec3d(temp);
            
            temp = new Vector3f(shape2.axisW);
            temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shapeClone.interiorAngle()*2));
            temp.rotate(Vector3f.NEGATIVE_X.getDegreesQuaternion(this.solid.dihedralAngle));
            // temp.rotate(Vector3f.NEGATIVE_Y.getDegreesQuaternion(45));
            shape2.axisW = new Vec3d(temp);
            // shape2.offset = shape2.offset.add(shapeClone.axisW.subtract(shape2.axisW).getX(), shapeClone.axisW.subtract(shape2.axisW).getY(), -1*shapeClone.axisW.subtract(shape2.axisW).getZ());
            this.foldedShapes[2] = shape2;
            
            
            PlatonicSolidShape shape3 = this.foldedShapes[3];
            // shape3.offset = shape3.offset.add(0, -2.001, 0);
            temp = new Vector3f(shape3.axisH);
            // temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shape1.interiorAngle()));
            // temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shape3.interiorAngle() * 3));
            shape3.axisH = new Vec3d(temp);
            
            temp = new Vector3f(shape3.axisW);
            // temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shape1.interiorAngle()));
            // temp.rotate(Vector3f.POSITIVE_Z.getDegreesQuaternion(shape3.interiorAngle() * 3));
            shape3.axisW = new Vec3d(temp);
            
            this.foldedShapes[3] = shape3;
            
        } else if (this.psEnum.ordinal() == PlatonicSolidEnum.CUBE.ordinal()) {

        } else if (this.psEnum.ordinal() == PlatonicSolidEnum.OCTAHEDRON.ordinal()) {
            
        } else if (this.psEnum.ordinal() == PlatonicSolidEnum.DODECAHEDRON.ordinal()) {
            
        } else if (this.psEnum.ordinal() == PlatonicSolidEnum.ICOSAHEDRON.ordinal()) {
            
        }
        // ObjectArrayList.wrap(foldedShapes).stream().forEach((s) -> {
        //     System.out.println("\naxisH:" + s.axisH + "\n axisW: " + s.axisW);
        //     ObjectArrayList.wrap(s.vertices).stream().forEach(v -> {
        //         System.out.println("\nVertex: (" + v.x + ", " + v.y + ", " + v.z + ")");
        //     });
        // });
        return this;
    }

}