package courseProject.view.mapD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import courseProject.model.ExitDirection;
import courseProject.model.Room;
import courseProject.view.twoD.drawable.SerializableBufferedImage;

/**
 * MapPanel is responsible for drawing all the map elements to the screen
 * 
 * @author Matthew Smith
 * @version 20/11/2012
 */
public class MapPanel extends JPanel implements Serializable{

	/** generated Serial ID */
	private static final long serialVersionUID = 9177045381045027586L;
	// private static final Color BACKGROUND = new Color(232,208,169);
	private static final Color INACTIVE_ROOM = new Color(0, 0, 0);
	private static final Color ACTIVE_ROOM = new Color(100, 100, 200);
	private static final Dimension ROOM = new Dimension(32, 32);
	private static final Dimension ROOM_EXIT = new Dimension(5, 5);
	private static final int EXIT_WIDTH = 8;
	private static final String DEFAULT_MAP_TEXTURE_PATH = "res/map.jpg";
	private static final String DEFAULT_ROOM_TEXTURE_PATH = "res/mapRoom.png";

	private Room currentRoom; // for centering the map

	private Map<Room, Point> roomLocations;
	private List<Point[]> exitLocations; // represents an exit from point[0] to
											// point[1]
	private SerializableBufferedImage mapTexture;
	private SerializableBufferedImage roomTexture;

	/**
	 * Constructor for the map panel.
	 */
	public MapPanel() {
		this.roomLocations = new HashMap<Room, Point>();
		this.exitLocations = new ArrayList<Point[]>();
		this.mapTexture = new SerializableBufferedImage(DEFAULT_MAP_TEXTURE_PATH);

		this.roomTexture = new SerializableBufferedImage(DEFAULT_ROOM_TEXTURE_PATH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D) g;

		// here we clear everything
		// drawRect(g, BACKGROUND, new Dimension(getWidth(),getHeight()), new
		// Point(0,0));

		g2d.drawImage(this.mapTexture.getImage(), 0, 0, getWidth(), getHeight(), null);
		// drawGrid(g);

		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(EXIT_WIDTH));
		
				  for (Point[] p : this.exitLocations) {
					  g2d.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
				  }
			  

		// g2d.setStroke(new BasicStroke(0));
		
				  for (Room key : this.roomLocations.keySet()) {
					  Point p = this.roomLocations.get(key);
					  if (key == this.currentRoom) { // literally a reference to the same
											// room, not just if the two are equal
						  drawBorderRect(g, ACTIVE_ROOM, ROOM, p, 5); 
						  // draw the active room in a different color

						  g2d.drawImage(this.roomTexture.getImage(), p.x, p.y, ROOM.width,
								  ROOM.height, null);
						  // g2d.setColor(Color.black);
						  // g.drawString(key.getDescription(), p.x, p.y);
					  } else {
						  drawBorderRect(g, INACTIVE_ROOM, ROOM, p, 2); 
						  // draw the inactive room in a different color
						  g2d.drawImage(this.roomTexture.getImage(), p.x, p.y, ROOM.width,
								  ROOM.height, null);
						  // g2d.setColor(Color.black);
						  // g.drawString(key.getDescription(), p.x, p.y);
					  }
				  }

	}

	/*
	 * private void drawGrid(Graphics graphics) {
	 * graphics.setColor(Color.black); for(int x=0;x<=getWidth();x+=ROOM.width)
	 * {
	 * 
	 * graphics.drawLine(x, 0, x, getHeight()); } for(int
	 * y=0;y<=getHeight();y+=ROOM.height) {
	 * 
	 * graphics.drawLine(0, y, getWidth(), y); } }
	 */

	/**
	 * Draws a rounded rectangle with the passed information. the padding will
	 * be additional rectangle in all directions.
	 * 
	 * @param graphics The Graphics for the rectangle to draw to
	 * @param color the color of the rounded rectangle
	 * @param dimension the dimension of the rectangle
	 * @param position where to start the rectangle
	 * @param padding the extra padding for the rectangle
	 */
	private void drawBorderRect(Graphics graphics, Color color,
			Dimension dimension, Point position, int padding) {
		graphics.setColor(color);
		graphics.fillRoundRect(position.x - padding, position.y - padding, 
								dimension.width + (padding * 2),
								dimension.height + (padding * 2), padding * 2,
								padding * 2);
		// fill a rounded rectangle starting behind the point passed and in front of where it ends.
	}

	/**
	 * Sets the current room for the map to center on.
	 * @param room The room for the map to center about.
	 */
	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
		this.roomLocations.clear();
		this.exitLocations.clear();
		while(getWidth()==0 && getHeight()==0) { 
			try{//wait until swing is done its setup
				Thread.sleep(1);
			} catch(InterruptedException e){
			}
		}
		Point p = new Point(getWidth() / 2, getHeight() / 2);
		this.roomLocations.put(room, p);
		locationSetup(room, p);
	}

	/**
	 * Helper method to Set up all of the points
	 * for which to draw the rooms and their exits
	 * 
	 * @param room The Room to setup the draw list 
	 * @param previous the location of the previous room (for off-setting this room relative to that room)
	 */
	private void locationSetup(Room room, Point previous) {
		Map<ExitDirection, Room> exits = room.getExitMap();
		for (ExitDirection key : exits.keySet()) {
			if (!this.roomLocations.containsKey(exits.get(key))) { // skip over rooms already in the set
				Point loc = new Point(previous);
				switch (key) { //adjust the location for the rooms based on the direction they are in.
				case north:
					loc.y -= (ROOM.getHeight() * 2) - ROOM_EXIT.getHeight();
					break;
				case east:
					loc.x += (ROOM.getWidth() * 2) - ROOM_EXIT.getWidth();
					break;
				case south:
					loc.y += (ROOM.getHeight() * 2) - ROOM_EXIT.getHeight();
					break;
				case west:
					loc.x -= (ROOM.getWidth() * 2) - ROOM_EXIT.getWidth();
				}
				Point exit1Point = new Point(previous); //set up the starting point for the exit
				exit1Point.x += ROOM.getWidth() / 2;
				exit1Point.y += ROOM.getHeight() / 2;

				Point exit2Point = new Point(loc); //set up the ending point for the exit
				exit2Point.x += ROOM.getWidth() / 2;
				exit2Point.y += ROOM.getHeight() / 2;
				this.exitLocations.add(new Point[] { exit1Point, exit2Point }); //add it to be drawn later
				
				
				if (exits.get(key).visited()) {
					
						this.roomLocations.put(exits.get(key), loc); 
						// map the room to the point it will be on the map
						locationSetup(exits.get(key), loc); // call this method again on the next room.
						// this will happen until there are no more
						// attached rooms to traverse or they have not been visited.
						
				}
			}
		}
	}

	/**
	 * Returns the current room to which the map is centered.
	 * @return the current room to which the map is centered.
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
}
