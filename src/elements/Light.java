package elements;

import primitives.Color;

/**
 * the class Light is an abstract class of light sources in our image.
 * It contains the field intensity that means the intensity and the color of the light.
 */
abstract class Light {
	protected Color intensity;

	/**
	 * this is an abstract constructors which determines once and for all what the color of the light source is.
	 * @param intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * this function is the only way for any other class (include the inheritance ones) to see the Color.
	 * @return the intensity.
	 */
	public Color getIntensity() {
		return intensity;
	}
}
