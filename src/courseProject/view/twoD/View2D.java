package courseProject.view.twoD;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import courseProject.controller.Command;
import courseProject.controller.CommandWord;
import courseProject.controller.InputEvent2D;
import courseProject.model.ModelChangeEvent;
import courseProject.view.twoD.drawable.Drawable2D;
import courseProject.view.twoD.drawable.Drawable2DArea;
import courseProject.view.twoD.drawable.Item2D;
import courseProject.view.twoD.drawable.Monster2D;
import courseProject.view.twoD.drawable.Player2D;
import courseProject.view.twoD.drawable.Room2D;
import courseProject.view.textD.ViewText;


/**
 * View2D is responsible for drawing all the elements to the screen
 * @author Denis Dionne
 * @version 04/11/2012
 *
 */
public class View2D extends ViewText implements MouseListener, ActionListener{

	private JFrame mainWindow;
	//private JPanel gamePanel;
	private Drawable2DArea drawArea;
	private List<Drawable2D> drawList;
	
	private JButton inventoryButton;
	private JTextArea textArea;
	
	
	/**
	 * Constructor for the 2D view, creates JPanel within a frame and initializes
	 * the list of listeners (input and drawable)
	 */
	public View2D(){
		super();
		
		drawList = new ArrayList<Drawable2D>();
		
		mainWindow = new JFrame("World of the Nameless");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setBounds(100, 100, 730, 455);
		mainWindow.setResizable(false);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setVisible(true);
		
		drawArea = new Drawable2DArea();
		drawArea.addMouseListener(this);
		
		inventoryButton = new JButton("Inventory");
		inventoryButton.addActionListener(this);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		JPanel gameContent = new JPanel(new GridLayout(1,2));
		
		gameContent.add(drawArea);
		gameContent.add(textArea);
		
		mainWindow.add(gameContent, BorderLayout.CENTER);
		mainWindow.add(inventoryButton, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Update the window bounds if a room is a different size
	 * @param rect The new rectangle bounds of the window.
	 */
	public void updateBounds(Rectangle rect) {
		mainWindow.setBounds(rect);
	}
	
	@Override
	public void displayMessage(String message) {
		textArea.append(message);
		textArea.append("\n");
	}
	
	@Override
	public void update(double delta) {
		
		for(Drawable2D drawable : drawList){
			drawable.update(delta);
			 //collision detection is complicated
			if(drawable.getClass().equals(Player2D.class)) { //if the element is the player, 
				
				for(Drawable2D other : drawList){ //loop over each drawable
					if(other.getClass().equals(Player2D.class)) {
						continue; //continue on if it is the room or the player again
					}
					if(other.getClass().equals(Room2D.class)) {
						String direction = ((Room2D)other).inExitBounds(drawable.getBounds());
						if(direction!=null) {
							notifyInputListeners(new InputEvent2D(new Command(CommandWord.go, direction)));
							Point newPlayerLocation = new Point(other.getBounds().width/2, other.getBounds().height/2);
							drawable.setLocation(newPlayerLocation);
							drawable.moveTo(newPlayerLocation);
						}
					}
					
					if(drawable.collidesWith(other)) { //check if it is colliding with the other drawable
						if(other.getClass().equals(Monster2D.class)) {
							String monsterName = ((Monster2D)other).getName(); //send input messages if it does collide
							notifyInputListeners(new InputEvent2D(new Command(CommandWord.attack, monsterName)));
						}
						if(other.getClass().equals(Item2D.class)) {
							String itemName = ((Item2D)other).getName();
							notifyInputListeners(new InputEvent2D(new Command(CommandWord.take, itemName)));
						}
					}
				}
			}
		}
		
		drawArea.repaint();
	}
	
	/**
	 * Moves the character to the coordinates specified by the mouse event
	 * @param e
	 */
	public void moveCharacter(InputEvent2D e){
		for(Drawable2D drawable : drawList){
			if(drawable.getClass().equals(Player2D.class)){
				drawable.moveTo(e.getCoordinates());
			}
		}
	}
	
	@Override
	public void handleModelChangeEvent(ModelChangeEvent e){
		displayMessage(e.getMessage());
		drawList = e.getDrawable();
		drawArea.updateDrawable(drawList);
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
	public void mouseClicked(MouseEvent mouse) {
	}


	@Override
	public void mouseEntered(MouseEvent mouse) {
	}


	@Override
	public void mouseExited(MouseEvent mouse) {
		
	}


	@Override
	public void mouseReleased(MouseEvent mouse) {
		
	}
	@Override
	public void end(){
		mainWindow.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		notifyInputListeners(new InputEvent2D(new Command(CommandWord.inventory,null)));
	}

	
	
	
}
