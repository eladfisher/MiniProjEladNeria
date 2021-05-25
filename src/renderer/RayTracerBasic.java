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
	
	//region Constants
	//
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	
	//
	private static final double INITIAL_K = 1.0;
	
	//
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	//endregion
	
	
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
		GeoPoint p = findClosestIntersection(ray);
		
		//if p is null there isn't intersection points and the color is the background
		if (p == null)
			return scene.background;
		
		return calcColor(p, ray);
	}
	
	/**
	 * calc the color of a specific point
	 *
	 * @param geopoint the point
	 * @param ray the ray from the camera to the pixel
	 * @return the color of p
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
				.add(scene.ambientLight.getIntensity());
	}
	
	
	/**
	 * the new function that calc a color of a point
	 * @param geoPoint the intersection geoPoint
	 * @param r the ray that intersect
	 * @param level how many reflection rays to create
	 * @param k the discount factor of the transparency
	 * @return the calculated color
	 */
	Color calcColor(GeoPoint geoPoint, Ray r,int level, double k) {
		Geometry g = geoPoint.geometry;
		Vector v = r.getDirection();
		Point3D p = geoPoint.point;
		
		Color res = g.getEmmission();
		
		
		//
		for (LightSource l : scene.lights)
		{
			double ln = l.getL(p).dotProduct(g.getNormal(p));
			double vn = v.dotProduct(g.getNormal(p));
			
			if (ln * vn > 0)
			{
				if (unshaded(l, l.getL(p), geoPoint.geometry.getNormal(geoPoint.point), geoPoint))
				{
					
					Vector specularV = l.getL(p)
							.subtract(g.getNormal(p).scale(2 * l.getL(p).dotProduct(g.getNormal(p))));
					double vrShininess = Math.pow(v.scale(-1).dotProduct(specularV), g.getMaterial().nShininess);
					
					Color diffuse = l.getIntensity(p).scale(g.getMaterial().Kd * Math.abs(ln));
					Color specular = l.getIntensity(p).scale(g.getMaterial().Ks * vrShininess);
					
					res = res.add(diffuse).add(specular);
				}
			}
		}
		
		return 1 == level ? res : res.add(calcGlobalEffects(geoPoint, r.getDirection(), level, k));
	}
	
	
	/**
	 * this func add to the color the reflection and transparency effect.
	 * @param gp the intersection geoPoint
	 * @param v direction vector of the intersected ray.
	 * @param level how many reflection rays to create
	 * @param k the discount factor of the transparency
	 * @return the calculated color
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
		Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		double kkr = k * material.kR;
		if (kkr > MIN_CALC_COLOR_K)
			color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
		double kkt = k * material.kT;
		if (kkt > MIN_CALC_COLOR_K)
			color = color.add(
					calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
		return color;
	}
	
	
	/**
	 * calculate the color of the reflection/transparency(etc) ray
	 * @param ray the reflection/transparency ray
	 * @param level the number of the additional reflection and transparency rays that can be added.
	 * @param kx the discount factor of the calculated color
	 * @param kkx the discount factor of the reflection or the transparency color
	 * @return the calculated color
	 */
	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
		GeoPoint gp = findClosestIntersection (ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
	}
	
	
	/**
	 * check if the point is un shaded
	 *
	 * @param light    the light source
	 * @param l        the light direction to the point
	 * @param n        the normal in the point
	 * @param geopoint the geopoint
	 * @return true if the point is unshaded
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		
		Ray lightRay = new Ray(geopoint.point, lightDirection,n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		
		//if there arent points the point is unshaded
		if (intersections == null) return true;
		
		double lightDistance = light.getDistance(geopoint.point);
		
		
		for (GeoPoint gp : intersections)
		{
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0&&
					gp.geometry.getMaterial().kT == 0)//check if the geometry isn't transparency
					return false;
		}
		
		return true;
	}
	
	/**
	 * find the closest intersection to the geometries
	 *
	 * @param ray the ray that intersect
	 * @return the closest intersection point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> l = scene.geometries.findGeoIntersections(ray);
		
		if (l == null) return null;
		
		return ray.findClosestGeoPoint(l);
		
	}
	
	/**
	 * return a reflected ray from a point  and a vector
	 *
	 * @param v the vector of the ray
	 * @param n the normal in the point p
	 * @param p the intersection point
	 * @return a reflection ray with the head of p
	 */
	private Ray constructReflectedRay( Point3D p,Vector v, Vector n) {
		Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
		
		return new Ray(p, r,n);
	}
	
	/**
	 * create a refraction ray from point and a vector
	 * @param v the vector to the point
	 * @param p the point to create
	 * @return a refraction ray
	 */
	private Ray constructRefractedRay(Point3D p, Vector v, Vector n){
		return new Ray(p,v,n);
	}
}
