/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Point;

import courseProject.model.Monster;

/**
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Monster2D extends Monster implements Drawable2D {

	/**
	 * @param name
	 * @param healthMax
	 * @param attack
	 * @param defence
	 * @param weapon
	 * @param armor
	 */
	public Monster2D(String name, int healthMax, int attack, int defence,
			int weapon, int armor) {
		super(name, healthMax, attack, defence, weapon, armor);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param m
	 */
	public Monster2D(Monster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see courseProject.view.twoD.Drawable2D#getPoint()
	 */
	@Override
	public Point getPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
