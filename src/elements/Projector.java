package elements;

import geometries.Polygon;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static primitives.Util.isZero;

/**
 * Spot light source:
 * - One-directional light source modeling (like a light bulb)
 * - Power (I0)
 * - A specific position in a scene at a point (pz, py, px).
 * - This lighting have a uniform direction.
 * [Like a projector. The direction of the light is not uniform to every point in the room, but it very centralized]
 * (as in the sun, no matter where you are in the room the direction of the sun is the same direction),
 * but the direction in which the light hits each object in the room varies with the lamp.]
 * - There are attenuation factors Kq, KJ, Kc with the distance d
 * (i.e. there is a thinning of the light due to the distance, but less than point light).
 */
public class Projector extends PointLight {
	 /**
	 * vTo: vector Toward, the camera direction
	 */private Vector vTo;
	/**
	 * vUp: vector Up, the camera up direction, must be orthogonal to vTo
	 */private Vector vUp;
	 private Vector vDown;
	 private Ray rDown;
	/**
	 * vRight: vector Right, the camera right direction, its just cross product of vTo and vUp
	 */private Vector vRight;
	 private Ray rRight;

	/**
	 * height: the height of the view plane
	 */private double height;
	/**
	 * width: the width of the view plane
	 */private double width;
	/**
	 * distance: the distance between the camera (point3D) and the view plane
	 */private double distance = 1;
	 
	private Point3D luPoint;
	
	private Polygon imgPolygon;

	private BufferedImage img;
	private int nX;
	private int nY;
	private double rationW;
	private double rationH;
	//Color c = new Color(image.getRGB(j, i));

	
	/**
	 * constructor that gets the basic parameters (position and intensity)
	 * and sets attenuation factors for no depending on distance.
	 *
	 * @param position  the position of the light source
	 */
	public Projector(Point3D position, Vector vTo, Vector vUp, BufferedImage img) {
		super(Color.WHITE, position);
		set_vTo_vUp(vTo, vUp);
		setImage(img);
	}
	
	public Projector(Point3D position, Vector vTo, Vector vUp, String imagePath) {
		// white = new Color(255, 255, 255)
		super(Color.WHITE, position);
		set_vTo_vUp(vTo, vUp);
		setImage(imagePath);
	}
	
	
	
	public void set_vTo_vUp(Vector vTo, Vector vUp) {
		if (!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException("vTo have to be orthogonal to vUp");
		}
		
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();
		this.vDown = vUp.scale(-1);
		
		vRight = vTo.crossProduct(vUp).normalize();
	}
	
	public Projector setImgDistance(double distance) {
		this.distance = distance;
		update_imgPolygon(); 
		return this;
	}

	public Projector setImage(String imagePath) {
		try {
			this.img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			throw new IllegalArgumentException("axisUp points at the camera.");
		}
		update_height_width();
		return this;
	}
	
	public Projector setImage(BufferedImage img) {
		this.img = img;
		update_height_width();
		return this;
	}
	
	public void update_luPoint(){
		this.luPoint = position.add(vTo.scale(distance)).add(vUp.scale(height/2)).add(vRight.scale(width/-2));
		this.rRight = new Ray(luPoint, vRight);
		this.rDown = new Ray(luPoint, vDown);
	}
	
	public void update_height_width(){
		this.nY = this.img.getHeight();
		this.nX = this.img.getWidth();
		this.height = this.nY;
		this.width = this.nX;
		this.rationH = this.height/this.nY;
		this.rationW = this.width/this.nX;
		update_imgPolygon();
	}
	
	public void update_imgPolygon(){
		update_luPoint();
		imgPolygon = new Polygon(luPoint,
			luPoint.add(vRight.scale(width)),
			luPoint.add(vRight.scale(width)).add(vDown.scale(height)),
			luPoint.add(vDown.scale(height)));
	}
	
	public void scaleImage(double scl){
		this.height = this.height * scl;
		this.width = this.width * scl;
		update_imgPolygon();
	}
	
	
	
	/**
	 * make the camera look at certain point
	 *
	 * @param p the new location of the camera
	 * @param axisUp the direction of the axis up
	 */
	public void lookAt(Point3D p, Vector axisUp) {
		Vector to = p.subtract(position).normalized();
		axisUp.normalize();
		
		//check is the vectors to and up orthogonal and make them the directions of the camera
		if (isZero(to.dotProduct(axisUp)))
		{
			set_vTo_vUp(to, axisUp);
			return;
		}
		
		if (axisUp.equals(to))
			throw new IllegalArgumentException("axisUp points at the camera.");
		
		Vector h = to.scale(-1);
		double cos_a = h.dotProduct(axisUp) / (h.length() * axisUp.length());
		double b = p.distance(position);
		
		double d = b / cos_a;
		
		Point3D pup = p.add(axisUp.scale(d));
		
		Vector up = pup.subtract(position).normalized();
		
		set_vTo_vUp(to, up);
		setImgDistance(p.distance(position));
	}
	
	
	
	
	/**
	 * setter for the quad attenuation factor, that doesn't depend the distance.
	 *
	 * @param p the wanted point to calc intensity
	 * @return the intensity of the color in the param point
	 */
	@Override
	public Color getIntensity(Point3D p) {
		Ray r = new Ray(p, position.subtract(p));
		Point3D pxlp;
		try {
			 pxlp = imgPolygon.findGeoIntersections(r).get(0).point;
		} catch (Exception e) {
			return Color.BLACK;
		}
		double xp = pxlp.distance(rDown);
		double yp = pxlp.distance(rRight);
		int ixp = (int)(xp/rationW);
		int iyp = (int)(yp/rationH);
		
		return new Color(img.getRGB(iyp, ixp));
	}
}
