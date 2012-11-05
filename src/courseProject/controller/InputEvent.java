/**
 * 
 */
package courseProject.controller;

import courseProject.controller.Command;

/**
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 02/11/2012
 */
public class InputEvent {

	
	protected Command command;
	
	/**
	 * 
	 */
	public InputEvent(Command command) {
		this.command = command;
	}

	public Command getCommand(){
		return command;
	}

}
