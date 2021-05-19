package elements;

import primitives.*;

/**
 *
 */
public interface LightSource {
	
	/**
	 *
	 * @param p
	 * @return
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 *
	 * @param p
	 * @return
	 */
	public Vector getL(Point3D p);

}
