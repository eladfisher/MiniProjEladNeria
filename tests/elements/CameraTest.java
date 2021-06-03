package elements;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Testing Camera Class
 *
 * @author Dan
 */
public class CameraTest {

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    @Test
    public void testConstructRayThroughPixel() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setVpDistance(10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
                camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 0, 0), "Bad ray");

        // TC02: 4X4 Corner (0,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
                camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 0, 0), "Bad ray");

        // TC03: 4X4 Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
                camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 1, 0), "Bad ray");

        // TC04: 4X4 Inside (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
                camera.setVpSize(8, 8).constructRayThroughPixel(4, 4, 1, 1), "Bad ray");

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
                camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 1, 1), "Bad ray");

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
                camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 1, 0), "Bad ray");

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
                camera.setVpSize(6, 6).constructRayThroughPixel(3, 3, 0, 1), "Bad ray");

    }

    @Test
    void lookAt() {
        Scene scene = new Scene("Test scene");

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
        Sphere sun = (Sphere) new Sphere(70, sun_p)
                .setEmission(new Color(java.awt.Color.yellow))
                .setMaterial(new Material()
                        .setKd(0.2)
                        .setKt(0.9)
                        .setShininess(100)
                        .setKs(1));

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


        Camera coolCamera = new Camera(new Point3D(1000, 150, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(200, 200).setVpDistance(1000);

        ImageWriter imageWriter = new ImageWriter("testLookAtBeforeImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();


        coolCamera.lookAt(new Point3D(0,50,0), new Vector(0,1,0));
        imageWriter = new ImageWriter("testLookAtAfterImage", 600, 600);
        render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    void rotateCameraAroundVto() {
        Scene scene = new Scene("Test scene");

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
        Sphere sun = (Sphere) new Sphere(70, sun_p)
                .setEmission(new Color(java.awt.Color.yellow))
                .setMaterial(new Material()
                        .setKd(0.2)
                        .setKt(0.9)
                        .setShininess(100)
                        .setKs(1));

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


        Camera coolCamera = new Camera(new Point3D(1000, 150, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(200, 200).setVpDistance(1000);
        coolCamera.lookAt(new Point3D(0,50,0), new Vector(0,1,0));

        ImageWriter imageWriter = new ImageWriter("testRotateBeforeImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();


        coolCamera.rotateCameraAroundVto(45);
        imageWriter = new ImageWriter("testRotateAfterImage", 600, 600);
        render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();



    }

    @Test
    void moveCamera() {
        Scene scene = new Scene("Test scene");

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
        Sphere sun = (Sphere) new Sphere(70, sun_p)
                .setEmission(new Color(java.awt.Color.yellow))
                .setMaterial(new Material()
                        .setKd(0.2)
                        .setKt(0.9)
                        .setShininess(100)
                        .setKs(1));

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


        Camera coolCamera = new Camera(new Point3D(1000, 150, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(200, 200).setVpDistance(1000);
        coolCamera.lookAt(new Point3D(0,50,0), new Vector(0,1,0));

        ImageWriter imageWriter = new ImageWriter("testMoveToBeforeImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();


        coolCamera.MoveCamera(coolCamera.getPoint3D().rotated_AroundVector(new Vector(0,1,0),Math.PI/-3),new Point3D(0,50,0),Math.PI/18);
        imageWriter = new ImageWriter("testMoveToAfterImage", 600, 600);
        render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
}
