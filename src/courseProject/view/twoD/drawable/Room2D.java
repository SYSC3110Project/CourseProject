/**
 * The Room2D.java file contains the class which will represent rooms in the game that will be drawn in 2D.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import courseProject.model.ExitDirection;
import courseProject.model.Inventory;
import courseProject.model.Monster;
import courseProject.model.Room;

/**
 * Room2D is an extension of the room class that implements the drawable interface.
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Room2D extends Room implements Drawable2D {
	
	/**Generated SerialID*/
	private static final long serialVersionUID = 7600587034222551945L;
	private SerializableBufferedImage sprite;
	private Rectangle bounds;
	
	private Map<ExitDirection, SerializableBufferedImage> exitImages;
	private Map<ExitDirection, Rectangle> exitBounds;

	/**
	 * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
	 * @param sprite The image for to represent the room
	 */
	public Room2D(String description, SerializableBufferedImage sprite) {
		super(description);
		this.sprite = sprite;// use 0,0 as origin
		this.bounds = new Rectangle(DEFAULT_X, DEFAULT_Y, this.sprite.getImage().getWidth(), this.sprite.getImage().getHeight());
		this.exitImages = new HashMap<ExitDirection, SerializableBufferedImage>();
		this.exitBounds = new HashMap<ExitDirection, Rectangle>();
	}

	/**
	 * Copy constructor copies all values from the passed Room2D to this new Room2D.
	 * @param toCopy The Room2D to copy.
	 */
	public Room2D(Room2D toCopy , ExitDirection fromDir, Room2D previousRoom, ArrayList<Room> explored) {
		super(toCopy);
		if(explored == null){
			explored = new ArrayList<Room>();
		}
		explored.add(this);
		exits = new HashMap<ExitDirection, Room>();
		for(ExitDirection s : toCopy.exits.keySet()){
			boolean foundExit = false;
    		ExitDirection reverse = reverseMapping(s);
    		for(Room r : explored){
    			if(toCopy.exits.get(s).getDescription() == r.getDescription()){
    				exits.put(s, r);
    				foundExit = true;
    			}
    		}
    		if(s == fromDir && !foundExit){
    			exits.put(s, previousRoom);
    		}
    		else if(!foundExit){
    			exits.put(s, new Room2D((Room2D)toCopy.exits.get(s), reverse, this, explored));
    		}
    	}
		
		
		this.items = new Inventory(toCopy.items);
		ArrayList<Monster> monsterList = new ArrayList<Monster>();
		for(Monster m: toCopy.monsters){
    		monsterList.add(new Monster2D((Monster2D)m));
    	}
    	monsters  = monsterList;
		
		this.sprite = toCopy.sprite;
		this.bounds = toCopy.bounds;
		this.exitImages = new HashMap<ExitDirection, SerializableBufferedImage>(toCopy.exitImages);
		this.exitBounds = new HashMap<ExitDirection, Rectangle>(toCopy.exitBounds);
	}
	
	/**
     * Adds an exit to this room in the passed direction.
     * @param dir The direction of the exit.
     * @param exit The room the exit connects to.
     */
    @Override
	public void addExit(ExitDirection dir, Room exit) {
        exits.put(dir,exit);

        SerializableBufferedImage exitImg;
    	switch(dir) {
    	case north:
        	exitImg = new SerializableBufferedImage("res\\NorthExit.png");
			exitImages.put(dir, exitImg);
			exitBounds.put(dir, new Rectangle(115, 0, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
        	break;
    	case south:
        	exitImg = new SerializableBufferedImage("res\\SouthExit.png");
			exitImages.put(dir, exitImg);
			exitBounds.put(dir, new Rectangle(100, 355, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	case east:
        	exitImg = new SerializableBufferedImage("res\\EastExit.png");
			exitImages.put(dir, exitImg);
			exitBounds.put(dir, new Rectangle(313, 105, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	case west:
        	exitImg = new SerializableBufferedImage("res\\WestExit.png");
			exitImages.put(dir, exitImg);
			exitBounds.put(dir, new Rectangle(0, 102, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	}
    }
    
    public ExitDirection inExitBounds(Rectangle bounds) {
    	
    	for(ExitDirection direction : exitBounds.keySet()) {
    		if(exitBounds.get(direction).contains(bounds)) {
    			return direction;
    		}
    	}
    	
    	return null;
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
	public SerializableBufferedImage getSprite() {
		return sprite;
	}

	@Override
	public void setSprite(SerializableBufferedImage image) {
		this.sprite = image;
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		
		graphics2d.drawImage(sprite.getImage(), bounds.x, bounds.y, bounds.width, bounds.height, null);
		for(ExitDirection direction : exits.keySet()) { 
			
			SerializableBufferedImage img = exitImages.get(direction);
			Rectangle bounds = exitBounds.get(direction);
			
			graphics2d.drawImage(img.getImage(), bounds.x, bounds.y, bounds.width, bounds.height, null);
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
