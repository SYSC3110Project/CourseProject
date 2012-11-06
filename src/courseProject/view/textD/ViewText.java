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
 * @version 02/11/2012
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
	 * updates the view based on the changes to the model
	 */
	public void update(ModelChangeEvent event)
	{
		displayMessage(event.getMessage());
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
	 * updates the view based on the changes to the model
	 */
	public void handleModelChangeEvent(ModelChangeEvent event)
	{
		displayMessage(event.getMessage());
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);
	}
	
	@Override
	public void update(double delta) {
		//Nothing to do for text-based game
		
	}
	
	@Override
	public void addInputListener(InputListener listener) {
		inputListeners.add(listener);
	}
	
	@Override
	public void notifyInputListeners(InputEvent event) {
		for(InputListener listener : inputListeners){
    		listener.input(event);
    	}
	}
    public void end(){
    	
    }
	
	@Override
	public void dispose() {
		//Nothing to do for text-based game
	}
	
}
