package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.Vector;

/**
 * Test class for Vector
 */
class VectorTest {

    /**
     * Test method for {@link Vector#Vector(Point3D)} (primitives.Vector)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC01: Test  point - Constructor result for vector 0 is exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Point3D(0, 0, 0)), "vector 0 is not allowed");

        // TC02: Test  coordinate - Constructor result for vector 0 is exception
        assertThrows(IllegalArgumentException.class,
                     () -> new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0)),
                     "vector 0 is not allowed");

        // TC03: Test  double - Constructor result for vector 0 is exception
        assertThrows(IllegalArgumentException.class, () -> new Vector(0.0, 0.0, 0.0), "vector 0 is not allowed");
    }

    /**
     * Test method for {@link Vector#add(Vector)} (primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test add a vector to another vector
        Vector v1 = new Vector(1,2,3);
        Vector rv1 = new Vector(-1,-2,-3);
        Vector v2 = new Vector(0,2,-1);
        Vector v3 = new Vector(1,0,4);
        assertTrue(v2.add(v3).equals(v1),"ERROR: add between vectors doesn't working");

        // =============== Boundary Values Tests ==================
        // TC01: Test add a vector to another vector that create 0 vector
        assertThrows(IllegalArgumentException.class,()->v1.add(rv1),"ERROR: vector 0 shouldn't be allowed");

    }

    /**
     * Test method for {@link Vector#subtract(Vector)} (primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test subtract a vector to another vector
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(0,2,-1);
        Vector v3 = new Vector(1,0,4);
        assertTrue(v1.subtract(v2).equals(v3),"ERROR: subtract between vectors doesn't working");

        // =============== Boundary Values Tests ==================
        // TC01: Test subtract a vector to itself create 0 vector
        assertThrows(IllegalArgumentException.class,()->v1.subtract(v1),"ERROR: vector 0 shouldn't be allowed");

    }

    /**
     * Test method for {@link Vector#scale(double)} (primitives.Vector)}.
     */
    @Test
    void testScale() {

        Vector v = new Vector(0,4,2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test a vector is scale by a number greater than 1
        assertTrue(new Vector(0,8,4).equals(v.scale(2)) ,"ERROR: scale by a number greater than 1 doesn't working");

        // TC02: Test a vector is scale by a number smaller than 1
        assertTrue(new Vector(0,2,1).equals(v.scale(0.5)) ,"ERROR: scale by a number smaller than 1 doesn't working");

        // TC03: Test a vector is scale by a negative number
        assertTrue(new Vector(0,-2,-1).equals(v.scale(-0.5)) ,"ERROR: scale by a negative number doesn't working");

        // =============== Boundary Values Tests ==================
        // TC01: Test a vector is scale by a 0
        assertThrows(IllegalArgumentException.class,()->v.scale(0),"ERROR: scale by zero shouldn't be possible");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)} (primitives.Vector)}.
     */
    @Test
    void testDotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for orthogonal vectors
        assertEquals(0,v1.dotProduct(v3),0.0001,"ERROR: dotProduct() for orthogonal vectors is not zero");

        // TC01: Test for not-orthogonal vectors
        assertEquals(-28.0,v1.dotProduct(v2),0.0001,"ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                     "crossProduct() for parallel vectors does not throw an exception");


    }

    /**
     * Test method for {@link Vector#lengthSquared()} (primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Test  a length squared of a regular vector
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(14.0, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");

    }

    /**
     * Test method for {@link Vector#length()} ()} (primitives.Vector)}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test  a length of a regular vector
        assertEquals(5.0, new Vector(0, 3, 4).length(), 0.00001, "length doesn't work");
    }

    /**
     * Test method for {@link Vector#normalize()} ()} (primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        // TC01: Test  that a regular vector is normalize and not create new vector
        assertTrue(vCopy == vCopyNormalize,"ERROR: normalize() function creates a new vector");

        // TC02: Test  that a regular vector is normalize is with length of 1
        assertEquals(1.0,vCopyNormalize.length(),"ERROR: normalize() result is not a unit vector");


    }

    /**
     * Test method for {@link Vector#normalized()} ()} (primitives.Vector)}.
     */
    @Test
    void testNormalized() {
        Vector v = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test  that a regular vector is normalize is with length of 1
        Vector u = v.normalized();
        assertTrue(u!=v,"ERROR: normalized() function does not create a new vector");

        // TC02: Test  that a regular vector is normalize is with length of 1
        assertEquals(1.0,u.length(),"ERROR: normalized() result is not a unit vector");
    }
}