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
 * tests  for the picture improvements for MP1
 */
public class MP1Tests {
	
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
	public void SuperSamplingTest() {
		Scene scene1 = new Scene("AntiAliasing");
		Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000);
		
		Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000).setRaysSampling(4);
		
		Camera camera3 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000).setRaysSampling(36);
		
		Geometry sphere1 = new Sphere(25,new Point3D(10, -10, -100)) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
		
		Geometry sphere2 = new Sphere(25, new Point3D(0, 30, -50)) //
				.setEmission(new Color(java.awt.Color.BLUE)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
		
		scene1.geometries.add(sphere1, sphere2);
		scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
		
		ImageWriter imageWriter1 = new ImageWriter("withoutAntiAliasing", 500, 500);
		Render render1 = new Render()//
				.setImageWriter(imageWriter1) //
				.setCamera(camera1) //
				.setRayTracer(new RayTracerBasic(scene1));
		render1.renderImage();
		render1.writeToImage();
		
		
		ImageWriter imageWriter2 = new ImageWriter("with4AntiAliasing", 500, 500);
		Render render2 = new Render()//
				.setImageWriter(imageWriter2) //
				.setCamera(camera2) //
				.setRayTracer(new RayTracerBasic(scene1));
		render2.renderImage();
		render2.writeToImage();
		
		ImageWriter imageWriter3 = new ImageWriter("with64AntiAliasing", 500, 500);
		Render render3 = new Render()//
				.setImageWriter(imageWriter3) //
				.setCamera(camera3.setSamplingDepth(64)) //
				.setRayTracer(new RayTracerBasic(scene1));
		render3.renderImage();
		render3.writeToImage();
	}
	
	
	/**
	 * build an image of a house and sun with very smooth and reflective ground
	 */
	@Test
	public void WOW_ImageTest() {
		Scene scene = new Scene("final scene");
		
		//region House
		double d = 20;
		
		//region walls
		Material wall_m = new Material().setKd(1).setKs(1).setShininess(30).setKt(0);
		Color wall_c = new Color(java.awt.Color.gray);
		
		Polygon r_wall = (Polygon) new Polygon(
				new Point3D(d * 2, d * 0, d * 1),
				new Point3D(d * 2, d * 0, d * -2),
				new Point3D(d * 2, d * 4, d * -2),
				new Point3D(d * 2, d * 4, d * 1)
		).setEmission(wall_c)
				.setMaterial(wall_m);
		
		Polygon b_wall = (Polygon) new Polygon(
				new Point3D(d * -2, d * 0, d * -2),
				new Point3D(d * 2, d * 0, d * -2),
				new Point3D(d * 2, d * 4, d * -2),
				new Point3D(d * 0, d * 6, d * -2),
				new Point3D(d * -2, d * 4, d * -2)
		).setEmission(wall_c)
				.setMaterial(wall_m);
		
		Polygon l_wall = (Polygon) new Polygon(
				new Point3D(d * -2, d * 0, d * 1),
				new Point3D(d * -2, d * 0, d * -2),
				new Point3D(d * -2, d * 4, d * -2),
				new Point3D(d * -2, d * 4, d * 1)
		).setEmission(wall_c)
				.setMaterial(wall_m);
		
		Geometries f_wall = new Geometries();
		Material wall_f = new Material().setKd(0.1).setKs(1).setShininess(200).setKt(0).setKr(0.5);
		
		
		Polygon f_wallLeft = (Polygon) new Polygon(
				new Point3D(d * -2, d * 0, d * 1),
				new Point3D(d * -0.7, d * 0, d * 1),
				new Point3D(d * -0.7, d * 2, d * 1),
				new Point3D(d * -2, d * 2, d * 1)
		).setEmission(wall_c)
				.setMaterial(wall_f);
		
		Polygon f_wallRight = (Polygon) new Polygon(
				new Point3D(d * 2, d * 0, d * 1),
				new Point3D(d * 2, d * 2, d * 1),
				
				new Point3D(d * 0.7, d * 2, d * 1),
				new Point3D(d * 0.7, d * 0, d * 1)
		).setEmission(wall_c)
				.setMaterial(wall_f);
		
		Polygon f_wallUp = (Polygon) new Polygon(
				new Point3D(d * -2, d * 2, d * 1),
				new Point3D(d * 2, d * 2, d * 1),
				new Point3D(d * 2, d * 4, d * 1),
				new Point3D(d * 0, d * 6, d * 1),
				new Point3D(d * -2, d * 4, d * 1)
		).setEmission(wall_c)
				.setMaterial(wall_f);
		
		f_wall.add(f_wallLeft,f_wallUp,f_wallRight);
		
		
		//endregion
		
		//region roof
		Material roof_m = new Material().setKd(0.4).setKs(0.1).setShininess(10).setKt(0);
		Color roof_c = new Color(java.awt.Color.RED);
		Polygon r_roof = (Polygon) new Polygon(
				new Point3D(d * 0, d * 6, d * 1.1),
				new Point3D(d * 0, d * 6, d * -2.1),
				new Point3D(d * 2.5, d * 3.5, d * -2.1),
				new Point3D(d * 2.5, d * 3.5, d * 1.1)
		).setEmission(roof_c)
				.setMaterial(roof_m);
		
		Polygon l_roof = (Polygon) new Polygon(
				new Point3D(d * 0, d * 6, d * 1.1),
				new Point3D(d * 0, d * 6, d * -2.1),
				new Point3D(d * -2.5, d * 3.5, d * -2.1),
				new Point3D(d * -2.5, d * 3.5, d * 1.1)
		).setEmission(roof_c)
				.setMaterial(roof_m);
		
		scene.lights.add(new PointLight(new Color(700,400,250),new Point3D(0,100,0)).setKl(0.0001).setKc(1).setKq(0.0002));
		//endregion
		
		//region door
		
		Material doorM = new Material().setKd(0.05).setKs(0).setShininess(30).setKt(0.5).setKr(0);
		Polygon door = (Polygon) new Polygon(
				new Point3D(d * -0.7, d * 0, d * 1.005),
				new Point3D(d * 0.7, d * 0, d * 1.005),
				new Point3D(d * 0.7, d * 2, d * 1.005),
				new Point3D(d * -0.7, d * 2, d * 1.005)
		).setEmission(new Color(139, 69, 19))
				.setMaterial(doorM);
		//endregion
		//endregion
		
		//region Ground
		Material ground_m = new Material().setKd(0.4).setKs(0).setShininess(0).setKt(0).setKr(0.3);
		Color ground_c = new Color(java.awt.Color.green).scale(0.25);
		Plane ground = (Plane) new Plane(new Vector(0, 1, 0), new Point3D(0, 0, 0))
				.setMaterial(ground_m)
				.setEmission(ground_c);
		//endregion
		
		//region Pyramid
		Point3D pyramid1Head = new Point3D(-120,150,90);
		
		Material pyramidMaterial = new Material().setKd(0.5).setKs(0.001).setShininess(30).setKt(0.6);
		Geometries pyramid1 = PyramidMaker(pyramid1Head,new Point3D(-50,0,20),new Color(255,215,0).scale(0.5),pyramidMaterial);
		
		Sphere sphereInPyramid1 = (Sphere) new Sphere(20, pyramid1Head.add(new Vector(0,-50,0))) //
				.setEmission(new Color(java.awt.Color.CYAN)) //
				.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(40).setKt(0.65));
		
		scene.geometries.add(pyramid1) ;
		scene.geometries.add(sphereInPyramid1);
		scene.lights.add(new PointLight(new Color(700, 400, 400).scale(1),pyramid1Head.add(new Vector(0,-20,0))).setKl(4E-5).setKq(0.00001));
//		//scene.lights.add(new SpotLight(new Color(700, 400, 400).scale(1),pyramid1Head.add(new Vector(0,-30,0)),new Vector(0,-1,0)).setKl(4E-5).setKq(2E-7));
		
		
		//endregion
		
		
		//region Path itself
		Material materialPath = new Material().setKd(0.1);
		Color emissionPath = new Color(101,67,33);
		Box pathFromHouse = (Box) new Box(new Point3D(d * -0.7, d * 0, d * 1.005),1.4*d,2.5,150).setEmission(emissionPath).setMaterial(materialPath);
		Box pathOrthogonal = (Box) new Box(new Point3D(d * -0.7, d * 0, d * 1.005+136),new Vector(0,0,28),new Vector(0,2.5,0),new Vector(1000,0,0)).setEmission(emissionPath).setMaterial(materialPath);
		scene.geometries.add(pathFromHouse,pathOrthogonal);
		//endregion
		
		//region lamps
		//new Point3D(d * -0.7+x, d * 0, d * 1.005+136)
		Material materialLampsSpheres = new Material().setKd(0.33).setKs(1).setShininess(30).setKt(0.7);
		Material materialLampsCylinder = new Material().setKd(1).setKs(0.6).setShininess(30).setKt(0);
		Color emissionLampSphere = new Color(0,0,200);
		
		scene.geometries.add( new Box(new Point3D(d * 0.7, 0, d * 1.005),new Vector(0,0,120+1*d),new Vector(0,6,0),new Vector(1000,0,0))
		.setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(60)));
//		scene.geometries.add(new Polygon(
//				new Point3D(d * -0.7, 5, d * 1.005+136)
//				,new Point3D(d * -0.7, 5, d * 1.005)
//				,new Point3D(d * -0.7+400, 5, d * 1.005)
//				,new Point3D(d * -0.7+400, 5, d * 1.005+136)
//							 ).setEmission(new Color(0,100,0)).setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(60)));

		
		for(int i = 50; i<400;i+=100){
			
			scene.geometries.add(new Sphere(12,new Point3D(d * -0.7+i, 30, d * 1.005+100)).setEmission(emissionLampSphere).setMaterial(materialLampsSpheres));
			scene.geometries.add(new Cylinder(new Tube(new Ray(new Point3D(d * -0.7+i, 0, d * 1.005+100),new Vector(0,1,0)),2),18).setEmission(Color.BLACK).setMaterial(materialLampsCylinder));
			scene.lights.add( new SpotLight( new Color(800,0,400),new Point3D(d * -0.7+i, 48, d * 1.005+100),new Vector(0,-1,0)).setKc(1).setKq(0.0007).setKl(0.00015));
		}
		
		//endregion
		
		Camera coolCamera = new Camera(new Point3D(1000, 130, 1000), new Vector(-1000, -100, -1000), new Vector(-1, 20, -1));
		
		coolCamera.MoveCamera(new Point3D(1000, 160, 1000), new Point3D(-15,50,0),0); //
				coolCamera.setVpSize(28, 28).setVpDistance(100);
				
				
		//region scene build
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		scene.setBackground(new Color(java.awt.Color.CYAN).scale(0.7));
		scene.geometries.add(
				r_wall,
				l_wall,
				b_wall,
				f_wall,
				door,
				r_roof, l_roof,
			ground
		);
		
		scene.lights.add(new DirectionalLight(
				new Color(java.awt.Color.WHITE).scale(0.1),
				new Vector(1, -1, -1)
		));
		//endregion
		
		ImageWriter imageWriter = new ImageWriter("final image Little House on the Prairie", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(coolCamera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		render.renderImage();
		render.writeToImage();
		
//		coolCamera.setRaysSampling(900);
//
//		imageWriter = new ImageWriter("final image with ray sampling 30", 600, 600);
//		render = new Render() //
//				.setImageWriter(imageWriter) //
//				.setCamera(coolCamera) //
//				.setRayTracer(new RayTracerBasic(scene));
//
//		render.renderImage();
//		render.writeToImage();
//
		
//		Camera DOFCamera = new Camera(new Point3D(1000, 130, 1000), new Vector(-1000, -100, -1000), new Vector(-1, 20, -1));
//
//		DOFCamera.MoveCamera(new Point3D(1000, 160, 1000), new Point3D(-15,50,0),0); //
//		DOFCamera.setVpSize(28, 28).setVpDistance(100);
//		DOFCamera.setSamplingDepth(4*4).setFocalPlaneDist(new Point3D(1000, 130, 1000).distance(Point3D.ZERO)).setApertureSize(13);
//
//		imageWriter = new ImageWriter("final image with DOF", 600, 600);
//		render = new Render() //
//				.setImageWriter(imageWriter) //
//				.setCamera(DOFCamera) //
//				.setRayTracer(new RayTracerBasic(scene));
//
//		render.renderImage();
//		render.writeToImage();
	}
	
	/**
	 * create a pyramid by 4 corners and head
	 * @param head the head point
	 * @param lu the left up point
	 * @param ld the left down point
	 * @param rd the right down point
	 * @param ru the right up
	 * @param emission the emission of the pyramid
	 * @param material the material of the pyramid
	 * @return a Geometries object of the pyramid
	 */
	public static Geometries PyramidMaker(Point3D head,Point3D lu,Point3D ld, Point3D rd, Point3D ru,Color emission, Material material){
		Geometries res = new Geometries(new Triangle(head,lu,ld).setEmission(emission).setMaterial(material),
										new Triangle(head,ld,rd).setEmission(emission).setMaterial(material),
										new Triangle(rd,ru,head).setEmission(emission).setMaterial(material),
										new Triangle(ru,lu,head).setEmission(emission).setMaterial(material));
		
		return res;
	}
	
	/**
	 * TODO
	 * @param head
	 * @param lu
	 * @param emission
	 * @param material
	 * @return
	 */
	public static Geometries PyramidMaker(Point3D head,Point3D lu, Color emission, Material material){
		Point3D centerG = new Point3D(head.getX(),lu.getY(),head.getZ());
		Vector rd_v = centerG.subtract(lu);
		Point3D rd = centerG.add(rd_v);
		Point3D ld = new Point3D(lu.getX(),centerG.getY(),rd.getZ());
		Point3D ru = new Point3D(rd.getX(),centerG.getY(),lu.getZ());
		
		
		Geometries res = new Geometries(new Triangle(head,lu,ld).setEmission(emission).setMaterial(material),
										new Triangle(head,ld,rd).setEmission(emission).setMaterial(material),
										new Triangle(rd,ru,head).setEmission(emission).setMaterial(material),
										new Triangle(ru,lu,head).setEmission(emission).setMaterial(material));
		
		return res;
	}
}
