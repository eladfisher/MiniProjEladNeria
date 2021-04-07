package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    /**
     * Test method for {@link Point3D#add(Vector)}  (primitives.point3D)}.
     */
    @Test
    void testAdd() {
        Point3D p1 = new Point3D(1, 2, 3);
        Vector v = new Vector(1,3,-9);
        Point3D p2 = new Point3D(2,5,-6);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test  that a vector with 0 field is normalized
        assertTrue(p2.equals(p1.add(v)),"ERROR: adding doesn't work.");

        // TC02: Test  that a vector with 0 field is normalized
        p1.add(v);
        assertTrue(!p1.equals(p2),"ERROR: adding shouldn't change the point.");

        // =============== Boundary Values Tests ==================
        // TC01: Test  that a regular vector is normalized
        assertTrue(Point3D.ZERO.equals(p1.add(new Vector(-1,-2,-3))),"ERROR: zeroing doesn't work.");

    }

    /**
     * Test method for {@link Point3D#subtract(Point3D)}    (primitives.point3D)}.
     */
    @Test
    void testSubtract() {
        Point3D p1 = new Point3D(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test  that a regular vector is normalized
        assertTrue(new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)),"ERROR: subtracting doesn't work");

        // =============== Boundary Values Tests ==================


    }

    /**
     * Test method for {@link Point3D#distanceSquared(Point3D)}    (primitives.point3D)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test of a regular vector
        Point3D p  = new Point3D(1,2,3);
        assertEquals(14.0,p.distanceSquared(Point3D.ZERO),"ERROR: point3D distance squared doesn't working");
        // =============== Boundary Values Tests ==================
        assertEquals(0.0,p.distanceSquared(p),"ERROR: Point3D distance squared doesn't return 0 when the two points are the same");
    }

    /**
     * Test method for {@link Point3D#distance(Point3D)}   (primitives.point3D)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test of a regular vector

        Point3D p = new Point3D(1, 2, 3);
        assertEquals(Math.sqrt(14), p.distanceSquared(Point3D.ZERO),0.00001, "ERROR: point3D distance squared doesn't working");

        // =============== Boundary Values Tests ==================
        assertEquals(0.0, p.distanceSquared(p),0.00001,
                     "ERROR: Point3D distance squared doesn't return 0 when the two points are the same");
    }
}