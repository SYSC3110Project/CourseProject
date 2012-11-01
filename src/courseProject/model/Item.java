package courseProject.model;

/**
 * Items to be picked up in the game
 * Valid types are "weapon" "armor" or "health"
 * 
 * @author Micheal Hamon
 * @author Denis Dionne
 * @version 18/10/12
 */
public class Item
{
    // instance variables - replace the example below with your own
    private int weight;	
    private String name;	
    private String desc;
    private ItemType type;
    private int value;		//damage for weapons, healing power for health, defence for armor

    /**
     * Constructor for Item takes in all the information pertaining to an item.
     * @param name The name of the item
     * @param desc A description of the item
     * @param weight The item's weight
     * @param type The type of Item
     * @param value The value for the associated item; damage for weapons, healing power for health, defence for armor
     */
    public Item(String name, String description, int weight, ItemType type, int value)
    {
        this.name = name;
        this.desc = description;
        this.weight = weight;
        this.type = type;
        this.value = value;
    }
    
    
    /**
     * Copy Constructor for objects of class Item
     * (used for the undo/redo commands)
     * @param i
     */
    public Item(Item toCopy){
    	
    	name = toCopy.name;
    	desc = toCopy.desc;
    	weight = toCopy.weight;
    	type = toCopy.type;
    	value = toCopy.value;
    	
    }

    /**
     *  
     */
    public int getWeight(){
        return weight;
    }
    public String getName(){
        return name;
    }
    public String getDesc(){
        return desc;
    }
    public ItemType getType(){
    	return type;
    }
    public int getValue(){
    	return value;
    }
    
    @Override
    public String toString() {
    	return name;
    }
    
    @Override
    public boolean equals(Object other) {
    	if(other == null) { //check first if the other object is null
    		return false;
    	}
    	if(other.getClass().equals(this.getClass())) {
    		Item toCompare = (Item) other;
    		boolean equal = (other == this) || //if they are the same object
    						((this.desc.equals(toCompare.desc)) && //compare each property of the item
    						(this.name.equals(toCompare.name)) &&
    						(this.type.equals(toCompare.type)) &&
    						(this.value == toCompare.value) &&
    						(this.weight == toCompare.value));
    		
    		return equal;    		
    	}
    	
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return 0; //TODO do a proper hashcode
    }
}
