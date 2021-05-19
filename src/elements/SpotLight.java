package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public class SpotLight extends  PointLight{
	
	private Vector direction;
	
	/**
	 *
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}
	
	/**
	 *
	 * @param p
	 * @return
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double product = Math.max(0,direction.dotProduct(getL(p)));
		
		return super.getIntensity(p).scale(product);
	}
}
