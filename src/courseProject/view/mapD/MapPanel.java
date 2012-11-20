package courseProject.view.mapD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

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
	private static final Color BACKGROUND = new Color(232,208,169);
	private static final Color INACTIVE_ROOM = new Color(183,175,163);
	private static final Color ACTIVE_ROOM = new Color(109,146,155);
	private static final Dimension ROOM = new Dimension(30,30);
	private static final Dimension ROOM_EXIT = new Dimension(5,5);

	private Room currentRoom; //for centering the map
	
	private Map<Room,Point> roomLocations;

	public MapPanel() {
		roomLocations = new HashMap<Room,Point>();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);

		// here we clear everything
		drawRect(g, BACKGROUND, new Dimension(getWidth(),getHeight()), new Point(0,0));
		//drawGrid(g);
		
		for(Room key : roomLocations.keySet()) {
			Point p = roomLocations.get(key);
			if(key == currentRoom) { //literally a reference to the same room, not just if the two are equal
				drawRect(g, ACTIVE_ROOM, ROOM, p); //draw the active room in a different color

				g.setColor(Color.black);
				//g.drawString(key.getDescription(), p.x, p.y);
				roomLocations.put(key, p);
			} else if(key.visited()){
				drawRect(g, INACTIVE_ROOM, ROOM, p); //draw the active room in a different color
				g.setColor(Color.black);
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

	private void drawRect(Graphics graphics, Color color, Dimension dimension, Point position) {
		graphics.setColor(color);
		graphics.fillRect(position.x, position.y, dimension.width, dimension.height);
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
		roomLocations.clear();
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
					roomLocations.put(exits.get(key), loc); //map the room to the point it will be on the map
					break;
				case east:
					loc.x+=(ROOM.getWidth()*2)-ROOM_EXIT.getWidth();
					roomLocations.put(exits.get(key), loc); //map the room to the point it will be on the map
					break;
				case south:
					loc.y+=(ROOM.getHeight()*2)-ROOM_EXIT.getHeight();
					roomLocations.put(exits.get(key), loc); //map the room to the point it will be on the map
					break;
				case west:
					loc.x-=(ROOM.getWidth()*2)-ROOM_EXIT.getWidth();
					roomLocations.put(exits.get(key), loc); //map the room to the point it will be on the map
				}
				locationSetup(exits.get(key), loc); //call the method on the next room.
			}
		}
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}
}
