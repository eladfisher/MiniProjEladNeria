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
	 *
	 * @return
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 *
	 * @param material
	 * @return
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
	
	/**
	 * @return
	 */
	public Color getEmmission() {
		return emmission;
	}
	
	/**
	 * @param emmission
	 * @return
	 */
	public Geometry setEmission(Color emmission) {
		this.emmission = emmission;
		return this;
	}
	
	public abstract Vector getNormal(Point3D point3D);
}
