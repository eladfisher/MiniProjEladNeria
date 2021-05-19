package elements;

import primitives.*;

/**
 * a class that represents an ambient light for a scene
 */
public class AmbientLight extends Light {
	
	/**
	 * ctor for the ambient light
	 * @param iA the color intensity
	 * @param kA
	 */
	public AmbientLight(Color iA, double kA) {
		super(  iA.scale(kA));
	}
	
	/**
	 * default ctor that make the light black
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}
	
	
}
