/**
 * ItemTest.java does robustness testing on the Item class in the model. Testing provides a safety net to fall back on when major changes are made.
 */
package courseProject.testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.model.Item;
import courseProject.model.ItemType;

/**
 * Tests the Item class for any inconsistencies.
 * @author Matthew Smith
 * @version 04/11/2012
 */
public class ItemTest {
	
	private Item item;

	@Before
	public void setUp() throws Exception {
		item = new Item("sword", "double edged", 5, ItemType.weapon, 42);
	}

	@Test
	public void testCopyConstructor() { // this method will also test to make sure the equals works properly
		Item copy = new Item(item);
		
		assertEquals("Item copied using copy constructor: ",item, copy); 
		assertNotSame("Item copied is not a reference to the same item: ",item, copy);
	}
	
	@Test
	public void testGetType() {
		assertEquals("The item is of type weapon", ItemType.weapon, item.getType());
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("The item is described as double edged", "double edged", item.getDesc());
	}
	
	@Test
	public void testGetName() {
		assertEquals("The item is named sword", "sword", item.getName());
	}
	
	@Test
	public void testGetWeight() {
		assertEquals("The item has a weight of 5", 5, item.getWeight());
	}
	
	@Test
	public void testGetValue() {
		assertEquals("The item has a value of 42", 42, item.getValue());
	}
}
