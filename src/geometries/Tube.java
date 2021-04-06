package geometries;

import primitives.Point3D;
import primitives.Ray;
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
        return null;
    }
}
