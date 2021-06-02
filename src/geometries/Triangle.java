package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * a class of Triangle for a 3D graphic model
 */
public class Triangle extends Polygon {

	public Triangle(Point3D p0, Point3D p1, Point3D p2) {
		super(p0, p1, p2);
	}
}
