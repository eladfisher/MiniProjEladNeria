package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Geometries
 */
class GeometriesTest {
	
	/**
	 * Test method for {@link Geometries#findIntersections(Ray)} (primitives.Plane)}.
	 */
	@Test
	void testFindIntersections() {
		//region ============ Equivalence Partitions Tests ==============
		
		
		//endregion
		
		
		//region =============== Boundary Values Tests ==================
		// TC01: there are no geometries at all
		assertNull(new Geometries().findIntersections(new Ray(new Point3D(1,4,3),new Vector(3,5,4))), "ERROR: there are no geometries at all");
		
		// TC02: there are no intersection points
		
		Ray ray02 = new Ray(new Point3D(-3.3866610310164, 2.4658689737339, 1.191879105173),
							new Vector(4.8773770658716, -1.1678648857608, -0.3405368871923));
		Geometries geometries = new Geometries();
		geometries.add(new Sphere(Math.sqrt(4.7480806531793), new Point3D(1.9830981616661, -4.1789435470063, 0)
		));
		
		geometries.add(new Triangle(new Point3D(1.5913891885845, 0.8972900190323, 0.6131727697483),
									new Point3D(4.4449233374946, 3.7594391959405, -1),
									new Point3D(5.8376558844398,-1.5370495424611,0)));
		
		geometries.add(new Plane(new Point3D(-4,1,0),
								 new Point3D(-6.1416690438609,1.8140491195724,3.2309488252121),
								 new Point3D(-5.5735072237192,5.4709453252502,0)) );
		
		assertNull(geometries.findIntersections(ray02), "ERROR: there are no intersection points");
		
		// TC03: there is one intersection point
		Ray ray03 = new Ray(new Point3D(-2.7157646362688,2.3778292836661,3.2112516806092), new Vector(-2.0939483448653,-6.3267996655464,-3.2112516806092));
		
		assertEquals(1,geometries.findIntersections(ray03).size(), "ERROR: there is one intersection point.");
		
		// TC04: there is two intersection geometries
		Ray ray04 = new Ray(new Point3D(5.4984552825654, -5.3968225442878, -1) , new Vector(-8.3507404082022, 3.3771252995638, 1));
		
		assertEquals(3,geometries.findIntersections(ray04).size(), "ERROR: there is two intersection geometries.");
		
		// TC05: there is two intersection geometries
		Ray ray05 = new Ray(new Point3D(6.8253719303411, 6.2677528871251, 0) , new Vector(-1.101969625074, -1.6914290794505, 0));
		
		assertEquals(4,geometries.findIntersections(ray05).size(), "ERROR: there is two intersection geometries.");
		//endregion
		
	}
}