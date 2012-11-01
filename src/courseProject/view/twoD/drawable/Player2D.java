/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Point;

import courseProject.model.Player;
import courseProject.model.Room;

/**
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Player2D extends Player implements Drawable2D {

	/**
	 * 
	 * @param room
	 * @param healthMax
	 * @param attack
	 * @param defence
	 */
	public Player2D(Room room, int healthMax, int attack, int defence) {
		super(room, healthMax, attack, defence);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param player
	 */
	public Player2D(Player2D player) {
		super(player);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public Point getPoint() {
		// TODO Auto-generated method stub
		return null;
	}


}
