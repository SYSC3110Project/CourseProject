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
		game=new Game();
		
	}

	@Test
	public void testGoRoom()
	{
		room1=game.getPlayer().getRoom();
		game.processCommand(new Command(CommandWord.go,"west"));
		room2=game.getPlayer().getRoom();
		assertTrue(room1!=room2);
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
	
	@Test
	public void testTake()
	{
		assertTrue(game.take(new Command(CommandWord.take,"magicOrb")));
	}
	
	@Test
	public void testDrop()
	{
		game.take(new Command(CommandWord.take,"magicOrb"));
		assertTrue(game.drop(new Command(CommandWord.drop,"magicOrb")));
	}
	
	@Test
	public void testAttack()
	{
		game.take(new Command(CommandWord.take,"magicOrb"));
		game.use(new Command(CommandWord.inventory,"magicOrb"));
		game.processCommand(new Command(CommandWord.go,"east"));
		assertTrue(game.attack(new Command(CommandWord.attack,"Orc")));
	}	
}
