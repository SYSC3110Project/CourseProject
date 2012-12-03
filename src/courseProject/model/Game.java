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
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;




import courseProject.controller.Command;
import courseProject.controller.CommandInterpreter;
import courseProject.controller.CommandWord;
import courseProject.view.twoD.drawable.Drawable2D;
import courseProject.view.twoD.drawable.Player2D;
import courseProject.view.twoD.drawable.SerializableBufferedImage;

/**
 * 
 * @author Matthew Smith
 * @author Andrew Venus
 * @author Mike Hamon
 * @author Denis Dionne
 * @version	01/11/2012
 */
public class Game implements Serializable
{
    private Player mc;		//player character
    private Stack<Player> undoStack;
    private Stack<Player> redoStack;
    transient private List<ModelListener> listeners;
        
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
    	listeners = new ArrayList<ModelListener>();
    	undoStack = new Stack<Player>();
        redoStack = new Stack<Player>();
        //createRooms();
    }
    
    /**
     * Adds a listener to the game model
     * @param listener
     */
    public void addModelListeners(ModelListener listener){
    	listeners.add(listener);
    }
    
    
    /**
     * gets the player
     * @return Player
     */
    public Player getPlayer(){
    	return mc;
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
        if(!(commandWord.equals(CommandWord.undo) || commandWord.equals(CommandWord.redo) || commandWord.equals(CommandWord.save))){
        	if(undoStack.isEmpty()){   //If the stack is empty, then any action should create a checkpoint
        		undoStack.push(new Player2D((Player2D)mc));
        		redoStack.clear();
        	}
        	else if(undoStack.peek().getRoom().getDescription() != (mc.getRoom().getDescription())){ //We already have the Checkpoint on the stack
        		undoStack.push(new Player2D((Player2D)mc));
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
        	inventory();
        }else if (commandWord.equals(CommandWord.attack)){
        	attackable = attack(command);
        }else if (commandWord.equals(CommandWord.character)){
        	character(command);
        }else if (commandWord.equals(CommandWord.undo)){
        	undo();
        }else if (commandWord.equals(CommandWord.redo)){
        	redo();
        }else if (commandWord.equals(CommandWord.use)){
        	use(command);
        }else if (commandWord.equals(CommandWord.save)){
        	try {
				save();
				System.out.println("saving...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    	help.append("around the giant's house.\n");
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
    	//NOTE: These must be added in their reverse draw priority.
    	drawList.add((Drawable2D)mc.getRoom());
    	drawList.add((Drawable2D)mc);    	
    	for(Monster m : mc.getRoom().getMonsters()){
    		if(!m.isDead()){
    			drawList.add((Drawable2D)m);
    		}
    	}
    	for(Item i : mc.getRoom().getItems()){
    		drawList.add((Drawable2D)i);
    	}
    	
    	for(ModelListener listener : listeners){
    		listener.handleModelChangeEvent(new ModelChangeEvent(msg, drawList));
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

        ExitDirection direction = ExitDirection.parse(command.getSecondWord());
        notifyListeners(mc.setRoom(direction));
        
        if(undoStack.peek().getRoom().getDescription() != mc.getRoom().getDescription()){
        	undoStack.push(new Player2D((Player2D)mc)); //Add a checkpoint referencing the start of the current room (since we changed rooms)
        }
        
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
     * Checks inventory
     * @param command
     */
    public void inventory(){
    	notifyListeners(mc.showInv());
        
    }
    /**
     * uses an item in the inventory or unequips from character
     * @param command
     */
    public void use(Command command){
    	if(!command.hasSecondWord()){
            notifyListeners("Use what?");
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
    	
		if(!undoStack.isEmpty()){
    		Player temp = undoStack.pop();
    		
    		redoStack.add(mc);
    		mc = temp;
    		
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
    		undoStack.push(mc);
    		mc = temp;
    		notifyListeners(mc.getRoom().getLoc());
    	}
    	else{
    		notifyListeners("nothing to redo");
    		
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
    
    public static Game load() throws IOException{

		try {
			FileInputStream	fileIn = new FileInputStream("gameData.ser");	
			ObjectInputStream In = new ObjectInputStream(fileIn);
			Game game = (Game) In.readObject();
			return game;
			
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
    
    /**
	 * Writes the Game to gameData.ser
	 * @throws IOException
	 */
	public void save() throws IOException{
		try{
			FileOutputStream fileOut = new FileOutputStream("gameData.ser");
			ObjectOutputStream Out = new ObjectOutputStream(fileOut);
			Out.writeObject(this);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
    
    
    /**
     * Adds an undo to the stack of undo events (needed in order to 
     * to add undo events for every mouse click
     */
    public void addUndo(){
    	undoStack.push(new Player2D((Player2D)mc));
    }
    
    
    /**
     * used by the level loader to set the Player.
     * @param player
     */
	public void setPlayer(Player player) {
		mc=player;
	}    
    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		//ImageIO.write(Image,"png",ImageIO.createImageOutputStream(out));
		out.defaultWriteObject();
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		
		//Image =ImageIO.read(ImageIO.createImageInputStream(in));
		in.defaultReadObject();
		listeners = new ArrayList<ModelListener>();
		
	}
    
    
    
    
}
