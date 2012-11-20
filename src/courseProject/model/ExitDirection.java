/**
 * 
 */
package courseProject.model;

/**
 * The ExitDirections class represents all the possible directions in which a room may have an exit
 * @author Matthew Smith
 * @version 19/11/2012
 */
public enum ExitDirection {
	north, south, east, west;
	
	/**
	 * 
	 * @param s The String for which to get the corresponding ExitDirection
	 * @return The ExitDirection corresponding to the passed String, null if it does not exist
	 */
	public static ExitDirection parse(String s) {
		for(ExitDirection dir : ExitDirection.values()) {
			if(s.equals(dir.toString())) {
				return dir;
			}
		} 
		return null;
	}
}
