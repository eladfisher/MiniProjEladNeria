package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for {@link Cylinder#getNormal(Point3D)}    (primitives.Cylinder)}.
     */
    @Test
    void testGetNormal() {

        Point3D p0 = new Point3D(0,0,0);
        Vector vector = new Vector(0,0,2);
        double r = 1, h= 8;

        Cylinder cylinder  = new Cylinder(new Ray(p0,vector),r,h);

        // ============ Equivalence Partitions Tests ==============

        Vector upV = new Vector(0,0,1);
        Point3D upP = new Point3D(0.38,-0.43,8);

        Vector downV = new Vector(0,0,-1);
        Point3D downP = new Point3D(0.38,-0.43,0);

        Vector middleV = new Vector(0,1,0);
        Point3D middleP = new Point3D(0,1,2);

        // TC01: point is in the far flat side of the ray origin
        assertTrue(cylinder.getNormal(upP).equals(upV),
                   "ERROR: wrong normal vector when the point is on the flat side away from the central point");

        // TC02: point is in the near flat side of the ray origin
        assertTrue(cylinder.getNormal(downP).equals(downV),
                   "ERROR: wrong normal vector when the point is on the flat side of the central point");

        // TC03: point isn't in flat side
        assertTrue(cylinder.getNormal(middleP).equals(middleV),
                   "ERROR: wrong normal vector when the point isn't on flat side");

        // =============== Boundary Values Tests ==================
        // TC01: point is in the middle of the far flat side of the ray origin
        Vector middleUpV = new Vector(0,0,1);
        Point3D middleUpP = new Point3D(0,0,8);
        assertTrue(cylinder.getNormal(middleUpP).equals(middleUpV),
                   "ERROR: wrong normal vector when the point is on the flat side away from the central point in the middle");

        // TC02: point is in the middle of the near flat side of the ray origin
        Vector middleDownV = new Vector(0,0,-1);
        Point3D middleDownP = new Point3D(0,0,0);
        assertTrue(cylinder.getNormal(middleDownP).equals(middleDownV),
                   "ERROR: wrong normal vector when the point is on the flat side near the central point in the middle");



    }
}