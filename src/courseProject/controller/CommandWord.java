package courseProject.controller;

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

public enum CommandWord
{
    go("go"), quit("quit"), help("help"), look("look"), take("take"), drop("drop"), inventory("inventory"), 
    attack("attack"), character("character"), undo("undo"), redo("redo");

    private String description;
    
    /**
     * Constructor - initialize the command words.
     */
    private CommandWord(String command)
    {
    	description = command;
    }
    
    /**
     * static method returns all the possible valid commands.
     * @return a string containing each of the possible commands
     */
    public static String getPossibleCommands() {
        StringBuffer buff = new StringBuffer();
        CommandWord[] words = CommandWord.values();
        for (int i=0;i<words.length;i++){
            buff.append(words[i]);
            if(i!=words.length-1) {
            	buff.append(", ");
            }
        }
        return buff.toString();
    }
    /**
     * Changes the string into a CommandWord
     * @param command
     * @return CommandWord to use
     */
    public static CommandWord getCommandFromString(String command) { 
    	if(command==null){
    		return null;
    	}
    	command.toLowerCase();
    	for(CommandWord word : CommandWord.values()) {
    		if(command.equals(word.name())) {
    			return word;
    		}
    	}
    	return null;
    }
    
    @Override
    public String toString() {
    	return description;
    }
}
