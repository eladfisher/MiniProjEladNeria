package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 *
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");

		this.vertices = List.of(vertices);

		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;

		for (int i = 1; i < vertices.length; ++i)
		{
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}
	
	/**
	 * create a polygon by the vertex
	 * @param _vertices the vertex of the polygon
	 */
	public Polygon(List<Point3D> _vertices) {
		if (_vertices.size() < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");

		this.vertices = new ArrayList<>(_vertices);

		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices.get(0), vertices.get(1), vertices.get(2));
		if (vertices.size() == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices.get(vertices.size() - 1).subtract(vertices.get(vertices.size() - 2));
		Vector edge2 = vertices.get(0).subtract(vertices.get(vertices.size() - 1));

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;

		for (int i = 1; i < vertices.size(); ++i)
		{
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices.get(i).subtract(vertices.get(0)).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices.get(i).subtract(vertices.get(i - 1));
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}
	
	/**
	 * get the normal at point
	 * @param point the point to get the normal
	 * @return the noraml at p
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}



	/**
	 * find the intersection geo point
	 * @param ray the intersected ray
	 * @return a list with geo points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		//the intersection points will be get by the super function.
		List<GeoPoint> intersectionList = plane.findGeoIntersections(ray,maxDistance);

		//if there aren't intersection points null will be return
		if (intersectionList == null)
			return null;

		//here we decide whether the point is on the polygon or not
		List<Vector> vectors = new ArrayList<Vector>(vertices.size());

		Point3D p0 = ray.getHead();
		for (Point3D p : vertices)
		{
			vectors.add(p.subtract(p0));
		}

		List<Vector> normals = new ArrayList<Vector>(vertices.size());
		for (int i = 0; i < vertices.size(); ++i)
		{
			normals
					.add(vectors.get(i).crossProduct(
							vectors.get((i + 1) % vertices.size()
							)).normalize());
		}

		Vector v = ray.getDirection();

		double Initsign = v.dotProduct(normals.get(0));
		boolean pos = true;

		if(Initsign<0)
			pos=false;

		if(isZero(Initsign))
			return null;

		for (Vector n:normals)
		{
			double sign = v.dotProduct(n);

			if(isZero(sign))
				return null;

			if((pos&&sign<0)||(!pos&&sign>0))
				return null;
		}

		//update that the geometry is the polygon and not the plain
		intersectionList.get(0).geometry=this;
		return intersectionList;
	}
	
	/**
	 * determine if a point is on the plane
	 * @param p the point
	 * @return true if on plane
	 */
	public boolean onPlane(Point3D p)
	{
		return plane.onPlane(p);
	}
	
	/**
	 * rotate the point around a vector
	 * @param u the vector to rotate
	 * @param a the degree in radians
	 */
	public void rotateAroundVector(Vector u, double a)
	{
		List<Point3D> ap = new LinkedList<>();
		for (int i = 0;i<vertices.size();++i) {
			Point3D h = vertices.get(i).rotated_AroundVector(u,a);
			ap.add(new Point3D(h.getX(),h.getY(),h.getZ()));
		}
		vertices = ap;
		plane = new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
	}
	
	/**
	 * rotate the point around a ray
	 * @param r the ray to rotate
	 * @param a the degree in radians
	 */
	public void rotateAroundRay(Ray r, double a)
	{
		List<Point3D> ap = new LinkedList<>();
		for (int i = 0;i<vertices.size();++i) {
			Point3D h = vertices.get(i).rotated_AroundRay(r,a);
			ap.add(new Point3D(h.getX(),h.getY(),h.getZ()));
		}
		vertices = ap;
		plane = new Plane(vertices.get(0),vertices.get(1),vertices.get(2));
	}
}
