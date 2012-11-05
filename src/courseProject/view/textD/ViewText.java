/**
 * 
 */
package courseProject.view.textD;

import java.util.ArrayList;
import java.util.List;

import courseProject.controller.CommandInterpreter;
import courseProject.controller.InputEvent;
import courseProject.controller.InputListener;
import courseProject.controller.TextController;
import courseProject.model.ModelChangeEvent;
import courseProject.model.ModelListener;


/**
 * the text based view of the game.
 * @author Matthew Smith
 * @author Andrew Venus
 * @author Mike Hamon
 * @version 02/11/2012
 */
public class ViewText implements ModelListener {
    private List<InputListener> inList;
    private TextController textC;
	public ViewText(){

    	inList = new ArrayList<InputListener>();
    	inList.add(new CommandInterpreter());
    	textC = new TextController();
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
		tell(textC.getText());
	}
	private void tell(InputEvent ie){
		for(InputListener IL : inList){
			IL.update(ie);
		}
	}
}
