package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;

    // region Constructors
    public Cylinder(Ray direction, double radius, double height) {
        super(direction, radius);

        if (height <= 0)
        {
            throw new IllegalArgumentException("height must be positive.");
        }
        this.height = height;
    }

    public Cylinder(Tube tube, double height) {
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
        //calc the 2 central points of the flat sides
        Point3D origin = direction.getHead();

        Vector dir = direction.getDirection();

        //calc with the origin point and go with the vector(the vector is normalize) the height of the cylinder
        Point3D oppositeOrigin = origin.add(dir.scale(height));

        //check if the param point is the origin point
        if (point3D.equals(origin))
            return direction.getDirection().scale(-1);

        //check if the param point is on the other side of the flat area
        if (point3D.equals(oppositeOrigin))
            return new Vector(direction.getDirection().getHead());

        //check if the point is on the flat side of the ray origin
        if (Util.isZero(point3D.subtract(origin).dotProduct(dir)))
            return direction.getDirection().scale(-1);

        //check if the point is on the flat side away from the ray origin
        if (Util.isZero(point3D.subtract(oppositeOrigin).dotProduct(dir)))
            return new Vector(direction.getDirection().getHead());

        return super.getNormal(point3D);
    }
}
