/**
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 24/10/12
 */

package courseProject.testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.model.Item;
import courseProject.model.ItemType;
import courseProject.model.Monster;
import courseProject.model.Player;
import courseProject.model.Room;

public class RoomTest {
	
	private Room northRoom;
	private Room southRoom;
	
	@Before
	public void setup() {
		northRoom = new Room("North Room");
		southRoom = new Room("South Room");
	}

	@Test
	public void testAddExit() {
		northRoom.addExit("south", southRoom);
		
		assertEquals("southRoom added as an exit to northRoom", southRoom, northRoom.getExit("south"));
	}
	
	@Test
	public void testCopyConstructor() {
		southRoom = new Room(northRoom);
		assertNotSame("southRoom is not just a reference to northRoom ", northRoom, southRoom);
	}
	
	@Test
	public void testAddMonster(){
		Monster ghost = new Monster("ghost", 60, 10, 10, 10, 10);
		northRoom.addMonster(ghost);
		assertEquals("ghost added to the northRoom", ghost, northRoom.getMonster("ghost"));
	}
	
	@Test
	public void testAddingTwoMonsters(){
		Monster ghost = new Monster("ghost", 60, 10, 10, 10, 10);
		Monster bat = new Monster("bat", 10, 1, 1, 1, 1);
		northRoom.addMonster(ghost);
		northRoom.addMonster(bat);
		assertEquals("northRoom should now contain two monsters", 2, northRoom.getMonsters().size());
	}
	
	@Test
	public void testGetMonsterNames(){
		Monster ghost = new Monster("ghost", 60, 10, 10, 10, 10);
		Monster bat = new Monster("bat", 10, 1, 1, 1, 1);
		northRoom.addMonster(ghost);
		northRoom.addMonster(bat);
		assertEquals("Monster names should be ghost and bat", "ghostbat", northRoom.getMonsterNames());
	}
	
	@Test
	public void testGetDescription(){
		assertEquals("North Room", northRoom.getDescription());
	}
	
	@Test
	public void testGetLocation(){
		northRoom.addExit("south", southRoom);
		assertEquals("You are North Room\nExits: south", northRoom.getLoc());
	}
	
	@Test
	public void testDropItem(){
		Item broom = new Item("broom", "a broom", 2, ItemType.weapon, 50);
		northRoom.drop(broom);
		assertEquals("list of items in the northRoom should have a size of 1", 1, northRoom.getItems().size());
	}
	
	@Test
	public void testGetItemNames(){
		Item broom = new Item("broom", "a broom", 2, ItemType.weapon, 50);
		Item stick = new Item("stick", "not a broom", 5, ItemType.weapon, 1);
		northRoom.drop(broom);
		northRoom.drop(stick);
		assertEquals("Item names should be broom and stick", "broom, stick", northRoom.getItemNames());
	}
	
	@Test
	public void testGetFullItemNames(){
		Item broom = new Item("broom", "a broom", 2, ItemType.weapon, 50);
		northRoom.drop(broom);
		assertEquals("broom: a broom (2 lbs)", northRoom.getItemFull("broom"));
	}
	
	@Test
	public void testGetItemWeight(){
		Item broom = new Item("broom", "a broom", 2, ItemType.weapon, 50);
		northRoom.drop(broom);
		assertEquals("Weight should be 2", 2, northRoom.getItemWeight("broom"));
	}
	
	@Test
	public void testItemPickup(){
		Item broom = new Item("broom", "a broom", 2, ItemType.weapon, 50);
		northRoom.drop(broom);
		northRoom.pickup("broom");
		assertEquals("Size of the list of items in the NorthRoom should be 0", 0, northRoom.getItems().size());
		
	}

}
