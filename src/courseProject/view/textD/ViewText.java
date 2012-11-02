/**
 * 
 */
package courseProject.view.textD;

import courseProject.model.ModelChangeEvent;
import courseProject.model.ModelListener;


/**
 * @author Matthew Smith
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ViewText implements ModelListener {
	

	//The text view displays by printing a string to the console
	public void display(String message)
	{
		System.out.println(message);
		
	}
	
	//updates the view based on the changes to the model
	public void update(ModelChangeEvent event)
	{
		display(event.getMessage());
	}
}
