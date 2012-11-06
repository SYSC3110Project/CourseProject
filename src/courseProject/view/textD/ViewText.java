/**
 * 
 */
package courseProject.view.textD;

import java.util.ArrayList;
import java.util.List;
import courseProject.controller.InputEvent;
import courseProject.controller.InputListener;
import courseProject.controller.TextController;
import courseProject.model.ModelChangeEvent;
import courseProject.model.ModelListener;
import courseProject.view.View;


/**
 * The text based view of the game.
 * @author Matthew Smith
 * @author Denis Dionne
 * @author Mike Hamon
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ViewText implements ModelListener, View {
	private TextController texCom;
	private List<InputListener> inputListeners;
	
	
	/**
	 * constructor of the TextView
	 * Initializes the list of inputListeners and initializes the reader
	 */
	public ViewText(){
		inputListeners = new ArrayList<InputListener>();
		texCom = new TextController();
	}

	/**
	 * Gets the command from the user and notifies listener
	 */
    public void getCommand() {
        notifyInputListeners(texCom.getText());
    }
    
    /**
	 * updates the view based on the changes to the model
	 * @param event Event object encapsulating the changes
	 */
	public void handleModelChangeEvent(ModelChangeEvent event)
	{
		displayMessage(event.getMessage());
	}
	
	/**
	 * Prints a message to the console
	 * @param message the message to be printed
	 */
	@Override
	public void displayMessage(String message) {
		System.out.println(message);
	}
	/**
	 * Updates the game world
	 * Text based game is not real time and therefore does not use delta
	 * @param delta the change in time since the last update
	 */
	@Override
	public void update(double delta) {
		getCommand();
	}
	/**
	 * adds listener to list of listeners
	 * @param listener the InputListener to be added to the list
	 */
	@Override
	public void addInputListener(InputListener listener) {
		inputListeners.add(listener);
	}
	/**
	 * notifies all listeners of an event
	 * @param event the event to notifiy the listeners about
	 */
	@Override
	public void notifyInputListeners(InputEvent event) {
		for(InputListener listener : inputListeners){
    		listener.input(event);
    	}
	}
	/**
	 * tells the player thanks
	 * (is used more for 2D)
	 */
	@Override
	public void dispose() {
		displayMessage("Thank you for playing\n");
	}
	
}
