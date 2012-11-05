/**
 * 
 */
package courseProject.model;

import java.util.List;

import courseProject.view.twoD.drawable.Drawable2D;


/**
 * Capture information about a change to the model including a text message for the player.
 * @author Matthew Smith
 * @author Denis Dionne
 * @author Andrew Venus
 * @version 05/11/2012
 */
public class ModelChangeEvent {
	
	List<Drawable2D> drawable;
	private String message;
	
	/**
	 * Event that describes the change in the game
	 * @param message is the String that will be displayed to the user
	 * @param drawable is the List of all the objects that we wish to draw to the screen
	 */
	public ModelChangeEvent(String message, List<Drawable2D> drawable) {
		this.message = message;
		this.drawable = drawable;
	}
	
	
	/**
	 * Get the message that is contained within the event
	 * @return the message that is contained within the event
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * get the list of all objects that we wish to draw to the screen
	 * @return the list of all objects that we wish to draw to the screen
	 */
	public List<Drawable2D> getDrawable(){
		return drawable;
	}

}
