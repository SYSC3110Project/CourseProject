package courseProject.gameEditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * ImageSplitter is a class which can select sections of images and 
 * return the information needed to re-draw that section of the image.
 * It will be used for picking sections of a tileset to draw in a given Room.
 * 
 * @author Matthew Smith
 * @version 11/23/2012
 */
public class ImageSplitter extends GridImager{

	/**eclipse Generated SerialID*/
	private static final long serialVersionUID = -4701743223624841432L;
	/**
	 * Constructor for the ImageSplitter.
	 */
	public ImageSplitter(){
		super();

		this.setPreferredSize(new Dimension(IMAGE_SIZE,IMAGE_SIZE));
	}
	
	@Override
	public void paintComponent(Graphics g) {

		g.setColor(DEF_BACKGROUND_COLOUR);
		g.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE); //clear the screen to remove artifacts
		
		
		Graphics2D g2d = (Graphics2D) g;
		if(image != null) { // if there is an image to draw, draw it
			g2d.drawImage(image, 0, 0, IMAGE_SIZE, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE, null);
		}
	
		if(gridVisible) {
			drawGrid(g); //draw the grid 
		}
				
		//Draw the selector to the screen over top of everything else
		g2d.drawImage(selector, selectorLocation.x, selectorLocation.y, selectorLocation.x+GRID_SECTIONS, selectorLocation.y+GRID_SECTIONS, 
						0, 0, GRID_SECTIONS, GRID_SECTIONS, null);
	}

	@Override
	protected void handleMouseEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}