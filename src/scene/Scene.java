package scene;

import elements.*;
import geometries.*;
import primitives.Color;

import java.util.*;

/**
 * full pdo object that represents a scene with geometries in the 3D world
 */
public class Scene {
	/**
	 * the scene name
	 */
	public String name;
	
	/**
	 * the scene background color
	 */
	public Color background;
	
	/**
	 * the scene ambient light
	 */
	public AmbientLight ambientLight;
	
	/**
	 * the scene geometries list
	 */
	public Geometries geometries;
	
	/**
	 * the scene lights list
	 */
	public List<LightSource> lights;

	/**
	 * ctor that gets the name of the scene and initialized the scene
	 * with black background and a new list of geometries
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
		lights = new LinkedList<LightSource>();
		background = Color.BLACK;
		geometries = new Geometries();
		ambientLight = new AmbientLight(Color.BLACK,0);
	}

	//region Chain Setters
	/**
	 * setter for the background color of the scene
	 * @param background the new
	 * @return the same instance for chaining method
	 */
	public Scene setBackground(Color background) {
		this.background = background;

		return this;
	}

	/**
	 * setter for the ambient light of the scene
	 * @param ambientLight  the new ambient light of the scene
	 * @return the same instance for chaining method
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;

		return this;
	}

	/**
	 * setter for the geometries of the scene
	 * @param geometries the new geometries of the scene
	 * @return the same instance for chaining method
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;

		return this;
	}

	/**
	 * setter for the lights of the scene
	 * @param lights the new lights of the scene
	 * @return the same instance for chaining method
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}

	//endregion
}
