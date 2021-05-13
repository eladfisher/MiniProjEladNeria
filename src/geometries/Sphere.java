package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

/**
 * a class of 3D sphere for a 3D graphic model
 */
public class Sphere implements  Geometry{

    final Point3D p0;
    final double radius;

    /**
     *  ctor that gets the middle point and the radius of the sphere
     * @param p0 the point in the sphere
     * @param radius the sphere radius
     */
    public Sphere(Point3D p0, double radius)
    {
        this.p0 = p0;
        this.radius = radius;
    }

    /**
     * getter for the middle point of the sphere
     * @return the middle point of the sphere field
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter for the radius of the sphere
     * @return
     */
    public double getRadius() {
        return radius;
    }

    /**
     * to string for a debug use only
     * @return
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "p0=" + p0 +
                ", radius=" + radius +
                '}';
    }

    /**
     * override of the equals function that determine if the object are identical
     * @param o the other onject to check
     * @return true if the object are equal, and false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && p0.equals(sphere.p0);
    }

    /**
     * implement of the get normal of the Geometry interface
     * @param p the point to get normal from
     * @return a norm
     */
    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(p0).normalize();
    }
    
    /**
     * override for the findIntersections function of the geometry interface
     * @param ray the ray that intersects with the sphere
     * @return a list with the intersect points
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p0 = ray.getHead();
        Vector v = ray.getDirection();
        Point3D o = this.p0;

        if(p0.equals(o))
            return List.of(ray.getPoint(radius));

        Vector U = o.subtract(p0);
        double tm = v.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared()-tm*tm);
        if(alignZero(d-radius)>=0)
            return null;
        

        double th = Math.sqrt(radius*radius-d*d);
        double t1 = alignZero(tm-th);
        double t2 = alignZero(tm+th);

        if(t1>0&&t2>0)
        {
            Point3D P1 = ray.getPoint(t1);
            Point3D P2 = ray.getPoint(t2);

            return List.of(P1,P2);
        }

        if(t1>0)
        {
            Point3D P1 = ray.getPoint(t1);
            return List.of(P1);
        }

        if(t2>0)
        {
            Point3D P2 = ray.getPoint(t2);
            return List.of(P2);
        }

        return null;
    }
}
