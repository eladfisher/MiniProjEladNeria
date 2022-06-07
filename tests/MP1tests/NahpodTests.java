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
    class Pair<K,V>{
        public K Key;
        public V Val;
        public Pair(K k, V v) {
            Key = k;
            Val = v;
        }
    }
    
    /**
* --------------------------------------> for benaya. <----------------------------------
    */
    Geometries superCoolSnowmanMaker(Point3D p, double a, double scale) {
        Color cbody = new Color(243,246,251).scale(0.99);
        Material mbody = new Material().setKd(1).setKs(0);
        Color cnose = new Color(93,57,13).scale(0.98);
        Material mnose = new Material().setKd(1).setKs(0);
        Color ceye = Color.Black;
        Material meye = new Material().setKd(1).setKs(1).setShininess(70).setKr(0.3);

        Ray up = new Ray(p, new Vector(0, 1, 0));
        Point3D middleP = p.add(new Vector(0, 5.77, 0).scale(scale)).rotated_AroundRay(up, a)
                , headP = p.add(new Vector(0, 9.43, 0).scale(scale)).rotated_AroundRay(up, a)
                , urnose = p.add(new Vector(0.3,  9.63, -1.45).scale(scale)).rotated_AroundRay(up, a)
                , ulnose = p.add(new Vector(-0.3, 9.63, -1.45).scale(scale)).rotated_AroundRay(up, a)
                , drnose = p.add(new Vector(0.6,  9,    -1.3).scale(scale)).rotated_AroundRay(up, a)
                , dlnose = p.add(new Vector(-0.6, 9,    -1.3).scale(scale)).rotated_AroundRay(up, a)
                , pnose = p.add(new Vector(0, 9.25, -3.4).scale(scale)).rotated_AroundRay(up, a)
                , reye = p.add(new Vector(0.6,  10.05, -1.22).scale(scale)).rotated_AroundRay(up, a)
                , leye = p.add(new Vector(-0.6, 10.05, -1.22).scale(scale)).rotated_AroundRay(up, a);

        Triangle  lnose = (Triangle) new Triangle(ulnose, dlnose, pnose).setMaterial(mnose).setEmission(cnose)
                , rnose = (Triangle) new Triangle(urnose, denose, pnose).setMaterial(mnose).setEmission(cnose)
                , unose = (Triangle) new Triangle(urnose, ulnose, pnose).setMaterial(mnose).setEmission(cnose)
                , dnose = (Triangle) new Triangle(drnose, dlnose, pnose).setMaterial(mnose).setEmission(cnose);
        
         Sphere body =   (Sphere) new Sphere(4*  scale, p      ).setEmission(cbody).setMaterial(mbody)
               ,middle = (Sphere) new Sphere(2.5*scale, middleP).setEmission(cbody).setMaterial(mbody)
               ,head =   (Sphere) new Sphere(1.5*scale, headP  ).setEmission(cbody).setMaterial(mbody)
               ,leye = (Sphere) new Sphere(0.3*scale,leye).setEmission(ceye).setMaterial(meye)
               ,reye = (Sphere) new Sphere(0.3*scale,reye).setEmission(ceye).setMaterial(meye);

        /**
         * snowman
         */
        Geometries snowman = new Geometries(lnose, rnose, unose, dnose, body, middle, head, leye, reye);
        return snowman;
    }
    Geometries superCoolSnowmanOnIce(Point3D p, double a, double scale) {
        /**
        * for start:
        {
            Scene scene = new Scene("Btest");
            Point3D centerPoint = Point3D.ZERO;
            Point3D cameraP = centerPoint.add(new Vector(24, 10, 24).scale(1.3));
            Vector up = new Vector(0, 1, 0);
            Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
            coolCamera.setVpDistance(5).setVpSize(6.5, 6.5);
            coolCamera.lookAt(centerPoint), up);
            scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
            
            Geometries pic = superCoolSnowmanOnIce(centerPoint, 0, 1);
            scene.geometries.add(pic);
        }
        */
        Color cice = new Color(200,233,233).scale(0.99);
        Material mice = new Material().setKd(1).setKs(0.6).setKt(0.1).setKr(0.3)
        // = new Material().setKd(1).setKs(1).setShininess(70).setKr(0.3);
        Geometries snowman = superCoolSnowmanMaker(p.add(new Vector(0,3,0).scale(scale)), a, scale);
        Plane ice = (Plane) new Plane(new Vector(0,1,0), p).setMaterial(mice).setEmission(cice);
        
        /**
         * snowman on ice
         */
        Geometries snowmanOnIce = new Geometries(snowman, Ice);
        return snowmanOnIce;
    }
 // ----------------------------------------> until here <----------------------------------

    /**
     * helpful
     *
     * @param p     place
     * @param a     degrees
     * @param scale scale
     * @return boat
     */
    Geometries boatMaker(Point3D p, double a, double scale) {
        Color c = new Color(java.awt.Color.WHITE).scale(0.65);
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
    Geometries duckMaker(Point3D p, double a, double scale) {
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
     * lightBugMaker
     * @param p place
     * @param d direction
     * @param scale scale
     * @param f_wing front wing degrees
     * @param b_wing back wing degrees
     * @return light bug
     */
    Pair<Geometries, LightSource> lightBugMaker(Point3D p, Vector d, double scale, double f_wing, double b_wing) {
        Material bodyM = new Material().setKs(0.5).setKd(1).setShininess(70).setKr(0.05);
        Color bodyC = Color.BLACK;
        Material tailM = new Material().setKs(0.5).setKd(1).setKt(0.5);
        Color tailC = new Color(java.awt.Color.YELLOW);
        Color tail_lC = new Color(java.awt.Color.WHITE).scale(0.3);
        Material wingM = new Material().setKs(0.2).setKd(1).setKt(0.7);
        Color wingC = new Color(java.awt.Color.PINK).scale(0.75);


        d.normalize();
        Vector hv = new Vector(1,0,0);
        Vector x = d.crossProduct(hv);
        Ray up = new Ray(p,x);
        double cos_a = hv.dotProduct(d);
        double a = -Math.acos(cos_a);
        Point3D bodyP = p.add(new Vector(1.5,0,0).scale(scale)).rotated_AroundRay(up, a)
                ,headP = p.add(new Vector(2.7,0,0).scale(scale)).rotated_AroundRay(up, a)
                ,wcccl = p.add(new Vector(1.2,0,0).scale(scale)).rotated_AroundRay(up, a)
                ,wcccr = p.add(new Vector(1.7,0,0).scale(scale)).rotated_AroundRay(up, a)

                ,wfcll = p.add(new Vector(0.4,0,2.2).scale(scale)).rotated_AroundRay(up, a)
                ,wfclr = p.add(new Vector(1.75,0,2.2).scale(scale)).rotated_AroundRay(up, a)
                ,wffl  = p.add(new Vector(1,0,4).scale(scale)).rotated_AroundRay(up, a)

                ,wfcrl = p.add(new Vector(1.35,0,2.1).scale(scale)).rotated_AroundRay(up, a)
                ,wfcrr = p.add(new Vector(2.85,0,2).scale(scale)).rotated_AroundRay(up, a)
                ,wffr  = p.add(new Vector(2,0,4.1).scale(scale)).rotated_AroundRay(up, a)

                ,wbcll = p.add(new Vector(0.4,0,-2.2).scale(scale)).rotated_AroundRay(up, a)
                ,wbclr = p.add(new Vector(1.75,0,-2.2).scale(scale)).rotated_AroundRay(up, a)
                ,wbbl  = p.add(new Vector(1,0,-4).scale(scale)).rotated_AroundRay(up, a)

                ,wbcrl = p.add(new Vector(1.35,0,-2.1).scale(scale)).rotated_AroundRay(up, a)
                ,wbcrr = p.add(new Vector(2.85,0,-2).scale(scale)).rotated_AroundRay(up, a)
                ,wbbr  = p.add(new Vector(2,0,-4.1).scale(scale)).rotated_AroundRay(up, a);

        Sphere tail = (Sphere) new Sphere(1*scale,p).setEmission(tailC).setMaterial(tailM)
                ,body = (Sphere) new Sphere(0.85*scale,bodyP).setEmission(bodyC).setMaterial(bodyM)
                ,head = (Sphere) new Sphere(0.6*scale,headP).setEmission(bodyC).setMaterial(bodyM);
        Polygon flWing = (Polygon) new Polygon(wcccl, wfcll, wffl, wfclr).setEmission(wingC).setMaterial(wingM)
                ,frWing = (Polygon) new Polygon(wcccr, wfcrl, wffr, wfcrr).setEmission(wingC).setMaterial(wingM)
                ,blWing = (Polygon) new Polygon(wcccl, wbcll, wbbl, wbclr).setEmission(wingC).setMaterial(wingM)
                ,brWing = (Polygon) new Polygon(wcccr, wbcrl, wbbr, wbcrr).setEmission(wingC).setMaterial(wingM);

        Ray axis = new Ray(p, d);
        flWing.rotateAroundRay(axis,b_wing);
        frWing.rotateAroundRay(axis,f_wing);
        blWing.rotateAroundRay(axis,-b_wing);
        brWing.rotateAroundRay(axis,-f_wing);

        Geometries bug = new Geometries(tail, body,head,flWing,frWing,blWing,brWing);
        LightSource tail_l = new PointLight(tail_lC, p);
        return new Pair<>(bug, tail_l);
    }


    /**
     * boring
     */
    @Test
    public void maybeTest() {
        Scene scene = new Scene("test");
        Point3D centerPoint = Point3D.ZERO;
        //Point3D cameraP = centerPoint.add(new Vector(0,1,0).scale(55));
        Point3D cameraP = centerPoint.add(new Vector(24, 5.7, 24).scale(1.3));
        Vector up = new Vector(0, 1, 0);
        Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
        coolCamera.setVpDistance(5).setVpSize(6.5, 6.5);
        //coolCamera.lookAt(centerPoint, new Vector(1,0,0));
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
        double Wd = 0.05;
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
                 duckMaker(centerPoint.add(new Vector(10,1,20)),     0           ,3)
                ,duckMaker(centerPoint.add(new Vector(-24,1,-24)),  -Math.PI/4   ,3)
                ,duckMaker(centerPoint.add(new Vector(0,1,-18)),    -Math.PI/2   ,3)
                ,duckMaker(centerPoint.add(new Vector(24,1,-24)),   -Math.PI/(4.0/3)   ,3));

        List<Point3D> bugsP = new ArrayList<>();
        bugsP.add(centerPoint.add(new Vector(-20,3,5)));
        bugsP.add(centerPoint.add(new Vector(-2,7,-10)));
        bugsP.add(centerPoint.add(new Vector(3,5,6)));
        bugsP.add(centerPoint.add(new Vector(20,2,-20)));
        bugsP.add(centerPoint.add(new Vector(28,5,26)));

        List<Pair<Geometries, LightSource>> bugs = new ArrayList<>();
        for (Point3D p : bugsP) {
            bugs.add(lightBugMaker(p, new Vector(1, 0, 1), 0.2, Math.PI / 18, -Math.PI / 18));
        }

        Geometries bugsG = new Geometries();
        for (Pair<Geometries, LightSource> b:bugs)
        {
            bugsG.add(b.Key);
        }
        //endregion

        scene.geometries.add(bugsG
                ,ducks
                ,boat
                ,lake
                ,sand
        );
        // scene.lights.add(new DirectionalLight(new Color(java.awt.Color.WHITE).scale(0.1), new Vector(1, -1, -1)));
        for (Pair<Geometries, LightSource> b:bugs)
        {
            scene.lights.add(b.Val);
        }

        //------------------------------> done here  <------------------------------

        coolCamera.setFocalPlaneDist(cameraP.distance(boatP)-coolCamera.getDistance());
        coolCamera.setApertureSize(0.2);
        coolCamera.setSamplingDepth(16);
        coolCamera.setRaysSampling(16);

        ImageWriter imageWriter = new ImageWriter("very_interesting_bug", 600, 600);
        scene.geometries.setBoundingBox();
        Render render = new Render()
                .setImageWriter(imageWriter)
                .setCamera(coolCamera) //
                .setRayTracer(new RayTracerBasic(scene))
                .setMultithreading(4)
                .setDebugPrint();

        render.renderImage();
        //render.printGrid(30,new Color(java.awt.Color.RED));
        render.writeToImage();
    }
}
