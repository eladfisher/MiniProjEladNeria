package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the integration between Camera and the rayTracer
 */
class integrationTestCameraRay {
	
	/**
	 * private func only for ex 4 that return the number of the intersection points of the camera and the geometries
	 * @param camera the camera
	 * @param geometries the geometries
	 * @param nX x of the pixel we on it
	 * @param nY y of the pixel we on it
	 * @return num of intersection.
	 */
	int getNomIntersectPoints(Camera camera,Geometries geometries,int nX,int nY)
	{
		int res = 0;
		
		for(int i = 0 ; i< nX;++i)
			for (int j=0 ; j<nY;++j)
				if(geometries.findIntersections(camera.constructRayThroughPixel(nX,nY,i,j))!=null)
					res += geometries.findIntersections(camera.constructRayThroughPixel(nX,nY,i,j)).size();
				
		return res ;
	}
	
	/**
	 *integration tests for the camera and the ray that constructed and the find intersection of the geometry
	 */
	@Test void IntegrationTestCameraRay()
	{
		Camera camera = new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
				.setVpDistance(1).setVpSize(3,3);
		
		Geometries geo = new Geometries(new Plane( new Vector(0,0,-1),new Point3D(0,0,-5)));
		
		//check for the regular case that there are 9 intersection points
		assertEquals(9,getNomIntersectPoints(camera,geo,3,3),"ERROR: test of 9 intersection points");
		
		//test for the case that there is a geometry but the geometry isn't behind the whole grid
		geo = new Geometries(new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2)));
		assertEquals(1,getNomIntersectPoints(camera,geo,3,3),"ERROR: test of 1 intersection points");
		
		//test for the case that there is a geometry but the geometry isn't behind the whole grid and there is a intersection wih the ray of the up-middle ray
		geo = new Geometries(new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2)));
		assertEquals(2,getNomIntersectPoints(camera,geo,3,3),"ERROR: test of 2 intersection points");
	}
	
}