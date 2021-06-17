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
        Color c = new Color(java.awt.Color.WHITE).scale(0.6);
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

    /**
     * duck
     * @param p place
     * @param a degrees
     * @param scale scale
     * @return duck
     */
    Geometries duckMaker(Point3D p, double a, double scale)
    {
        Color bodyC = new Color(java.awt.Color.WHITE).scale(0.6);
        Material bodyM = new Material().setKd(1).setKs(0);
        Color noseC = new Color(255,69,0);
        Material noseM = new Material().setKd(1);
        Color eyeC = Color.BLACK;
        Material eyeM = new Material().setKd(1).setKs(1).setShininess(70).setKr(0.3);

        Ray up = new Ray(p, new Vector(0,1,0));
        Point3D  tP = p.add(new Vector(-3,1.5,0).scale(scale)).rotated_AroundRay(up, a)
                ,tfd = p.add(new Vector(-0.64, -0.63, 0.44).scale(scale)).rotated_AroundRay(up, a)
                ,tfu = p.add(new Vector(-0.47, 0.66, 0.59).scale(scale)).rotated_AroundRay(up, a)
                ,tbd = p.add(new Vector(-0.64, -0.63, -0.44).scale(scale)).rotated_AroundRay(up, a)
                ,tbu = p.add(new Vector(-0.47, 0.66, -0.59).scale(scale)).rotated_AroundRay(up, a)
                ,neckB = p.add(new Vector(0.5,0,0).scale(scale)).rotated_AroundRay(up, a)
                ,headP = p.add(new Vector(1.5,1.5,0).scale(scale)).rotated_AroundRay(up, a)
                ,nfd = p.add(new Vector(1.83, 1.18, 0.2).scale(scale)).rotated_AroundRay(up, a)
                ,nfu = p.add(new Vector(1.94, 1.6, 0.22).scale(scale)).rotated_AroundRay(up, a)
                ,nbd = p.add(new Vector(1.83, 1.18, -0.2).scale(scale)).rotated_AroundRay(up, a)
                ,nbu = p.add(new Vector(1.94, 1.6, -0.22).scale(scale)).rotated_AroundRay(up, a)
                ,noseP = p.add(new Vector(2.5,1.1,0).scale(scale)).rotated_AroundRay(up, a)
                ,feyeP = p.add(new Vector(1.81, 1.68, 0.35).scale(scale)).rotated_AroundRay(up, a)
                ,beyeP = p.add(new Vector(1.81, 1.68, -0.35).scale(scale)).rotated_AroundRay(up, a);

        Sphere      body = (Sphere) new Sphere(1*scale,p).setEmission(bodyC).setMaterial(bodyM)
                    ,head = (Sphere) new Sphere(0.5*scale,headP).setEmission(bodyC).setMaterial(bodyM)
                    ,feye = (Sphere) new Sphere(0.1*scale,feyeP).setEmission(eyeC).setMaterial(eyeM)
                    ,beye = (Sphere) new Sphere(0.1*scale,beyeP).setEmission(eyeC).setMaterial(eyeM);
        Cylinder neck = (Cylinder) new Cylinder(new Ray(neckB,headP.subtract(neckB)),0.3*scale,neckB.distance(headP)).setEmission(bodyC).setMaterial(bodyM);
        Triangle    ftail = (Triangle) new Triangle(tP,tfd,tfu).setEmission(bodyC).setMaterial(bodyM)
                    ,btail = (Triangle) new Triangle(tP,tbd,tbu).setEmission(bodyC).setMaterial(bodyM)
                    ,utail = (Triangle) new Triangle(tP,tbu,tfu).setEmission(bodyC).setMaterial(bodyM)
                    ,dtail = (Triangle) new Triangle(tP,tfd,tbd).setEmission(bodyC).setMaterial(bodyM)
                    ,fnose = (Triangle) new Triangle(noseP,nfd,nfu).setEmission(noseC).setMaterial(noseM)
                    ,bnose = (Triangle) new Triangle(noseP,nbd,nbu).setEmission(noseC).setMaterial(noseM)
                    ,unose = (Triangle) new Triangle(noseP,nbu,nfu).setEmission(noseC).setMaterial(noseM)
                    ,dnose = (Triangle) new Triangle(noseP,nfd,nbd).setEmission(noseC).setMaterial(noseM);

        /**
         * duck
         */
        Geometries duck = new Geometries(body, neck, head, feye, beye, ftail,btail ,utail,dtail ,fnose ,bnose ,unose ,dnose);
        return duck;
    }


    /**
     * boring
     */
    @Test
    public void maybeTest() {
        Scene scene = new Scene("test");
        Point3D centerPoint = Point3D.ZERO;
        Point3D cameraP = centerPoint.add(new Vector(24, 5, 24).scale(1.3));
        Vector up = new Vector(0, 1, 0);
        Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
        coolCamera.setVpDistance(10).setVpSize(13, 13);
        //coolCamera.lookAt(centerPoint, new Vector(0,0,1));
        coolCamera.lookAt(centerPoint.add(new Vector(24, 3, 24)), up);
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
        //region stuff
        Point3D boatP = centerPoint.add(new Vector(24,0,24));
        Geometries boat = boatMaker(boatP,Math.PI/5,0.2);
        Geometries ducks = new Geometries(
                 duckMaker(centerPoint.add(new Vector(-4,1,6)),     0           ,3)
                ,duckMaker(centerPoint.add(new Vector(-24,1,-24)),  -Math.PI/4   ,3)
                ,duckMaker(centerPoint.add(new Vector(0,1,-18)),    -Math.PI/2   ,3)
                ,duckMaker(centerPoint.add(new Vector(24,1,-24)),   -Math.PI/(4.0/3)   ,3));
        //endregion

        scene.geometries.add( ducks
                ,boat
                ,lake
                , sand
        );
        scene.lights.add(
                new DirectionalLight(new Color(java.awt.Color.WHITE).scale(0.3), new Vector(1, -1, -1))
        );
        //------------------------------> done here  <------------------------------

        coolCamera.setFocalPlaneDist(cameraP.distance(boatP)-coolCamera.getDistance());
        coolCamera.setApertureSize(0.4);
        coolCamera.setSamplingDepth(16);
        coolCamera.setRaysSampling(16);

        ImageWriter imageWriter = new ImageWriter("interesting", 600, 600);

        Render render = new Render()
                .setImageWriter(imageWriter)
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        //render.printGrid(30,new Color(java.awt.Color.RED));
        render.writeToImage();
    }
}
