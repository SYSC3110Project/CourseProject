/**
 * 
 */
package courseProject.model;

/**
 * Capture information about a change to the model including a text message for the player.
 * @author Matthew Smith
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ModelChangeEvent {
	
	private String message;
	
	/**
	 * constructor for ModelChangeEvent
	 * @param message
	 */
	
	public ModelChangeEvent(String message)
	{
		this.message=message;
	}
	/**
	 * returns the message meant for the player
	 * @return String
	 */
	public String getMessage()
	{
		return message;
	}
}