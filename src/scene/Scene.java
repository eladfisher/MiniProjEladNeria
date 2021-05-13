package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * full pdo object that represents a scene with geometries in the 3D world
 */
public class Scene {
	public String name;
	public Color background;
	public AmbientLight ambientLight;
	public Geometries geometries;
	
	/**
	 * ctor that gets the name of the scene and initialized the scene
	 * with black background and a new list of geometries
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
		
		background = Color.BLACK;
		geometries = new Geometries();
	}
	
	//region Chain Setters
	/**
	 *
	 * @param background
	 * @return
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		
		return this;
	}
	
	/**
	 *
	 * @param ambientLight
	 * @return
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		
		return this;
	}
	
	/**
	 *
	 * @param geometries
	 * @return
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		
		return this;
	}
	
	//endregion
}
