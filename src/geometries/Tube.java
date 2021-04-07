package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Tube implements Geometry {
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
}
