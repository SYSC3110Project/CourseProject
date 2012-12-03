/**
 * 
 */
package courseProject.model;

/** 
 * @author Micheal Hamon
 * @author Andrew Venus
 * @author Denis Dionne
 * @version 18/10/12
 */
public class Monster extends Creature{

	/**Generated SerialID*/
	private static final long serialVersionUID = -8103136071584798070L;

	public Monster(String name, int healthMax, int attack, int defence, int weapon, int armor) {
		super(name, healthMax, attack, defence);
        this.weapon = new Item("blank","blank",0,ItemType.weapon,weapon);
        this.armor = new Item("blank","blank",0,ItemType.armor,armor);
	}
	
	/**
	 * Copy Constructor for objects of class Monster
	 * @param m
	 */
	public Monster(Monster m){
		super(m.name, m.healthMax, m.attack, m.defence);
		weapon = new Item(m.weapon);
		armor = new Item(m.armor);
		health = m.health;
    	inv=new Inventory(m.inv);
	}
	
	public String attack(Player p){
		return super.attack(p);
	}
	public void addItem(Item i){
		inv.add(i);
	}
	public void drop(Room currRoom){
		for(int i=0;i<inv.getSize();i++)
    	{
			currRoom.drop(inv.getItem(i));
    	}
	}
	
	public void rev(){
		health = healthMax;
	}
}
