/**
 * The player class inheriting form creature.
 * Most variables have been moved to Creature.
 * Player now has current wounds instead of current health.
 * Player now has an attack and defense stat as well as weapons and armor.
 * Healing Items now use the creatures heal method and have a positive value.
 * @author Andrew
 *
 */
public class PlayerCreature extends Creature {
	public static final int DEFAULT_HEALTH=20;
	public static final int DEFAULT_ATTACK=0;
	public static final int DEFAULT_DEFENSE=0;
	private int limit;
	private int mass;
	private Item armor;
	private Item weapon;
	
	public PlayerCreature(Room room){
		super(room,DEFAULT_HEALTH,DEFAULT_ATTACK,DEFAULT_DEFENSE);
		limit = 20;		//max weight
        mass = 0;		//current weight
	}
	
	/**
     * moves the player to a new room
     * @param direction
     * @return
     */
    public String setRoom(String direction){
        Room nextRoom = currentRoom.getExits(direction);
        if (nextRoom == null) {
            return "There is no door!";
        }
        else {
            super.move(nextRoom);
            return getLoc();
        }
    }
    
    public String getLoc(){
        return currentRoom.getLoc();
    }
    
    /**
     * Gets details about the current room
     * (exits and items)
     * @param name
     * @return
     */
    public String look(){
        return currentRoom.getItemNames();
    }
    /**
     * Gets details on item
     * @param name
     * @return
     */
    public String lookat(String name){
        String desc = currentRoom.getItemFull(name);
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
        int diff = (mass+currentRoom.getItemWeight(name))-limit;
        if(diff>0){
            return "Item is to heavy to pick up by "+diff+" lbs";
        }
        Item i = currentRoom.pickup(name);
        if(i==null){
            return "Item does not exist";
        }
        mass = mass + i.getWeight();
        inventory.add(i);
        return "You have picked up "+name;
    }
    /**
     * displays character's inventory and carry weight
     * @return
     */
    public String showInv(){
    	String s = "";
    	if(inventory.isEmpty()){
    		return "You don't have anything";
    	}
    	for(Item i : inventory){
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
    	for(Item i : inventory){
    		if(i.getName().equals(name)){
    			String s = "";
    			if (i.getType().equals("weapon")){
    				if(weapon!=null){
    					inventory.add(weapon);
    				}
    				weapon = i;
    				inventory.remove(i);
    				s = s + i.getName()+" was equiped";
    			}else if(i.getType().equals("armor")){
    				if(armor!=null){
    					inventory.add(armor);
    				}
    				armor = i;
    				inventory.remove(i);
    				s = s + i.getName()+" was equiped";
    			}else if(i.getType().equals("health")){
    				s = s + i.getName() + " was used";
    				heal(i.getValue());
    				inventory.remove(i);
    				mass = mass - i.getWeight();
    				s = s + "\n"+(i.getValue())+" health recovered";
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
    	for(Item i : inventory){
    		if(i.getName().equals(name)){
    			currentRoom.drop(i);
    			inventory.remove(i);
    			mass = mass - i.getWeight();
    			return i.getName()+ " was dropped";
    		}
    	}
        return "You don't have that";
    }
    
    /**
     * Attacks a monster and returns the result of the attack.
     * @param target
     * @return result of the attack
     */
    // with players now having an attack stat should we allow unarmed attacks?
    public String fight (Creature target){
    	//do damage
    	String s;
    	int damageDealt;
    	if (weapon==null){
    		s = "You're unarmed";
    	}else{
    		damageDealt=attack(target,weapon.getValue());
    		s = damageDealt + " damage was done to " + target;
    	}
    	return s;
    }

    
    /**
     * Gets the characters current health and equipment
     * @return
     */
    public String character(){
    	int health=maxHealth-wounds;
    	String s = "Health "+health+"/"+maxHealth+"\nWeapon ";
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
