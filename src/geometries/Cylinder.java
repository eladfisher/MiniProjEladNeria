package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    double height;

    // region Constructors
    public Cylinder(Ray direction, double radius, double height) {
        super(direction, radius);

        if (height <= 0) {
            throw new IllegalArgumentException("height must be positive.");
        }
        this.height = height;
    }

    public Cylinder(Tube tube, double height)
    {
        super(tube.direction, tube.radius);
        this.height = height;
    }
    //endregion


    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", direction=" + direction +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
