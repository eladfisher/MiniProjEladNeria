package primitives;

/**
 * a class that represents a material for the geometries.
 *
 * material affect on:
 * -the shininess of the material that affect on the specular light
 * -the transparency and reflection of the light
 */
public class Material {
	public double Kd, Ks;
	public int nShininess;
	
	/**
	 *a ctor that initialized the fields of the material
	 */
	public Material() {
		Kd = 0; Ks = 0;
		nShininess = 0;
	}
	
	/**
	 *
	 * @param kd
	 * @return
	 */
	public Material setKd(double kd) {
		Kd = kd;
		return this;
	}
	
	/**
	 *
	 * @param ks
	 * @return
	 */
	public Material setKs(double ks) {
		Ks = ks;
		return this;
	}
	
	/**
	 *
	 * @param nShininess
	 * @return
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
}
