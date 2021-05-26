package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * a class of Tube for a 3D graphic model
 */
public class Tube extends Geometry {
    Ray direction;
    double radius;

    public Tube(Ray direction, double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("radius can't be not positive.");
        this.direction = direction;
        this.radius = radius;
    }

    //region Getters
    public Ray getDirection() {
        return direction;
    }

    public double getRadius() {
        return radius;
    }
    //endregion


    @Override
    public String toString() {
        return "Tube{" +
                "direction=" + direction +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        double t = direction.getDirection().dotProduct(point3D.subtract(direction.getHead()));

        if (Util.isZero(t))
            return point3D.subtract(direction.getHead()).normalize();

        Point3D o = direction.getHead().add(direction.getDirection().scale(t));
        return point3D.subtract(o).normalize();
    }
    
    /**
     *
     * @param ray
     * @return
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
    
    /**
     *
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
    
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }
}
