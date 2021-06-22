package geometries;


import primitives.AABBBox;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a class for a 3D graphic model that finds the intersection points between a shape to a ray
 */
public abstract class Intersectable {
    
    
    
    /**
     * the bounding box of the geometry
     */
    private  AABBBox boundaryBox;
    
    /**
     * full PDO of geo point:
     * bind point and geometry
     */
    public static class GeoPoint {
        /**
         * the geometry of point
         */
        public Geometry geometry;
        /**
         * the point on the geomtry
         */
        public Point3D point;

        /**
         *get a geo point if geometry
         * @param geometry the geometry
         * @param point the point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * check if the object are equals
         * @param o the other object to compare
         * @return true if equals
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
     * a default implemetion for the find intersection method using find geo intersection
     * @param ray the intersect ray
     * @return a list with al the intersection points
     */
    public List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    /**
     * a method that find all the geopoints intersection
     * @param ray the intersect ray
     * @return the list with the intersection geo point
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find all the intersection points in some distance from the ray's head
     * @param ray the intersected ray
     * @param maxDistance the max distance
     * @return a list with the intersected points
     */
    public abstract  List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);
    
    /**
     * getter for the min point of the boundary box
     * @return the min point of the boundary box
     */
    public abstract Point3D getMinPoint();
    
    /**
     * getter for the max point of the boundary box
     * @return the max point of the boundary box
     */
    public abstract Point3D getMaxPoint();
    
    /**
     * setter that build a boundary box around the geometry
     */
    public void setBoundingBox(){
        boundaryBox = new AABBBox(getMinPoint(),getMaxPoint());
    }
    
    /**
     * getter for the bounding box
     * @return the bounding box
     */
    public AABBBox getAABBBox(){
        return boundaryBox;
    }
}
