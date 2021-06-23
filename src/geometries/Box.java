package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * a 3D geometry of a box for 3D graphic model
 */
public class Box extends Geometry {
	/**
	 * the first point of the box.
	 */
	Point3D p;
	/**
	 * vectors of edges
	 */
	Vector vw, vh, vd;
	/**
	 * list of the corners.
	 */
	Polygon[] corners;
	
	/**
	 * default ctor that gets the first point and the width height and depth of the box
	 *
	 * @param point  the left down point
	 * @param width  the width of the box
	 * @param height the height of the box
	 * @param depth  the depth of the box
	 */
	public Box(Point3D point, double width, double height, double depth) {
		p = point;
		vw = new Vector(width, 0, 0);
		vh = new Vector(0, height, 0);
		vd = new Vector(0, 0, depth);
		
		Point3D h = p.add(vw).add(vh).add(vd);
		Vector _vw = vw.scale(-1);
		Vector _vh = vh.scale(-1);
		Vector _vd = vd.scale(-1);
		
		corners = new Polygon[6];
		corners[0] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vh), p.add(vh))
				.setEmission(new Color(java.awt.Color.RED)).setMaterial(new Material().setKd(1));
		corners[1] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vd), p.add(vd))
				.setEmission(new Color(java.awt.Color.GREEN)).setMaterial(new Material().setKd(1));
		corners[2] = (Polygon) new Polygon(p, p.add(vd), p.add(vd).add(vh), p.add(vh))
				.setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material().setKd(1));
		corners[3] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vh), h.add(_vh))
				.setEmission(new Color(java.awt.Color.CYAN)).setMaterial(new Material().setKd(1));
		corners[4] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vd), h.add(_vd))
				.setEmission(new Color(java.awt.Color.MAGENTA)).setMaterial(new Material().setKd(1));
		corners[5] = (Polygon) new Polygon(h, h.add(_vd), h.add(_vd).add(_vh), h.add(_vh))
				.setEmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKd(1));
	}
	
	/**
	 * default ctor that gets the first point and the width height and depth of the box
	 *
	 * @param point the left down point
	 * @param vw    the width of the box
	 * @param vh    the height of the box
	 * @param vd    the depth of the box
	 */
	public Box(Point3D point, Vector vw, Vector vh, Vector vd) {
		p = point;
		
		this.vw = vw;
		this.vh = vh;
		this.vd = vd;
		
		Point3D h = p.add(vw).add(vh).add(vd);
		Vector _vw = vw.scale(-1);
		Vector _vh = vh.scale(-1);
		Vector _vd = vd.scale(-1);
		
		corners = new Polygon[6];
		corners[0] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vh), p.add(vh))
				.setEmission(new Color(java.awt.Color.RED)).setMaterial(new Material().setKd(1));
		corners[1] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vd), p.add(vd))
				.setEmission(new Color(java.awt.Color.GREEN)).setMaterial(new Material().setKd(1));
		corners[2] = (Polygon) new Polygon(p, p.add(vd), p.add(vd).add(vh), p.add(vh))
				.setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material().setKd(1));
		corners[3] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vh), h.add(_vh))
				.setEmission(new Color(java.awt.Color.CYAN)).setMaterial(new Material().setKd(1));
		corners[4] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vd), h.add(_vd))
				.setEmission(new Color(java.awt.Color.MAGENTA)).setMaterial(new Material().setKd(1));
		corners[5] = (Polygon) new Polygon(h, h.add(_vd), h.add(_vd).add(_vh), h.add(_vh))
				.setEmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKd(1));
	}
	
	/**
	 * getter for the Center of the box.
	 *
	 * @return the center of the box, if we want to know it.
	 */
	public Point3D getCenter() {
		return p.add(vd.scale(0.5)).add(vh.scale(0.5)).add(vw.scale(0.5));
	}
	
	/**
	 * get the normal of the box in certain point
	 *
	 * @param point3D the point of the normal
	 * @return the normal at p
	 */
	@Override
	public Vector getNormal(Point3D point3D) {
		//search the plain that the point is on him
		for (Polygon p :
				corners)
		{
			if (p.onPlane(point3D))
				return p.getNormal(point3D);
		}
		return corners[0].getNormal(point3D);
	}
	
	/**
	 * find the intersections geo point between the ray and the geometry
	 *
	 * @param ray         the intersected ray
	 * @param maxDistance the max distance
	 * @return the intersections points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> res = null;
		
		for (Polygon i : corners)
		{
			List<GeoPoint> l = i.findGeoIntersections(ray, maxDistance);
			
			if (l != null && res == null)
				res = new ArrayList<GeoPoint>(l);
			
			else if (res != null && l != null)
				res.addAll(l);
		}
		
		return res;
	}
	
	/**
	 * find the min point
	 *
	 * @return the min point of the box
	 */
	@Override
	public Point3D getMinPoint() {
		double x = Double.POSITIVE_INFINITY, y = Double.POSITIVE_INFINITY, z = Double.POSITIVE_INFINITY;
		
		//find the min coordinate for each point of the box
		for (Point3D p : getPoints())
		{
			if (p.getX() < x)
				x = p.getX();
			
			if (p.getY() < y)
				y = p.getY();
			
			if (p.getZ() < z)
				z = p.getZ();
		}
		
		return new Point3D(x, y, z);
	}
	
	/**
	 * find the max point
	 *
	 * @return the max point of the box
	 */
	@Override
	public Point3D getMaxPoint() {
		double x = Double.NEGATIVE_INFINITY, y = Double.NEGATIVE_INFINITY, z = Double.NEGATIVE_INFINITY;
		
		//find the max coordinate for each point of the box
		for (Point3D p :
				getPoints())
		{
			if (p.getX() > x)
				x = p.getX();
			
			if (p.getY() > y)
				y = p.getY();
			
			if (p.getZ() > z)
				z = p.getZ();
			
		}
		
		return new Point3D(x, y, z);
	}
	
	/**
	 * the getter for the material field
	 *
	 * @return the material field
	 */
	@Override
	public Material getMaterial() {
		return super.getMaterial();//material;
	}
	
	/**
	 * setter for the material
	 *
	 * @param material the new material
	 * @return the geometry for chaining method
	 */
	@Override
	public Geometry setMaterial(Material material) {
		for (Polygon p :
				corners)
		{
			p.setMaterial(material);
		}
		return this;
	}
	
	/**
	 * getter for the emission light
	 *
	 * @return the emmision color
	 */
	@Override
	public Color getEmmission() {
		return emmission;
	}
	
	/**
	 * setter for the emission field
	 *
	 * @param emmission the new emmision
	 * @return the same instance for chaining method
	 */
	@Override
	public Geometry setEmission(Color emmission) {
		for (Polygon p :
				corners)
		{
			p.setEmission(emmission);
		}
		return this;
	}
	
	/**
	 * method that rotate the box around the center point of the box, on a degrees around vector u.
	 *
	 * @param u vector that we rotate around it.
	 * @param a degrees we rotate.
	 */
	public void rotateAroundVector(Vector u, double a) {
		rotateAroundRay(new Ray(getCenter(), u), a);
	}
	
	/**
	 * method that rotate the box around r (some ray in the universe we get).
	 *
	 * @param r ray that we rotate around it.
	 * @param a degrees we rotate.
	 */
	public void rotateAroundRay(Ray r, double a) {
		for (Polygon p :
				corners)
		{
			p.rotateAroundRay(r, a);
		}
	}
	
	/**
	 * func, mainly for boundary box.
	 *
	 * @return list of the corner points.
	 */
	private List<Point3D> getPoints() {
		List<Point3D> l = new ArrayList<Point3D>(8);
		l.add(p);
		l.add(p.add(vw));
		l.add(p.add(vd));
		l.add(p.add(vh));
		l.add(p.add(vw).add(vd));
		l.add(p.add(vd).add(vh));
		l.add(p.add(vh).add(vw));
		l.add(p.add(vh).add(vw).add(vd));
		
		return l;
	}
}
