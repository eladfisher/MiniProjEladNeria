package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

        if (isZero(t))
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


    /**
     * http://hugi.scene.org/online/hugi24/coding%20graphics%20chris%20dragan%20raytracing%20shapes.htm
     *
     * from there.
     * @param ray the intersected ray
     * @param maxDistance the max distance
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector d = ray.getDirection();
        Vector v = direction.getDirection();
        double dv = d.dotProduct(v);

        if (ray.getHead().equals(direction.getHead())) {
            if (isZero(dv) && alignZero(radius - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }

            Vector dvv = v.scale(d.dotProduct(v));

            if (d.equals(dvv)) {
                return null;
            }
            double offset = alignZero(Math.sqrt(radius * radius / d.subtract(dvv).lengthSquared()));
            if (alignZero(offset - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(offset)));
            }
            return null;
        }

        Vector x = ray.getHead().subtract(direction.getHead());

        double xv = x.dotProduct(v);

        double a = 1 - dv * dv;
        double b = 2 * d.dotProduct(x) - 2 * dv * xv;
        double c = x.lengthSquared() - xv * xv - radius * radius;

        if (isZero(a)) {
            if (isZero(b) || alignZero((-c / b) - maxDistance) > 0) {
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(-c / b)));
        }

        double delta = alignZero(b * b - 4 * a * c);

        if (delta <= 0) {
            return null;
        }

        List<GeoPoint> intersections = null;
        double t = alignZero(-(b + Math.sqrt(delta)) / (2 * a));
        if (t > 0) {
            intersections = new ArrayList<>();
            intersections.add(new GeoPoint(this, ray.getPoint(t)));
        }
        t = alignZero(-(b - Math.sqrt(delta)) / (2 * a));
        if (t > 0 && alignZero(t - maxDistance) <= 0) {
            if (intersections == null) {
                intersections = new ArrayList<>();
            }
            intersections.add(new GeoPoint(this, ray.getPoint(t)));
        }

        return intersections;
    }
}
