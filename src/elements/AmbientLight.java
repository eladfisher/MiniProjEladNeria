package elements;

import primitives.*;

/**
 * a class that represents an ambient light for a scene
 */
public class AmbientLight {
	Color iA;
	double kA;
	
	/**
	 * ctor for the ambient light
	 * @param iA the color intensity
	 * @param kA
	 */
	public AmbientLight(Color iA, double kA) {
		this.iA = iA;
		this.kA = kA;
	}
	
	/**
	 * the getter for the intencity of the ambient light
	 * @return
	 */
	public Color getiA() {
		return iA;
	}
}
