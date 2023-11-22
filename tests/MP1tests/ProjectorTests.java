package MP1tests;

import elements.Camera;
import elements.Projector;
import geometries.Geometries;
import geometries.Rectangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;


public class ProjectorTests {

    /**
     * boring
     */
    @Test
    public void maybeTest() {
		/*
		In order to do stuff i would love to move to "simplicity mode"/.
		But, the only way to achive somthing you don't know how to get is to make it yourself, and...
		Well, i don't need simplicity mode to make stuff simpler,
		but if someone will ever want to use it he will need to figure out this idiocityLevel on his one.
		Anyway, this theoretic simpleMode contains the next terms:
		 - 	only one LightSource: the projector.
		 - 	everything is totally white (255, 255, 255)
		 - 	everything is in boring matrial mode: kd = 1, everything else = 0.
		 - 	only polygons and only in the shape of MALBEN or whatever it is.
		 - 	one up direction = up; posible flour but not recommand.
		 - 	all of those terms seposingly should give you the opportunity to add to the camera 
			a fiture that capture only one polygon at the time.
			and if you have that, you would be able to print those pictures and glue them to this object.
			and than you would have very cool perspaction paint. if you know hat I mean and how to spell it.
		
		anddddd back to the project!
		*/
		
        Scene scene = new Scene("test");
        Point3D centerPoint = Point3D.ZERO;
        //Point3D cameraP = centerPoint.add(new Vector(0,1,0).scale(55));
        Point3D cameraP = centerPoint.add(new Vector(600,180,600));
		Point3D lookAtP = centerPoint.add(new Vector(0,180,0));//.add(new Vector(24, 3, 24));
        Vector up = Vector.UP;
        Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
        coolCamera.setVpDistance(5).setVpSize(6.5, 6.5);
        //coolCamera.lookAt(centerPoint, new Vector(1,0,0));
        coolCamera.lookAt(lookAtP, up);
        //scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.1));
		
		// String photoPath = "D:\\Users\\mahpo\\Documents\\MyProjects\\MiniProjEladNeria-main\\images\\interesting_very.png";
		String photoPath = "F:\\users\\Nerya\\Downloads\\testRotateAfterImage.png";
        //String photoPath = "C:\\Users\\Nerya\\IdeaProjects\\MiniProjEladNeria\\images\\with4AntiAliasing.png";

		Color bc = Color.BLACK;//new Color(100,4,150);//Color.WHITE;
		Material bm = Material.BASIC;
		
		Geometries wall = Rectangle.wallMaker(499,
                new Point3D(400,0,-500),
                new Point3D(400,0,200),
                new Point3D(-500,0,200)).setEmmission(bc).setMaterial(bm);
		//coolCamera.lookAt((Rectangle) wall.get(0));

		Projector p = new Projector(cameraP,coolCamera.getvTo(),coolCamera.getvUp(),photoPath);
		p.scaleImage(0.005);
		p.lookAt(lookAtP,up);
		scene.lights.add(p);
        scene.geometries.add(wall);

        //------------------------------> start here <------------------------------
		/*
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

		*/
        //------------------------------> done here  <------------------------------
		
		/*

        coolCamera.setFocalPlaneDist(cameraP.distance(boatP)-coolCamera.getDistance());
        coolCamera.setApertureSize(0.2);
        coolCamera.setSamplingDepth(16);
        coolCamera.setRaysSampling(16);
		*/


        ImageWriter imageWriter = new ImageWriter("Grafity", 600, 600);
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
