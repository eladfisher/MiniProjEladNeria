package primitives;

import java.util.Objects;

public class Vector {

    final Point3D head;

    /**
     * the ctor that get the head point
     *
     * @param headPoint the head point of the new vector
     */
    public Vector(Point3D headPoint) {
        if (headPoint.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector can't be 0...");

        head = new Point3D(headPoint.x, headPoint.y, headPoint.z);
    }

    /**
     * the ctor that get the head point represented by 3 coordinates of the point
     *
     * @param x the x coordinate of the head point
     * @param y the y coordinate of the head point
     * @param z the z coordinate of the head point
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        head = new Point3D(x, y, z);

        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector can't be 0...");
    }

    /**
     * the ctor that get the head point represented by 3 values of the coordinates of the point
     * @param x the x value of the coordinate of the head point
     * @param y the y value of the coordinate of the head point
     * @param z the z value of the coordinate of the head point
     */
    public  Vector(double x, double y, double z)
    {
        head = new Point3D(x, y, z);

        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector can't be 0...");
    }

    /**
     * getter for the head of the vector
     * @return the head of the wanted vector
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * override of the equals function that decide if the objects are equals
     * @param o the other object to check if they equals
     * @return true if the objects are equals, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }




}
