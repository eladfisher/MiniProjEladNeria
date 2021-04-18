package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for {@link Plane#getNormal(Point3D)}    (primitives.Plane)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Point3D p1 = new Point3D(0, 1, 2);
        Point3D p2 = new Point3D(3, 2, 1);
        Point3D p3 = new Point3D(0, 0, 0);
        Vector normal = new Vector(-3, 6, -3).normalize();
        Vector rNormal = normal.scale(-1);

        Plane plane = new Plane(p1, p2, p3);
        Vector planeNormal = plane.getNormal(p1);

        // TC01: Test for a point on the plane to check if the normal is the right normal
        assertTrue(planeNormal.equals(normal)||planeNormal.equals(rNormal), "ERROR: plane doesn't return the right normal");

    }

    @Test
    void findIntersections() {
        Point3D p = new Point3D(1.526468642633,1.116078754799,1.000000000000);
        Vector v = new Vector(1.395334998728, 1.143208888624, 2.000000000000);
        Plane pl = new Plane(v, p);


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for regular Intersect ray
        Ray r01 = new Ray(new Point3D(-2.973063724219, -5.330502575512, 2.766862208437), new Vector(1.216571959839, 2.052835554495, 0.4469546644399));
        List<Point3D> l01 = List.of(new Point3D(-0.4813089860582, -1.125931955411, 3.682304483639));

        assertEquals(l01, pl.findIntersections(r01), "ERROR: Test for regular Intersect ray");

        // TC02: Test for regular not Intersect ray
        Ray r02 = new Ray(new Point3D(-2.973063724219, -5.330502575512, 2.766862208437), new Vector(-1.216571959839, -2.052835554495, -0.4469546644399));

        assertNull(pl.findIntersections(r02), "ERROR: Test for regular not Intersect ray");


        // ============== Boundary Values Tests ================
        // TC11: Test for regular Ray start at the plane.
        Ray r11 = new Ray(new Point3D(-0.4813089860582, -1.125931955411, 3.682304483639), new Vector(1.216571959839, 2.052835554495, 0.4469546644399));

        assertNull(pl.findIntersections(r11), "ERROR: Test for regular Ray start at the plane.");

        // TC12: Test for Intersect Vertical ray to the plain
        //(-1.756491764380, -3.277667021017, 3.213816872877) + λ (0.5180755717739, 0.4244633720007, 0.7425823508278)
        Ray r12 = new Ray(new Point3D(-1.756491764380, -3.277667021017, 3.213816872877), new Vector(0.5180755717739, 0.4244633720007, 0.7425823508278));
        List<Point3D> l12 = List.of(new Point3D(-0.7608207665156, -2.461905977296, 4.640959456948));

        assertEquals(l12, pl.findIntersections(r12), "ERROR: Test for Intersect Vertical ray to the plain");

        // TC13: Test for Not intersect Vertical ray to the plain
        Ray r13 = new Ray(new Point3D(-1.756491764380, -3.277667021017, 3.213816872877), new Vector(-0.5180755717739, -0.4244633720007, -0.7425823508278));
        assertNull(pl.findIntersections(r13), "ERROR: Test for Not intersect Vertical ray to the plain");

        // TC14: Test for Vertical ray to the plain start at the plane.
        Ray r14 = new Ray(new Point3D(-0.7608207665156, -2.461905977296, 4.640959456948), new Vector(-0.5180755717739, -0.4244633720007, -0.7425823508278));
        assertNull(pl.findIntersections(r14), "ERROR: Test for Vertical ray to the plain start at the plane.");

        // TC15: Test for ray parallel to the plane
        Ray r15 = new Ray(new Point3D(-1.756491764380, -3.277667021017, 3.213816872877), new Vector(0.2795117804574, 1.335974021885, -0.9586549733090));
        assertNull(pl.findIntersections(r15), "ERROR: Test for ray parallel to the plane.");

        // TC16: Test for A ray merges with a plane
        Ray r16 = new Ray(new Point3D(-0.7608207665156, -2.461905977296, 4.640959456948), new Vector(0.2795117804574, 1.335974021885, -0.9586549733090));
        assertNull(pl.findIntersections(r16), "ERROR: Test for A ray merges with a plane.");
    
        // TC17: Test for regular Ray start at the plane point.
        Ray r17 = new Ray(pl.planePoint, new Vector(1.216571959839, 2.052835554495, 0.4469546644399));
    
        assertNull(pl.findIntersections(r11), "ERROR: Test for regular Ray start at the plane point.");
    
    }
}