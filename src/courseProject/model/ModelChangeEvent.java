/**
 * 
 */
package courseProject.model;

/**
 * @author Matthew Smith
 * @author Andrew Venus
 * @version 02/11/2012
 */
public class ModelChangeEvent {
	
	private String message;
	private boolean sucess;
	
	public ModelChangeEvent(String message)
	{
		this.message=message;
		sucess=true;
	}
	
	public ModelChangeEvent(String message,boolean sucess)
	{
		this.message=message;
		this.sucess=sucess;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public boolean getSucess()
	{
		return sucess;
	}
}