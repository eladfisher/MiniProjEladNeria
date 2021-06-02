package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * point light source:
 *   - Multi-directional light source modeling (like a light bulb)
 *   - Power (I0)
 *   - A specific position in a scene at a point (pz, py, px).
 *   - This lighting does not have a uniform direction, but at every point the lamp shines on it.
 *     [Like a normal light bulb. The direction of the light is not uniform to every point in the room
 *      (as in the sun, no matter where you are in the room the direction of the sun is the same direction),
 *      but the direction in which the light hits each object in the room varies with the lamp.]
 *   - There are attenuation factors Kq, KJ, Kc with the distance d
 *     (i.e. there is a thinning of the light due to the distance).
 *
 * If so, the intensity of a point light source is equal to the intensity of the thinning parts by distance.
 * ==> Il = I0 / (Kc + Kl*d + Kq*(d^2))
 */
public class PointLight extends Light implements LightSource {

	private Point3D position;
	private double Kc, Kl, Kq;

	/**
	 * constructor that gets the basic parameters (position and intensity)
	 * and sets attenuation factors for no depending on distance.
	 * @param intensity the color of the light (from the abstract class Light)
	 * @param position the PointLight position in the universe (or in the room).
	 */
	public PointLight(Color intensity, Point3D position) {
		super(intensity);
		this.position = position;
		Kc = 1;
		Kl = 0;
		Kq = 0;
	}

	//region setters
	/**
	 * setter for the constant attenuation factor, that doesn't depend the distance.
	 * @param kc [1 - the original color. 0 - dark. (if it depend just in this facter)]
	 * @return this LightPoint.
	 */
	public PointLight setKc(double kc) {
		Kc = kc;
		return this;
	}

	/**
	 * setter for the l attenuation factor, that depend a little on the distance.
	 * @param kl Kl attenuation factor
	 * @return this LightPoint.
	 */
	public PointLight setKl(double kl) {
		Kl = kl;
		return this;
	}

	/**
	 * setter for the quad attenuation factor, that doesn't depend the distance.
	 * @param kq Kq attenuation factor
	 * @return this LightPoint.
	 */
	public PointLight setKq(double kq) {
		Kq = kq;
		return this;
	}
	//endregion

	/**
	 * setter for the quad attenuation factor, that doesn't depend the distance.
	 * @param p the wanted point to calc intensity
	 * @return the intensity of the color in the param point
	 */
	@Override
	public Color getIntensity(Point3D p) {

		Point3D p0 = position;
		double distance = p.distance(p0);
		double distanceSquared = p.distanceSquared(p0);

		double denominator = Kc+distance*Kl+distanceSquared*Kq;

		return getIntensity().reduce(denominator);
	}

	/**
	 * getter for the vector of the light direction
	 * @param p the point that intersect and wanted the light ray to it
	 * @return normalized vector with the light ray
	 */
	@Override
	public Vector getL(Point3D p) {
		return p.subtract(position).normalize();
	}

	/**
	 *
	 * @param point
	 * @return
	 */
	@Override
	public double getDistance(Point3D point) {
		return point.distance(position);
	}
}
