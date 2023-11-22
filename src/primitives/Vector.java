package primitives;

/**
 * a class of 3D Vector in the 3D dimensional for graphic scenes
 */
public class Vector {
    
    /**
     * the head point of the vector
     */
    Point3D head;

    public static Vector UP = new Vector(0,1,0);
    public static Vector RIGHT = new Vector(1,0,0);

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
     *
     * @param x the x value of the coordinate of the head point
     * @param y the y value of the coordinate of the head point
     * @param z the z value of the coordinate of the head point
     */
    public Vector(double x, double y, double z) {
        head = new Point3D(x, y, z);

        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector can't be 0...");
    }

    /**
     * getter for the head of the vector
     *
     * @return the head of the wanted vector
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * override of the equals function that decide if the objects are equals
     *
     * @param o the other object to check if they equals
     * @return true if the objects are equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    /**
     * add two vectors
     *
     * @param vectorToAdd the vector to add to 'this' vector
     * @return a new vector that it is the equivalent Vector
     */
    public Vector add(Vector vectorToAdd) {
        return new Vector(head.add(vectorToAdd));
    }

    /**
     * subtract two vectors
     *
     * @param vectorToSub the vector to sub to 'this' vector
     * @return a new vector that it is the equivalent Vector
     */
    public Vector subtract(Vector vectorToSub) {
        return head.subtract(vectorToSub.head);
    }

    /**
     * scale a vector by a scalar number
     *
     * @param num the scalar number to scale the vector with it
     * @return a new scaled vector
     */
    public Vector scale(double num) {
        double x = num * head.x.coord;
        double y = num * head.y.coord;
        double z = num * head.z.coord;

        return new Vector(x, y, z);
    }

    /**
     * do dot product between 2 vectors
     *
     * @param theOtherVector the other vector to do the dot product
     * @return the result of the dot product of the vectors
     */
    public double dotProduct(Vector theOtherVector) {
        double res = head.x.coord * theOtherVector.head.x.coord;
        res += head.y.coord * theOtherVector.head.y.coord;
        res += head.z.coord * theOtherVector.head.z.coord;

        return res;
    }

    /**
     * do a cross product between 2 vectors
     *
     * @param otherVector the other vector for the cross product
     * @return a new result vector of the cross product between the 2 vectors
     */
    public Vector crossProduct(Vector otherVector) {
        /**
         * the cross product of a vector can be calculated with the algebraic equation:
         * if the 2 vectors are: (x1,y1,z1) X (x2,y2,z2)
         * the result is:
         * X:y1*z2-y2*z1, Y:z1*x2-z2*x1, Z: x1*y2-x2*y1.
         */
        double x = head.y.coord * otherVector.head.z.coord - head.z.coord * otherVector.head.y.coord;
        double y = head.z.coord * otherVector.head.x.coord - head.x.coord * otherVector.head.z.coord;
        double z = head.x.coord * otherVector.head.y.coord - head.y.coord * otherVector.head.x.coord;

        return new Vector(x, y, z);
    }

    /**
     * calc the length squared of the vector
     *
     * @return the length squared of the vector
     */
    public double lengthSquared() {
        return Point3D.ZERO.distanceSquared(head);
    }

    /**
     * calc the length of the vector
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize a vector
     *
     * @return the normalize vector (same instance)
     */
    public Vector normalize() {
        Vector v = this.scale(1.0 / length());
        head = v.head;

        return this;
    }

    /**
     * does a normalized for a vector and don't change the vector
     *
     * @return new normalized vector
     */
    public Vector normalized() {
        Vector v = new Vector(head);

        return v.normalize();
    }

    /**
     * to string for debug use only
     * @return the data for debug only
     */
    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }
}
