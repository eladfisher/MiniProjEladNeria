package geometries;


import primitives.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * a interface for a 3D graphic model that finds the intersection points between a shape to a ray
 */
public interface Intersectable {
    
    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
    
        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }
    
        /**
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
        
    }
    
    /**
     *
     * @param ray
     * @return
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }
    
    
    public  List<GeoPoint> findGeoIntersections(Ray ray);
    
}
