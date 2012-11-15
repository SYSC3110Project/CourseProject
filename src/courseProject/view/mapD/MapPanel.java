package courseProject.view.mapD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

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
	private static final Color BACKGROUND = new Color(255,204,153);
	private static final Color INACTIVE_ROOM = new Color(204,51,0);
	private static final Color ACTIVE_ROOM = new Color(255,204,153);
	private static final Dimension ROOM = new Dimension(200,200);
	private static final Dimension ROOM_EXIT = new Dimension(50,50);
	
	private Room currentRoom; //for centering the map

	public MapPanel() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		// here we clear everything
		drawRect(g, BACKGROUND, new Dimension(getWidth(),getHeight()), new Point(0,0));
        
        //draw the current room at the center of the window
		Point p = new Point((this.getHeight()-ROOM.height)/2,(this.getWidth()-ROOM.width)/2);
        drawRect(g, ACTIVE_ROOM, ROOM, p);
        
        
	}
	
	private void drawRect(Graphics graphics, Color color, Dimension dimension, Point position) {
		graphics.setColor(color);
		graphics.fillRect(position.x, position.y, dimension.width, dimension.height);
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
}
