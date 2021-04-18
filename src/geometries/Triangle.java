package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * a class of Triangle for a 3D graphic model
 */
public class Triangle extends Plane {
	
	public Triangle(Point3D p0, Point3D p1, Point3D p2) {
		super(p0, p1, p2);
	}
	
	@Override
	public List<Point3D> findIntersections(Ray ray) {
		return null;
	
	}
	
	
}
