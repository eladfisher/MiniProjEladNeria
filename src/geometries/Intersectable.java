package geometries;


import primitives.*;

import java.util.List;


public interface Intersectable {

    public List<Point3D> findIntersections(Ray ray);

}
