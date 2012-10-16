import java.util.*;
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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private Map<String,Room> exits;
    private List<Item> items;

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
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String dir, Room exit) 
    {
        exits.put(dir,exit);
    }

    public Room getExits (String dir){
        return exits.get(dir);
    }
    
    public String getLoc(){
        String dec = "";
        dec = dec + "You are " + this.description + "\n";
        dec = dec + "Exits: ";
        for (String dir : exits.keySet()){
            dec = dec + dir + " ";
        }
        return dec;
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * creates an item in the room
     * for start up
     * @param name
     * @param desc
     * @param weight
     * @param type
     * @param value
     */
    public void setItem(String name, String desc, int weight, String type, int value){
        items.add(new Item(name,desc,weight,type,value));
    }
    /**
     * list of all items in the room
     * @return
     */
    public String getItemNames(){
        String names = "";
        for (Item i : items){
            names = names + i.getName() + " ";
        }
        return names;
    }
    /**
     * full details for an item in the room
     * @param name
     * @return
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
     * weight of an item in the room
     * @param name
     * @return
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
     * takes item
     * @param name
     * @return
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
     * drops item
     * @param i
     */
    public void drop(Item i){
        items.add(i);
    }
}
