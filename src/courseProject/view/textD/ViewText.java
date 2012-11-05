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


/**
 * the text based view of the game.
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 02/11/2012
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ViewText implements ModelListener {
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
	 * The text view displays by printing a string to the console
	 * @param message
	 */
	public void display(String message)
	{
		System.out.println(message);
		
	}
	/**
	 * updates the view based on the changes to the model
	 */
	public void update(ModelChangeEvent event)
	{
		display(event.getMessage());
	}
	/**
	 * adds a listener to the list of listeners
	 * @param listener that will be added to the list of listeners
	 */
	public void addInputListeners(InputListener listener){
		inputListeners.add(listener);
	}
	
	
	/**
	 * Gets the command from the user
	 * @return the next command input by the user
	 */
    public void getCommand() {
        
        
        notifyInputListeners(texCom.getText());
        
        //return new Command(CommandWord.getCommandFromString(word1), word2);
    }
    
    /**
     * notifies all the listeners of the input event
     * @param e is the input event
     */
    public void notifyInputListeners(InputEvent e){
    	for(InputListener listener : inputListeners){
    		listener.input(e);
    	}
    }
    public void end(){
    	
    }
	
}
