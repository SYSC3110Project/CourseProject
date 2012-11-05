/**
 * 
 */
package courseProject.view.textD;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import courseProject.controller.Command;
import courseProject.controller.CommandWord;
import courseProject.controller.InputEvent;
import courseProject.controller.InputListener;
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
	
	private List<InputListener> inputListeners;
	private Scanner reader;
		
	/**
	 * constructor of the TextView
	 * Initializes the list of inputListeners and initializes the reader
	 */
	public ViewText(){
		reader = new Scanner(System.in);
		inputListeners = new ArrayList<InputListener>();
	}
	
	/**
	 * Gets the command from the user
	 * @return the next command input by the user
	 */
    public void getCommand() {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }
        tokenizer.close();
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        
        InputEvent evt = new InputEvent(new Command(CommandWord.getCommandFromString(word1), word2));
        notifyInputListeners(evt);
        
        //return ;
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
	
	@Override
	public void dispose() {
		//Nothing to do for text-based game
	}
	
}
