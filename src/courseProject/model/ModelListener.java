/**
 * 
 */
package courseProject.model;





/**
 * @author Matthew Smith
 * @version 01/11/2012
 */
public interface ModelListener {
	
	/**
	 * Updates the information about the game
	 * @param e is the event that contains the information about the change in the game
	 */
	public void update(ModelChangeEvent e);

}
