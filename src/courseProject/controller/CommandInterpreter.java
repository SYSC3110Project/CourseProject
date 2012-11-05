package courseProject.controller;

import java.util.Scanner;

import courseProject.model.Game;

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
 * @version 29/10/2012
 */
public class CommandInterpreter implements InputListener
{
	private Game game;
    /**
     * Create a parser to read from the terminal window.
     */
    public CommandInterpreter() {

    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand(String inputLine) {
        String word1 = null;
        String word2 = null;

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }
        tokenizer.close();
        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        
        return new Command(CommandWord.getCommandFromString(word1), word2);
    }
    public void update(InputEvent IE){
    	Command com = getCommand(IE.getCommand());
    	game.turn(com);
    }
    
    /**
     * static method returns all the possible commands the parser can handle.
     * @return a string containing each of the possible commands
     */
    public static String getPossibleCommands(){
        return CommandWord.getPossibleCommands();
    }
}
