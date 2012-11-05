package courseProject.controller;



import courseProject.model.Game;
import courseProject.view.View;
import courseProject.view.textD.ViewText;
import courseProject.view.twoD.View2D;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author Michael Hamon
 * @author Matthew Smith
 * @author Denis Dionne
 * @version 04/11/2012
 */
public class CommandInterpreter implements InputListener
{
	

    private Game game;
    private View view;
    private boolean finished;

    /**
     * Create a parser to read from the terminal window.
     */
    public CommandInterpreter(View view, Game game) {
        this.view = view;
        this.game = game;
        view.addInputListener(this);
    }

    
    /**
     * receives an input from the view and acts accordingly
     * @param e is the event coming from the view (whether it be a textview, 2Dview or 3Dview)
     */
    public void input(InputEvent e){
    	//need to implement this
    	if(e.getClass().getName() == "courseProject.controller.InputEvent2D"){
    		if(e.getCommand() == null){
    			((View2D) view).moveCharacter((InputEvent2D)e);
    		}
    	}
    	else{
    		finished = game.processCommand(e.getCommand());
    	}
    	
    }

    /**
     * static method returns all the possible commands the parser can handle.
     * @return a string containing each of the possible commands
     */
    public static String getPossibleCommands(){
        return CommandWord.getPossibleCommands();
    }
    
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args){
    	View2D view = new View2D();
    	Game game = new Game();
    	CommandInterpreter c = new CommandInterpreter(view, game);

    	game.addModelListeners(view);
    	c.play();
    }

    
    /**
     * this is the loop used to play the game
     */
    private void play(){
    	game.printWelcome();
    	finished = false;
        while (! finished) {
        	//view.getCommand();
            if(game.getPlayer().isDead()){
            	break;
            }
        }
    }
}
