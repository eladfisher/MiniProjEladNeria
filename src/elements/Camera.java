package elements;

import primitives.*;

import static primitives.Util.*;

/**
 * the camera class that represents the camera of a scene.
 * the use of this class is to use camera in order to render a scene
 */
public class Camera {
	private final Point3D point3D;
	
	private final Vector vTo;
	private final Vector vUp;
	private final  Vector vRight;
	
	private double height;
	private double width;
	private double distance;
	
	/**
	 * @param point3D the point where the Camera is
	 * @param vTo     the direction where the camera looking
	 * @param vUp     the direction that up relate to the camera
	 */
	public Camera(Point3D point3D, Vector vTo, Vector vUp) {
		
		//
		if (!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException("vTo have to be orthogonal to vUp");
		}
		
		this.point3D = point3D;
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();
		
		vRight = vTo.crossProduct(vUp);
		
		
	}
	
	
	//region Getters
	
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
	
	
	//region getters using method chaining
	
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
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
		Point3D p0 = this.point3D;
		Point3D pC = p0.add(vTo.scale(distance));// find the center of the view plane
		
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
		
		return new Ray(p0, Pij.subtract(p0));
	}
	
	
	//endregion
	
	
}
