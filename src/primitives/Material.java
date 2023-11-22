package primitives;

/**
 * a class that represents a material for the geometries.
 *
 * material affect on:
 * -the shininess of the material that affect on the specular light
 * -the transparency and reflection of the light
 */
public class Material {
    public static Material BASIC = new Material().setKd(1);
    /**
     * the kd discount factor
     */
    public double Kd ;
    
    /**
     * the ks discount factor
     */
    public double    Ks;
    
    /**
     * the kt discount factor
     */
    public double kT ;
    
    /**
     * the kr discount factor
     */
    public double   kR;
    
    /**
     * the shaininess level of the material
     */
    public int nShininess;

    /**
     *a ctor that initialized the fields of the material
     */
    public Material() {
    
        
        Kd = 0;
    
        
        Ks = 0;

       
        kT=0;

        
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
     * setter for the Kd discount factor
     * @param kd the new Kd discount factor
     * @return the same instance for chaining method
     */
    public Material setKd(double kd) {
        Kd = kd;
        return this;
    }

    /**
     * setter for the Ks discount factor
     * @param ks the new Ks discount factor
     * @return the same instance for chaining method
     */
    public Material setKs(double ks) {
        Ks = ks;
        return this;
    }

    /**
     * setter of the shininess value
     * @param nShininess the new shininess value
     * @return the same instance for chaining method
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
