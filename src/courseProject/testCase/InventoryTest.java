/**
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 24/10/12
 */

package courseProject.testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.model.Inventory;
import courseProject.model.Item;
import courseProject.model.ItemType;

public class InventoryTest {
	
	private Inventory inventory;
	private Inventory bag;
	private Item broom;
	
	@Before
	public void setup() {
		inventory = new Inventory();
		bag = new Inventory();
		broom = new Item("broom","bla", 2, ItemType.weapon, 2);
	}

	
	
	@Test
	public void testCopyConstructor() {
		bag = new Inventory(inventory);
		assertNotSame("bag is not just a reference to inventory ", inventory, bag);
	}
	
	@Test
	public void testAddItem(){
		inventory.add(broom);
		assertEquals("Size of the inventory should be 1", 1, inventory.getSize());
	}
	
	@Test
	public void testIsEmpty(){
		assertTrue(inventory.isEmpty());
	}
	
	@Test
	public void testRemoveItem(){
		inventory.add(broom);
		inventory.remove(broom);
		assertTrue(inventory.isEmpty());
	}
	
	@Test
	public void testGetMass(){
		inventory.add(broom);
		inventory.add(new Item("rock", "it's just a rock, get over it", 18, ItemType.weapon, 20));
		assertEquals("Mass of the inventory should be 20", 20, inventory.getMass());
	}
	
	@Test
	public void testGetItemNames(){
		inventory.add(broom);
		inventory.add(new Item("rock", "it's just a rock, get over it", 18, ItemType.weapon, 20));
		assertEquals("broom, rock", inventory.getItemNames());
	}
	

}
