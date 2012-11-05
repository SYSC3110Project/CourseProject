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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;


import courseProject.controller.Command;
import courseProject.controller.CommandInterpreter;
import courseProject.controller.CommandWord;
import courseProject.view.twoD.drawable.Drawable2D;
import courseProject.view.twoD.drawable.Item2D;
import courseProject.view.twoD.drawable.Monster2D;
import courseProject.view.twoD.drawable.Player2D;
import courseProject.view.twoD.drawable.Room2D;

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
    }
    /**
     * starting point of the game
     * @param args
     */
    
    /**
     * Adds a listener to the game model
     * @param listener
     */
    public void addModelListeners(ModelListener listener){
    	listeners.add(listener);
    }
    


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room2D outside, theater, pub, lab, office;
      
        // create the rooms
       // BufferedImage orb = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB); 
        BufferedImage orb = null;
        try {
            orb = ImageIO.read(new File("C:\\eclipse\\workspace\\CourseProject\\res\\Orb of Blood.png"));
        } catch (IOException e) {
        }
        outside = new Room2D("outside the main entrance of the university", orb);
        theater = new Room2D("in a lecture theater", orb);
        pub = new Room2D("in the campus pub", orb);
        lab = new Room2D("in a computing lab", orb);
        office = new Room2D("in the computing admin office", orb);
        
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
        Item stapler = new Item2D("stapler","fear it",3,ItemType.weapon,4, orb);
        Item broom = new Item2D("broom","sweap your foes away",12,ItemType.weapon,10, orb);
        Item beer1 = new Item2D("beer", "nice and cold", 4, ItemType.health, 2, orb);
        Item beer2 = new Item2D("beer", "nice and cold", 4, ItemType.health, 2, orb);
        Item textBook = new Item2D("textbook", "its really thick", 5, ItemType.armor, 3, orb);
        
        //
        office.drop(stapler);
        office.drop(broom);
        pub.drop(beer1);
        pub.drop(beer2);
        theater.drop(textBook);
        
        //initialize creatures
        Monster m = new Monster2D("prof", 10, 1, 1, 4, 2, orb);
    	m.addItem(new Item2D("candy","yay sugar",1,ItemType.health,6, orb));
        theater.addMonster(m);

        //initialize player
        mc = new Player2D(outside,20,1,1, orb);
        undoStack.add(new Player2D((Player2D)mc));
    }
    
    
    /**
     * gets the player
     * @return Player
     */
    public Player getPlayer(){
    	return mc;
    }

    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {
        
        StringBuffer welcome = new StringBuffer();
    	welcome.append("\nWelcome to the World of Nameless!");
    	welcome.append("\nWorld of Nameless is a new adventure game with no plot yet.");
    	welcome.append("\nType 'help' if you need help.\n");
    	welcome.append(mc.getLoc());
    	String welcomeString = new String(welcome);
    	
    	notifyListeners(welcomeString);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        boolean attackable = false;	//prevents player from being attacked right when entering room/using help
        if(command.isUnknown()) {
            notifyListeners("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        
        
        //if command isn't undo or redo and the last undo doesn't go back to the start of the current room, then add a Checkpoint to the stack
        if(!(commandWord.equals(CommandWord.undo) || commandWord.equals(CommandWord.redo))){
        	if(undoStack.isEmpty()){   //If the stack is empty, then any action should create a checkpoint
        		undoStack.add(new Player2D((Player2D)mc));
        		redoStack.clear();
        	}
        	else if(undoStack.peek().getRoom().getDescription() != (mc.getRoom().getDescription())){ //We already have the Checkpoint on the stack
        		undoStack.add(new Player((Player2D)mc));
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
        	notifyListeners(mc.getRoom().monsterAttack(mc));
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    public void printHelp() 
    {
    	StringBuffer help = new StringBuffer();
    	help.append("\nYour are lost. You are alone. You wander\n");
    	help.append("around at the university.\n");
    	help.append("Your command words are:\n --> ");
    	help.append(CommandInterpreter.getPossibleCommands());
    	help.append("\n");
    	String helpString = new String(help);
    	
    	notifyListeners(helpString);
    	
    	
    }
    
    /**
     * notifies the listeners of the change in the game (player, room, monsters and items) and also gives them a message to print to the console
     * @param msg message to be shown to the player
     */
    private void notifyListeners(String msg){
    	List<Drawable2D> drawList = new ArrayList<Drawable2D>();
    	drawList.add((Drawable2D)mc);
    	drawList.add((Drawable2D)mc.getRoom());
    	for(Monster m : mc.getRoom().getMonsters()){
    		if(!m.isDead()){
    			drawList.add((Drawable2D)m);
    		}
    	}
    	for(Item i : mc.getRoom().getItems()){
    		drawList.add((Drawable2D)i);
    	}
    	for(ModelListener listener : listeners){
    		listener.update(new ModelChangeEvent(msg, drawList));
    	}
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            notifyListeners("Go where?/n");
            return;
        }

        String direction = command.getSecondWord();
        notifyListeners(mc.setRoom(direction));
        undoStack.add(new Player2D((Player2D)mc)); //Add a checkpoint referencing the start of the current room (since we changed rooms)
        
    }
    
    
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    public boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            notifyListeners("Quit what?/n");
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
    public void lookAround(Command command){
        if(!command.hasSecondWord()) {
            notifyListeners(mc.getLoc());
        }else{
            //look at certain item
            String name = command.getSecondWord();
            notifyListeners(mc.lookat(name));
        }
    }
    /**
     * Pick up an item in the room
     * @param command
     */
    public boolean take(Command command){
        if(!command.hasSecondWord()){
            notifyListeners("Take what?");
            return false;
        }else{
        	String takStr = mc.pickup(command.getSecondWord());
            notifyListeners(takStr);
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
    public boolean drop(Command command){
        boolean sucess;
    	if(!command.hasSecondWord()){
            notifyListeners("Drop what?");
            return false;
        }else{
        	sucess=mc.drop(command.getSecondWord());
        	if(sucess)
        	{
        		notifyListeners(command.getSecondWord()+ " was dropped");
        		return true;
        	}
        	else
        	{
        		notifyListeners("You don't have that");
        		return false;
        	}
        }
           
   }
   
    /**
     * Checks inventory (no second word) or uses an item in inventory
     * @param command
     */
    public void inventory(Command command){
    	if(!command.hasSecondWord()){
            notifyListeners(mc.showInv());
        }else{
            notifyListeners(mc.use(command.getSecondWord()));
        }
    }
    /**
     * Attack an enemy
     * @param command
     */
    public boolean attack(Command command){
    	if(!command.hasSecondWord()){
    		notifyListeners("Attack what?");
    		return false;
    	}else{
    		String atkStr = mc.attack(command.getSecondWord());
    		notifyListeners(atkStr);
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
    public void character(Command command){
    	if(!command.hasSecondWord()){
    		notifyListeners(mc.character());
    	}else{
    		notifyListeners("What are you even trying to do?");
    		//what should this do?
    	}
    }

    
    
    /**
     * Undo command which brings the player to the beginning of the last room entered (will undo all the actions done in the room)
     * If you happen to undo twice, without doing any actions in between, then you will undo to the start of the previous room (and so forth)
     */
    public void undo(){
		if(!(undoStack.isEmpty())){
    		Player temp = undoStack.pop();
    		UpdateRoomReferences(temp);
    		redoStack.add(mc);
    		mc = temp;
    		Room2D temp2 = (Room2D) mc.getRoom();
    		if(temp2.getClass().equals(Room2D.class)){
    			System.out.println("bla");
    		}
    		
    		notifyListeners(mc.getRoom().getLoc());
    	}
		else{
			notifyListeners("nothing to undo");
		}
    }
    
    
    /**
     * Redo command which essentially undoes the undo command
     */
    public void redo(){
    	if(!(redoStack.isEmpty())){
    		Player temp = redoStack.pop();
    		UpdateRoomReferences(temp);
    		undoStack.add(mc);
    		mc = temp;
    		notifyListeners(mc.getRoom().getLoc());
    	}
    	else{
    		notifyListeners("nothing to redo");
    		
    	}
    }
    
    
    /**
     * Helper method for the undo() and redo() commands
     * Updates the references between rooms
     * @param temp
     */
    public void UpdateRoomReferences(Player temp){
    	for(String s: temp.getRoom().getExitMap().keySet()){
			Room2D adjacent = (Room2D)temp.getRoom().getExitMap().get(s);
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
    
    
    /**
     * getter method for the undoStack
     * @return the undoStack
     */
    public Stack<Player> getUndoStack(){
    	return undoStack;
    }
    
    /**
     * getter method for the redoStack
     * @return the redoStack
     */
    public Stack<Player> getRedoStack(){
    	return redoStack;
    }
}
