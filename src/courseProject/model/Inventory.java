/**
 * The Inventory.java file contains the class which will represent an inventory of items in the game.
 */
package courseProject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An inventory is a list of Items and there total weight. 
 * Rooms and Creatures have an inventory.
 * @author Andrew Venus
 * @version 04/11/12
 */
public class Inventory {
	private List<Item> items;
	private int mass;
	
	public Inventory()
	{
		items=new ArrayList<Item>();
		mass=0;
	}
	
	
	/**
     * Copy Constructor for objects of class Inventory
     * @param inv
     */
	public Inventory(Inventory inv)
	{
		items=new ArrayList<Item>();
		mass=0;
    	for(int i=0;i<inv.getSize();i++)
    	{
    		items.add(inv.getItem(i));
    	}
	}
	/**
	 * adds the item to the inventory
	 * @param item
	 */
	public void add(Item item)
	{
		items.add(item);
		mass+=item.getWeight();
	}
	
	public void remove(Item item)
	{
		items.remove(item);
		mass-=item.getWeight();
	}
	
	public int getMass()
	{
		return mass;
	}
	
	public int getSize()
	{
		return items.size();
	}
	
	public Item getItem(int i)
	{
		return items.get(i);
	}
	
	public Item getItem(String name)
	{
		for (Item i : items){
            if(i.getName().equals(name)){
            	return getItem(i);
            }
		}
		return null;
	}
	
	public Item getItem(Item i)
	{
		return items.get(items.indexOf(i));
	}
	
	 /**
     * returns string naming all of the items in the inventory
     * @return the name of each item in the room
     */
    public String getItemNames(){
    	StringBuffer buff = new StringBuffer();
        for (int i=0;i<items.size();i++){
            buff.append(items.get(i));
            if(i!=items.size()-1) {
            	buff.append(", ");
            }
        }
        return buff.toString();
    }
    
    public boolean isEmpty()
    {
    	return items.isEmpty();
    }
}
