
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labeled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author Micheal Hamon
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 18/10/12
 */

package courseProject.model;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class Room 
{
    private String description;
    private Map<String,Room> exits;
    private List<Item> items;
    private List<Monster> monsters;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        items = new ArrayList<Item>();
        monsters = new ArrayList<Monster>();
    }
    
    /**
     * Copy Constructor for objects of class Room
     * @param r the room to copy from
     */
    public Room(Room r){
    	description = r.description;
    	exits = new HashMap<String, Room>();
    	exits.putAll(r.exits);
    	
    	List<Item> itemList = new ArrayList<Item>();
    	for(Item i: r.items){
    		itemList.add(new Item(i));
    	}
    	items  = itemList;
    	
    	List<Monster> monsterList = new ArrayList<Monster>();
    	for(Monster m: r.monsters){
    		monsterList.add(new Monster(m));
    	}
    	monsters  = monsterList;
    }

    /**
     * Adds an exit to this room in the passed direction.
     * @param dir The direction of the exit.
     * @param exit The room the exit connects to.
     */
    public void addExit(String dir, Room exit) 
    {
        exits.put(dir,exit);
    }

    /**
     * Gets the exit accociated with the passed direction
     * @param dir the direction of the exit to get
     * @return the connecting room in the passed direction
     */
    public Room getExit (String dir){
        return exits.get(dir);
    }
    
    /**
     * returns a Map of each of the exits in a map with their direction as the key
     * @return the Map of the exits from the room.
     */
    public Map<String,Room> getExitMap(){
    	return exits;
    }
    
    /**
     * add a monster to the room with the passed parameters
     * @param m the monster to add
     */
    public void addMonster(Monster m){
    	monsters.add(m);
    }
    
    /**
     * returns the Monster with the passed name
     * @param name the name of the Monster
     * @return the monster with the passed name
     */
    public Monster getMonster(String name){
    	for (Monster m : monsters){
    		if(m.getName().equals(name)){
    			return m;
    		}
    	}
    	return null;
    }
    
    /**
     * returns a string with each of the names of the monsters in the room.
     * @return a string of all monster's names
     */
    public String getMonsterNames(){
    	StringBuffer monNames = new StringBuffer();
    	for(Monster m : monsters){
    		if(!m.isDead()){
    			monNames.append(m.getName());
    		}
    	}
    	return monNames.toString();
    }
    
    /**
     * iterates through each monster and performs their attack on the player.
     * @param p the player which the monsters are attacking
     * @return a string description of the battle
     */
    public String monsterAttack(Player p){
    	StringBuffer s = new StringBuffer();
    	for (Monster m : monsters){
    		if(!m.isDead()){ // if the monster is dead, it doesn't attack
				s.append(m.getName());
				s.append(" attacks, ");
				s.append(m.attack(p));
				s.append("\n");
    		}
    	}
    	return s.toString();
    }
    
    /**
     * revives all monsters upon player leaving a room
     */
    public void revMonster(){
    	for (Monster m : monsters){
    		m.rev();
    	}
    }
    
    /**
     * returns the possible exits from this location
     * @return a String description of the exits from this location
     */
    public String getLoc(){
        StringBuffer dec = new StringBuffer();
        dec.append("You are ");
        dec.append(this.description);
        dec.append("\nExits: ");
        
        Iterator<String> dir = exits.keySet().iterator(); 
        while(dir.hasNext()){
        	dec.append(dir.next());
            if(dir.hasNext()) {
            	dec.append(", ");
            }
        }
        
        return dec.toString();
    }
    
    /**
     * returns the description of the room.
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * creates an item in the room
     * for start up
     * @param name the name of the item
     * @param desc a description of the item
     * @param weight the weight of the item
     * @param type the type of item
     * @param value the value modifier for the item
     */
    public void setItem(String name, String desc, int weight, ItemType type, int value){
        items.add(new Item(name,desc,weight,type,value));
    }
    
    /**
     * returns string naming all of the items in the room
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
     * returns the full details for an item in the room
     * @param name name of the item to check
     * @return a full description of the item
     */
    public String getItemFull(String name){
        for (Item i : items){
            if(i.getName().equals(name)){
                return ""+i.getName()+": "+i.getDesc()+" ("+i.getWeight()+" lbs)";
            }
        }
        return "";
    }
    
    /**
     * check weight of an item in the room
     * @param name name of item to check
     * @return the weight of the item
     */
    public int getItemWeight(String name){
        for (Item i : items){
            if(i.getName().equals(name)){
                return i.getWeight();
            }
        }
        return 0;
    }
    
    /**
     * takes the item specified, this will remove the item from the room
     * @param name the name of the item to pick up
     * @return the item to pick up
     */
    public Item pickup(String name){
        for (Item i : items){
            if(i.getName().equals(name)){
                items.remove(i);
                return i;
            }
        }
        return null;
    }
    
    /**
     * drops the passed item in the room
     * @param i the item to drop
     */
    public void drop(Item i){
        items.add(i);
    }
}
