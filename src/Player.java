import java.util.*;
/**
 * The player character
 * The plan is to refactor 'player' into an 'alive' abstract class and have 'player' and 'monster' extend it
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private Room currRoom;
    private List<Item> inv;
    private int limit;
    private int mass;
    private int health;
    private int healthMax;
    private Item armor;
    private Item weapon;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room room){
        currRoom = room;
        inv = new ArrayList<Item>();
        limit = 20;		//max weight
        mass = 0;		//current weight
        health = 17;	//current health
        healthMax = 20;	//max health
    }
    /**
     * moves the player to a new room
     * @param direction
     * @return
     */
    public String setRoom(String direction){
        Room nextRoom = currRoom.getExits(direction);
        if (nextRoom == null) {
            return "There is no door!";
        }
        else {
            currRoom = nextRoom;
            return getLoc();
        }
    }
    public Room getRoom(){
        return currRoom;
    }
    
    public String getLoc(){
        return currRoom.getLoc();
    }
    /**
     * Gets details about the current room
     * (exits and items)
     * @param name
     * @return
     */
    public String look(){
        return currRoom.getItemNames();
    }
    /**
     * Gets details on item
     * @param name
     * @return
     */
    public String lookat(String name){
        String desc = currRoom.getItemFull(name);
            if(desc.equals("")){
                return "There is no such item in this room";
            }else{
                return desc;
            }
    }
    /**
     * picks up item from current room
     * @param name
     * @return
     */
    public String pickup(String name){
        int diff = (mass+currRoom.getItemWeight(name))-limit;
        if(diff>0){
            return "Item is to heavy to pick up by "+diff+" lbs";
        }
        Item i = currRoom.pickup(name);
        if(i==null){
            return "Item does not exist";
        }
        mass = mass + i.getWeight();
        inv.add(i);
        return "You have picked up "+name;
    }
    /**
     * displays character's inventory and carry weight
     * @return
     */
    public String showInv(){
    	String s = "";
    	if(inv.isEmpty()){
    		return "You don't have anything";
    	}
    	for(Item i : inv){
    		s = s + i.getName() + ", ";
    	}
    	s = s+ "\nweight: " + mass +"/"+limit+ " lbs";
    	return s;
    }
    /**
     * use an item
     * weapons and armor get equipped
     * health items get used up
     * @param name
     * @return
     */
    public String use(String name){
    	for(Item i : inv){
    		if(i.getName().equals(name)){
    			String s = "";
    			if (i.getType().equals("weapon")){
    				if(weapon!=null){
    					inv.add(weapon);
    				}
    				weapon = i;
    				inv.remove(i);
    				s = s + i.getName()+" was equiped";
    			}else if(i.getType().equals("armor")){
    				if(armor!=null){
    					inv.add(armor);
    				}
    				armor = i;
    				inv.remove(i);
    				s = s + i.getName()+" was equiped";
    			}else if(i.getType().equals("health")){
    				s = s + i.getName() + " was used";
    				hurt(i.getValue());
    				inv.remove(i);
    				mass = mass - i.getWeight();
    				s = s + "\n"+(i.getValue()*(-1))+" health recovered";
    			}
    			return s;
    		}
    	}
    	return "You don't have that";
    }
    /**
     * drops an item into the current room
     * @param name
     * @return
     */
    public String drop(String name){
    	for(Item i : inv){
    		if(i.getName().equals(name)){
    			currRoom.drop(i);
    			inv.remove(i);
    			mass = mass - i.getWeight();
    			return i.getName()+ " was dropped";
    		}
    	}
        return "You don't have that";
    }
    /**
     * Used for taking damage/ healing
     * (use negative value to heal)
     * @param value
     */
    public void hurt (int value){
    	health = health - value;
    	if(health>healthMax){
    		health = healthMax;
    	}else if(health<=0){
    		health = 0;
    		//dead
    	}
    }
    /**
     * do damage to target
     * STILL WORKING ON THIS
     * @param target
     * @return
     */
    public String attack (String target){
    	//do damage
    	String s;
    	if (weapon==null){
    		s = "You're unarmed";
    	}else{
    		s = weapon.getValue() + " damage was done to " + target;
    	}
    	return s;
    }
    /**
     * Gets the characters current health and equipment
     * @return
     */
    public String character(){
    	String s = "Health "+health+"/"+healthMax+"\nWeapon ";
    	if(weapon==null){
    		s = s + "none";
    	}else{
    		s = s + weapon.getName();
    	}
    	s = s +"\nArmor ";
    	if(armor==null){
    		s = s + "none";
    	}else{
    		s = s + armor.getName();
    	}
    	return s;
    }
}
