/**
 * 
 * @author Andrew and Micheal
 * @version 18/10/12
 */

import java.util.ArrayList;
import java.util.List;


public abstract class Creature {

    protected List<Item> inv;
    protected int health;
    protected int healthMax;
    protected Item armor;
    protected Item weapon;
    protected int attack;
    protected int defence;
    protected String name;
    
    public Creature(String name, int healthMax, int attack, int defence){
    	this.attack = attack;
    	this.defence = defence;
        inv = new ArrayList<Item>();
        this.healthMax = healthMax;	//max health
        health = this.healthMax;	//current health
        this.name = name;
        
    }
    /**
     * do damage to target
     * @param target
     * @return
     */
    protected String attack (Creature c){
    	String s;
    	if (weapon==null){
    		s = "You're unarmed";
    	}else{
        	int damage = weapon.getValue()*attack;
        	s = c.hurt(damage);
    	}
    	return s;
    }
    /**
     * Used for taking damage
     * @param value
     */
    public String hurt (int value){
    	String s;
    	int damage;
    	if(armor!=null){
    		damage = value - (armor.getValue()*defence);
    	}else{
    		damage = value;
    	}
    	if(damage<=0){
    		damage = 0;
    		s = "nothing happend"; 
    	}else{
    		s = damage+" damage taken by "+name;
    	}
    	health = health - damage;
    	if(health<=0){
    		health = 0;
    		s = s + "\n"+name+" has died";
    	}
    	return s;
    }
    /**
     * heals the creature
     * @param value
     * @return
     */
    public String heal(int value){
    	String s;
    	s = value+" health recovered";
    	health = health + value;
		if(health>healthMax){
			health = healthMax;
		} 
		return s;
    }
    /**
     * Check if creature is dead
     * @return
     */
    public boolean isDead(){
    	if(health==0){
    		return true;
    	}else{
    		return false;
    	}
    }
    public String getName(){
    	return name;
    }
}