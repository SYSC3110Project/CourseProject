package courseProject.model;





/**
 * @author Matthew Smith
 * @author Andrew Venus
 * @version 02/11/2012
 */

public interface ModelListener {
	
	/**
	 * Updates the information about the game
	 * @param e is the event that contains the information about the change in the game
	 */
	public void update(ModelChangeEvent e);

	public void update(ModelChangeEvent event);
	
	
}
