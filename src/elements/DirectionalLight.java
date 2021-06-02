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
	 * Builds a directional light object given its position and direction.
	 * @param intensity Light's intensity(color)
	 * @param direction Light's position
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction;
	}

	@Override
	public Color getIntensity(Point3D p) {
		return intensity;
	}

	@Override
	public Vector getL(Point3D p) {
		return direction.normalize();
	}

	/**
	 *
	 * @param point
	 * @return
	 */
	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
}
