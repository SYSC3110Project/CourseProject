/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 */

package courseProject.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


import courseProject.controller.Command;
import courseProject.controller.CommandInterpreter;
import courseProject.controller.CommandWord;
import courseProject.view.textD.*;

/**
 * 
 * @author Matthew Smith
 * @author Andrew Venus
 * @author Mike Hamon
 * @author Denis Dionne
 * @version	01/11/2012
 */
public class Game 
{
    private CommandInterpreter parser;
    private Player mc;		//player character
    private Stack<Player> undoStack;
    private Stack<Player> redoStack;
    private List<ModelListener> listeners;
        
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
    	listeners = new ArrayList<ModelListener>();
    	undoStack = new Stack<Player>();
        redoStack = new Stack<Player>();
        createRooms();
        parser = new CommandInterpreter();
    }
    
    public static void main(String[] args){
    	Game g = new Game();
    	ViewText textView= new ViewText();
    	g.listeners.add(textView);
    	g.play();
    }
    
    /**
     * notifies all ModelListeners when a change happens
     * @param event
     */
    public void notifyListeners(ModelChangeEvent event)
    {
    	for(ModelListener listener:listeners)
    	{
    		listener.update(event);
    	}
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialize room exits
        outside.addExit("east",theater);
        outside.addExit("south",lab);
        outside.addExit("west",pub);
        theater.addExit("west",outside);
        pub.addExit("east",outside);
        lab.addExit("north",outside);
        lab.addExit("east",office);
        office.addExit("west",lab);
        
        // initialize items in rooms
        office.setItem("stapler","fear it",3,ItemType.weapon,4);
        office.setItem("broom","sweap your foes away",12,ItemType.weapon,10);
        pub.setItem("beer", "nice and cold", 4, ItemType.health, 2);
        pub.setItem("beer", "nice and cold", 4, ItemType.health, 2);
        theater.setItem("textbook", "its really thick", 5, ItemType.armor, 3);
        
        //initialize creatures
        Monster m = new Monster("prof", 10, 1, 1, 4, 2);
    	m.addItem(new Item("candy","yay sugar",1,ItemType.health,6));
        theater.addMonster(m);

        //initialize player
        mc = new Player(outside,20,1,1);
        undoStack.add(new Player(mc));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(mc.isDead()){
            	break;
            }
        }
        gamePrint("Thank you for playing.  Good bye.");
    }
    
    public boolean turn(Command com){
    	boolean finished = processCommand(com);
    	if (mc.isDead()){
    		finished = true;
    	}
    	return finished;
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gamePrint("\nWelcome to the World of Nameless!");
        gamePrint("World of Nameless is a new adventure game with no plot yet.");
        gamePrint("Type 'help' if you need help.\n");
        gamePrint(mc.getLoc());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        boolean attackable = false;	//prevents player from being attacked right when entering room/using help
        if(command.isUnknown()) {
            gamePrint("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        
        
        //if command isn't undo or redo and the last undo doesn't go back to the start of the current room, then add a Checkpoint to the stack
        if(!(commandWord.equals(CommandWord.undo) || commandWord.equals(CommandWord.redo))){
        	if(undoStack.isEmpty()){   //If the stack is empty, then any action should create a checkpoint
        		undoStack.add(new Player(mc));
        		redoStack.clear();
        	}
        	else if(undoStack.peek().getRoom().getDescription() != (mc.getRoom().getDescription())){ //We already have the Checkpoint on the stack
        		undoStack.add(new Player(mc));
        	}
        }
        
        
        if (commandWord.equals(CommandWord.help)) {
            printHelp();
        }
        else if (commandWord.equals(CommandWord.go)) {
            goRoom(command);
        }
        else if (commandWord.equals(CommandWord.quit)) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals(CommandWord.look)) {
            lookAround(command);
        }
        else if (commandWord.equals(CommandWord.take)) {
        	attackable = take(command);
        }
        else if (commandWord.equals(CommandWord.drop)) {
            attackable = drop(command);
        }else if (commandWord.equals(CommandWord.inventory)){
        	inventory(command);
        }else if (commandWord.equals(CommandWord.attack)){
        	attackable = attack(command);
        }else if (commandWord.equals(CommandWord.character)){
        	character(command);
        }else if (commandWord.equals(CommandWord.undo)){
        	undo();
        }else if (commandWord.equals(CommandWord.redo)){
        	redo();
        }
        if(attackable){
        	gamePrint(mc.getRoom().monsterAttack(mc));
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        gamePrint("You are lost. You are alone. You wander");
        gamePrint("around at the university.");
        gamePrint("");
        gamePrint("Your command words are:");
        gamePrint(" " + CommandInterpreter.getPossibleCommands());
        
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gamePrint("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        gamePrint(mc.setRoom(direction));
        undoStack.add(new Player(mc)); //Add a checkpoint referencing the start of the current room (since we changed rooms)
        
    }
    
    
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            gamePrint("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    /**
     * Look around a room (no second command) or look at a item in the room
     * These are going to need to be revamped if we add multiple players
     * @param command
     */
    private void lookAround(Command command){
        if(!command.hasSecondWord()) {
            gamePrint(mc.getLoc());
        }else{
            //look at certain item
            String name = command.getSecondWord();
            gamePrint(mc.lookat(name));
        }
    }
    /**
     * Pick up an item in the room
     * @param command
     */
    private boolean take(Command command){
        if(!command.hasSecondWord()){
            gamePrint("Take what?");
            return false;
        }else{
        	String takStr = mc.pickup(command.getSecondWord());
            gamePrint(takStr);
            if(takStr.equals("Item does not exist")){ //this is UGLY change when we add viewer
            	return false;
            }
            return true;
        }
    }
    /**
     * Drop an item from inventory into the room
     * @param command
     */
    private boolean drop(Command command){
        boolean sucess;
    	if(!command.hasSecondWord()){
            gamePrint("Drop what?");
            return false;
        }else{
        	sucess=mc.drop(command.getSecondWord());
        	if(sucess)
        	{
        		gamePrint(command.getSecondWord()+ " was dropped");
        		return true;
        	}
        	else
        	{
        		gamePrint("You don't have that");
        		return false;
        	}
        	
            	
            }
           
        }
   
    /**
     * Checks inventory (no second word) or uses an item in inventory
     * @param command
     */
    private void inventory(Command command){
    	if(!command.hasSecondWord()){
            gamePrint(mc.showInv());
        }else{
            gamePrint(mc.use(command.getSecondWord()));
        }
    }
    /**
     * Attack an enemy
     * @param command
     */
    private boolean attack(Command command){
    	if(!command.hasSecondWord()){
    		gamePrint("Attack what?");
    		return false;
    	}else{
    		String atkStr = mc.attack(command.getSecondWord());
    		gamePrint(atkStr);
    		if(atkStr.equals("There is no such creature here")){ //this is UGLY change when we add viewer
    			return false;
    		}
    		return true;
    	}
    }
    /**
     * Checks character stats
     * @param command
     */
    private void character(Command command){
    	if(!command.hasSecondWord()){
    		gamePrint(mc.character());
    	}else{
    		gamePrint("What are you even trying to do?");
    		//what should this do?
    	}
    }
    /**
     * Used to print text
     * (so its easy to change where we print later)
     * @param mess the message to print
     */
    private void gamePrint(String mess){
    	if(mess!=null&&!mess.equals("")){
    		this.notifyListeners(new ModelChangeEvent(mess));
    	}
    }
    
    
    /**
     * Undo command which brings the player to the beginning of the last room entered (will undo all the actions done in the room)
     * If you happen to undo twice, without doing any actions in between, then you will undo to the start of the previous room (and so forth)
     */
    private void undo(){
		if(!(undoStack.isEmpty())){
    		Player temp = undoStack.pop();
    		UpdateRoomReferences(temp);
    		redoStack.add(mc);
    		mc = temp;
    		gamePrint(mc.getRoom().getLoc());
    	}
		else{
			gamePrint("nothing to undo");
		}
    }
    
    
    /**
     * Redo command which essentially undoes the undo command
     */
    private void redo(){
    	if(!(redoStack.isEmpty())){
    		Player temp = redoStack.pop();
    		UpdateRoomReferences(temp);
    		undoStack.add(mc);
    		mc = temp;
    		gamePrint(mc.getRoom().getLoc());
    	}
    	else{
    		gamePrint("nothing to redo");
    		
    	}
    }
    
    
    /**
     * Helper method for the undo() and redo() commands
     * Updates the references between rooms
     * @param temp
     */
    private void UpdateRoomReferences(Player temp){
    	for(String s: temp.getRoom().getExitMap().keySet()){
			Room adjacent = temp.getRoom().getExitMap().get(s);
			if( s == "west"){
				adjacent.getExitMap().put("east", temp.getRoom());
			}
			else if(s == "east"){
				adjacent.getExitMap().put("west", temp.getRoom());
			}
			else if(s == "north"){
				adjacent.getExitMap().put("south", temp.getRoom());
			}
			else{
				adjacent.getExitMap().put("north", temp.getRoom());
			}
		}
    }
}
