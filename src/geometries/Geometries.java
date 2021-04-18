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
	
	
	@Override
	public List<Point3D> findIntersections(Ray ray) {
		
		List<Point3D> res = null;
		
		for (Intersectable i : geometries)
		{
			List<Point3D> l = i.findIntersections(ray);
			
			if(l!=null&&res==null)
				res = new ArrayList<Point3D>(l);
			
			else if (res!=null&&l!=null)
				res.addAll(l);
		}
		
		return res;
	}
}
