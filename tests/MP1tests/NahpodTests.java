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
 * MY TEST. Don't touch it fisher.
 */
public class NahpodTests {

    /**
     * helpful
     *
     * @param p     place
     * @param a     degrees
     * @param scale scale
     * @return boat
     */
    Geometries boatMaker(Point3D p, double a, double scale) {
        Color c = new Color(java.awt.Color.WHITE).scale(0.7);
        Material m = new Material().setKd(1).setKs(0);
        Ray up = new Ray(p, new Vector(0, 1, 0));
        Point3D head = p.add(new Vector(0, 12, 0).scale(scale)).rotated_AroundRay(up, a)
                , rd = p.add(new Vector(12, 0, 0).scale(scale)).rotated_AroundRay(up, a)
                , ld = p.add(new Vector(-12, 0, 0).scale(scale)).rotated_AroundRay(up, a)
                , bd = p.add(new Vector(0, 0, -1.5).scale(scale)).rotated_AroundRay(up, a)
                , fd = p.add(new Vector(0, 0, 1.5).scale(scale)).rotated_AroundRay(up, a)
                , fu = p.add(new Vector(0, 7, 4).scale(scale)).rotated_AroundRay(up, a)
                , bu = p.add(new Vector(0, 7, -4).scale(scale)).rotated_AroundRay(up, a)
                , ru = p.add(new Vector(20, 11, 0).scale(scale)).rotated_AroundRay(up, a)
                , lu = p.add(new Vector(-20, 11, 0).scale(scale)).rotated_AroundRay(up, a);

        Triangle ffrr = (Triangle) new Triangle(ru, rd, fu).setMaterial(m).setEmission(c)
                , ffcr = (Triangle) new Triangle(fd, rd, fu).setMaterial(m).setEmission(c)
                , ffcl = (Triangle) new Triangle(fd, ld, fu).setMaterial(m).setEmission(c)
                , ffll = (Triangle) new Triangle(lu, ld, fu).setMaterial(m).setEmission(c)
                , bbrr = (Triangle) new Triangle(ru, rd, bu).setMaterial(m).setEmission(c)
                , bbcr = (Triangle) new Triangle(bd, rd, bu).setMaterial(m).setEmission(c)
                , bbcl = (Triangle) new Triangle(bd, ld, bu).setMaterial(m).setEmission(c)
                , bbll = (Triangle) new Triangle(bu, ld, lu).setMaterial(m).setEmission(c)
                , fcr = (Triangle) new Triangle(fd, head, rd).setMaterial(m).setEmission(c)
                , fcl = (Triangle) new Triangle(fd, head, ld).setMaterial(m).setEmission(c)
                , bcr = (Triangle) new Triangle(bd, head, rd).setMaterial(m).setEmission(c)
                , bcl = (Triangle) new Triangle(bd, head, ld).setMaterial(m).setEmission(c);


        /**
         * boat
         */
        Geometries boat = new Geometries(ffrr, ffcr, ffcl, ffll, bbrr, bbcr, bbcl, bbll, fcr, fcl, bcr, bcl);
        return boat;
    }

    Geometries duckMaker(Point3D p, double a, double scale)
    {
        return new Geometries();
    }


    /**
     * boring
     */
    @Test
    public void maybeTest() {
        Scene scene = new Scene("test");
        Point3D centerPoint = Point3D.ZERO;
        Point3D cameraP = centerPoint.add(new Vector(0, 10, 40).scale(1));
        Vector up = new Vector(0, 1, 0);
        Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
        coolCamera.setVpDistance(10).setVpSize(13, 13);
        //coolCamera.lookAt(centerPoint, new Vector(0,0,1));
        coolCamera.lookAt(centerPoint.add(new Vector(2, 0, 0)), up);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        //------------------------------> start here <------------------------------
        //region ground
        Material sandM = new Material().setKd(1).setKs(0).setKt(0).setKr(0);
        Color sandC = new Color(java.awt.Color.GREEN);
        Polygon sand = (Polygon) new Polygon(
                new Point3D(54, 0, 54)
                , new Point3D(54, 0, -66)
                , new Point3D(-66, 0, -66)
                , new Point3D(-66, 0, 54)
        ).setEmission(sandC).setMaterial(sandM);

        //region lake
        Material waterM = new Material().setKd(1).setKs(0.6).setKt(0.1).setKr(0.3);
        Color waterC = new Color(java.awt.Color.BLUE);
        double Wd = 0.3;
        Geometries lake = new Geometries(
                new Cylinder(new Ray(centerPoint, up), 30, Wd).setEmission(waterC).setMaterial(waterM)
                , new Cylinder(new Ray(centerPoint.add(new Vector(24, 0, -24)), up), 18, Wd).setEmission(waterC).setMaterial(waterM)
                , new Cylinder(new Ray(centerPoint.add(new Vector(-6, 0, -30)), up), 24, Wd).setEmission(waterC).setMaterial(waterM)
                , new Cylinder(new Ray(centerPoint.add(new Vector(18, 0, 18)), up), 12, Wd).setEmission(waterC).setMaterial(waterM)
                , new Cylinder(new Ray(centerPoint.add(new Vector(6, 0, -1)), up), 30, Wd).setEmission(waterC).setMaterial(waterM)
        );
        //endregion
        //endregion
        //region boat
        Geometries boat = boatMaker(centerPoint.add(new Vector(3,0,24)),Math.PI/4,0.4);
        //endregion

        scene.geometries.add(
                boat
                ,lake
                , sand
        );
        scene.lights.add(
                new DirectionalLight(new Color(java.awt.Color.WHITE).scale(0.3), new Vector(1, -1, -1))
        );
        //------------------------------> done here  <------------------------------

        ImageWriter imageWriter = new ImageWriter("interesting", 600, 600);

        Render render = new Render()
                .setImageWriter(imageWriter)
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }
}
