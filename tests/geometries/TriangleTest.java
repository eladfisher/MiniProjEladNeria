package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link Triangle#getNormal(Point3D)}    (primitives.Triangle)}.
     */
    @Test
    void  getNormal(){
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

}