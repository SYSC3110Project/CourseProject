/**
 * The Monster2D.java file contains the class which will represent monsters in the game that will be drawn in 2D.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import courseProject.Util;
import courseProject.model.Monster;

/**
 * Monster2D is an extension of the monster class that implements the drawable interface.
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Monster2D extends Monster implements Drawable2D {
	
	private BufferedImage sprite;
	private Rectangle bounds;
	private boolean interpolating;
	private Point interpolatingTo;
	private double timeSinceLastInterpolationUpdate;

	/**
	 * Constructor for the monster class will initialise all parameters of the monster.
	 * @param name the name of the monster
     * @param healthMax the maximum health of the monster
     * @param attack the attack stat of the monster
     * @param defence the defence stat of the monster
	 * @param weapon the weapon which the monster is using
	 * @param armor the armor which the monster is using
	 * @param sprite The image for to represent the monster
	 */
	public Monster2D(String name, int healthMax, int attack, int defence, int weapon, int armor, BufferedImage sprite) {
		super(name, healthMax, attack, defence, weapon, armor);
		this.sprite = sprite;// use 0,0 as origin
		this.bounds = new Rectangle(DEFAULT_X, DEFAULT_Y, sprite.getWidth(), sprite.getHeight());
		this.interpolating = false;
		this.timeSinceLastInterpolationUpdate = 0;
	}

	/**
	 * Copy constructor copies all values from the passed Monster2D to this new Monster2D.
	 * @param toCopy The Monster2D to copy.
	 */
	public Monster2D(Monster2D toCopy) {
		super(toCopy);
		this.sprite = toCopy.sprite;
		this.bounds = toCopy.bounds;
		this.interpolating = toCopy.interpolating;
		this.interpolatingTo = toCopy.interpolatingTo;
		this.timeSinceLastInterpolationUpdate = toCopy.timeSinceLastInterpolationUpdate;
	}

	@Override
	public Point getLocation() {
		return bounds.getLocation();
	}

	@Override
	public void setLocation(Point point) {
		this.bounds.setLocation(point);
		
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		
	}

	@Override
	public BufferedImage getSprite() {
		return sprite;
	}

	@Override
	public void setSprite(BufferedImage image) {
		this.sprite = image;
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		
		graphics2d.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
	
	@Override
	public void update(double delta) {
		if(interpolating) {
			timeSinceLastInterpolationUpdate+=delta; //time since the last interpolation update
			updateInterpolation();
		}
	}

	@Override
	public boolean collidesWith(Drawable2D other) {
		bounds.intersects(other.getBounds());
		return false;
	}

	@Override
	public void moveTo(Point point) {
		interpolating = true; //start interpolating
		interpolatingTo = point;
	}
	
	/**
	 * Helper method updates the location of the sprite moving it towards the point passed in moveTo()
	 */
	private void updateInterpolation() {
		if(timeSinceLastInterpolationUpdate>= INTERPOLATION_STEPS) { //only update every 0.25 seconds
			timeSinceLastInterpolationUpdate = 0; //reset the time so we update another 0.25 seconds from now
			
			bounds.setLocation(Util.interpolateHelper(bounds.getLocation(), interpolatingTo, Util.DEFAULT_STEP));
			
			if(bounds.getLocation().equals(interpolatingTo)) { //if we have reached the point, stop moving
				interpolating = false;
			}
		}
	}

}
