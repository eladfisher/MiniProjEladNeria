package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link Tube#getNormal(Point3D)}    (primitives.Tube)}.
     */

    @Test
    void testGetNormal() {
        Point3D p0 = new Point3D(0,0,0);
        Vector v = new Vector(1,0,0);
        double r = 1;
        Ray ray = new Ray(p0,v);
        Tube tube = new Tube(ray,r);

        Point3D p = new  Point3D(1,0,1);
        Point3D pointInTube = new  Point3D(0,0.5,0);
        Point3D pointOutTube = new  Point3D(8,1,8);

        Vector normal = new Vector(0,0,1).normalize();


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test of a regular point on the tube
        assertTrue(tube.getNormal(p).equals(normal),"ERROR: GetNormal doesn't return the right ");

        // TC02: Test of a regular point in the tube
        assertThrows(IllegalArgumentException.class,()->tube.getNormal(pointInTube),"point in tube should throw an exception");

        // TC03: Test of a regular point out the tube
        assertThrows(IllegalArgumentException.class,()->tube.getNormal(pointOutTube),"point outside tube should throw an exception");

        // =============== Boundary Values Tests ==================
        Point3D point0 = new Point3D(0,1,0);
        Point3D point = new Point3D(0,0,1);
        Vector vector = new Vector(1,0,0);
        Tube t = new Tube(new Ray(point0,vector),1);

        // TC01: P and P0 create 90 degree with ray vector
        assertTrue(t.getNormal(point).equals(new Vector(0,-1,1)));
    }
}