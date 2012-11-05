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


/**
 * the text based view of the game.
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 02/11/2012
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ViewText implements ModelListener {
	
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
        
        notifyInputListeners(new InputEvent(new Command(CommandWord.getCommandFromString(word1), word2)));
        
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
	
}
