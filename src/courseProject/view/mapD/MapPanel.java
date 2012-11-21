package courseProject.view.mapD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import courseProject.model.ExitDirection;
import courseProject.model.Room;



/**
 * MapPanel is responsible for drawing all the map elemnts to the screen
 * @author Matthew Smith
 * @version 15/11/2012
 *
 */
public class MapPanel extends JPanel {

	/**generated Serial ID*/
	private static final long serialVersionUID = 9177045381045027586L;
	//private static final Color BACKGROUND = new Color(232,208,169);
	private static final Color INACTIVE_ROOM = new Color(0,0,0);
	private static final Color ACTIVE_ROOM = new Color(100,100,200);
	private static final Dimension ROOM = new Dimension(32,32);
	private static final Dimension ROOM_EXIT = new Dimension(5,5);
	private static final int EXIT_WIDTH = 8;
	private static final String DEFAULT_MAP_TEXTURE_PATH = "res/map.jpg";
	private static final String DEFAULT_ROOM_TEXTURE_PATH = "res/mapRoom.png";

	private Room currentRoom; //for centering the map
	
	private Map<Room,Point> roomLocations;
	private List<Point[]> exitLocations; //represents an exit from point[0] to point[1]
	private BufferedImage mapTexture;
	private BufferedImage roomTexture;

	public MapPanel() {
		roomLocations = new HashMap<Room,Point>();
		exitLocations = new ArrayList<Point[]>();
		try {
			mapTexture = ImageIO.read(new File(DEFAULT_MAP_TEXTURE_PATH));
			
			roomTexture = ImageIO.read(new File(DEFAULT_ROOM_TEXTURE_PATH));
		} catch (IOException e) {
		}
		this.setPreferredSize(new Dimension(200,200));
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);

		Graphics2D g2d = (Graphics2D)g;

		// here we clear everything
		//drawRect(g, BACKGROUND, new Dimension(getWidth(),getHeight()), new Point(0,0));

		g2d.drawImage(mapTexture, 0, 0, this.getWidth(), this.getHeight(), null);
		//drawGrid(g);

		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(EXIT_WIDTH));
		for(Point[] p : exitLocations) {			
			g2d.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
		}


		//g2d.setStroke(new BasicStroke(0));
		for(Room key : roomLocations.keySet()) {
			Point p = roomLocations.get(key);
			if(key == currentRoom) { //literally a reference to the same room, not just if the two are equal
				drawBorderRect(g, ACTIVE_ROOM, ROOM, p, 5); //draw the active room in a different color

				g2d.drawImage(roomTexture, p.x, p.y, ROOM.width, ROOM.height, null);
				//g2d.setColor(Color.black);
				//g.drawString(key.getDescription(), p.x, p.y);
				roomLocations.put(key, p);
			} else {
				drawBorderRect(g, INACTIVE_ROOM, ROOM, p,2); //draw the active room in a different color
				g2d.drawImage(roomTexture, p.x, p.y, ROOM.width, ROOM.height, null);
				//g2d.setColor(Color.black);
				//g.drawString(key.getDescription(), p.x, p.y);
			}
		}

	}
	/*
	private void drawGrid(Graphics graphics) {
		graphics.setColor(Color.black);
		for(int x=0;x<=getWidth();x+=ROOM.width) {

			graphics.drawLine(x, 0, x, getHeight());
		}
 		for(int y=0;y<=getHeight();y+=ROOM.height) {

			graphics.drawLine(0, y, getWidth(), y);
		}
	}*/

	private void drawBorderRect(Graphics graphics, Color color, Dimension dimension, Point position, int borderWidth) {
		graphics.setColor(color);
		graphics.fillRoundRect(position.x-borderWidth, position.y-borderWidth, dimension.width+(borderWidth*2), 
								dimension.height+(borderWidth*2),borderWidth*2,borderWidth*2);
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
		roomLocations.clear();
		exitLocations.clear();
		Point p = new Point(this.getWidth()/2,this.getHeight()/2);
		roomLocations.put(room, p);
		locationSetup(room, p);
	}

	public void locationSetup(Room room, Point previous) {
		Map<ExitDirection, Room> exits = room.getExitMap();
		for(ExitDirection key : exits.keySet()) {
			if(!roomLocations.containsKey(exits.get(key))) { //skip over rooms already in the set
				Point loc = new Point(previous);
				switch(key) {
				case north:
					loc.y-=(ROOM.getHeight()*2)-ROOM_EXIT.getHeight();
					break;
				case east:
					loc.x+=(ROOM.getWidth()*2)-ROOM_EXIT.getWidth();
					break;
				case south:
					loc.y+=(ROOM.getHeight()*2)-ROOM_EXIT.getHeight();
					break;
				case west:
					loc.x-=(ROOM.getWidth()*2)-ROOM_EXIT.getWidth();
				}
				Point exit1Point = new Point(previous);
				exit1Point.x+=ROOM.getWidth()/2;
				exit1Point.y+=ROOM.getHeight()/2;

				Point exit2Point = new Point(loc);
				exit2Point.x+=ROOM.getWidth()/2;
				exit2Point.y+=ROOM.getHeight()/2;

				this.exitLocations.add(new Point[]{exit1Point,exit2Point});

				if(exits.get(key).visited()) {
					roomLocations.put(exits.get(key), loc); //map the room to the point it will be on the map
					locationSetup(exits.get(key), loc); //call the method on the next room.
				}
			}
		}
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
}
