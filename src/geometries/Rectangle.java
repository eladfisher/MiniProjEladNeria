package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * a class of Triangle for a 3D graphic model
 */
public class Rectangle extends Polygon {
	private Rectangle(Point3D... ver){
		super(ver);
	}

	private void update(Rectangle r){
		this.vertices = r.vertices;
		this.boundaryBox = r.boundaryBox;
		this.plane = r.plane;
		this.emmission = r.emmission;
	}

	/**
	 * the ctor of the triangle
	 * @param p the first point of the triangle
	 */
	public Rectangle(Point3D p, Vector v1, Vector v2) {
		if (!isZero(v1.dotProduct(v2)))
		{
			throw new IllegalArgumentException("v1 have to be orthogonal to v2");
		}
		update(new Rectangle(p, p.add(v1), p.add(v1).add(v2), p.add(v2)));
	}
	
	public Rectangle(Point3D p, double w, double h) {
		Vector v1 = Vector.UP.scale(h);
		Vector v2 = Vector.RIGHT.scale(w);
		update(new Rectangle(p, p.add(v1), p.add(v1).add(v2), p.add(v2)));
	}
	
	public Rectangle(Point3D p, Vector v1, double h) {
		if (!isZero(v1.getHead().getY()))
			throw new IllegalArgumentException("v1 has to be MAKBIL to the ground.");
		Vector v2 = Vector.UP.scale(h);
		update(new Rectangle(p, p.add(v2), p.add(v1).add(v2), p.add(v1)));
	}
	
	public Rectangle(Point3D p1, Point3D p2, double h) {
		if (!isZero(p1.getY()-p2.getY()))
			throw new IllegalArgumentException("p1 and p2 have to be in the same height.");
		update(new Rectangle(p1, p1.add(Vector.UP.scale(h)), p2.add(Vector.UP.scale(h)), p2));
	}
	
	public Rectangle(double w, double h) {
		update(new Rectangle(Point3D.ZERO, w, h));
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
		|| vertices.get(0).subtract(vertices.get(1)).normalized().equals(Vector.UP.scale(-1))
		|| vertices.get(1).subtract(vertices.get(2)).normalized().equals(Vector.UP)
		|| vertices.get(1).subtract(vertices.get(2)).normalized().equals(Vector.UP.scale(-1)))
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

	public Point3D getCenter(){
		return vertices.get(2).add(vertices.get(0).subtract(vertices.get(2)).scale(0.5));
	}

	public static Geometries wallMaker(double h, Point3D p1, Point3D p2, Point3D... ps){
		LinkedList<Point3D> lps = new LinkedList<Point3D>();
		lps.add(p1);
		lps.add(p2);
		lps.addAll(Arrays.stream(ps).toList());
		return wallMaker(lps, h);
	}

	public static Geometries wallMaker(List<Point3D> lps, double h){
		if (lps.size() < 2)
			throw new IllegalArgumentException("you should have at least 2 points.");

		Geometries res = new Geometries();

		for (int i = 0; i < lps.size()-1; i++) {
			res.add(new Rectangle(lps.get(i), lps.get(i+1),h));
		}
		return res;
	}
}
