package renderer;

import jdk.jshell.spi.ExecutionControl;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
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
		
		return calcColor(p);
	}
	
	/**
	 *calc the color of a specific point
	 * @param p the point
	 * @return the color of p
	 */
	Color calcColor(GeoPoint p)
	{
		return scene.ambientLight.getIntensity().add(p.geometry.getEmmission());
	}
}
