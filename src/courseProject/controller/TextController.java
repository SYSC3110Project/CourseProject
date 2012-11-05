package courseProject.controller;

import java.util.ArrayList;
import java.util.List;
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
		return new InputEvent(inputLine);
	}
}
