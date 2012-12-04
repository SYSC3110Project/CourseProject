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

import courseProject.Util;
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
	
	private Point[][] levelLayout;
	private Point[][] levelObjects;

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
		
		int layoutSize = Util.IMAGE_SIZE/Util.GRID_SECTIONS;

		
		// make an array for each grid section and x/y coords for the image section it will show
		levelLayout = new Point[layoutSize][layoutSize]; 
		levelObjects = new Point[layoutSize][layoutSize];
		for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				levelLayout[row][col] = new Point(0,0);
			}
		}		
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
        
        int x=0;
        int y=0;
        switch(dir) {
    	case north:
        	exitImg = new SerializableBufferedImage("res\\NorthSouthExit.png");
			exitImages.put(dir, exitImg);
			x = Util.IMAGE_SIZE/2-((Util.IMAGE_SIZE/2)%Util.GRID_SECTIONS);
			y = 0;
			exitBounds.put(dir, new Rectangle(x, y, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
        	break;
    	case south:
        	exitImg = new SerializableBufferedImage("res\\NorthSouthExit.png");
			exitImages.put(dir, exitImg);
			x = Util.IMAGE_SIZE/2-((Util.IMAGE_SIZE/2)%Util.GRID_SECTIONS);
	        y = Util.IMAGE_SIZE-Util.GRID_SECTIONS;
			exitBounds.put(dir, new Rectangle(x, y, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	case east:
        	exitImg = new SerializableBufferedImage("res\\EastWestExit.png");
			exitImages.put(dir, exitImg);
	        x = Util.IMAGE_SIZE-Util.GRID_SECTIONS;
	        y = Util.IMAGE_SIZE/2-((Util.IMAGE_SIZE/2)%Util.GRID_SECTIONS);
			exitBounds.put(dir, new Rectangle(x, y, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	case west:
        	exitImg = new SerializableBufferedImage("res\\EastWestExit.png");
			exitImages.put(dir, exitImg);
			x=0;
			y=Util.IMAGE_SIZE/2-((Util.IMAGE_SIZE/2)%Util.GRID_SECTIONS);
			exitBounds.put(dir, new Rectangle(x, y, exitImg.getImage().getWidth(), exitImg.getImage().getHeight()));
		break;
    	}
    }
    
    public ExitDirection inExitBounds(Rectangle bounds) {
    	
    	for(ExitDirection direction : exitBounds.keySet()) {
    		if(exitBounds.get(direction).intersects(bounds)) {
    			return direction;
    		}
    	}
    	
    	return null;
    }
    
    
    public void setBackground(String backgroundLayer) {
    	int layoutSize = Util.IMAGE_SIZE/Util.GRID_SECTIONS;
    	backgroundLayer = backgroundLayer.substring(2, backgroundLayer.length()-2);
        String[] lines = backgroundLayer.split("\n");
        
        for(int i=0;i<lines.length;i++) {
        	lines[i] = lines[i].trim();
        }
        
        String[][] hexElement = new String[lines.length][];

        //start at 1 and end at -1 because DOM includes the first and last new line after the node name
        for (int i=0; i<lines.length; i++) { 
        	hexElement[i] = lines[i].split(" ");
        }
    	
    	for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				levelLayout[row][col] = getPointFromString(hexElement[row][col]);
			}
		}	
    }
    
    
	public void setObjectLayer(String objectLayer) {
		int layoutSize = Util.IMAGE_SIZE/Util.GRID_SECTIONS;
		objectLayer = objectLayer.substring(2, objectLayer.length()-2);
        String[] lines = objectLayer.split("\n");
        
        for(int i=0;i<lines.length;i++) {
        	lines[i] = lines[i].trim();
        }
        
        String[][] hexElement = new String[lines.length][];

        //start at 1 and end at -1 because DOM includes the first and last new line after the node name
        for (int i=0; i<lines.length; i++) { 
        	hexElement[i] = lines[i].split(" ");
        }
    	
    	for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				levelObjects[row][col] = getPointFromString(hexElement[row][col]);
			}
		}	
	}

	public Point getPointFromString(String s) {
		Point p = new Point();
		
		p.x = Util.hexCharToInt(s.charAt(0))*Util.GRID_SECTIONS;
		p.y = Util.hexCharToInt(s.charAt(1))*Util.GRID_SECTIONS;
		
		return p;
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
		
		//graphics2d.drawImage(sprite.getImage(), bounds.x, bounds.y, bounds.width, bounds.height, null);
		
		if(sprite.getImage() != null) { // if there is an image to draw, draw it
			for(int row = 0;row<Util.IMAGE_SIZE;row+=Util.GRID_SECTIONS) {
				for(int col = 0;col<Util.IMAGE_SIZE;col+=Util.GRID_SECTIONS) {
					
					int rowIndex = row/Util.GRID_SECTIONS;
					int colIndex = col/Util.GRID_SECTIONS;

					graphics2d.drawImage(sprite.getImage(), col, row, col+Util.GRID_SECTIONS, row+Util.GRID_SECTIONS,  //draw the background content
							levelLayout[rowIndex][colIndex].x, levelLayout[rowIndex][colIndex].y,
							levelLayout[rowIndex][colIndex].x+Util.GRID_SECTIONS, levelLayout[rowIndex][colIndex].y+Util.GRID_SECTIONS, null);
					

					if(levelObjects[rowIndex][colIndex] != null) { //if there is a level Object there, draw it
						graphics2d.drawImage(sprite.getImage(), col, row, col+Util.GRID_SECTIONS, row+Util.GRID_SECTIONS, 
								levelObjects[rowIndex][colIndex].x, levelObjects[rowIndex][colIndex].y,
								levelObjects[rowIndex][colIndex].x+Util.GRID_SECTIONS, levelObjects[rowIndex][colIndex].y+Util.GRID_SECTIONS, null);
					}
					
				}
			}
		}
		
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
