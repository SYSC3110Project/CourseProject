package courseProject.testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.model.Item;
import courseProject.model.ItemType;
import courseProject.model.Monster;
import courseProject.model.Player;
import courseProject.model.Room;

public class MonsterTest {
	private Room northRoom;
	private Room southRoom;
	private Player p1;
	private Monster m1;
	@Before
	public void setup(){
		northRoom = new Room("North Room");
		southRoom = new Room("South Room");
		northRoom.addExit("south", southRoom);
		southRoom.addExit("north",northRoom);
		p1 = new Player(northRoom,20,0,0);
		
		m1 = new Monster("MONSTER", 20, 5, 5, 5, 5);
		northRoom.addMonster(m1);
	}
	@Test
	public void testAttack(){
		String cha = p1.character();
		m1.attack(p1);
		assertFalse("Monster should have damaged player, health should be lower",cha.equals(p1.character()));
	}
	@Test
	public void testAddItem(){
		m1.addItem(new Item("WEAPON","DESC",1,ItemType.weapon,1));
		assertFalse("Should not have the item in it now",northRoom.getItemNames().contains("WEAPON"));
		m1.drop(northRoom);
		assertTrue("Should have the item in it now",northRoom.getItemNames().contains("WEAPON"));
	}
	@Test
	public void testDrop(){
		//Covered by testAddItem()
		//may need to add separate tests later when inventory get refactored
	}
	@Test
	public void testRev(){
		m1.hurt(999);
		assertTrue("The monster should have died",m1.isDead());
		m1.rev();
		assertFalse("The monster should now be alive again",m1.isDead());
	}
}
