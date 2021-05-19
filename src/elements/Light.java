package elements;

import primitives.Color;

/**
 *
 */
abstract class Light {
	private Color intensity;
	
	/**
	 *
	 * @param intensity
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}
	
	/**
	 *
	 * @return
	 */
	public Color getIntensity() {
		return intensity;
	}
}
