package courseProject.controller;

import java.awt.Point;

/**
 * 
 * @author Denis Dionne
 * @version 02/11/2012
 *
 */

public class InputEvent2D extends InputEvent {

	
	protected Point coordinates;
	
	
	/**
	 * generates the event
	 * @param command is the command input by the user
	 */
	public InputEvent2D(Command command) {
		super(command);
		
	}
	
	/**
	 * Generates a different event which includes the coordinates of the mouse click
	 * @param point
	 */
	public InputEvent2D(Point point) {
		super(null);
		this.coordinates = point;
	}

	/**
	 * getter for the coordinates
	 * @return the Point2D.Double coordinates
	 */
	public Point getCoordinates(){
		return coordinates;
	}
}
