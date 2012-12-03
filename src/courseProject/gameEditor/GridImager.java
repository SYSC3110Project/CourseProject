package courseProject.gameEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class GridImager extends JPanel implements MouseListener{

	/**Generated SerialID*/
	private static final long serialVersionUID = 3288631116708502322L;
	protected static final Color DEF_GRID_COLOR = Color.red;
	protected static final Color DEF_BACKGROUND_COLOUR = Color.magenta;
	protected static final int GRID_SECTIONS = 32;
	protected static final int IMAGE_SIZE = 512;
	protected static final String SELECTOR_PATH = "res/selector.png";
	protected BufferedImage image;
	protected BufferedImage selector;
	protected Point selectorLocation;
	private String imagePath;
	private String imagePathRel;
	protected boolean gridVisible;
	protected Color gridColor;

	protected List<GridListener> listeners;
	
	public GridImager() {
		super();
		try {
            selector = ImageIO.read(new File(SELECTOR_PATH)); //load the image of the selector
        } catch (IOException e) {}
		
		selectorLocation = new Point(0,0); //start the selector at 0,0

		gridColor = DEF_GRID_COLOR; //set the color of the grid
		gridVisible = true;
		
		listeners = new ArrayList<GridListener>();
		
		this.addMouseListener(this); // listen for mouse events on self.
	}
	
	public void addGridListener(GridListener listener) {
		listeners.add(listener);
	}

	public void removeGridListener(GridListener listener) {
		listeners.remove(listener);
	}
	
	protected void notifyGridListeners(GridEvent e) {
		for(GridListener l : listeners) {
			l.handleGridEvent(e);
		}
	}
	
	/**
	 * Set the Image which will be drawn under the grid.
	 * @param source the File source for the image to be loaded.
	 */
	public void setImage(File source) {
		this.imagePath = source.getAbsolutePath();
		try {
			source = new File(imagePath);
	        image = ImageIO.read(source);
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(this, "Cannot read File: "+imagePath, "Error Opening File", JOptionPane.ERROR_MESSAGE);
	    }
		this.repaint();
	}

	/**
	 * Return the path of the image being used.
	 * @return The image Path.
	 */
	public String getImagePath() {
		return imagePath;
	}
	public Point getSelectorPoint() {
		return selectorLocation;
	}

	/**
	 * Set the color of the grid drawn on the screen
	 * @param color The new color of the grid.
	 */
	public void setGridColor(Color color) {
		this.gridColor = color;
	}

	/**
	 * Return the color of the grid drawn on the screen.
	 * @return The color of the grid.
	 */
	public Color getGridColor() {
		return gridColor;
	}

	/**
	 * Helper method draws a grid to the specified graphics using 
	 * the GRID_SECTIONS constant for the grid spacing.
	 * @param g The Graphics to draw the grid to.
	 */
	protected void drawGrid(Graphics g) {
		g.setColor(gridColor); 
		for(int step=0;step<=IMAGE_SIZE;step+=GRID_SECTIONS) { //draw the columns
			g.drawLine(step, 0, step, IMAGE_SIZE); 
			g.drawLine(0, step, IMAGE_SIZE, step);  
		} 
	}
	
	public abstract void paintComponent(Graphics g);
		
	protected abstract void handleMouseEvent(MouseEvent e);

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectorLocation.x = e.getX()-e.getX()%32; //set the selector icon to the new grid point (mod for positioning with the grid)
		selectorLocation.y = e.getY()-e.getY()%32;
		handleMouseEvent(e);
		this.repaint();
	}

}