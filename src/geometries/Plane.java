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
    
    /**
     * the normal to the plane
     */
    Vector normal;
    
    /**
     * the point on the plane
     */
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
     * @return the normal
     */
    public  Vector getNormal(){return normal ;}

    /**
     * to string for debug use only
     * @return the string for debug
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
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        
        Point3D p0 = ray.getHead();
        Vector v =  ray.getDirection();

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

        Point3D point = ray.getPoint(t);

        //check that the distance is bigger than the max distance
        if(alignZero(t - maxDistance) > 0)
            return null;

        return List.of(new GeoPoint(this,point));
    }
    
    /**
     * getter for the min point of the boundary box
     * @return the min point of the boundary box
     */
    @Override
    public Point3D getMinPoint() {
        return new Point3D(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    }
    
    /**
     * getter for the max point of the boundary box
     * @return the max point of the boundary box
     */
    @Override
    public Point3D getMaxPoint() {
        return new Point3D(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    }
    
    /**
     * check if the point is on the plane
     * @param p the point p to check
     * @return true if on plane
     */
    public boolean onPlane(Point3D p)
    {
        if (p.equals(planePoint)) return true;
        if (isZero(p.subtract(planePoint).dotProduct(normal))) return true;
        return false;
    }
}
