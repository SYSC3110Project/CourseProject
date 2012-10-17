

/**
 * Items to be picked up in the game
 * Valid types are "weapon" "armor" or "health"
 * "health" types should have positive value values
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private int weight;	
    private String name;	
    private String desc;
    private String type;
    private int value;		//damage for weapons, healing power for health, defence for armor

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String desc, int weight, String type, int value)
    {
        this.name = name;
        this.desc = desc;
        this.weight = weight;
        this.type = type;
        this.value = value;
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
    public String getType(){
    	return type;
    }
    public int getValue(){
    	return value;
    }
}
