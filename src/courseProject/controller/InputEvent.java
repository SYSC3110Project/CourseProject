/**
 * 
 */
package courseProject.controller;

/**
 * @author Matthew Smith
 * @author Mike Hamon
 * @author Denis Dionne
 * @version 02/11/2012
 */
public class InputEvent {
	
	protected Command command;
	
	/**
	 * 
	 */
	public InputEvent(Command command) {
		// TODO Auto-generated constructor stub
		this.command = command;
	}
	
	public Command getCommand(){
		return command;
	}

}
