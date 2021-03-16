package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane {

    Vector normal;
    Point3D planePoint;

    /**
     * the ctor that gets a point in the plane and get a vertical vector of the plane
     * @param normal the normal vector of the plane
     * @param planePoint a point in the plane
     */
    public Plane(Vector normal, Point3D planePoint) {
        this.normal = normal;
        this.planePoint = planePoint;
    }

    /**
     * ctor that gets 3 points on the plane
     * @param point1 the first point in the plane
     * @param point2 the second point in the plane
     * @param point3 the three point in the plane
     */
    public Plane( Point3D point1,Point3D point2, Point3D point3) {
        this.normal = null;
        this.planePoint = point1;
    }

    /**
     * get the normal to the plane
     * @return the normal of the plane
     */
    public  Vector getNormal(Point3D point)
    {
        return normal;
    }

}
