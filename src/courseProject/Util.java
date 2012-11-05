/**
 * The Util.java file contains a class with static methods and parameters which will be used for various locations in the code.
 */
package courseProject;

import java.awt.Point;

/**
 * The utilities class contains useful methods for doing menial tasks.
 * @author Matthew Smith
 * @version 05/11/2012
 */
public class Util {

	/**The default stepping value for interpolating.*/
	public static final int DEFAULT_STEP = 1;
	
	/**Prime number used for seeding hash code.*/
	public static final int HASH_SEED = 37;
	
	/**
	 * Calculates a new point offset by the specified step from the 'from' point to the 'to' point
	 * @param from The Starting point for the interpolation.
	 * @param to The point to step towards.
	 * @param step The amount of movement that should happen in the x and y direction.
	 * @return The new Point one step closer to the 'to' Point
	 */
	public static Point interpolateHelper(Point from, Point to, int step) {
		int x = from.x;
		int y = from.y;
		
		// X-direction
		if(from.getX()-to.getX() > 0) { //we are going in a negative x direction from where we are
			x-=step;
		}
		else if (from.getX()-to.getX() < 0) { //we are going in a postive x direction 
			x+=step;
		}
		
		// Y-direction
		if(from.getY()-to.getY() > 0) { //we are going in a negative y direction from where we are
			y-=step;
		}
		else if (from.getY()-to.getY() < 0) { //we are going in a postive y direction 
			y+=step;
		}
		
		return new Point(x, y);
	}

}
