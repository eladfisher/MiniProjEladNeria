package primitives;

/**
 * AABB box as bounding volume for time improvements
 */
public class AABBBox {
	
	/**
	 * the closest point to the 0,0,0
	 */
	Point3D minPoint;
	
	/**
	 * the far point from the 0,0,0
	 */
	Point3D maxPoint;
	
	/**
	 * ctor that gets the 2 points of the box
	 * @param minPoint the closest point to the 0,0,0
	 * @param maxPoint the far point from the 0,0,0
	 */
	public AABBBox(Point3D minPoint, Point3D maxPoint) {
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
	}
	
	public boolean isIntersect(Ray ray){
		
		return false;
	}
}
