/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Point;

import courseProject.model.Room;

/**
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Room2D extends Room implements Drawable2D {

	/**
	 * @param description
	 */
	public Room2D(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param r
	 */
	public Room2D(Room r) {
		super(r);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see courseProject.view.twoD.drawable.Drawable2D#getPoint()
	 */
	@Override
	public Point getPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
