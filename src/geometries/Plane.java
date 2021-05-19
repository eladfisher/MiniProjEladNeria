package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class of Plane for a 3D graphic model
 */
public class Plane extends Geometry{

    Vector normal;
    Point3D planePoint;

    /**
     * the ctor that gets a point in the plane and get a vertical vector of the plane
     * @param normal the normal vector of the plane
     * @param planePoint a point in the plane
     */
    public Plane(Vector normal, Point3D planePoint) {
        this.normal = normal.normalized();
        this.planePoint = planePoint;
    }

    /**
     * ctor that gets 3 points on the plane
     * @param point1 the first point in the plane
     * @param point2 the second point in the plane
     * @param point3 the three point in the plane
     */
    public Plane( Point3D point1,Point3D point2, Point3D point3) {

        //calc the normal with cross product of 2 vectors on the plane
        Vector v1 = point1.subtract(point2);
        Vector v2 = point2.subtract(point3);
        this.normal = v1.crossProduct(v2).normalize();

        this.planePoint = point1;
    }

    /**
     * get the normal to the plane
     * @return the normal of the plane
     */
    public  Vector getNormal(Point3D point)
    {
        return getNormal();
    }

    /**
     * getter for the normal field
     * @return
     */
    public  Vector getNormal(){return normal ;}

    /**
     * to string for debug use only
     * @return
     */
    @Override
    public String toString() {
        return "Plane{" +
                "normal=" + normal +
                ", planePoint=" + planePoint +
                '}';
    }
    
    
    /**
     * override for the findIntersections function of the geometry interface
     * @param ray the ray that intersects with the plane
     * @return a list with the intersect point
     */
    
    
    /**
     *
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        
        Point3D p0 = ray.getHead();
        Vector v = ray.getDirection();
    
        Vector n = normal;
    
        //the ray start at the plane point
        if(p0.equals(planePoint))
            return null;
    
        double nv = alignZero(n.dotProduct(v));
    
        //ray inside or parallel to plane
        if (isZero(nv))
            return null;
    
        double nq = alignZero(n.dotProduct(planePoint.subtract(p0)));
        double t =nq/nv;
    
        //if there isn't points ahead of the ray
        if (t<=0)
            return null;
    
        return List.of(new GeoPoint(this,ray.getPoint(t)));
    }
}
