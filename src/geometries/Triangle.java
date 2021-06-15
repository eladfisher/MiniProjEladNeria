package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * a class of Triangle for a 3D graphic model
 */
public class Triangle extends Polygon {
	
	/**
	 * the ctor of the triangle
	 * @param p0 the first point of the triangle
	 * @param p1 the second point of the triangle
	 * @param p2 the thirst point of the triangle
	 */
	public Triangle(Point3D p0, Point3D p1, Point3D p2) {
		super(p0, p1, p2);
	}
}
