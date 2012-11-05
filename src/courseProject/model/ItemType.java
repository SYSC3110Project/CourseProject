/**
 * ItemType.java contains an enumeration which represents the different types of items which are present in the game.
 */
package courseProject.model;

/**
 * Each enumeration represents a type of item which will be in the game.
 * @author Matthew Smith
 * @version 04/11/2012
 */
public enum ItemType {
	weapon("Attack Strength"), armor("Defence"), health("Health Restoration");
	
	/**A description of what the type of item's value from the item class represents.*/
	private String valueDescription;
	
	/**
	 * Initializes the type with a description.
	 * @param description A description of what the type of item's value from the item class represents.
	 */
	private ItemType(String valueDescription) {
		this.valueDescription = valueDescription;
	}
	
	/**
	 * Gets a description of what the item's 'value' represents.
	 * @return a description of what the item's 'value' represents.
	 */
	public String getValueDescription() {
		return valueDescription;
	}
}
