package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link Sphere#getNormal(Point3D)}    (primitives.Sphere)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Point3D p = new  Point3D(4,1,1);
        Point3D pointOutSphere = new  Point3D(2,1,1);
        Point3D pointInSphere = new  Point3D(8,1,1);
        Point3D p0 = new Point3D(1,1,1);
        Vector normal = new Vector(1,0,0);
        Sphere sphere = new Sphere(p0,3);

        // TC01: Test for regular point
        assertTrue(sphere.getNormal(p).equals(normal),"ERROR: normal of sphere isn't working");



    }
}