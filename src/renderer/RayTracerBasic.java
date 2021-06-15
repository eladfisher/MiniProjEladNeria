package renderer;


import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * a implementation of the basic class ray tracer basic
 */
public class RayTracerBasic extends RayTracerBase {

	//region Constants
	/**
	 * the amount of times that we calc the global effect depth of the recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	
	/**
	 * the initial discount factor for global effects
	 */
	private static final double INITIAL_K = 1.0;
	
	/**
	 * the min discount factor value to add the color
	 */
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

	//region color calculations
	/**
	 * calc the color of a specific point
	 *
	 * @param geopoint the point
	 * @param ray      the ray from the camera to the pixel
	 * @return the color of p
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) {
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
				.add(scene.ambientLight.getIntensity());
	}

	/**
	 *calc color based on local effects of diffusive and specular lights
	 * @param intersection the intersection point
	 * @param ray the intersected ray
	 * @param k the discount factor of the color
	 * @return  the calculated color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray,double k) {
		Vector v = ray.getDirection();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0) return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		double kd = material.Kd, ks = material.Ks;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights)
		{
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0)
			{
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(material.Kd, l, n, lightIntensity),
							calcSpecular(material.Ks, l, n, v, nShininess, lightIntensity));
				}

			}
		}
		return color;
	}

	/**
	 * calc the specular color of a certain point and a light source
	 * @param ks the discount factor of the material
	 * @param l the L vector from the light source
	 * @param n the normal in the point
	 * @param v the direction vector of the intersect ray
	 * @param nShininess the shininess level of the geometry
	 * @param lightIntensity the light intensity
	 * @return the specular color of the point
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {

		Vector specularV = l.subtract(n.scale(2 * l.dotProduct(n)));
		double vrShininess = Math.pow(v.scale(-1).dotProduct(specularV), nShininess);

		return lightIntensity.scale(ks * vrShininess);
	}

	/**
	 * calc the diffusive color of a certain point and a light source
	 * @param kd the discount factor of the material
	 * @param l the L vector from the light source
	 * @param n the normal in the point
	 * @param lightIntensity the light intensity
	 * @return the diffusive color of the color
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		double ln = l.dotProduct(n);
		return lightIntensity.scale(kd * Math.abs(ln));
	}

	/**
	 * the new function that calc a color of a point
	 *
	 * @param intersection the intersection geoPoint
	 * @param ray          the ray that intersect
	 * @param level        how many reflection rays to create
	 * @param k            the discount factor of the transparency
	 * @return the calculated color
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
		Color color = intersection.geometry.getEmmission();
		color = color.add(calcLocalEffects(intersection, ray, k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));
	}


	/**
	 * this func add to the color the reflection and transparency effect.
	 *
	 * @param gp    the intersection geoPoint
	 * @param v     direction vector of the intersected ray.
	 * @param level how many reflection rays to create
	 * @param k     the discount factor of the transparency
	 * @return the calculated color
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
		Color color = Color.BLACK;
		Vector n = gp.geometry.getNormal(gp.point);
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
	 *
	 * @param ray   the reflection/transparency ray
	 * @param level the number of the additional reflection and transparency rays that can be added.
	 * @param kx    the discount factor of the calculated color
	 * @param kkx   the discount factor of the reflection or the transparency color
	 * @return the calculated color
	 */
	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
	}
	//endregion

	//region shaded
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

		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

		//if there arent points the point is unshaded
		if (intersections == null) return true;

		double lightDistance = light.getDistance(geopoint.point);


		for (GeoPoint gp : intersections)
		{
			if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
					gp.geometry.getMaterial().kT == 0)//check if the geometry isn't transparency
				return false;
		}

		return true;
	}

	/**
	 * calc the transparency level of the point
	 *
	 * @param light    the light source
	 * @param l        the light direction to the point
	 * @param n        the normal in the point
	 * @param geopoint the geopoint
	 * @return the transparency discount factor of the material
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {

		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);
		double lightDistance = light.getDistance(geopoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay,lightDistance);
		if (intersections == null) return 1.0;
		double ktr = 1.0;
		for (GeoPoint gp : intersections)
		{

			ktr *= gp.geometry.getMaterial().kT;
			if (ktr < MIN_CALC_COLOR_K) return 0.0;

		}
		return ktr;
	}
	//endregion

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

	//region reflected ray

	/**
	 * return a reflected ray from a point  and a vector
	 *
	 * @param v the vector of the ray
	 * @param n the normal in the point p
	 * @param p the intersection point
	 * @return a reflection ray with the head of p
	 */
	private Ray constructReflectedRay(Point3D p, Vector v, Vector n) {
		Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));

		return new Ray(p, r, n);
	}

	//endregion

	//region refracted ray
	/**
	 * create a refraction ray from point and a vector
	 *
	 * @param v the vector to the point
	 * @param p the point to create
	 * @param n the normal vector at the point
	 * @return a refraction ray
	 */
	private Ray constructRefractedRay(Point3D p, Vector v, Vector n) {
		return new Ray(p, v, n);
	}
	//endregion

}
