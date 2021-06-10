package elements;

import com.sun.source.tree.NewClassTree;
import geometries.Plane;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * the camera class that represents the camera of a scene.
 * the use of this class is to use camera in order to render a scene
 */
public class Camera {
	private Point3D point3D;
	
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	
	private double height;
	private double width;
	private double distance;
	
	double apertureSize;
	double focalPlaneDist = 0;
	
	int samplingDepth = 1;
	
	
	int raysSampling = 1;
	
	/**
	 * TODO
	 *
	 * @param point3D the point where the Camera is
	 * @param vTo     the direction where the camera looking
	 * @param vUp     the direction that up relate to the camera
	 */
	public Camera(Point3D point3D, Vector vTo, Vector vUp) {
		set_vTo_vUp(vTo, vUp);
		this.point3D = point3D;
	}
	
	
	//region Getters
	
	/**
	 * getter of the rays sampling field
	 *
	 * @return the rays sampling field
	 */
	public int getRaysSampling() {
		return raysSampling;
	}
	
	/**
	 * getter for the point field
	 *
	 * @return the central point
	 */
	public Point3D getPoint3D() {
		return point3D;
	}
	
	/**
	 * getter for the point field
	 *
	 * @return the central point
	 */
	public Vector getvTo() {
		return vTo;
	}
	
	/**
	 * getter for the up vector field
	 *
	 * @return the vUp vector
	 */
	public Vector getvUp() {
		return vUp;
	}
	
	/**
	 * getter for the right vector field
	 *
	 * @return the vRight vector
	 */
	public Vector getvRight() {
		return vRight;
	}
	
	/**
	 * getter for the Height field
	 *
	 * @return the Height of the plan view
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * getter for the Width field
	 *
	 * @return the Width of the plan view
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * getter for the distance field
	 *
	 * @return the distance from the plain view
	 */
	public double getDistance() {
		return distance;
	}
	
	
	//endregion
	
	//region Setters
	
	/**
	 * setter for the ray samplings field
	 *
	 * @param raysSampling the new value for the ray sampling field
	 * @return the same instance for chaining method
	 */
	public Camera setRaysSampling(int raysSampling) {
		this.raysSampling = raysSampling;
		return this;
	}
	
	/**
	 * TODO
	 *
	 * @param samplingDepth
	 * @return
	 */
	public Camera setSamplingDepth(int samplingDepth) {
		this.samplingDepth = samplingDepth;
		return this;
	}
	
	/**
	 * TODO
	 *
	 * @param vTo
	 * @param vUp
	 */
	public void set_vTo_vUp(Vector vTo, Vector vUp) {
		if (!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException("vTo have to be orthogonal to vUp");
		}
		
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();
		
		vRight = vTo.crossProduct(vUp).normalize();
	}
	
	/**
	 * TODO
	 *
	 * @param p
	 */
	public void setPoint3D(Point3D p) {
		point3D = p;
	}
	
	/**
	 * TODO
	 *
	 * @param apertureSize
	 * @return
	 */
	public Camera setApertureSize(double apertureSize) {
		this.apertureSize = apertureSize;
		return this;
	}
	
	/**
	 * TODO
	 *
	 * @param focalPlaneDist
	 * @return
	 */
	public Camera setFocalPlaneDist(double focalPlaneDist) {
		this.focalPlaneDist = focalPlaneDist;
		
		return this;
	}
	
	
	/**
	 * update the size of the view plane
	 *
	 * @param width  the new width for the view plane
	 * @param height the new height for the view plane
	 * @return the current instance of camera after the changed
	 */
	public Camera setVpSize(double width, double height) {
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	/**
	 * update the distance of the camera from view plane
	 *
	 * @param distance the new distance from the view plane
	 * @return the current instance of camera after the changed
	 */
	public Camera setVpDistance(double distance) {
		this.distance = distance;
		
		return this;
	}
	//endregion
	
	//region Move Camera Methods
	
	/**
	 * TODO
	 *
	 * @param p
	 * @param axisUp
	 */
	public void lookAt(Point3D p, Vector axisUp) {
		Vector to = p.subtract(point3D).normalized();
		axisUp.normalize();
		
		if (isZero(to.dotProduct(axisUp)))
		{
			set_vTo_vUp(to, axisUp);
			return;
		}
		
		
		if (axisUp.equals(to))
			throw new IllegalArgumentException("axisUp points at the camera.");
		
		Vector h = to.scale(-1);
		double cos_a = h.dotProduct(axisUp) / (h.length() * axisUp.length());
		double b = p.distance(point3D);
		
		double d = b / cos_a;
		
		Point3D pup = p.add(axisUp.scale(d));
		
		Vector up = pup.subtract(point3D).normalized();
		
		set_vTo_vUp(to, up);
	}
	
	/**
	 * TODO
	 *
	 * @param a
	 */
	public void rotateCameraAroundVto(double a) {
		Point3D Hup = point3D.add(vUp);
		Hup.rotateAroundRay(new Ray(point3D, vTo), a);
		set_vTo_vUp(vTo, Hup.subtract(point3D));
	}
	
	/**
	 * TODO
	 *
	 * @param newPlace
	 * @param lookAtP
	 * @param a
	 */
	public void MoveCamera(Point3D newPlace, Point3D lookAtP, double a) {
		point3D = newPlace;
		lookAt(lookAtP, new Vector(0, 1, 0));
		rotateCameraAroundVto(a);
	}
	
	//endregion
	
	//region methods
	
	
	/**
	 * construct a ray Through a specific Pixel on the view plane
	 * and return the ray
	 * TODO update the description for list of rays
	 *
	 * @param nX the number of pixels in each column
	 * @param nY the number of pixels in each row
	 * @param j  the x coordinate of the wanted pixel
	 * @param i  the y coordinate of the wanted pixel
	 * @return a new ray from the center of the camera that go through the center of the param pixel.
	 */
	public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {
		Point3D p0 = this.point3D;
		Point3D pC = getCenterPoint();
		
		
		double ratioY = height / nY;//the ratio between the height and the number of pixels in each column
		double ratioX = width / nX;//the ratio between the width and the number of pixels in each row
		
		double nxJ = (double) (nX - 1) / 2;
		double xJ = (j - nxJ) * ratioX;//the x coordinate of the pixel
		
		double nyI = (double) (nY - 1) / 2;
		double yI = -(i - nyI) * ratioY;//the  coordinate of the pixel
		
		Point3D Pij = pC;//.add(vRight.scale(xJ)).add(vUp.scale(yI))
		
		
		//without this if statement an exception will be thrown
		//because vector 0 will be created in case that either of xJ or yI will be 0
		if (xJ != 0)// if there is a need to change the x axis point
			Pij = Pij.add(vRight.scale(xJ));
		
		if (yI != 0)// if there is a need to change the y axis point
			Pij = Pij.add(vUp.scale(yI));
		
		List<Ray> rays = null;
		
		if (raysSampling != 1)
			rays = createRaySampling(Pij, ratioY, ratioX);
		
		else
			rays = List.of(new Ray(p0, Pij.subtract(p0)));
		
		//if there is focus generate and return the focused rays
		if (0 != focalPlaneDist && samplingDepth != 1)
		{
			List<Ray> DOFrays = new LinkedList<>();
			
			//calc and add all the DOF rays
			for (Ray r: rays)
			{
				DOFrays.addAll(createDepthOfFieldRays(Pij,r));
			}
			
			//return all the DOF rays
			return createDepthOfFieldRays(Pij, new Ray(p0, Pij.subtract(p0)));
		}
		
		
		//if there isn't focus return the rays that calculated before no matter if the rays are anti aliasing or a single ray
		else
			return rays;
	}
	
	
	/**
	 * TODO
	 *
	 * @param samplingDepth
	 * @param ru
	 * @param rd
	 * @param lu
	 * @param ld
	 * @return
	 */
	private List<Point3D> generatePointsInPixel(int samplingDepth, Point3D ru, Point3D rd, Point3D lu, Point3D ld) {
		int sq = (int) Math.sqrt(samplingDepth);
		Vector r = lu.subtract(ru).scale((double) 1 / sq);
		Vector d = lu.subtract(ld).scale((double) 1 / sq);
		List<Point3D> res = new LinkedList<Point3D>();
		
		//TODO
		for (int i = 0; i < sq; i++)
		{
			for (int j = 0; j < sq; j++)
			{
				Point3D p = lu;
				
				//TODO
				if (i != 0)
					p = p.add(r.scale(i));
				
				//TODO
				if (j != 0)
					p = p.add(d.scale(j));
				
				res.add(p);
			}
		}
		return res;
	}
	
	/**
	 * TODO
	 *
	 * @return
	 */
	private Point3D getCenterPoint() {
		Point3D p0 = this.point3D;
		return p0.add(vTo.scale(distance));
	}
	
	/**
	 * construct a ray Through a specific Pixel on the view plane
	 * and return the ray
	 *
	 * @param nX the number of pixels in each column
	 * @param nY the number of pixels in each row
	 * @param j  the x coordinate of the wanted pixel
	 * @param i  the y coordinate of the wanted pixel
	 * @return a new ray from the center of the camera that go through the center of the param pixel.
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int i, int j) {
		return constructRaysThroughPixel(nX, nY, i, j).get(0);
	}
	
	
	//endregion
	
	//region appearance improvements
	
	/**
	 * TODO
	 *
	 * @param pij
	 * @param ray
	 * @return
	 */
	private List<Ray> createDepthOfFieldRays(Point3D pij, Ray ray) {
		
		//TODO
		Plane focalPlane = new Plane(vTo, getCenterPoint().add(vTo.scale(focalPlaneDist)));
		
		Point3D focalPoint = focalPlane.findIntersections(ray).get(0);
		
		Point3D ru = pij.add(vUp.scale(apertureSize / 2)).add(vRight.scale(apertureSize / 2));
		Point3D rd = pij.add(vUp.scale(-apertureSize / 2)).add(vRight.scale(apertureSize / 2));
		Point3D lu = pij.add(vUp.scale(apertureSize / 2)).add(vRight.scale(-apertureSize / 2));
		Point3D ld = pij.add(vUp.scale(-apertureSize / 2)).add(vRight.scale(-apertureSize / 2));
		
		List<Point3D> gridPoints = generatePointsInPixel(samplingDepth, ru, rd, lu, ld);
		
		List<Ray> r = new LinkedList<Ray>();
		r.add(ray);
		
		for (Point3D p : gridPoints)
		{
			r.add(new Ray(p, focalPoint.subtract(p)));
		}
		
		return r;
	}
	
	/**
	 * create list with rays that intersect the pixel
	 *
	 * @param pixel       the pixel to create rays trough it
	 * @param pixelHeight the height of a pixel
	 * @param pixelWidth  the width of the poxel
	 * @return
	 */
	private List<Ray> createRaySampling(Point3D pixel, double pixelHeight, double pixelWidth) {
		//find the 4 corners of the pixel
		Point3D ru = pixel.add(vUp.scale(pixelHeight / 2)).add(vRight.scale(pixelWidth / 2));
		Point3D rd = pixel.add(vUp.scale(-pixelHeight / 2)).add(vRight.scale(pixelWidth / 2));
		Point3D lu = pixel.add(vUp.scale(pixelHeight / 2)).add(vRight.scale(-pixelWidth / 2));
		Point3D ld = pixel.add(vUp.scale(-pixelHeight / 2)).add(vRight.scale(-pixelWidth / 2));
		
		
		List<Ray> res = new LinkedList<>();
		
		List<Point3D> pointsInPixel = generatePointsInPixel(raysSampling, ru, rd, lu, ld);
		
		//create all the rays that intersect the VB through the points
		for (Point3D p : pointsInPixel)
		{
			res.add(new Ray(point3D, p.subtract(point3D)));
		}
		
		return res;
	}
	
	//endregion
	
	
}
