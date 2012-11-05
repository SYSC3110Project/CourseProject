/**
 * The Room2D.java file contains the class which will represent rooms in the game that will be drawn in 2D.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import courseProject.model.Room;

/**
 * Room2D is an extension of the room class that implements the drawable interface.
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Room2D extends Room implements Drawable2D {
	
	private BufferedImage sprite;
	private Rectangle bounds;

	/**
	 * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
	 * @param sprite The image for to represent the room
	 */
	public Room2D(String description, BufferedImage sprite) {
		super(description);
		this.sprite = sprite;// use 0,0 as origin
		this.bounds = new Rectangle(DEFAULT_X, DEFAULT_Y, sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * Copy constructor copies all values from the passed Room2D to this new Room2D.
	 * @param toCopy The Room2D to copy.
	 */
	public Room2D(Room2D toCopy) {
		super(toCopy);
		this.sprite = toCopy.sprite;
		this.bounds = toCopy.bounds;
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
		//Nothing to do right now
		//could update 
	}

	@Override
	public boolean collidesWith(Drawable2D other) {
		bounds.intersects(other.getBounds());
		return false;
	}

	@Override
	public void moveTo(Point point) {
		//The room shouldn't really have this as an option.
	}
}
