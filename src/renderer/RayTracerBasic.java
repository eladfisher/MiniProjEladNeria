package renderer;


import elements.LightSource;
import geometries.Geometry;
import jdk.jshell.spi.ExecutionControl;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * a implementation of the basic class ray tracer basic
 */
public class RayTracerBasic extends RayTracerBase {
	
	
	/**
	 * a ctor that gets a scene
	 *
	 * @param scene the param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	
	/**
	 *trace the ray to the geometries
	 * @param ray the ray that intersect the scene
	 * @return the color of the closest point that the trace ray found
	 */
	@Override
	Color traceRay(Ray ray) {
		GeoPoint p = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		
		//if p is null there isn't intersection points and the color is the background
		if (p == null)
			return scene.background;
		
		return calcColor(p,ray);
	}
	
	/**
	 *calc the color of a specific point
	 * @param p the point
	 * @param r the ray from the camera to the pixel
	 * @return the color of p
	 */
	Color calcColor(GeoPoint geoPoint,Ray r)
	{
		Geometry g = geoPoint.geometry;
		Vector v = r.getDirection();
		Point3D p = geoPoint.point;
		
		Color res = scene.ambientLight.getIntensity().add(g.getEmmission());
		
		
		
		//
		for (LightSource l:scene.lights)
		{
			Vector specularV = l.getL(p).subtract(g.getNormal(p).scale(2*l.getL(p).dotProduct(g.getNormal(p))));
			double vrShininess = Math.pow(v.scale(-1).dotProduct(specularV),g.getMaterial().nShininess);
			
			
			Color diffuse = l.getIntensity(p).scale(g.getMaterial().Kd*Math.abs(l.getL(p).dotProduct(g.getNormal(p))));
			Color specular = l.getIntensity(p).scale(g.getMaterial().Kd*vrShininess);
			
			res = res.add(diffuse).add(specular);
		}
		
		return res;
	}
}
