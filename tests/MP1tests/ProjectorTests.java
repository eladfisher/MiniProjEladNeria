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
        Point3D cameraP = centerPoint.add(new Vector(600,200,600));
		Point3D lookAtP = centerPoint.add(new Vector(0,200,0));//.add(new Vector(24, 3, 24));
        Vector up = Vector.UP;
        Camera coolCamera = new Camera(cameraP, new Vector(0, 0, 1), new Vector(0, 1, 0));
        coolCamera.setVpDistance(5).setVpSize(6.5, 6.5);
        //coolCamera.lookAt(centerPoint, new Vector(1,0,0));
        Vector vt = coolCamera.getvTo();
        Vector vu = coolCamera.getvUp();
        //coolCamera.MoveCamera(new Point3D(-500,180,-150), new Point3D(400,180,-150), 0);
        coolCamera.lookAt(lookAtP, up);
        // scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.1));
		
		// String photoPath = "D:\\Users\\mahpo\\Documents\\MyProjects\\MiniProjEladNeria-main\\images\\interesting_very.png";
		//String photoPath = "F:\\users\\Nerya\\Downloads\\testRotateAfterImage.png";
		String photoPath = "F:\\users\\Nerya\\Downloads\\dfhsf.png";
        //String photoPath = "C:\\Users\\Nerya\\IdeaProjects\\MiniProjEladNeria\\images\\with4AntiAliasing.png";

		Color bc = Color.BLACK;//new Color(100,4,150);//Color.WHITE;
		Material bm = Material.BASIC;
		
		Geometries wall = Rectangle.wallMaker(400,
                new Point3D(0,0,400),
                new Point3D(0,0,0),
                new Point3D(400,0,0)).setEmmission(bc).setMaterial(bm);
		coolCamera.lookAt((Rectangle) wall.get(0),-20);

		Projector p = new Projector(cameraP,vt,vu,photoPath);
		p.lookAt(lookAtP,up);
		p.update_height_width(0.005);
		scene.lights.add(p);
        scene.geometries.add(wall);

        double nx = coolCamera.getWidth();
        double ny = coolCamera.getHeight();
        double rat = nx/ny;

        ImageWriter imageWriter = new ImageWriter("Grafity2", (int)(600*rat),600); //600, 600);
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
