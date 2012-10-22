import java.util.*;

/**
 * The player character
 * The plan is to refactor 'player' into an 'alive' abstract class and have 'player' and 'monster' extend it
 * 
 * @author Andrew and Micheal 
 * @version 18/10/12
 */
public class Player extends Creature{
    private int limit;
    private Room currRoom;
    private int mass;
    /**
     * Constructor for objects of class Player
     */
    public Player(Room room, int healthMax, int attack, int defence){
    	super("player",healthMax,attack,defence);
        currRoom = room;
        mass = 0;		//current weight
        limit = 20;		//max weight
    }
    
    
    /**
     * Copy Constructor for objects of class Player
     * @author Denis Dionne
     * @param p
     */
    public Player(Player p){
    	super("player", p.healthMax, p.attack, p.defence);
    	currRoom = new Room(p.currRoom);
    	limit = p.limit;
    	mass = p.mass;
    	health = p.health;
    	if(p.armor != null){
    		armor = new Item(p.armor);
    	}
    	if(p.weapon != null){
    		weapon = new Item(p.weapon);
    	}
    	
    	List<Item> itemList = new ArrayList<Item>();
    	for(Item i: p.inv){
    		itemList.add(new Item(i));
    	}
    	inv  = itemList;
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
        	currRoom.revMonster();
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
     * do damage to target
     * @param target
     * @return
     */
    public String attack (String target){
    	Monster m = currRoom.getMonster(target);
    	if (m==null){
    		return "There is no such creature here";
    	}
    	String s = super.attack(m);
    	if(m.isDead()){
    		m.drop(currRoom);
    	}
    	return s;
    }
    /**
     * displays character's inventory and carry weight
     * @return
     */
    public String showInv(){
    	String s = "";
    	if(inv.isEmpty()){
    		return "weight: " + mass +"/"+limit+ " lbs";
    	}
    	for(Item i : inv){
    		s = s + i.getName() + " ";
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
    				s = s +"\n"+heal(i.getValue());
    				inv.remove(i);
    				mass = mass - i.getWeight();
    			}
    			return s;
    		}
    	}
    	return "You don't have that";
    }
    
    
    
    /**
     * Gets the characters current health and equipment
     * @return
     */
    public String character(){
    	String s = "Health "+health+"/"+healthMax+", Weapon ";
    	if(weapon==null){
    		s = s + "none";
    	}else{
    		s = s + weapon.getName();
    	}
    	s = s +", Armor ";
    	if(armor==null){
    		s = s + "none";
    	}else{
    		s = s + armor.getName();
    	}
    	return s;
    }
}
