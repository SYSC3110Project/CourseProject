/**
 * The View.java file contains the view interface which all views will be interfaced with.
 */
package courseProject.view;

import courseProject.controller.InputEvent;
import courseProject.controller.InputListener;

/**
 * @author Matthew Smith
 * @version 05/11/2012
 */
public interface View {
	
	/**
	 * Show a message in the view.
	 * @param message The message to display on screen.
	 */
	public void displayMessage(String message);
	
	/**
	 * Update method will update all components of the view.
	 * @param delta the time difference since the last update call.
	 */
	public void update(double delta);
	
	/**
	 * Add an input listener to the view.
	 * @param listener The <code>InputListener</code> to add.
	 */
	public void addInputListener(InputListener listener);
	
	/**
	 * Notify all input listeners of an input event.
	 * @param event The input event to notify about.
	 */
	public void notifyInputListeners(InputEvent event);
	
	
	/**
	 * Closes the view and ends any threads which are active in the view.
	 */
	public void dispose();
}
