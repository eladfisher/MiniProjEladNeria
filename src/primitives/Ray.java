package primitives;

import java.util.Objects;

public class Ray {

    Point3D head;
    Vector direction;

    /**
     * default ctor that gets the head point and the direction of the ray
     * @param head the head point
     * @param direction the direction of the ray
     */
    public Ray(Point3D head, Vector direction)
    {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * getter for the head
     * @return the head point
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * getter for the direction
     * @return the direction ray
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return head.equals( ray.head) && direction.equals( ray.direction);
    }

    /**
     * to string function for debug use only
     * @return
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
