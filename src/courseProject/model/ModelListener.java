/**
 * ModelListener.java contains the interface with which will be implemented for any class that needs to listen for model changes.
 */
package courseProject.model;

/**
 * The ModelListener interface is implemented in any class which needs to be informed when the game model changes.
 * @author Matthew Smith
 * @author Andrew Venus
 * @version 02/11/2012
 */
public interface ModelListener {
	
	/**
	 * Updates the information about the game
	 * @param event is the event that contains the information about the change in the game
	 */
	public void handleModelChangeEvent(ModelChangeEvent event);
}
