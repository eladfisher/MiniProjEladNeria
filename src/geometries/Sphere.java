package geometries;

import primitives.Point3D;
import primitives.Vector;

import java.util.Objects;

public class Sphere implements  Geometry{

    final Point3D p0;
    final double radius;

    /**
     *  ctor that gets the middle point and the radius of the sphere
     * @param p0 the point in the sphere
     * @param radius the sphere radius
     */
    Sphere(Point3D p0, double radius)
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
        return null;
    }
}
