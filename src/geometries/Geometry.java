package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * a interface of Geometry for a 3D graphic model
 */
public interface Geometry extends Intersectable {
    Vector getNormal(Point3D point3D);
}
