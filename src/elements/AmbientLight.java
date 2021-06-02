package elements;

import primitives.*;

/**
 * a class that represents an ambient light for a scene, extends the class Light.
 * The source of ambient lighting is represented by a light source that is constant in intensity and constant in color,
 * and affects all objects in the scene equally.
 *
 * kA - a constant that is the exclusion factor. If you want to reduce the light, you can multiply by kA,
 * which is a fraction, and thus reduce the lighting.
 */
public class AmbientLight extends Light {

	/**
	 * ctor for the ambient light which gets parameters.
	 * @param iA the color intensity
	 * @param kA the exclusion factor, in order to reduce the light. between 0 (dark) to 1 (the original intensity).
	 */
	public AmbientLight(Color iA, double kA) {
		super(  iA.scale(kA));
	}

	/**
	 * default ctor that make the light black (= no ambient light).
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}


}
