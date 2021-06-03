package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * a 3D geometry of a box for 3D graphic model
 */
public class Box extends Geometry  {
    Point3D p;
    //double w,h,d;
    //Vector vw, vh, vd;
    Polygon[] corners;
    
    /**
     * default ctor that gets the first point and the width height and depth of the box
     * @param point the left down point
     * @param width the width of the box
     * @param height the height of the box
     * @param depth the depth of the box
     */
    public Box(Point3D point, double width, double height, double depth) {
        p = point;
        Vector vw = new Vector(width, 0, 0);
        Vector vh = new Vector(0, height, 0);
        Vector vd = new Vector(0, 0, depth);

        Point3D h = p.add(vw).add(vh).add(vd);
        Vector _vw = vw.scale(-1);
        Vector _vh = vh.scale(-1);
        Vector _vd = vd.scale(-1);

        corners = new Polygon[6];
        corners[0] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vh), p.add(vh))    .setEmission(new Color(java.awt.Color.RED)).setMaterial(new Material().setKd(1));
        corners[1] = (Polygon) new Polygon(p, p.add(vw), p.add(vw).add(vd), p.add(vd))    .setEmission(new Color(java.awt.Color.GREEN)).setMaterial(new Material().setKd(1));
        corners[2] = (Polygon) new Polygon(p, p.add(vd), p.add(vd).add(vh), p.add(vh))    .setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material().setKd(1));
        corners[3] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vh), h.add(_vh)).setEmission(new Color(java.awt.Color.CYAN)).setMaterial(new Material().setKd(1));
        corners[4] = (Polygon) new Polygon(h, h.add(_vw), h.add(_vw).add(_vd), h.add(_vd)).setEmission(new Color(java.awt.Color.MAGENTA)).setMaterial(new Material().setKd(1));
        corners[5] = (Polygon) new Polygon(h, h.add(_vd), h.add(_vd).add(_vh), h.add(_vh)).setEmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKd(1));
    }
    
    
    /**
     * get the normal of the box in certain point
     * @param point3D the point of the normal
     * @return the normal at p
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        //search the plain that the point is on him
        for (Polygon p :
                corners) {
            if (p.onPlane(point3D))
                return p.getNormal(point3D);
        }
        return corners[0].getNormal(point3D);
    }
    
    /**
     * find the intersections geo point between the ray and the geometry
     * @param ray the intersected ray
     * @param maxDistance the max distance
     * @return the intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> res = null;

        for (Polygon i : corners)
        {
            List<GeoPoint> l = i.findGeoIntersections(ray,maxDistance);

            if(l!=null&&res==null)
                res = new ArrayList<GeoPoint>(l);

            else if (res!=null&&l!=null)
                res.addAll(l);
        }

        return res;
    }

    /**
     * the getter for the material field
     * @return the material field
     */
    @Override
    public Material getMaterial() {
        return super.getMaterial();//material;
    }

    /**
     * setter for the material
     * @param material the new material
     * @return the geometry for chaining method
     */
    @Override
    public Geometry setMaterial(Material material) {
        for (Polygon p :
                corners) {
            p.setMaterial(material);
        }
        return this;
    }

    /**
     * getter for the emission light
     * @return the emmision color
     */
    @Override
    public Color getEmmission() {
        return emmission;
    }

    /**
     * setter for the emission field
     * @param emmission the new emmision
     * @return the same instance for chaining method
     */
    @Override
    public Geometry setEmission(Color emmission) {
        for (Polygon p :
                corners) {
            p.setEmission(emmission);
        }
        return this;
    }

    public void rotateAroundVector(Vector u, double a)
    {
        for (Polygon p :
                corners) {
            p.rotateAroundVector(u,a);
        }
    }

    public void rotateAroundRay(Ray r, double a)
    {
        for (Polygon p :
                corners) {
            p.rotateAroundRay(r,a);
        }
    }
}