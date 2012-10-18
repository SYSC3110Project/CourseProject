import java.util.*;
/**
 * A Creature is any creature in the game.
 * Creatures have combat stats such as health, attack and defense.
 * Creature has a current room and a list of carried items
 * A enemy monsters is an unchanged creatures.
 * Player is subclass of Creature with a carry limit and equipped items.
 * 
 * @author Andrew
 *
 */

public class Creature {
	protected Room currentRoom;
	protected int maxHealth;
	protected int currentHealth;				//how much damage the creature has taken
	protected int attack;						//how much damage a creature does to other creatures
	protected int defense;						//how much damage a creature ignores from other creatures attacks
	protected Item armor;
	protected Item weapon;
	protected List<Item> inventory;
	
	public Creature(Room room, int health, int attack, int defense){
		currentRoom=room;
		inventory=new ArrayList<Item>();
		maxHealth=health;
		currentHealth=maxHealth;
		this.attack=attack;
		this.defense=defense;
	}
	
	/**
	 * Adds an item to the creatures inventory.
	 * @param i
	 */
	public void addItem(Item i){
		inventory.add(i);
	}
	
	/**
	 * Moves the creature to the specified room
	 * @param room
	 */
	public void move(Room room){
		currentRoom=room;
	}
	
	/**
	 * Attacks target creature. The power of the attack is equal to the attackers attack stat.
	 * If the creature has a weapon than add weapons value to the attack.
	 * Returns how much damage was done.
	 * @param target
	 * @return damage done
	 */
	
	public int attack(Creature target){
		if(weapon==null)
		{
			return target.hurt(attack);
		}
		else
		{
			return target.hurt(attack+weapon.getValue());	
		}
		
		
	}
	
	/**
	 * This creature is dealt damage equal to (power of the attack)-(defense of this creature).
	 * If the creature has more defense than nothing happens.
	 * Currently does not allow creatures to go into negative health.
	 * returns how much damage was done 
	 * @param power
	 * @return damage taken
	 */
	
	public int hurt(int power){
		int damage;
		if(armor==null)
		{
			damage=power-defense;
		}
		else
		{
			damage=power-defense-armor.getValue();
		}
		if(damage>0){
			currentHealth-=damage;
		}
		
		if(currentHealth<0){
			currentHealth=0;
		}
		return damage;
	}
	
	/**
	 * This creature is healed this amount. currentHealth increases by amount.
	 * If healed to above maxHealth, currentHealth is set to maxHealth.
	 * @param amount
	 */
	public void heal(int amount){
		currentHealth+=amount;
		if(currentHealth>maxHealth){
			currentHealth=maxHealth;
		}
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}

	public Item getArmor() {
		return armor;
	}

	public void setArmor(Item armor) {
		this.armor = armor;
	}

	public Item getWeapon() {
		return weapon;
	}

	public void setWeapon(Item weapon) {
		this.weapon = weapon;
	}
}
