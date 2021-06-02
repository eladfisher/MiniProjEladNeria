package renderer;

import primitives.*;
import primitives.Ray;
import scene.Scene;

/**
 * an abstract class for the ray tracer that support with the action(s) of:
 * a.trace ray
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * a ctor that gets a scene
	 * @param scene the param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * return the color of the pixel that gets by the intersection of the ray and the scene
	 * @param ray the ray that intersect the scene
	 * @return the color of the intersection point
	 */
	abstract Color traceRay(Ray ray);
}
