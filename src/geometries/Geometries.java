package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that collect geometries
 */
public class Geometries implements Intersectable {
	
	/**
	 * list of the geometries
	 */
	List<Intersectable> geometries;
	
	/**
	 * ctor that gets geometries for th collection
	 * @param geometries the geometries of the collection
	 */
	public Geometries(Intersectable... geometries) {
		this.geometries = new ArrayList<Intersectable>();

		for (Intersectable geo : geometries)
		{
			this.geometries.add(geo);
		}
	}
	
	/**
	 * add geometries to the collection
	 * @param geometries the geometries to add
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable geo : geometries)
		{
			this.geometries.add(geo);
		}
	}


	/**
	 * find all the intersection points in some distance from the ray's head
	 * @param ray the intersected ray
	 * @param maxDistance the max distance
	 * @return a list with the intersected points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> res = null;

		for (Intersectable i : geometries)
		{
			List<GeoPoint> l = i.findGeoIntersections(ray,maxDistance);

			if(l!=null&&res==null)
				res = new ArrayList<GeoPoint>(l);

			else if (res!=null&&l!=null)
				res.addAll(l);
		}

		return res;
	}
}
