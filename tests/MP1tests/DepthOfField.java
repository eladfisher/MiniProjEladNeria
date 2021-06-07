package MP1tests;

import geometries.*;
import org.junit.jupiter.api.Test;
import elements.*;
import primitives.*;
import primitives.Vector;
import renderer.*;
import scene.Scene;

import java.util.*;

/**
 * TODO
 */
public class DepthOfField {
	
	@Test
	public void DepthOfFieldTests()
	{
		Scene scene = new Scene("DepthOfFieldTest");
		Material sMaterial = new Material().setKd(0.8).setKs(0.5).setShininess(20);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1))
				.setLights(List.of(//new PointLight(new Color(java.awt.Color.MAGENTA),new Point3D(12.5,15,0) ).setKl(0.00001).setKq(0.000005)
						 new DirectionalLight(new Color(java.awt.Color.BLUE).scale(0.8),new Vector(0,-1,-1))
				))
		.setGeometries(new Geometries(new Geometries(new Sphere(10, new Point3D(0, 0, 0)).setEmission(new Color( java.awt.Color.RED).scale(0.6)).setMaterial(sMaterial),
													 new Sphere(15, new Point3D(30, 0, 0)).setEmission(new Color(
															 java.awt.Color.GREEN).scale(0.6)).setMaterial(sMaterial))));
		
		Camera camera = new Camera(Point3D.ZERO,new Vector(1,0,0),new Vector(0,1,1));
		
		camera.MoveCamera(new Point3D(-30,7,20),new Point3D(0,0,0),0);
		
		camera.setVpDistance(10).setVpSize(13,13);
		
		ImageWriter imageWriter = new ImageWriter("DepthOfField with spheres before",600,600);
		
		Render render = new Render()
				.setImageWriter(imageWriter)
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		render.renderImage();
		render.writeToImage();
		
		camera.setSamplingDepth(64).setFocalPlaneDist(23).setApertureSize(3);
		 imageWriter = new ImageWriter("DepthOfField with spheres after",600,600);
		
		 render = new Render()
				.setImageWriter(imageWriter)
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		render.renderImage();
		render.writeToImage();
		
		
	}
	
	
	@Test
	public void DOF() {
		Scene scene1 = new Scene("DOF");
		Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000);
		Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000).setFocalPlaneDist(50).setApertureSize(3);
		Geometry sphere1 = new Sphere(25,new Point3D(10, -10, -100)) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
		Geometry sphere2 = new Sphere(25, new Point3D(0, 30, -50)) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
		scene1.geometries.add(sphere1, sphere2);
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
		
		ImageWriter imageWriter1 = new ImageWriter("withoutDOF", 500, 500);
		Render render1 = new Render()//
				.setImageWriter(imageWriter1) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render1.renderImage();
		render1.writeToImage();
		
		ImageWriter imageWriter2 = new ImageWriter("withDOF", 500, 500);
		Render render2 = new Render()//
				.setImageWriter(imageWriter2) //
				.setCamera(camera2.setSamplingDepth(64)) //
				.setRayTracer(new RayTracerBasic(scene1));
		render2.renderImage();
		render2.writeToImage();
	}
	
	
	
	
	
	
	
	
}
