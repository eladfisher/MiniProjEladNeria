package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {
	
	List<Intersectable> geometries;
	
	public Geometries(Intersectable... geometries) {
		this.geometries = new ArrayList<Intersectable>();
		
		for (Intersectable geo : geometries)
		{
			this.geometries.add(geo);
		}
	}
	
	public void add(Intersectable... geometries) {
		for (Intersectable geo : geometries)
		{
			this.geometries.add(geo);
		}
	}
	
	
	
	
	/**
	 *
	 * @param ray
	 * @return
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		List<GeoPoint> res = null;
		
		for (Intersectable i : geometries)
		{
			List<GeoPoint> l = i.findGeoIntersections(ray);
			
			if(l!=null&&res==null)
				res = new ArrayList<GeoPoint>(l);
			
			else if (res!=null&&l!=null)
				res.addAll(l);
		}
		
		return res;
	}
}
