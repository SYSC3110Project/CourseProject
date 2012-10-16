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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player mc;		//player character
        
    /**
     * Create the game and initialize its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String[] args){
    	Game g = new Game();
    	g.play();
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
        outside.setExit("east",theater);
        outside.setExit("south",lab);
        outside.setExit("west",pub);
        theater.setExit("west",outside);
        pub.setExit("east",outside);
        lab.setExit("north",outside);
        lab.setExit("east",office);
        office.setExit("west",lab);
        
        // initialize items in rooms
        office.setItem("stapler","fear it",3,"weapon",4);
        office.setItem("broom","sweap your foes away",12,"weapon",10);
        pub.setItem("beer", "nice and cold", 4, "health", -2);
        pub.setItem("beer", "nice and cold", 4, "health", -2);

        mc = new Player(outside);
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
        }
        gamePrint("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gamePrint("");
        gamePrint("Welcome to the World of Nameless!");
        gamePrint("World of Nameless is a new adventure game with no plot yet.");
        gamePrint("Type 'help' if you need help.");
        gamePrint("");
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

        if(command.isUnknown()) {
            gamePrint("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            lookAround(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }else if (commandWord.equals("inv")){
        	inv(command);
        }else if (commandWord.equals("attack")){
        	attack(command);
        }else if (commandWord.equals("char")){
        	character(command);
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
        gamePrint(" " + parser.getCommands());
        
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
            String names = mc.look();
            if(!names.equals("")){
                gamePrint("Items: "+names);
            }
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
    private void take(Command command){
        if(!command.hasSecondWord()){
            gamePrint("Take what?");
        }else{
            gamePrint(mc.pickup(command.getSecondWord()));
        }
    }
    /**
     * Drop an item from inventory into the room
     * @param command
     */
    private void drop(Command command){
        if(!command.hasSecondWord()){
            gamePrint("Drop what?");
        }else{
            gamePrint(mc.drop(command.getSecondWord()));
        }
    }
    /**
     * Checks inventory (no second word) or uses an item in inventory
     * @param command
     */
    private void inv(Command command){
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
    private void attack(Command command){
    	if(!command.hasSecondWord()){
    		gamePrint("Attack what?");
    	}else{
    		gamePrint(mc.attack(command.getSecondWord()));
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
    		//what should this do?
    	}
    }
    /**
     * Used to print text
     * (so its easy to change where we print later)
     * @param mess
     */
    private void gamePrint(String mess){
        System.out.printf(mess+"\n");
    }
}

