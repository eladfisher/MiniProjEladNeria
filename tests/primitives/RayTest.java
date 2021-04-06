package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for the ctor of the ray ctor
     */
    @Test
    void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: a normal point and a vector
        Point3D point = new Point3D(1,2,3);
        Vector v = new Vector(1,3,9);
        Ray r = new Ray(point,v);
        assertTrue(r.getDirection().equals(v)&&r.getHead().equals(point),"ray ctor does not work");
    }

}