/**
 * The Room2D.java file contains the class which will represent rooms in the game that will be drawn in 2D.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import courseProject.model.Room;

/**
 * Room2D is an extension of the room class that implements the drawable interface.
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Room2D extends Room implements Drawable2D {
	
	private BufferedImage sprite;
	private Rectangle bounds;
	
	private Map<String, BufferedImage> exitImages;

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
		this.bounds = new Rectangle(DEFAULT_X, DEFAULT_Y, this.sprite.getWidth(), this.sprite.getHeight());
		this.exitImages = new HashMap<String, BufferedImage>();
	}

	/**
	 * Copy constructor copies all values from the passed Room2D to this new Room2D.
	 * @param toCopy The Room2D to copy.
	 */
	public Room2D(Room2D toCopy) {
		super(toCopy);
		this.sprite = toCopy.sprite;
		this.bounds = toCopy.bounds;
		this.exitImages = new HashMap<String, BufferedImage>(toCopy.exitImages);
	}
	
	/**
     * Adds an exit to this room in the passed direction.
     * @param dir The direction of the exit.
     * @param exit The room the exit connects to.
     */
    public void addExit(String dir, Room exit) {
        exits.put(dir,exit);
        
        if(dir.equals("north")) {
        	BufferedImage northExit;
        	try {
        		northExit = ImageIO.read(new File("res\\NorthExit.png"));
        		exitImages.put(dir, northExit);
            } catch (IOException e) {
            }
        }
        else if(dir.equals("south")) {
        	BufferedImage northExit;
        	try {
        		northExit = ImageIO.read(new File("res\\SouthExit.png"));
        		exitImages.put(dir, northExit);
            } catch (IOException e) {
            }
		}
        else if(dir.equals("east")) {
        	BufferedImage northExit;
        	try {
        		northExit = ImageIO.read(new File("res\\EastExit.png"));
        		exitImages.put(dir, northExit);
            } catch (IOException e) {
            }
		}
        else if(dir.equals("west")) {
        	BufferedImage northExit;
        	try {
        		northExit = ImageIO.read(new File("res\\WestExit.png"));
        		exitImages.put(dir, northExit);
            } catch (IOException e) {
            }
		}
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
		for(String direction : exits.keySet()) { //Very Hackish
			if(direction.equals("north")) {
				BufferedImage img = exitImages.get(direction);
				graphics2d.drawImage(img, 115, 0, img.getWidth(), img.getHeight(), null);
	        }
	        else if(direction.equals("south")) {
	        	BufferedImage img = exitImages.get(direction);
				graphics2d.drawImage(img, 100, 355, img.getWidth(), img.getHeight(), null);
			}
	        else if(direction.equals("east")) {
	        	BufferedImage img = exitImages.get(direction);
				graphics2d.drawImage(img, 313, 105, img.getWidth(), img.getHeight(), null);
			}
	        else if(direction.equals("west")) {
	        	BufferedImage img = exitImages.get(direction);
				graphics2d.drawImage(img, 0, 102, img.getWidth(), img.getHeight(), null);
			}
		}
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
