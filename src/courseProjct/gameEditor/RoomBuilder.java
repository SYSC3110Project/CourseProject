/**
 * 
 */
package courseProjct.gameEditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * @author Matthew Smith
 *
 */
public class RoomBuilder extends GridImager{

	/**generated SerialID*/
	private static final long serialVersionUID = -3585892715409340417L;
	
	private Point[][] levelLayout;

	public RoomBuilder() {
		int layoutSize = IMAGE_SIZE/GRID_SECTIONS;

		// make an array for each grid section and x/y coords for the image section it will show
		levelLayout = new Point[layoutSize][layoutSize]; 
		for(int row = 0;row<layoutSize;row++) {
			for(int col = 0;col<layoutSize;col++) {
				levelLayout[row][col] = new Point(row*GRID_SECTIONS,col*GRID_SECTIONS);
			}
		}		
		this.setPreferredSize(new Dimension(IMAGE_SIZE,IMAGE_SIZE));
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

				g2d.drawImage(image, col, row, col+GRID_SECTIONS, row+GRID_SECTIONS, 
						levelLayout[rowIndex][colIndex].x, levelLayout[rowIndex][colIndex].y,
						levelLayout[rowIndex][colIndex].x+GRID_SECTIONS, levelLayout[rowIndex][colIndex].y+GRID_SECTIONS, null);
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

	@Override
	protected void handleMouseEvent(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
