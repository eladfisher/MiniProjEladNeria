package geometries;

import primitives.*;

/**
 * a interface of Geometry for a 3D graphic model
 */
public abstract class Geometry extends Intersectable {
	/**
	 * every geometry in the universe have a color, called emission.
	 * in the test we get we saw that they called it emmission so we did that too:)
	 */
	protected Color emmission;
	/**
	 * the material that tell us how the Geometry Color depend on external light sources and other geometries.
	 */
	private Material material;
	
	
	/**
	 * Constructor for every geometry that set the color to be black and the material to the default.
	 */
	public Geometry() {
		emmission = Color.BLACK;
		material = new Material();
	}

	/**
	 * the getter for the material field
	 * @return the material field
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * setter for the material
	 * @param material the new material
	 * @return the geometry for chaining method
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
	

	/**
	 * getter for the emission light
	 * @return the emmision color
	 */
	public Color getEmmission() {
		return emmission;
	}

	/**
	 * setter for the emission field
	 * @param emmission the new emmision
	 * @return the same instance for chaining method
	 */
	public Geometry setEmission(Color emmission) {
		this.emmission = emmission;
		return this;
	}

	/**
	 * abstract method that every geometry need, method that return the normal of a point on the geometry.
	 * we need it for the light effects on the geometry.
	 *
	 * @param point3D point on the geometry.
	 * @return the normal to the geometry in this point.
	 */
	public abstract Vector getNormal(Point3D point3D);
	
	
}
