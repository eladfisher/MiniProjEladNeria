package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * a 3D geometry of a box for 3D graphic model
 */
public class Box extends Geometry {
    Point3D p;
    //double w,h,d;
    Vector vw, vh, vd;
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
        vw = new Vector(width, 0, 0);
        vh = new Vector(0, height, 0);
        vd = new Vector(0, 0, depth);

        Point3D h = p.add(vw).add(vh).add(vd);
        Vector _vw = vw.scale(-1);
        Vector _vh = vh.scale(-1);
        Vector _vd = vd.scale(-1);

        corners = new Polygon[6];
        corners[0] = new Polygon(p, p.add(vw), p.add(vw).add(vh), p.add(vh));
        corners[1] = new Polygon(p, p.add(vw), p.add(vw).add(vd), p.add(vd));
        corners[2] = new Polygon(p, p.add(vd), p.add(vd).add(vh), p.add(vh));
        corners[3] = new Polygon(h, h.add(_vw), h.add(_vw).add(_vh), h.add(_vh));
        corners[4] = new Polygon(h, h.add(_vw), h.add(_vw).add(_vd), h.add(_vd));
        corners[5] = new Polygon(h, h.add(_vd), h.add(_vd).add(_vh), h.add(_vh));
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
        return null;
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
}
