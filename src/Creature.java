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
	protected int wounds;				//how much damage the creature has taken
	protected int attack;				//how much damage a creature does to other creatures
	protected int defense;			//how much damage a creature ignores from other creatures attacks
	protected List<Item> inventory;
	
	public Creature(Room room, int health, int attack, int defense){
		currentRoom=room;
		inventory=new ArrayList<Item>();
		maxHealth=health;
		wounds=0;
		this.attack=attack;
		this.defense=defense;
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
	 * Bonus is extra damage from non stats, for example a players weapon or temporary buffs.
	 * Returns how much damage was done.
	 * @param target
	 * @return damage done
	 */
	
	public int attack(Creature target,int bonus){
		return target.hurt(attack+bonus);	
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
		int damage=power-defense;
		if(damage>0){
			wounds+=damage;
		}
		
		if(wounds>maxHealth){
			wounds=maxHealth;
		}
		return damage;
	}
	
	/**
	 * This creature is healed this amount. Wounds decrease by amount.
	 * If healed more than current wounds, wounds is set to zero.
	 * @param amount
	 */
	public void heal(int amount){
		if(wounds<amount){
			wounds=0;
		}
		else{
			wounds-=amount;
		}
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
}
