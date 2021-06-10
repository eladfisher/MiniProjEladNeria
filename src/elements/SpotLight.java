package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Spot light source:
 * - One-directional light source modeling (like a light bulb)
 * - Power (I0)
 * - A specific position in a scene at a point (pz, py, px).
 * - This lighting have a uniform direction.
 * [Like a projector. The direction of the light is not uniform to every point in the room, but it very centralized]
 * (as in the sun, no matter where you are in the room the direction of the sun is the same direction),
 * but the direction in which the light hits each object in the room varies with the lamp.]
 * - There are attenuation factors Kq, KJ, Kc with the distance d
 * (i.e. there is a thinning of the light due to the distance, but less than point light).
 */
public class SpotLight extends PointLight {
	
	private Vector direction;
	
	/**
	 * constructor that gets the basic parameters (position and intensity)
	 * and sets attenuation factors for no depending on distance.
	 *
	 * @param intensity the intensity of the light source
	 * @param position  the position of the light source
	 * @param direction the direction of the spot light
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalized();
	}
	
	/**
	 * setter for the quad attenuation factor, that doesn't depend the distance.
	 *
	 * @param p the wanted point to calc intensity
	 * @return the intensity of the color in the param point
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double product = Math.max(0, direction.dotProduct(getL(p)));
		
		return super.getIntensity(p).scale(product);
	}
}
