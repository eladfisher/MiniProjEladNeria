package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

        Triangle plane = new Triangle(p1, p2, p3);
        Vector tNormal = plane.getNormal(p1);

        // TC01: Test for a point on the triangle to check if the normal is the right normal
        assertTrue(tNormal.equals(normal)||tNormal.equals(rNormal), "ERROR: triangle doesn't return the right normal");
    }
    
    
    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        //region ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is intersect inside the Triangle (1 points)
        Triangle triangle = new Triangle(new Point3D(-2.6080381866028,4.7214781195145,2.8584393332226),
                                         new Point3D(-8.0241543905165,4.5100412695138,2.5069124726233),
                                         new Point3D (-2,3,1.5477453727712));
    
        Ray ray01 = new Ray(new Point3D(-5.22335,5.93598,0),new Vector(-0.0659058258395, -0.8084030176633, 1.20186));
        List<Point3D> r01 = List.of(new Point3D(-5.3568962024572, 4.2978979080351, 2.4353513037836));
        List<Point3D> l01 = triangle.findIntersections(ray01);
        assertEquals(r01,l01, "ERROR: Ray's line is intersect inside the Triangle");
    
    
        // TC02: Ray's line is intersect outside the Triangle against edge (0 points)
        Ray ray02 = new Ray( new Point3D(-5.22335,5.93598,0) ,
                             new Vector(0.5203169127622, -0.1561034864257, 3.6045512216973));
        
        List<Point3D> l02 = triangle.findIntersections(ray02);
        assertNull(l02, "ERROR: Ray's line is intersect outside the Triangle against edge");
        
        
        // TC03: Ray's line is intersect outside the Triangle against vertex (0 points)
        Ray ray03 = new Ray( new Point3D(-5.22335,5.93598,0) ,
                             new Vector(4.4091404604915, -0.3093642427694, 3.6209348660581));
        
        List<Point3D> l03 = triangle.findIntersections(ray03);
        assertNull(l03, "ERROR: Ray's line is intersect outside the Triangle against edge");
    
        //endregion
    
        //region =============== Boundary Values Tests ==================
        // TC11: Ray's line is intersect on the Triangle edge (0 points)
        Ray ray11 = new Ray(new Point3D(-5.22335,5.93598,0)
                ,new Vector(0.1947603808229, -1.3089964871186, 2.7013361962188));
    
        List<Point3D> l11 = triangle.findIntersections(ray11);
        assertNull(l11, "ERROR: Ray's line is intersect on the Triangle edge");
        
        // TC12: Ray's line is intersect on edge's continuation (0 points)
        Ray ray12 = new Ray(new Point3D(-5.22335,5.93598,0)
                ,new Vector(0.1947603808229, -1.3089964871186, 2.7013361962188));
    
        List<Point3D> l12 = triangle.findIntersections(ray12);
        assertNull(l12, "ERROR: Ray's line is intersect on In vertex");
        
        // TC13: Ray's line is intersect on edge's continuation (0 points)
        Ray ray13 = new Ray(new Point3D(-5.22335,5.93598,0)
                ,new Vector(2.6153118133972, -1.2145018804855, 2.8584393332226));
    
        List<Point3D> l13 = triangle.findIntersections(ray13);
        assertNull(l13, "ERROR: Ray's line is intersect on In vertex");
        
        
        
        
        
    }
}