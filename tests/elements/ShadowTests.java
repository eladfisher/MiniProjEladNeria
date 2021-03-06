package elements;


import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


import geometries.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */

public class ShadowTests {
	/**
	 * the scene for the tests in this class
	 */
	private Scene scene = new Scene("Test scene");
	
	/**
	 * the camera for this class tests
	 */
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVpSize(200, 200).setVpDistance(1000);
	
	/**
	 * the sphere for the tests with the shadowing
	 */
	Sphere sphereTest = (Sphere) new Sphere(60, new Point3D(0, 0, -200)) //
			.setEmission(new Color(java.awt.Color.BLUE)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));//
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add( //
							  sphereTest, //
							  new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))//
		);
		
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
								  .setKl(1E-5).setKq(1.5E-7));

		
		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleCenter() {
		Vector v = new Vector(7,7,7);
		
		scene.geometries.add( //
							  sphereTest, //
							  new Triangle(new Point3D(-70, -40, 0).add(v), new Point3D(-40, -70, 0).add(v), new Point3D(-68, -68, -4).add(v)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
		);
		
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
								  .setKl(1E-5).setKq(1.5E-7));
		
		
		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleCenter", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleBig() {
		Vector v = new Vector(-10,-10,50);
		
		scene.geometries.add( //
							  sphereTest, //
							  new Triangle(new Point3D(-70, -40, 0).add(v), new Point3D(-40, -70, 0).add(v), new Point3D(-68, -68, -4).add(v)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
		);
		
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
								  .setKl(1E-5).setKq(1.5E-7));
		
		
		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleBig", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleHuge() {
		Vector v = new Vector(-15,-15,80);
		
		scene.geometries.add( //
							  sphereTest, //
							  new Triangle(new Point3D(-70, -40, 0).add(v), new Point3D(-40, -70, 0).add(v), new Point3D(-68, -68, -4).add(v)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
		);
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
								  .setKl(1E-5).setKq(1.5E-7));
		
		
		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleHuge", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleUp() {
		Vector v = new Vector(15,15,20);
		
		scene.geometries.add( //
							  sphereTest, //
							  new Triangle(new Point3D(-70, -40, 0).add(v), new Point3D(-40, -70, 0).add(v), new Point3D(-68, -68, -4).add(v)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
		);
		
		scene.lights.add( //
						  new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
								  .setKl(1E-5).setKq(1.5E-7));
		
		
		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowSphereTriangleUp", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
							  new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
									  .setMaterial(new Material().setKs(0.8).setShininess(60)), //
							  new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
									  .setMaterial(new Material().setKs(0.8).setShininess(60)), //
							  new Sphere(30, new Point3D(0, 0, -115)) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
		);
		scene.lights.add( //
						  new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
								  .setKl(4E-4).setKq(2E-5));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		
		
		render.renderImage();
		render.writeToImage();
	}

}
