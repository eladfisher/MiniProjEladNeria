package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public class DirectionalLight extends Light implements LightSource{
	
	private Vector direction;
	
	/**
	 *
	 * @param intensity
	 * @param direction
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction;
	}
	
	/**
	 *
	 * @param p
	 * @return
	 */
	@Override
	public Color getIntensity(Point3D p) {
		return getIntensity();
	}
	
	/**
	 *
	 * @param p
	 * @return
	 */
	@Override
	public Vector getL(Point3D p) {
		return direction;
	}
}
