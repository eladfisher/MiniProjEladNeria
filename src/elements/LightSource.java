package elements;

import primitives.*;

/**
 * interface that contains all of the lights that have a direction.
 * (like the directional light, unlike the ambient light).
 * This type of light in the Phong model can affect different places in different ways,
 * so its getters get the point in question.
 */
public interface LightSource {
	
	/**
	 * getter for intensity.
	 * @param p the point in question.
	 * @return the intensity of the light source in this point.
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * getter for the direction. we need the direction of the light source,
	 * because the light source affects in different ways
	 * depending on the direction in which it hits the Geometry on which the point resides.
	 * @param p the point in question.
	 * @return the direction of the light source in this point.
	 */
	public Vector getL(Point3D p);

}
