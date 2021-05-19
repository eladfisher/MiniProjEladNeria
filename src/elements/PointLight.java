package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public class PointLight extends Light implements LightSource {
	
	private Point3D position;
	private double Kc, Kl, Kq;
	
	/**
	 * @param intensity
	 * @param position
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
	 * @param kc
	 * @return
	 */
	public PointLight setKc(double kc) {
		Kc = kc;
		return this;
	}
	
	/**
	 * @param kl
	 * @return
	 */
	public PointLight setKl(double kl) {
		Kl = kl;
		return this;
	}
	
	/**
	 * @param kq
	 * @return
	 */
	public PointLight setKq(double kq) {
		Kq = kq;
		return this;
	}
	//endregion
	
	/**
	 * @param p
	 * @return
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
	 * @param p
	 * @return
	 */
	@Override
	public Vector getL(Point3D p) {
		return p.subtract(position).normalize();
	}
}
