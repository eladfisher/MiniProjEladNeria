package primitives;

/**
 *
 */
public class Material {
	public double Kd, Ks;
	int nShininess;
	
	/**
	 *
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
