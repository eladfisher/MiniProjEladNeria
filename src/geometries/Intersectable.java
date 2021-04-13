package geometries;


import primitives.*;

import java.util.List;

/**
 * a interface for a 3D graphic model that finds the intersection points between a shape to a ray
 */
public interface Intersectable {

    public List<Point3D> findIntersections(Ray ray);

}
