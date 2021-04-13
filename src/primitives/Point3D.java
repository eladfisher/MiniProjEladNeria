package primitives;

import java.util.Objects;

import static java.lang.Math.sqrt;

/**
 * a class of 3D point in the 3D dimensional for graphic scenes
 */
public class Point3D {
    Coordinate x, y, z;
    static public final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * ctor that gets 3 coordinates
     *
     * @param x the coordinate of the X-axis
     * @param y the coordinate of the Y-axis
     * @param z the coordinate of the Z-axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * ctor that gets 3 coordinates based on double numbers
     *
     * @param x the coordinate of the X-axis
     * @param y the coordinate of the Y-axis
     * @param z the coordinate of the Z-axis
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }


    /**
     * add a vector to a point
     *
     * @param vector vector to add for the point
     * @return a new point with the added vector
     */
    public Point3D add(Vector vector) {

        Coordinate resX = new Coordinate(x.coord + vector.head.x.coord);
        Coordinate resY = new Coordinate(y.coord + vector.head.y.coord);
        Coordinate resZ = new Coordinate(z.coord + vector.head.z.coord);

        return new Point3D(resX, resY, resZ);
    }

    /**
     * return a vector that created by 2 points in the 3D dimension
     *
     * @param otherPoint the head point of the new vector
     * @return vector that its head is the param point and it's tail is 'this' point
     */
    public Vector subtract(Point3D otherPoint) {
        Coordinate resX = new Coordinate(x.coord - otherPoint.x.coord);
        Coordinate resY = new Coordinate(y.coord - otherPoint.y.coord);
        Coordinate resZ = new Coordinate(z.coord - otherPoint.z.coord);

        return new Vector(resX, resY, resZ);
    }

    /**
     * calc the distance squared between 2 points
     *
     * @param otherPoint the other point to calc the distance
     * @return the calculated distance squared between those points
     */
    public double distanceSquared(Point3D otherPoint) {

        double distanceX = (otherPoint.x.coord - x.coord);
        double distanceY = (otherPoint.y.coord - y.coord);
        double distanceZ = (otherPoint.z.coord - z.coord);

        return distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
    }

    /**
     * calc the distance between 2 points
     *
     * @param otherPoint the other point to calc the distance
     * @return the calculated distance between those points
     */
    public double distance(Point3D otherPoint) {
        return sqrt(distanceSquared(otherPoint));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) &&
                z.equals(point3D.z);
    }

    /**
     * to sring func for debug only
     * @return
     */
    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double getX() {
        return x.coord;
    }

    public double getY() {
        return y.coord;
    }

    public double getZ() {
        return z.coord;
    }
}
