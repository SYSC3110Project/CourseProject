/**
 * 
 */
package courseProject.controller;

/**
 * @author Matthew Smith
 * @author Mike Hamon
 * @version 01/11/2012
 * @author Denis Dionne
 * @version 02/11/2012
 */
public class InputEvent {
	private String command;
	
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
