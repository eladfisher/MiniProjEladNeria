package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.Vector;


class VectorTest {

    /**
     *
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

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
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
        Vector v1 = new Vector(1, 2, 3);

        assertEquals(14.0, v1.lengthSquared(), 0.00001, "ERROR: lengthSquared() wrong value");


    }

    /**
     * Test method for {@link Vector#length()} ()} (primitives.Vector)}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(5.0, new Vector(0, 3, 4).length(), 0.00001, "length doesn't work");
    }

    /**
     * Test method for {@link Vector#normalize()} ()} (primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============


    }

    @Test
    void testNormalized() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
    }
}