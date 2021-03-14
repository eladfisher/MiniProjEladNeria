package primitives;

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
     * @param x the coordinate of the X-axis
     * @param y the coordinate of the Y-axis
     * @param z the coordinate of the Z-axis
     */
    public  Point3D(double x, double y ,double z)
    {
        this.x= new Coordinate(x);
        this.y= new Coordinate(y);
        this.z= new Coordinate(z);
    }


    /**
     * add a vector to a point
     *
     * @param the vector to add for the point
     * @return a new point with the added vector
     */
    public Point3D add(Vector vector) {

        Coordinate resX = new Coordinate(x.coord+vector.head.x.coord);
        Coordinate resY = new Coordinate(y.coord+vector.head.y.coord);
        Coordinate resZ = new Coordinate(z.coord+vector.head.z.coord);

        return new Point3D(resX,resY,resZ);
    }

    public Vector subtract(Point3D otherPoint)
    {
        Coordinate resX = new Coordinate( otherPoint.x.coord-x.coord);
        Coordinate resY = new Coordinate(otherPoint.y.coord-y.coord);
        Coordinate resZ = new Coordinate( otherPoint.z.coord-z.coord);

        return new Vector(resX,resY,resZ);
    }




}
