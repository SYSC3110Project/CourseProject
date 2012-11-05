/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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

	@Override
	public Point2D.Double getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Point2D.Double point) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle2D.Double getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBounds(Rectangle2D.Double bounds) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSprite(BufferedImage image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D graphics2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collidesWith(Drawable2D other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveTo(Double point) {
		// TODO Auto-generated method stub

	}

}