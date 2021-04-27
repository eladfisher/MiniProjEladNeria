package elements;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Testing Camera Class
 *
 * @author Dan
 *
 */
public class CameraTest {
	
	/**
	 * Test method for
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void testConstructRayThroughPixel() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setVpDistance(10);
		
		// ============ Equivalence Partitions Tests ==============
		// TC01: 3X3 Corner (0,0)
		assertEquals(new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
					 camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 0, 0),"Bad ray");
		
		// TC02: 4X4 Corner (0,0)
		assertEquals( new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
					 camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 0, 0),"Bad ray");
		
		// TC03: 4X4 Side (0,1)
		assertEquals( new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
					 camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 1, 0),"Bad ray");
		
		// TC04: 4X4 Inside (1,1)
		assertEquals( new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
					 camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 1, 1),"Bad ray");
		
		// =============== Boundary Values Tests ==================
		// TC11: 3X3 Center (1,1)
		assertEquals( new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
					 camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 1, 1),"Bad ray");
		
		// TC12: 3X3 Center of Upper Side (0,1)
		assertEquals( new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
					 camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 1, 0),"Bad ray");
		
		// TC13: 3X3 Center of Left Side (1,0)
		assertEquals( new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
					 camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 0, 1),"Bad ray");
		
	}
	
}
