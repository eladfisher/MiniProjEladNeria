package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * a class of Triangle for a 3D graphic model
 */
public class Rectangle extends Polygon {
	
	/**
	 * the ctor of the triangle
	 * @param p the first point of the triangle
	 */
	public Rectangle(Point3D p, Vector v1, Vector v2) {
		super(p, p.add(v1), p.add(v1).add(v2), p.add(v2));
		if (!isZero(v1.dotProduct(v2)))
		{
			throw new IllegalArgumentException("v1 have to be orthogonal to v2");
		}
	}
	
	public Rectangle(Point3D p, double w, double h) {
		super(p, p.add(Vector.UP.scale(h)), p.add(Vector.UP.scale(h)).add(Vector.RIGHT.scale(w)), p.add(Vector.RIGHT.scale(w)));
	}
	
	public Rectangle(Point3D p, Vector v1, double h) {
		super(p, p.add(v1), p.add(v1).add(Vector.UP.scale(h)), p.add(Vector.UP.scale(h)));
	}
	
	public Rectangle(Point3D p1, Point3D p2, double h) {
		super(p1, p1.add(Vector.UP.scale(h)), p2.add(Vector.UP.scale(h)), p2);
	}
	
	public Rectangle(double w, double h) {
		super(Point3D.ZERO,
				Point3D.ZERO.add(Vector.UP.scale(h)),
				Point3D.ZERO.add(Vector.UP.scale(h)).add(Vector.RIGHT.scale(w)),
				Point3D.ZERO.add(Vector.RIGHT.scale(w)));
	}
	
	public Rectangle Move(Vector v){
		List<Point3D> ap = new LinkedList<>();
		for (int i = 0;i<vertices.size();++i) {
			ap.add(vertices.get(i).add(v));
		}
		vertices = ap;
		plane = new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
		return this;
	}
	
	public Rectangle Moved(Vector v){
		Vector v1 = vertices.get(0).subtract(vertices.get(1));
		Vector v2 = vertices.get(2).subtract(vertices.get(1));
		
		return new Rectangle(vertices.get(1), v1, v2);
	}


	public boolean normalRec(){
		if(vertices.get(0).subtract(vertices.get(1)).normalized().equals(Vector.UP)
		|| vertices.get(1).subtract(vertices.get(2)).normalized().equals(Vector.UP))
			return true;
		return false;
	}
	
	public double getHeight(){
		double h = vertices.get(0).getY() - vertices.get(1).getY();
		double w = vertices.get(1).getY() - vertices.get(2).getY();
		if (normalRec())
			if (isZero(h))
				return Math.abs(w);
			else
				return Math.abs(h);
		else
			return 	vertices.get(0).distance(vertices.get(1));
	}

	public double getWidth(){
		double h = vertices.get(0).getY() - vertices.get(1).getY();
		double w = vertices.get(1).getY() - vertices.get(2).getY();
		if (normalRec())
			if (isZero(h))
				return vertices.get(0).distance(vertices.get(1));
			else
				return vertices.get(1).distance(vertices.get(2));
		else
			return 	vertices.get(0).distance(vertices.get(1));
	}
}
