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
	
	/**
	 * check if the ray is intersect the box
	 * @param r the intersect ray
	 * @return true if intersect
	 */
	public boolean isIntersect(Ray r){
		
		// the algorithm is smith' method
		
		
		double tmin, tmax, tymin, tymax, tzmin, tzmax;
		
		
		
		//if the x coordinate of the head is bigger than 0 so the min point actually closer to the ray head
		if (r.direction.head.getX() >= 0) {
			tmin = (minPoint.getX() - r.head.getX()) / r.direction.head.getX();
			tmax = (maxPoint.getX() - r.head.getX()) / r.direction.head.getX();
		}
		//else, if the x coordinate of the ray direction is negative the smaller t value will be the value of the max point because
		// divide by negative number will switch between the big and the small values
		//for ex: 4/3 is bigger than 2/3, but 4/-3 is smaller than 2/-3
			else {
				tmin = (maxPoint.getX() - r.head.getX()) / r.direction.head.getX();
				tmax = (minPoint.getX() - r.head.getX()) / r.direction.head.getX();
			}
		
		//if the y coordinate of the head is bigger than 0 so the min point actually closer to the ray head
			if (r.direction.head.getY() >= 0) {
				tymin = (minPoint.getY() - r.head.getY()) / r.direction.head.getY();
				tymax = (maxPoint.getY() - r.head.getY()) / r.direction.head.getY();
			}
			
			//else, if the y coordinate of the ray direction is negative the smaller t value will be the value of the max point because
			// divide by negative number will switch between the big and the small values
			//for ex: 4/3 is bigger than 2/3, but 4/-3 is smaller than 2/-3
			else {
				tymin = (maxPoint.getY() - r.head.getY()) / r.direction.head.getY();
				tymax = (minPoint.getY() - r.head.getY()) / r.direction.head.getY();
			}
			
			//check if the ty is not in the same values as tmin and tmax
			if ( (tmin > tymax) || (tymin > tmax) )
				return false;
			
			//if they Overlap find the new values of the tmin and tmax
			if (tymin > tmin)
				tmin = tymin;
			
			if (tymax < tmax)
				tmax = tymax;
			
			//does the same with the Z coordinate as x,y
			if (r.direction.head.getZ() >= 0) {
				tzmin = (minPoint.getZ() - r.head.z.coord) / r.direction.head.getZ();
				tzmax = (maxPoint.getZ() - r.head.z.coord) / r.direction.head.getZ();
			}
			
			else {
				tzmin = (maxPoint.getZ() - r.head.getZ()) / r.direction.head.getZ();
				tzmax = (minPoint.getZ() - r.head.getZ()) / r.direction.head.getZ();
			}
			
			
			//check if tz is overlap with the common part of tx and ty
			if ( (tmin > tzmax) || (tzmin > tmax) )
				return false;
			
			return true;
		
	}
	
	/**
	 * a method that returns the middle point of the boundary volume box
	 * @return the middle of the volume boundary box
	 */
	public Point3D getMiddlePoint(){
		
		double x = (minPoint.getX()+maxPoint.getX())/2;
		double y = (minPoint.getY()+maxPoint.getY())/2;
		double z = (minPoint.getZ()+maxPoint.getZ())/2;
		return new Point3D(x,y,z);
	}
	
	/**
	 * find a char that represents the longest axis
	 * @return the vector of the longest axis
	 */
	public Vector getLongestAxis()
	{
		double x = maxPoint.getX()-minPoint.getX();
		double y = maxPoint.getY()-minPoint.getY();
		double z = maxPoint.getZ()-minPoint.getZ();
		
		//find the max length axis
		double max = Math.max(x,y);
		max = Math.max(max,z);
		
		//return the vector of this axis
		if(max == x)
			return new Vector(1,0,0);
		
		if(max == y)
			return new Vector(0,1,0);
		
		return new Vector(0,0,1);
	}
	
	/**
	 * get the middle point of the longest axis
	 * @return the middle point of the longest axis
	 */
	public double getMiddleAxisPoint(){
		//return the middle point of the longest axis
		//the vector of direction is normalized so it has 1 in the coordinate of the
		// longest axis and this make the dot product left only the value of the longest axis
		return getMiddlePoint().subtract(Point3D.ZERO).dotProduct(getLongestAxis());
	}
}
