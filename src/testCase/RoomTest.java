/**
 * @author Matthew Smith
 * @version 24/10/12
 */

package testCase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import courseProject.Room;

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

}
