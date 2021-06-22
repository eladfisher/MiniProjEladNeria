package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * a class that collect geometries
 */
public class Geometries extends Intersectable {
	
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
		this.geometries.addAll(Arrays.asList(geometries));
	}


	/**
	 * find all the intersection points in some distance from the ray's head
	 * @param ray the intersected ray
	 * @param maxDistance the max distance
	 * @return a list with the intersected points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		
		if(boundaryBox!=null)
			if(!boundaryBox.isIntersect(ray))
				return null;
		
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
	
	@Override
	public Point3D getMinPoint() {
		List<Point3D> minPs = new LinkedList<>();
		for (Intersectable g :
				geometries) {
			minPs.add(g.getMinPoint());
		}


		double   x = Double.POSITIVE_INFINITY
				,y = Double.POSITIVE_INFINITY
				,z = Double.POSITIVE_INFINITY;
		for (Point3D p :
				minPs) {
			if(p.getX()<x)
				x=p.getX();
			if(p.getY()<y)
				y=p.getY();
			if(p.getZ()<z)
				z=p.getZ();
		}

		return new Point3D(x,y,z);
	}
	
	@Override
	public Point3D getMaxPoint() {
		List<Point3D> maxPs = new LinkedList<>();
		for (Intersectable g :
				geometries) {
			maxPs.add(g.getMaxPoint());
		}


		double   x = Double.NEGATIVE_INFINITY
				,y = Double.NEGATIVE_INFINITY
				,z = Double.NEGATIVE_INFINITY;
		for (Point3D p :
				maxPs) {
			if(p.getX()>x)
				x=p.getX();
			if(p.getY()>y)
				y=p.getY();
			if(p.getZ()>z)
				z=p.getZ();
		}

		return new Point3D(x,y,z);
	}

	@Override
	public void setBoundingBox(){
		
		if (geometries.size()<=2)
			return;
		
		for (Intersectable g: geometries) {
			g.setBoundingBox();
		}
		
		BuildBoundingBoxHierarchy();
	}
	
	/**
	 * build the bounding box tree hierarchy
	 */
	public void BuildBoundingBoxHierarchy(){
		
		super.setBoundingBox();
		
		if (geometries.size()<=2)
			return;
		
		Geometries bigger = new Geometries();
		Geometries smaller = new Geometries();
		Vector axis = boundaryBox.getLongestAxis();
		double middlePoint = boundaryBox.getMiddleAxisPoint();
		double minDistance = Double.POSITIVE_INFINITY;
		Intersectable closestGeometry = null;
		
		//make the partition of the geometries
		for (Intersectable g: geometries) {
			
			double disToMid =g.boundaryBox.getMiddlePoint().subtract(Point3D.ZERO).dotProduct(axis);
			
			
			
			if(disToMid<minDistance)
				minDistance = disToMid;
			
			if(disToMid > middlePoint)
			{
				bigger.add(g);
			}
			
			else if (disToMid < middlePoint){
				smaller.add(g);
			}
			
			else{
				if (smaller.geometries.size()==0)
					smaller.geometries.add(g);
					else
						bigger.geometries.add(g);
			}
		
			
			
		}
		
		if(bigger.geometries.size()==0||smaller.geometries.size()==0)
		{
			System.out.println("bigger or smaller is 0");
		}
		
		bigger.BuildBoundingBoxHierarchy();
		smaller.BuildBoundingBoxHierarchy();

		
	
	}
	
}
