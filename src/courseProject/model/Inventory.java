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
	
	/**
	 * Constructor for an empty Inventory
	 */
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
	
	/**
	 * removes item form the inventory.
	 * @param item
	 */
	public void remove(Item item)
	{
		items.remove(item);
		mass-=item.getWeight();
	}
	/**
	 * returns the total weight of the inventory
	 * @return mass
	 */
	public int getMass()
	{
		return mass;
	}
	
	/**
	 * returns how many items are in the inventory
	 * @return size
	 */
	public int getSize()
	{
		return items.size();
	}
	
	
	
	/**
	 * returns the first item with the given name
	 * @param name
	 * @return
	 */
	public Item getItem(String name)
	{
		for (Item i : items){
            if(i.getName().equals(name)){
            	return getItem(i);
            }
		}
		return null;
	}
	
	
	/**
	 * returns the ith item in the inventory
	 * @param i
	 * @return item
	 */
	public Item getItem(int i)
	{
		return items.get(i);
	}
	
	/**
	 * returns the item taht is the given item
	 * @param i
	 * @return item
	 */
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
    
    /**
     * returns whether the inventory is empty
     * @return empty
     */
    public boolean isEmpty()
    {
    	return items.isEmpty();
    }
    
    public List<Item> getAllItems(){
    	return new ArrayList<Item>(items);
    }
}
