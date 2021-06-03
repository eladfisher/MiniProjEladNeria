package lights;

import geometries.*;
import org.junit.jupiter.api.Test;
import elements.*;
import primitives.*;
import primitives.Vector;
import renderer.*;
import scene.Scene;

import java.util.*;

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
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500),
                        new Vector(-1, -1, -2)) //
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
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(new Point3D(-950, -900, -1000), 200) //
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

        scene.lights
                .add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
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
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140),
                        new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
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
    
    /**
     * build an image of a house and sun with very smooth and reflective ground
     */
    @Test
    public void WOW_ImageTest() {
        Material wall_m = new Material().setKd(1).setKs(1).setShininess(30).setKt(0);
        Color wall_c = new Color(java.awt.Color.gray);
        double d = 20;
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
        
        Polygon f_wall = (Polygon) new Polygon(
                new Point3D(d * -2, d * 0, d * 1),
                new Point3D(d * 2, d * 0, d * 1),
                new Point3D(d * 2, d * 4, d * 1),
                new Point3D(d * 0, d * 6, d * 1),
                new Point3D(d * -2, d * 4, d * 1)
        ).setEmission(wall_c)
                .setMaterial(wall_m);


        Material roof_m = new Material().setKd(0.4).setKs(0.1).setShininess(10).setKt(0);
        Color roof_c = new Color(java.awt.Color.RED);
        Polygon r_roof = (Polygon) new Polygon(
                new Point3D(d * 0, d * 6, d * 1),
                new Point3D(d * 0, d * 6, d * -2),
                new Point3D(d * 2.5, d * 3.5, d * -2),
                new Point3D(d * 2.5, d * 3.5, d * 1)
        ).setEmission(roof_c)
                .setMaterial(roof_m);
        
        Polygon l_roof = (Polygon) new Polygon(
                new Point3D(d * 0, d * 6, d * 1),
                new Point3D(d * 0, d * 6, d * -2),
                new Point3D(d * -2.5, d * 3.5, d * -2),
                new Point3D(d * -2.5, d * 3.5, d * 1)
        ).setEmission(roof_c)
                .setMaterial(roof_m);

        Polygon door = (Polygon) new Polygon(
                new Point3D(d * -0.7, d * 0, d * 1.005),
                new Point3D(d * 0.7, d * 0, d * 1.005),
                new Point3D(d * 0.7, d * 2, d * 1.005),
                new Point3D(d * -0.7, d * 2, d * 1.005)
        ).setEmission(new Color(139, 69, 19))
                .setMaterial(wall_m);

        Material ground_m = new Material().setKd(0.3).setKs(0).setShininess(0).setKt(0).setKr(0.3);
        Color ground_c = new Color(java.awt.Color.green);
        Plane ground = (Plane) new Plane(new Vector(0, 1, 0), new Point3D(0, 0, 0))
                .setMaterial(ground_m)
                .setEmission(ground_c);


        Point3D sun_p = new Point3D(-390, 240, -190);
        Sphere sun = (Sphere) new Sphere(sun_p, 70)
                .setEmission(new Color(java.awt.Color.yellow))
                .setMaterial(new Material()
                        .setKd(0.2)
                        .setKt(0.9)
                        .setShininess(100)
                        .setKs(1));


        Camera coolCamera = new Camera(new Point3D(1000, 150, 1000), new Vector(-1000, -100, -1000), new Vector(-1, 20, -1)) //
                .setVpSize(200, 200).setVpDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        scene.setBackground(new Color(java.awt.Color.CYAN).scale(0.7));
        scene.geometries.add(
                r_wall, l_wall, b_wall, f_wall,
                door,
                r_roof, l_roof,
                ground,
                sun
        );

        scene.lights.add(new DirectionalLight(
                new Color(java.awt.Color.WHITE).scale(0.5),
                new Vector(1, -1, -1)
        ));
        scene.lights.add(
                new PointLight(new Color(java.awt.Color.WHITE), sun_p)
        );

        ImageWriter imageWriter = new ImageWriter("amazingImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
    
    /**
     * a test with a lot of reflective spheres and transparent spheres and reflective plain
     */
    @Test
    public void WOWOW_ImageTest() {
        Material ground_m = new Material().setKd(1).setKs(0).setShininess(0).setKt(0).setKr(0.35);
        Color ground_c = new Color(java.awt.Color.DARK_GRAY);
        Plane ground = (Plane) new Plane(new Vector(0, 1, 0), new Point3D(0, 0, 0))
                .setMaterial(ground_m)
                .setEmission(ground_c);

        Point3D p_l1 = new Point3D(50, 100, 1009);
        Sphere s_l1 = (Sphere) new Sphere(p_l1, 30)
                .setMaterial(new Material().setKd(1).setKs(0).setShininess(100).setKt(0.7).setKr(0.2))
                .setEmission(new Color(java.awt.Color.YELLOW));

        Point3D p_l2 = new Point3D(-70, 100, -70);
        Sphere s_l2 = (Sphere) new Sphere(p_l2, 8)
                .setMaterial(new Material().setKd(1).setKs(0).setShininess(100).setKt(0.7).setKr(0.2))
                .setEmission(new Color(java.awt.Color.YELLOW));

        Material ball_m = new Material().setKd(1).setKs(1).setShininess(0).setKt(0).setKr(0.4);
        Color ball_c = new Color(java.awt.Color.blue).scale(0.7);

        List<Sphere> spheres = new ArrayList<Sphere>();
        spheres.add((Sphere) new Sphere(new Point3D(-100, 50, 100), 50)
                .setEmission(ball_c)
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(70, 30, -60), 30)
                .setEmission(ball_c)
                .setMaterial(new Material().setKd(1).setKs(1).setShininess(0).setKt(0.6).setKr(0.3)));
        spheres.add((Sphere) new Sphere(new Point3D(40, 80, -600), 80)
                .setEmission(new Color(java.awt.Color.GREEN).scale(0.4))
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(-20, 20, 250), 20)
                .setEmission(new Color(java.awt.Color.RED).scale(0.7))
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(60, 25, 400), 25)
                .setEmission(new Color(java.awt.Color.yellow).scale(0.3))
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(-45, 30, 750), 30)
                .setEmission(new Color(java.awt.Color.MAGENTA).scale(0.7))
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(30, 15, 600), 15)
                .setEmission(new Color(java.awt.Color.cyan).scale(0.3))
                .setMaterial(new Material().setKd(1).setKs(1).setShininess(0).setKt(0.6).setKr(0.3)));
        spheres.add((Sphere) new Sphere(new Point3D(5, 15, 10), 15)
                .setEmission(new Color(java.awt.Color.black).scale(0.3))
                .setMaterial(ball_m));
        spheres.add((Sphere) new Sphere(new Point3D(15, 10, 430), 10)
                .setEmission(new Color(java.awt.Color.LIGHT_GRAY).scale(0.01))
                .setMaterial(ball_m));


        Camera coolCamera = new Camera(new Point3D(0, 50, 1000), new Vector(0, -5, -100), new Vector(0, 100, -5)) //
                .setVpSize(200, 200).setVpDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.01));
        scene.geometries.add(
                ground,
                //s_l1,
                s_l2
        );
        for (Sphere s :
                spheres) {
            scene.geometries.add(s);
        }

        scene.lights.add(new DirectionalLight(
                new Color(java.awt.Color.WHITE).scale(0),
                new Vector(1, -1, -1)
        ));
        scene.lights.add(
                new PointLight(new Color(java.awt.Color.WHITE).scale(0.4), p_l2)
        );

        ImageWriter imageWriter = new ImageWriter("amazingImage3", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void BoxCylinder_ImageTest() {
        Material ground_m = new Material().setKd(1).setKs(0).setShininess(0).setKt(0).setKr(0.35);
        Color ground_c = new Color(java.awt.Color.DARK_GRAY);
        Plane ground = (Plane) new Plane(new Vector(0, 1, 0), new Point3D(0, 0, 0))
                .setMaterial(ground_m)
                .setEmission(ground_c);

        Box b = (Box) new Box(new Point3D(-30,25,0),50,30,20).
                //setEmission(new Color(java.awt.Color.GREEN).scale(0.8)).
                setMaterial(new Material().setKd(1).setKs(0.2).setShininess(30).setKt(0).setKr(0.1));
        b.rotateAroundRay(new Ray(new Point3D(0,50,50),new Vector(1,0,0)),Math.PI/6);
        b.rotateAroundRay(new Ray(new Point3D(0,50,50),new Vector(1,0,0)),-Math.PI/6);

        //b.rotateAroundVector(new Vector(1,0,0),Math.PI);
        //b.rotateAroundVector(new Vector(1,0,0),-Math.PI/6);

        Camera coolCamera = new Camera(new Point3D(-200, 700, 707), new Vector(0, -5, -100), new Vector(0, 100, -5)) //
                .setVpSize(200, 200).setVpDistance(1000);
        coolCamera.lookAt(new Point3D(0,0,0),new Vector(0,1,0));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.01));
        scene.geometries.add(
                //ground,
                b//,
                //new Cylinder(
                //        new Ray(new Point3D(20,20,20),
                //                new Vector(20,2,20)),
                //        30,100).
                //        setEmission(new Color(java.awt.Color.BLUE)).
                //        setMaterial(new Material().setKd(1).setKs(0.2).setShininess(0).setKt(0.3).setKr(0.35))
        );

        scene.lights.add(new DirectionalLight(
                new Color(java.awt.Color.WHITE).scale(0.6),
                new Vector(1, -1, -1)
        ));


        ImageWriter imageWriter = new ImageWriter("BoxCylinderImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
}





