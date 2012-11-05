package courseProject.controller;

import java.util.Scanner;
/**
 * 
 * @author Mike Hamon
 *
 */
public class TextController {
    private Scanner reader;         // source of command input
    public TextController(){
        reader = new Scanner(System.in);
    }
	public InputEvent getText(){

        String inputLine;   // will hold the full input line

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();
        Command com = getCommand(inputLine);
		return new InputEvent(com);
	}
	private Command getCommand(String inputLine) {
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
}
