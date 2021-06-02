package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * a interface of Geometry for a 3D graphic model
 */
public abstract class Geometry implements Intersectable {
	protected Color emmission;
	private Material material;

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

	public abstract Vector getNormal(Point3D point3D);
}
