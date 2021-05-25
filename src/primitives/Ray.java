package primitives;

import geometries.Intersectable.*;

import java.util.*;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class of 3D Ray in the 3D dimensional for graphic scenes
 */
public class Ray {
	
	Point3D head;
	Vector direction;
	
	//the delta to move the head of the ray for shadowing
	private static final double DELTA = 0.1;
	
	/**
	 * default ctor that gets the head point and the direction of the ray
	 *
	 * @param head      the head point
	 * @param direction the direction of the ray
	 */
	public Ray(Point3D head, Vector direction) {
		this.head = head;
		this.direction = direction.normalize();
	}
	
	/**
	 * ctor that make ray that is with the head moved in the direction of the normal
	 *
	 * @param head      the head of the ray
	 * @param direction the direction of the ray
	 * @param normal    the normal of the ray
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		Vector delta;
		
		if (alignZero(direction.dotProduct(normal)) > 0)
			delta = normal.scale(DELTA);
		
		else
			delta = normal.scale(-DELTA);
		
		this.direction = direction.normalized();
		this.head = head.add(delta);
	}
	
	/**
	 * getter for the head
	 *
	 * @return the head point
	 */
	public Point3D getHead() {
		return head;
	}
	
	/**
	 * getter for the direction
	 *
	 * @return the direction ray
	 */
	public Vector getDirection() {
		return direction;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ray ray = (Ray) o;
		return head.equals(ray.head) && direction.equals(ray.direction);
	}
	
	/**
	 * to string function for debug use only
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Ray{" +
				"head=" + head +
				", direction=" + direction +
				'}';
	}
	
	//region methods
	
	/**
	 * get a point that is  with distance of t from the ray origin
	 *
	 * @param t the distance of the point from the ray origin
	 * @return the point that on the ray and with distance of t from the ray origin
	 */
	public Point3D getPoint(double t) {
		if (isZero(t))
			return head;
		
		return head.add(direction.scale(t));
	}
	
	/**
	 * @param points the points that we want to find the closest point to
	 * @return the closest point from the points to the head of the ray
	 */
	public Point3D findClosestPoint(List<Point3D> points) {
		
		//if the list is null so there isn't closest point
		if (points == null)
			return null;
		
		Point3D p0 = head;
		Point3D res = points.get(0);
		double d = res.distanceSquared(head);
		
		//find the closest point except the first one
		for (Point3D p : points)
		{
			
			double pD = p.distanceSquared(p0);
			
			if (pD < d)
			{
				res = p;
				d = pD;
			}
		}
		
		return res;
	}
	
	/**
	 * @param points
	 * @return
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
		
		//if the list is null so there isn't closest point
		if (points == null)
			return null;
		
		Point3D p0 = head;
		GeoPoint res = points.get(0);
		double d = res.point.distanceSquared(head);
		
		//find the closest point except the first one
		for (GeoPoint p : points)
		{
			
			double pD = p.point.distanceSquared(p0);
			
			if (pD < d)
			{
				res = p;
				d = pD;
			}
		}
		
		return res;
		
	}
	
	
	//endregion
}
