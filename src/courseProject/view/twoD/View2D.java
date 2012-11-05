package courseProject.view.twoD;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import courseProject.controller.InputEvent2D;
import courseProject.model.ModelChangeEvent;
import courseProject.view.twoD.drawable.Drawable2D;
import courseProject.view.twoD.drawable.Player2D;
import courseProject.view.textD.ViewText;


/**
 * View2D is responsible for drawing all the elements to the screen
 * @author Denis Dionne
 * @version 04/11/2012
 *
 */
public class View2D extends ViewText implements MouseListener{

	private JFrame mainWindow;
	private JPanel gamePanel;
	private List<Drawable2D> drawList;
	
	
	/**
	 * Constructor for the 2D view, creates JPanel within a frame and initializes
	 * the list of listeners (input and drawable)
	 */
	public View2D(){
		super();
		mainWindow = new JFrame("World of the Nameless");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(400, 400);
		gamePanel = new JPanel();
		mainWindow.add(gamePanel);
		drawList = new ArrayList<Drawable2D>();
		mainWindow.setVisible(true);
		gamePanel.addMouseListener(this);
		
	}
	
	
	/**
	 * paints all the drawable components to the gamePanel
	 * @param g
	 */
	public void paint(Graphics g) {
		Graphics2D graphics2D = (Graphics2D)g;
		for(Drawable2D drawable : drawList){
			drawable.update(0);
			drawable.draw(graphics2D);
		}
	}
	
	/**
	 * Moves the character to the coordinates specified by the mouse event
	 * @param e
	 */
	public void moveCharacter(InputEvent2D e){
		//
		for(Drawable2D drawable : drawList){
			if(drawable.getClass().equals(Player2D.class)){
				drawable.moveTo(e.getCoordinates());
			}
		}
		paint(gamePanel.getGraphics());
	}
	
	
	/**
	 * updates the elements needed to be drawn (if player changes room, the room, its items and the monsters need to be changed)
	 * @param e is the event that contains all the information about the change in game
	 */
	public void update(ModelChangeEvent e){
		System.out.println(e.getMessage());
		drawList = e.getDrawable();
		paint(gamePanel.getGraphics());
		
	}
	/**
	 * When you press the mouse, it generates an event that gets sent to all inputListeners, notifying them of the coordinates
	 * of the mouse at the time it was released
	 */
	@Override
	public void mousePressed(MouseEvent mouse) {
		notifyInputListeners(new InputEvent2D(new Point(mouse.getX(),mouse.getY())));
		
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
