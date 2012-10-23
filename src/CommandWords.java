
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "take", "drop", "inv", "attack", "char", "undo", "redo"
    };

    /**
     * Constructor - initialize the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String commandToCheck)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(commandToCheck))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * static method returns all the possible valid commands.
     * @return a string containing each of the possible commands
     */
    public static String getPossibleCommands(){
        StringBuffer com = new StringBuffer();
        for (int i=0;i<validCommands.length;i++){
            com.append(validCommands[i]);
            if(i!=validCommands.length-1) {
            	com.append(", ");
            }
        }
        return com.toString();
    }
}
