package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Directional light source:
 *   - A source of light that is in infinity, a very, very far away location (like the sun).
 *   - Power (I0)
 *   - Direction (dz, dy, dx) - (vector indicating the direction of projection of the light)
 *   - There is no attenuation due to distance (i.e. there is no thinning of the lighting due to the distance) If so,
 *     the power of a directional light source is simply the power.
 * it extends the abstract class Light and implements the interface LightSource,
 * when in the entire space there is the same direction and intensity.
 */
public class DirectionalLight extends Light implements LightSource{
	
	private Vector direction;
	
	/**
	 * constructor that use the abstract ctor for the intensity and set the vector in his direction parameter.
	 * @param intensity intensity, in the abstract class Light.
	 * @param direction because its DIRECTIONAL Light.
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalized();
	}
	
	/**
	 * Function  in order to know the intensity. For the interface LightSource.
	 * @param p point in the scene that we want to check how the directional light effects at her.
	 * @return the regular constant intensity from Light class, because the directional light is never changes.
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity();
	}
	
	/**
	 * Function in order to know the direction. For the interface LightSource.
	 * @param p p point in the scene that we want to check how the directional light effects at her.
	 * @return the constant direction Vector, because the directional light is never changes.
	 */
	@Override
	public Vector getL(Point3D p) {
		return direction;
	}
}
