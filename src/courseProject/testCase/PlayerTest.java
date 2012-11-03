package courseProject.testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.ItemType;
import courseProject.Monster;
import courseProject.Player;
import courseProject.Room;

public class PlayerTest {
	private Room northRoom;
	private Room southRoom;
	private Player p1;
	private Player p2;
	@Before
	public void setup(){
		northRoom = new Room("North Room");
		southRoom = new Room("South Room");
		northRoom.addExit("south", southRoom);
		southRoom.addExit("north",northRoom);
		northRoom.setItem("WEAPON", "DESC", 1, ItemType.weapon, 1);

		p1 = new Player(northRoom,20,10,5);
	}
	@Test
	public void testCopyConstructor(){
		p2 = new Player(p1);
		assertTrue("Copy of player should be the same as original",p1.equals(p2));
	}
	@Test
	public void testSetRoom(){
		p1.setRoom("east");
		assertEquals("Player should not move because room is not valid",northRoom,p1.getRoom());
		p1.setRoom("south");
		assertEquals("Player should be in the south room",southRoom,p1.getRoom());
	}
	@Test
	public void testPickup(){
		String inv = p1.showInv();
		p1.pickup("FAIL");
		assertEquals("Player inventory should not change",inv,p1.showInv());
		p1.pickup("WEAPON");
		assertFalse("Player inventory should be changed",inv.equals(p1.showInv()));
	}
	@Test
	public void testDrop(){
		p1.pickup("WEAPON");
		String inv = p1.showInv();
		p1.drop("FAIL");
		assertEquals("Player inventory should not change",inv,p1.showInv());
		p1.drop("WEAPON");
		assertFalse("Player inventory should be changed",inv.equals(p1.showInv()));
	}
	@Test
	public void testAttack(){
		Monster m = new Monster("MONSTER", 1, 0, 0, 0, 0);
		northRoom.addMonster(m);
		p1.pickup("WEAPON");
		p1.attack("MONSTER");
		assertFalse("Should not be able to kill with no weapon",m.isDead());
		p1.use("WEAPON");
		p1.attack("FAIL");
		assertFalse("Attacking wrong target should not kill it",m.isDead());
		p1.attack("MONSTER");
		assertTrue("Attacking correct target with weapon should work",m.isDead());
	}
	@Test
	public void testUse(){
		northRoom.setItem("ARMOR", "DESC", 1, ItemType.armor, 1);
		northRoom.setItem("HEALTH", "DESC", 1, ItemType.health, 1);
		p1.hurt(5);//to test healing item
		p1.pickup("ARMOR");
		p1.pickup("WEAPON");
		p1.pickup("HEALTH");
		String cha = p1.character();
		p1.use("FAIL");
		assertTrue("Didn't use a valid item, stats should be the same",cha.equals(p1.character()));
		p1.use("HEALTH");
		assertFalse("Used healing item, health should be higher",cha.equals(p1.character()));
		cha = p1.character();
		p1.use("WEAPON");
		assertFalse("Used weapon, should now be listed",cha.equals(p1.character()));
		cha = p1.character();
		p1.use("ARMOR");
		assertFalse("Used armor, should now be listed",cha.equals(p1.character()));
	}
	@Test
	public void testHurt(){
		String cha = p1.character();
		p1.hurt(0);
		assertTrue("Did no damage, stats should be the same",cha.equals(p1.character()));
		p1.hurt(5);
		assertFalse("Did damage, health should be lower",cha.equals(p1.character()));
		p1.hurt(999);
		assertTrue("Hit its weakpoint for massive damage, should be dead",p1.isDead());
	}
	@Test
	public void testHeal(){
		p1.hurt(5);
		String cha = p1.character();
		p1.heal(0);
		assertTrue("Healed no health, stats should be the same",cha.equals(p1.character()));
		p1.heal(999);
		assertFalse("Healed all health, health should be higher",cha.equals(p1.character()));
		cha = p1.character();
		p1.heal(999);
		assertTrue("Healed while at full health, stats should be the same",cha.equals(p1.character()));
	}
}
