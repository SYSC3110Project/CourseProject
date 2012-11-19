/**
 * Item.java contains the class which represents an item object in the game.
 */
package courseProject.model;


/**
 * Items to be picked up in the game.
 * Valid types are specified in the ItemType
 * @author Micheal Hamon
 * @author Denis Dionne
 * @author Matthew Smith
 * @version 04/11/2012
 */
public class Item
{
    private int weight;	
    private String name;	
    private String description;
    private ItemType type;
    private int value;		//damage for weapons, healing power for health, defence for armor

    /**
     * Constructor for Item takes in all the information pertaining to an item.
     * @param name The name of the item
     * @param description A description of the item
     * @param weight The item's weight
     * @param type The type of Item
     * @param value The value for the associated item; damage for weapons, healing power for health, defence for armor
     */
    public Item(String name, String description, int weight, ItemType type, int value)
    {
    	this.weight = weight;
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }
    
    /**
     * Copy Constructor for objects of class Item
     * (used for the undo/redo commands)
     * @param toCopy the item for which to create a copy of.
     */
    public Item(Item toCopy){
    	
    	name = toCopy.name;
    	description = toCopy.description;
    	weight = toCopy.weight;
    	type = toCopy.type;
    	value = toCopy.value;
    }

    /**
     * Gets the weight of the item.
     * @return the weight of the item.
     */
    public int getWeight(){
        return weight;
    }
    
    /**
     * Gets the name of the item.
     * @return the name of the item.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the description of the item.
     * @return the description of the item.
     */
    public String getDesc(){
        return description;
    }
    
    /**
     * Gets what type of item this is.
     * @return the <code>ItemType</code> of this item.
     */
    public ItemType getType(){
    	return type;
    }
    
    /**
     * Gets the value for the item; damage for weapons, healing power for health, defence for armor.
     * @return the value of the item; damage for weapons, healing power for health, defence for armor.
     */
    public int getValue(){
    	return value;
    }
    
    @Override
    public String toString() {
    	StringBuffer buff = new StringBuffer();
    	buff.append(name);
    	buff.append(": ");
    	buff.append(description);
    	buff.append(",\nType: ");
    	buff.append(type.name());
    	buff.append(",\n");
    	buff.append(type.getValueDescription());
    	buff.append(": ");
    	buff.append(value);
    	return buff.toString();
    }
    
    @Override
    public boolean equals(Object other) {
    	if(other == null) { //check first if the other object is null
    		return false;
    	}
    	if(other.getClass().equals(this.getClass())) {
    		Item toCompare = (Item) other;
    		boolean equal = (other == this) || //if they are the same object
    						((this.description.equals(toCompare.description)) && //compare each property of the item
    						(this.name.equals(toCompare.name)) &&
    						(this.type.equals(toCompare.type)) &&
    						(this.value == toCompare.value) &&
    						(this.weight == toCompare.weight));
    		return equal;    		
    	}
    	
    	return false;
    }
}
