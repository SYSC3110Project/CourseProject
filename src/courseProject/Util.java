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
	
	/**The Grid size for drawing the tileset*/
	public static final int GRID_SECTIONS = 32;
	/**The default image size for tilesets*/
	public static final int IMAGE_SIZE = 512;
	
	/**
	 * Takes an int and changes it into a hex char between 0 and F.
	 * above 15 or below 0 return an 'X'
	 * @param i the integer to change
	 * @return Hex representation of int
	 */
	public static char intToHexChar(int i) {
		switch (i) {
		case 0:
			return '0';
		case 1:
			return '1';
		case 2:
			return '2';
		case 3:
			return '3';
		case 4:
			return '4';
		case 5:
			return '5';
		case 6:
			return '6';
		case 7:
			return '7';
		case 8:
			return '8';
		case 9:
			return '9';
		case 10:
			return 'A';
		case 11:
			return 'B';
		case 12:
			return 'C';
		case 13:
			return 'D';
		case 14:
			return 'E';
		case 15:
			return 'F';			
		}

		return 'X';
	}
	
	/**
	 * Takes a hex char between 0 and F and changes it into an int.
	 * above 15 or below 0 return an -1.
	 * @param c the character to change
	 * @return integer representation of hex char
	 */
	public static int hexCharToInt(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;			
		}

		return -1;
	}
	
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
