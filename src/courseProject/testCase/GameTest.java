package courseProject.testCase;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import courseProject.model.*;
import courseProject.controller.*;


/**
 * Tests the methods that are responsibilities of the game, mostly the undo, redo functionality.
 * Also tests if boolean methods return true for success.
 * @author Andrew
 *
 */
public class GameTest {
	
	Game game;
	Room room1;
	Room room2;
	
	@Before
	public void setUp(){
		LevelLoader loader=new LevelLoader();
		game=new Game();
		try {
			Player player=loader.LoadLevel("res\\game\\Game.xml");
			game.setPlayer(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUndo()
	{	
		Stack<Player> stack1=game.getUndoStack();
		game.undo();
		Stack<Player> stack2=game.getUndoStack();
		assertTrue(stack1==stack2);
	}
	
	@Test
	public void testRedo()
	{
		Stack<Player> stack1=game.getRedoStack();
		game.redo();
		Stack<Player> stack2=game.getRedoStack();
		assertTrue(stack1==stack2);
	}
}
