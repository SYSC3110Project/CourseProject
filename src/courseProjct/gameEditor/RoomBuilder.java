package courseProjct.gameEditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import courseProject.Util;


/**
 * The RoomBuilder class represents what a room will look like when it is being drawn in the game.
 * @author Matthew Smith
 * @version 11/25/2012
 */
public class RoomBuilder extends GridImager{

	/**generated SerialID*/
	private static final long serialVersionUID = -3585892715409340417L;
	
	private Point[][] levelLayout;
	private Point[][] levelObjects;

	/**
	 * RoomBuilder is used to represent the layout and content of a Game Room before it is saved to an XML file.
	 */
	public RoomBuilder() {
		int layoutSize = IMAGE_SIZE/GRID_SECTIONS;

		
		// make an array for each grid section and x/y coords for the image section it will show
		levelLayout = new Point[layoutSize][layoutSize]; 
		levelObjects = new Point[layoutSize][layoutSize];
		for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				levelLayout[row][col] = new Point(0,0);
			}
		}		
		this.setPreferredSize(new Dimension(IMAGE_SIZE,IMAGE_SIZE));
	}
	
	/**
	 * Set the point on the Image which will be drawn at the selector's location
	 * @param startLocation The location to draw from on the source image
	 */
	public void setBackgroundAtSelector(Point startLocation) {
		int row = selectorLocation.y/GRID_SECTIONS;
		int col = selectorLocation.x/GRID_SECTIONS;
		
		levelLayout[row][col].setLocation(startLocation);
	}
	
	/**
	 * Set the point on the Image which will be drawn at the selector's location
	 * @param startLocation The location to draw from on the source image
	 */
	public void setObjectAtSelector(Point startLocation) {
		int row = selectorLocation.y/GRID_SECTIONS;
		int col = selectorLocation.x/GRID_SECTIONS;
		
		levelObjects[row][col] = new Point(startLocation);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(DEF_BACKGROUND_COLOUR);
		g.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE); //clear the screen to remove artifacts


		if(image != null) { // if there is an image to draw, draw it
			for(int row = 0;row<IMAGE_SIZE;row+=GRID_SECTIONS) {
				for(int col = 0;col<IMAGE_SIZE;col+=GRID_SECTIONS) {
					
					int rowIndex = row/GRID_SECTIONS;
					int colIndex = col/GRID_SECTIONS;

					g2d.drawImage(image, col, row, col+GRID_SECTIONS, row+GRID_SECTIONS,  //draw the background content
							levelLayout[rowIndex][colIndex].x, levelLayout[rowIndex][colIndex].y,
							levelLayout[rowIndex][colIndex].x+GRID_SECTIONS, levelLayout[rowIndex][colIndex].y+GRID_SECTIONS, null);
					

					if(levelObjects[rowIndex][colIndex] != null) { //if there is a level Object there, draw it
						g2d.drawImage(image, col, row, col+GRID_SECTIONS, row+GRID_SECTIONS, 
								levelObjects[rowIndex][colIndex].x, levelObjects[rowIndex][colIndex].y,
								levelObjects[rowIndex][colIndex].x+GRID_SECTIONS, levelObjects[rowIndex][colIndex].y+GRID_SECTIONS, null);
					}
					
				}
			}
		}

		if(gridVisible) {
			drawGrid(g); //draw the grid 
		}

		//Draw the selector to the screen over top of everything else
		g2d.drawImage(selector, selectorLocation.x, selectorLocation.y, selectorLocation.x+GRID_SECTIONS, selectorLocation.y+GRID_SECTIONS, 
				0, 0, GRID_SECTIONS, GRID_SECTIONS, null);
	}

	/**
	 * 
	 * @param doc
	 * @return
	 */
	public Element toXMLElement(Document doc) {
		Element objects = doc.createElement("objects");
		objects.setTextContent(this.objectLayerToString());
		
		Element background = doc.createElement("background");
		background.setTextContent(this.backgroundLayerToString());

		Element layout = doc.createElement("layout");
		layout.appendChild(background);
		layout.appendChild(objects);
		
		return layout;
	}
	
	/**
	 * 
	 * @return
	 */
	public String objectLayerToString() {
		StringBuffer buff = new StringBuffer();

		int layoutSize = IMAGE_SIZE/GRID_SECTIONS;
		
		for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				if(levelObjects[row][col] != null) {
					int x = levelObjects[row][col].x/GRID_SECTIONS;
					int y = levelObjects[row][col].y/GRID_SECTIONS;
					
					buff.append(Util.intToHexChar(x));
					buff.append(Util.intToHexChar(y));
					buff.append(" ");
				} else {
					buff.append("XX ");
				}
			}
			buff.append("\n");
		}	
		
		return buff.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String backgroundLayerToString() {
		StringBuffer buff = new StringBuffer();

		int layoutSize = IMAGE_SIZE/GRID_SECTIONS;

		for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) { //initialize each point starting with 0,0
				int x = levelLayout[row][col].x/GRID_SECTIONS;
				int y = levelLayout[row][col].y/GRID_SECTIONS;
				
				buff.append(Util.intToHexChar(x));
				buff.append(Util.intToHexChar(y));
				buff.append(" ");
			}
			buff.append("\n");
		}	
		
		return buff.toString();
	}
	
	@Override
	protected void handleMouseEvent(MouseEvent e) {
		this.notifyGridListeners(new GridEvent(this, selectorLocation.x, selectorLocation.y));
	}

}
