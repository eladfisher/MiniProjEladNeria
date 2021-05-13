package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

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
	
	/**
	 * test method for the find closest intersection point of the ray class
	 */
	@Test
	void testFindClosestPoint() {
		
		//================EPA==============
		//test case 00 the point is in the middle of the points
		
		Ray r = new Ray(new Point3D(3,7,3),new Vector(1,4,5));
		List<Point3D> l00 =List.of(r.getPoint(6),
								   r.getPoint(7),
								   r.getPoint(3),
								   r.getPoint(9));
		
		assertEquals(r.getPoint(3), r.findClosestPoint(l00), "ERROR: point is in the middle");
		
		//================BVA==============
		//test case 01 the point is in the start of the list
		List<Point3D> l01 =List.of(r.getPoint(3),
								   r.getPoint(7),
								   r.getPoint(8),
								   r.getPoint(9));
		
		assertEquals(r.getPoint(3), r.findClosestPoint(l00), "ERROR: point is in the start");
		
		//test case 02 the point is in the end of the list
		List<Point3D> l02 =List.of(r.getPoint(44),
								   r.getPoint(7),
								   r.getPoint(8),
								   r.getPoint(3));
		
		assertEquals(r.getPoint(3), r.findClosestPoint(l00), "ERROR: point is in the end");
		
		//test case 03 the points list is in null
		assertNull( r.findClosestPoint(null), "ERROR: null list");
	}
}