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

import java.util.List;

import static primitives.Util.alignZero;

/**
 * a implementation of the basic class ray tracer basic
 */
public class RayTracerBasic extends RayTracerBase {
	
	//the delta to move the head of the ray for shadowing
	private static final double DELTA = 0.1;
	
	/**
	 * a ctor that gets a scene
	 *
	 * @param scene the param scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}
	
	
	/**
	 * trace the ray to the geometries
	 *
	 * @param ray the ray that intersect the scene
	 * @return the color of the closest point that the trace ray found
	 */
	@Override
	Color traceRay(Ray ray) {
		GeoPoint p = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		
		//if p is null there isn't intersection points and the color is the background
		if (p == null)
			return scene.background;
		
		return calcColor(p, ray);
	}
	
	
	
	/**
	 * calc the color of a specific point
	 *
	 * @param geoPoint the point
	 * @param r        the ray from the camera to the pixel
	 * @return the color of p
	 */
	Color calcColor(GeoPoint geoPoint, Ray r) {
		Geometry g = geoPoint.geometry;
		Vector v = r.getDirection();
		Point3D p = geoPoint.point;
		
		Color res = scene.ambientLight.getIntensity().add(g.getEmmission());
		
		
		//
		for (LightSource l : scene.lights)
		{
			double ln = l.getL(p).dotProduct(g.getNormal(p));
			double vn = v.dotProduct(g.getNormal(p));
			
			if (ln * vn > 0)
			{
				if (unshaded(l,l.getL(p),geoPoint.geometry.getNormal(geoPoint.point), geoPoint)) {
				
				Vector specularV = l.getL(p)
						.subtract(g.getNormal(p).scale(2 * l.getL(p).dotProduct(g.getNormal(p))));
				double vrShininess = Math.pow(v.scale(-1).dotProduct(specularV), g.getMaterial().nShininess);
				
				Color diffuse = l.getIntensity(p).scale(g.getMaterial().Kd * Math.abs(ln));
				Color specular = l.getIntensity(p).scale(g.getMaterial().Ks * vrShininess);
				
				res = res.add(diffuse).add(specular);
				}
			}
		}
		
		return res;
	}
	
	/**
	 *
	 * @param light
	 * @param l
	 * @param n
	 * @param geopoint
	 * @return
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
		Point3D point = geopoint.point.add(delta);
		Ray lightRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		
		if (intersections == null) return true;
		
		double lightDistance = light.getDistance(geopoint.point);
		
		
		for (GeoPoint gp : intersections) {
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0)
				return false;
		}
		
		return true;
	}
	
	
}
