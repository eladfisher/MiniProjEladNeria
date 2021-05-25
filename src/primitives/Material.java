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
	
	public double kT,kR;
	
	public int nShininess;
	
	/**
	 *a ctor that initialized the fields of the material
	 */
	public Material() {
		Kd = 0; Ks = 0;
		
		/**
		 *
		 */
		kT=0;
		
		/**
		 *
		 */
		kR=0;
		
		
		nShininess = 0;
	}
	
	/**
	 *  setter for kt
	 * @param kT the new value for kt
	 * @return the same instance for chaining method
	 */
	public Material setKt(double kT) {
		this.kT = kT;
		return this;
	}
	
	/**
	 * setter for kR
	 * @param kR the new value for kR
	 * @return the same instance for chaining method
	 */
	public Material setKr(double kR) {
		this.kR = kR;
		return this;
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
