import java.util.*;

/** 
 * @author Andrew and Micheal
 * @version 18/10/12
 */
public class Monster extends Creature{

	public Monster(String name, int healthMax, int attack, int defence, int weapon, int armor) {
		super(name, healthMax, attack, defence);
        this.weapon = new Item("blank","blank",0,"weapon",weapon);
        this.armor = new Item("blank","blank",0,"armor",armor);
	}
	
	/**
	 * Copy Constructor for objects of class Monster
	 * @param m
	 * @author Denis Dionne
	 */
	public Monster(Monster m){
		super(m.name, m.healthMax, m.attack, m.defence);
		weapon = new Item(m.weapon);
		armor = new Item(m.armor);
		health = m.health;
    	List<Item> itemList = new ArrayList<Item>();
    	for(Item i: m.inv){
    		itemList.add(new Item(i));
    	}
    	inv  = itemList;
	}
	
	public String attack(Player p){
		return super.attack(p);
	}
	public void addItem(Item i){
		inv.add(i);
	}
	public void drop(Room currRoom){
		for(Item i : inv){
			currRoom.drop(i);
		}
	}
	
	public void rev(){
		health = healthMax;
	}
}
