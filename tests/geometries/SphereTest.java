package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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


    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere( new Point3D(1, 0, 0),1d);

        //region ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                                                                new Vector(3, 1, 0)));
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        
        assertEquals( List.of(p1, p2), result,"Ray crosses sphere");
        
        // TC03: Ray starts inside the sphere (1 point)
        Sphere sp03 = new Sphere(new Point3D(-7.04,5.16,2),1.5);
        List l03 =  List.of(new Point3D(-5.977227068529, 6.168291356648, 1.677723354489));
        Ray ray03 = new Ray(new Point3D(-7.248100929974, 5.207740358653, 1),new Vector(0.7340957903833, 0.5548437696832, 0.3914738328231));
        List<Point3D> r03 = sp03.findIntersections(ray03);
        
        assertEquals( l03, r03,"Ray crosses sphere");
        
        // TC04: Ray starts after the sphere (0 points)
        Ray ray04 = new Ray(new Point3D(-4.701942679562, 4.789669778943, 1.288529908453),new Vector(0.9640458396722, -0.2396487415863, 0.1148220347615));
        List<Point3D> r04 = sp03.findIntersections(ray04);
        
        assertEquals( null, r04,"Ray starts after the sphere ");
        //endregion
        
        //region =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        List l11 =  List.of(new Point3D (-8.451544613208 ,4.893427241144 ,1.568165808256));
        Ray ray11 = new Ray(new Point3D(-6.615496379665,3.889698880691,1.324624739420),new Vector(-0.8715603879459, 0.4764634519109, 0.1156073923099));
        List<Point3D> r11 = sp03.findIntersections(ray11);
        
        assertEquals( l11, r11,"Ray starts at sphere and goes inside");
        
        // TC12: Ray starts at sphere and goes outside (0 points)
        Ray ray12 = new Ray(new Point3D(-6.615496379665,3.889698880691,1.324624739420),new Vector(0.8715603879459, -0.4764634519109, -0.1156073923099));
        List<Point3D> r12 = sp03.findIntersections(ray12);
    
        assertNull(r12,"Ray starts at sphere and goes inside");
        
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        List l13 =  List.of(new Point3D (-6.373810127057,3.834645054102,1.777232586377),new Point3D(-7.706189872943 ,6.485354945898 ,2.222767413623));
        Ray ray13 = new Ray(new Point3D(-5.020278391185,1.141854224602,1.324624739420),new Vector(-0.4441265819621 ,0.8835699639318 ,0.1485116090822));
    
        List<Point3D> r13 = sp03.findIntersections(ray13);
        assertEquals( l13, r13,"Ray starts before the sphere");
        
        // TC14: Ray starts at sphere and goes inside (1 points)
        List l14 =  List.of(new Point3D(-7.706189872943 ,6.485354945898 ,2.222767413623));
        Ray ray14 = new Ray(new Point3D(-6.373810127057,3.834645054102,1.777232586377),new Vector(-0.4441265819621 ,0.8835699639318 ,0.1485116090822));
    
        List<Point3D> r14 = sp03.findIntersections(ray14);
        assertEquals( l14, r14,"Ray starts at sphere and goes inside");
        
        // TC15: Ray starts inside (1 points)
        List l15 =  List.of(new Point3D(-7.924337441525, 6.020204303399, 2.853226725983));
        Ray ray15 = new Ray(new Point3D(-6.339998919604,4.479101608203,1.324624739420),new Vector(-0.5895582943501, 0.5734695355991, 0.5688178173218));
    
        List<Point3D> r15 = sp03.findIntersections(ray15);
        assertEquals( l15, r15,"Ray starts inside");
        
        // TC16: Ray starts at the center (1 points)
        List l16 =  List.of(new Point3D(-7.924337441525, 6.020204303399, 2.853226725983));
        Ray ray16 = new Ray(new Point3D(-7.04,5.16,2),new Vector(-0.5895582943501, 0.5734695355991, 0.5688178173218));
    
        List<Point3D> r16 = sp03.findIntersections(ray16);
        assertEquals( l16, r16,"Ray starts at the center");
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        Ray ray17 = new Ray(new Point3D(-6.373810127057,3.834645054102,1.777232586377),new Vector(0.4441265819621 ,-0.8835699639318 ,-0.1485116090822));
    
        List<Point3D> r17 = sp03.findIntersections(ray17);
        assertNull(r17,"Ray starts at sphere and goes outside");
        
        // TC18: Ray starts after sphere (0 points)
        Ray ray18 = new Ray(new Point3D(-5.611256171272, 3.770245894591, 0.6215189338229),new Vector(0.5895582943501, -0.5734695355991, -0.5688178173218));
    
        List<Point3D> r18 = sp03.findIntersections(ray18);
        assertNull(r18,"Ray starts after sphere");
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        Ray ray19 = new Ray(new Point3D(-8.782674337452,3.791559243977,2.548411223087),new Vector(2.873678168485,-0.2189426618708,-0.7904416355050));
    
        List<Point3D> r19 = sp03.findIntersections(ray19);
        assertNull(r19,"Ray starts before the tangent point");
        
        // TC20: Ray starts at the tangent point
        Ray ray20 = new Ray(new Point3D(-7.128193683612, 3.665506019807, 2.093325330632),new Vector(2.873678168485,-0.2189426618708,-0.7904416355050));
    
        List<Point3D> r20 = sp03.findIntersections(ray20);
        assertNull(r20,"Ray starts at the tangent point");
        
        // TC21: Ray starts after the tangent point
        Ray ray21 = new Ray(new Point3D(-5.908996168967, 3.572616582106, 1.757969587582),new Vector(2.873678168485,-0.2189426618708,-0.7904416355050));
    
        List<Point3D> r21 = sp03.findIntersections(ray21);
        assertNull(r21,"Ray starts after the tangent point");
    
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Ray ray22 = new Ray(new Point3D(-7.226612830811,1.997735528770,2.197471104758),new Vector(2.873678168485,-0.2189426618708,-0.7904416355050));
    
        List<Point3D> r22 = sp03.findIntersections(ray22);
        assertNull(r22,"Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
        //endregion
    }

}