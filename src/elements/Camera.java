package elements;

import primitives.*;
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

	/**
	 * @param point3D the point where the Camera is
	 * @param vTo     the direction where the camera looking
	 * @param vUp     the direction that up relate to the camera
	 */
	public Camera(Point3D point3D, Vector vTo, Vector vUp) {
		set_vTo_vUp(vTo,vUp);
		this.point3D = point3D;
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

	//region Setters

	/**
	 *
	 * @param vTo
	 * @param vUp
	 */
	public void set_vTo_vUp(Vector vTo, Vector vUp){
		if (!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException("vTo have to be orthogonal to vUp");
		}

		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();

		vRight = vTo.crossProduct(vUp).normalize();
	}

	/**
	 *
	 * @param p
	 */
	public void setPoint3D(Point3D p)
	{
		point3D = p;
	}

	/**
	 *
	 * @param p
	 * @param axisUp
	 */
	public void lookAt(Point3D p, Vector axisUp){
		Vector to = p.subtract(point3D).normalized();
		axisUp.normalize();

		if (isZero(to.dotProduct(axisUp)))
			set_vTo_vUp(to, axisUp);

		if (axisUp.equals(to))
			throw new IllegalArgumentException("axisUp points at the camera.");

		Vector h = to.scale(-1);
		double cos_a = h.dotProduct(axisUp)/(h.length()*axisUp.length());
		double b = p.distance(point3D);

		double d = b/cos_a;

		Point3D pup = p.add(axisUp.scale(d));

		Vector up = pup.subtract(point3D).normalized();

		set_vTo_vUp(to,up);
	}

    /**
     *
     * @param a
     */
	public void rotateCameraAroundVto(double a){
		Point3D h0 = Point3D.ZERO;
		Vector ToH0 = h0.subtract(point3D);
		Point3D Hup = point3D.add(vUp);
		Hup = Hup.add(ToH0);

		Matrix v = new Matrix(Hup);
		double x = vTo.getHead().getX(),
				y = vTo.getHead().getY(),
				z = vTo.getHead().getZ(),
				ca = Math.cos(a),
				sa = Math.sin(a);
		double[][] ra = {
				{ca+x*x*(1-ca),		x*y*(1-ca)-z*sa,	x*z*(1-ca)+y*sa},
				{x*y*(1-ca)+z*sa,	ca+y*y*(1-ca),		z*y*(1-ca)-x*sa},
				{x*z*(1-ca)-y*sa,	z*y*(1-ca)+x*sa,	ca+z*z*(1-ca)}
		};

		Matrix rm = new Matrix(ra);

		Matrix rotate = rm.times(v);

		double[][] drot = rotate.getData();
		Point3D newone = new Point3D(drot[0][0],drot[1][0],drot[2][0]);

		set_vTo_vUp(vTo,newone.subtract(h0));
	}

	public void MoveCamera(Point3D newPlace, Point3D lookAtP, double a)
    {
        point3D = newPlace;
        lookAt(lookAtP,new Vector(0,1,0));
        rotateCameraAroundVto(a);
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
