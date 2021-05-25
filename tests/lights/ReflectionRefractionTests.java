package lights;

import geometries.Plane;
import geometries.Polygon;
import org.junit.jupiter.api.Test;
import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(150, 150).setVpDistance(1000);

		scene.geometries.add( //
							  new Sphere( new Point3D(0, 0, -50),50) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
							  new Sphere( new Point3D(0, 0, -50),25) //
									  .setEmission(new Color(java.awt.Color.RED)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
						  new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
								  .setKl(0.0004).setKq(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(2500, 2500).setVpDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
							  new Sphere( new Point3D(-950, -900, -1000),400) //
									  .setEmission(new Color(0, 0, 100)) //
									  .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
							  new Sphere( new Point3D(-950, -900, -1000),200) //
									  .setEmission(new Color(100, 20, 20)) //
									  .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
							  new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
										   new Point3D(670, 670, 3000)) //
									  .setEmission(new Color(20, 20, 20)) //
									  .setMaterial(new Material().setKr(1)),
							  new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
										   new Point3D(-1500, -1500, -2000)) //
									  .setEmission(new Color(20, 20, 20)) //
									  .setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
								 .setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(200, 200).setVpDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
							  new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
							  new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
									  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
							  new Sphere( new Point3D(60, 50, -50),30) //
									  .setEmission(new Color(java.awt.Color.BLUE)) //
									  .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
								 .setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void WOW_ImageTest()
	{
		Material wall_m = new Material().setKd(0).setKs(0).setShininess(30).setKt(0);
		Color wall_c = new Color(java.awt.Color.gray);
		Polygon r_wall = (Polygon) new Polygon(
				new Point3D(2,0,1),
				new Point3D(2,0,-2),
				new Point3D(2,4,-2),
				new Point3D(2,4,1)
		).setEmission(wall_c)
		.setMaterial(wall_m);
		Polygon b_wall = (Polygon) new Polygon(
				new Point3D(-2,	0,-2),
				new Point3D(2,	0,-2),
				new Point3D(2,	4,-2),
				new Point3D(0,	6,-2),
				new Point3D(-2,	4,-2)
		).setEmission(wall_c)
				.setMaterial(wall_m);
		Polygon l_wall = (Polygon) new Polygon(
				new Point3D(-2,0,1),
				new Point3D(-2,0,-2),
				new Point3D(-2,4,-2),
				new Point3D(-2,4,1)
		).setEmission(wall_c)
				.setMaterial(wall_m);
		Polygon f_wall = (Polygon) new Polygon(
				new Point3D(-2,	0,	1),
				new Point3D(2,	0,	1),
				new Point3D(2,	4,	1),
				new Point3D(0,	6,	1),
				new Point3D(-2,	4,	1)
		).setEmission(wall_c)
				.setMaterial(wall_m);


		Material roof_m = new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6);
		Color roof_c = new Color(java.awt.Color.RED);
		Polygon r_roof = (Polygon) new Polygon(
				new Point3D(0,	6,	1),
				new Point3D(0,	6,	-2),
				new Point3D(2.5,	3.5,	-2),
				new Point3D(2.5,	3.5,	1)
		).setEmission(roof_c)
                .setMaterial(roof_m);
		Polygon l_roof = (Polygon) new Polygon(
				new Point3D(0,	6,	1),
				new Point3D(0,	6,	-2),
				new Point3D(-2.5,3.5,	-2),
				new Point3D(-2.5,3.5,	1)
        ).setEmission(roof_c)
                .setMaterial(roof_m);

        Material ground_m = new Material().setKd(0).setKs(0).setShininess(20).setKt(0);
        Color ground_c = new Color(java.awt.Color.green);
        Plane ground = (Plane) new Plane(new Vector( 0,1,0),new Point3D(0,0,0))
                .setMaterial(ground_m)
                .setEmission(ground_c);

		Material ball_m = new Material().setKd(0).setKs(0).setShininess(30).setKt(0);
		Color ball_c = new Color(java.awt.Color.gray);
		Sphere sphere = (Sphere) new Sphere(new Point3D(0,0,0),50)
				.setEmission(ball_c)
				.setMaterial(ball_m);

        Camera coolCamera = new Camera(
        		new Point3D(200, 200, 1000),
				new Vector(-10,-10,-50),
				new Vector(-10,260,-50)) //
                .setVpSize(200, 200)
				.setVpDistance(500);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene.setBackground(new Color(java.awt.Color.CYAN));
        scene.geometries.add(
                r_wall,l_wall,b_wall,f_wall,
                r_roof,l_roof,
				ground,sphere,
				new Triangle(
						new Point3D(0,0,0),
						new Point3D(0,250,0),
						new Point3D(-250,0,0)
				).setEmission(ball_c).setMaterial(ball_m)
        );

        scene.lights.add(new DirectionalLight(
                new Color(java.awt.Color.WHITE).scale(0.7),
                new Vector(-1,-1,1)
        ));

        ImageWriter imageWriter = new ImageWriter("amazingImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }



	@Test
	public void WOW_ImageTestOfFisher()
	{
		Material wall_m = new Material().setKd(0).setKs(0).setShininess(30).setKt(0);
		Color wall_c = new Color(java.awt.Color.gray);

		Color ball_c = new Color(java.awt.Color.gray);
		Sphere sphere = (Sphere) new Sphere(new Point3D(0,0,0),30)
				.setEmission(ball_c)
				.setMaterial(wall_m);

		Camera coolCamera = new Camera(
				new Point3D(200, 200, 1000),
				new Vector(-10,-10,-50),
				new Vector(-10,260,-50)) //
				.setVpSize(200, 200)
				.setVpDistance(500);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		scene.setBackground(new Color(java.awt.Color.CYAN));
		scene.geometries.add(
				sphere,
				new Triangle(
						new Point3D(0,0,0),
						new Point3D(0,250,0),
						new Point3D(-250,0,0)
				).setEmission(ball_c).setMaterial(wall_m)
		);

		scene.lights.add(new DirectionalLight(
				new Color(java.awt.Color.WHITE).scale(0.7),
				new Vector(-1,-1,1)
		));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.MAGENTA),1));

		ImageWriter imageWriter = new ImageWriter("amazingFishImage", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(coolCamera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

}





