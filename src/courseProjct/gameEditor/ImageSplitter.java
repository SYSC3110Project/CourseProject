package courseProjct.gameEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImageSplitter extends JPanel implements MouseListener{

	/**eclipse Generated SerialID*/
	private static final long serialVersionUID = -4701743223624841432L;
	private static final Color DEF_GRID_COLOR = Color.red;
	private static final int GRID_SECTIONS = 32;
	private static final int IMAGE_SIZE = 512;
	private static final String SELECTOR_PATH = "res/selector.png";
	
	private BufferedImage image;
	private BufferedImage selector;
	private Point selectorLocation;
	private Color gridColor;
	private String imagePath;

	public ImageSplitter(){
		super();
		
		try {
            selector = ImageIO.read(new File(SELECTOR_PATH));
        } catch (IOException e) {}
		
		selectorLocation = new Point(0,0);
		
		this.addMouseListener(this);
		gridColor = DEF_GRID_COLOR;
		this.setPreferredSize(new Dimension(IMAGE_SIZE,IMAGE_SIZE));
	}
	
	
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
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setGridColor(Color color) {
		this.gridColor = color;
	}
	
	public Color getGridColor() {
		return gridColor;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		g.setColor(Color.cyan);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2d = (Graphics2D) g;
		if(image != null) {
			g2d.drawImage(image, 0, 0, IMAGE_SIZE, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE, null);
		}
		
		
		drawGrid(g);

		g2d.drawImage(selector, selectorLocation.x, selectorLocation.y, selectorLocation.x+GRID_SECTIONS, selectorLocation.y+GRID_SECTIONS, 
						0, 0, GRID_SECTIONS, GRID_SECTIONS, null);
	}

	private void drawGrid(Graphics g) {
		g.setColor(gridColor); 
		for(int x=0;x<=IMAGE_SIZE;x+=GRID_SECTIONS) {
			g.drawLine(x, 0, x, IMAGE_SIZE); 
		}
		
		for(int y=0;y<=IMAGE_SIZE;y+=GRID_SECTIONS) {
			g.drawLine(0, y, IMAGE_SIZE, y); 
		} 
	}


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
		selectorLocation.x = e.getX()-e.getX()%32;
		selectorLocation.y = e.getY()-e.getY()%32;
		this.repaint();
	}
}