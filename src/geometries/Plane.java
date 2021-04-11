package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Plane implements Geometry{

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


    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p0 = ray.getHead();
        Vector v = ray.getDirection();

        Vector n = normal;

        if(p0.equals(planePoint))
            return null;

        double nv = alignZero(n.dotProduct(v));

        return null;
    }
}
