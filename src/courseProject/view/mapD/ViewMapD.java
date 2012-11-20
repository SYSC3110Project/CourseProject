package courseProject.view.mapD;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import courseProject.controller.Command;
import courseProject.controller.CommandWord;
import courseProject.controller.InputEvent;
import courseProject.model.ModelChangeEvent;
import courseProject.model.Room;
import courseProject.view.textD.ViewText;
import courseProject.view.twoD.drawable.Drawable2D;
import courseProject.view.twoD.drawable.Room2D;

public class ViewMapD extends ViewText implements ActionListener{
	private JFrame mainWindow;
	private MapPanel mapArea;
	private JTextArea textArea;
	private JTextField inputField;
	private JPanel textAreaPanel;
	
	public ViewMapD(){
		super();
		mainWindow = new JFrame("World of the Nameless");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setBounds(100, 100, 455, 455);
		mainWindow.setResizable(false);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setVisible(true);
		
		mapArea = new MapPanel();
		textAreaPanel = new JPanel(new BorderLayout());

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setToolTipText("What is happening to me");

		JPanel inputFieldPane = new JPanel(new BorderLayout());

		inputField = new JTextField();
		inputField.addActionListener(this);
		inputField.setToolTipText("Input Text commands here");

		JLabel inputLabel = new JLabel(" >");

		inputFieldPane.add(inputLabel, BorderLayout.WEST);
		inputFieldPane.add(inputField, BorderLayout.CENTER);		

		textAreaPanel.add(textArea, BorderLayout.CENTER);
		textAreaPanel.add(inputFieldPane, BorderLayout.SOUTH);
		
		JPanel infoPanel = new JPanel(new GridLayout(2,1));
		
		infoPanel.add(mapArea);
		infoPanel.add(textAreaPanel);

		mainWindow.add(infoPanel, BorderLayout.CENTER);
	}
	/**
	 * Prints messages to text area
	 * @param message the text to be printed
	 */
	@Override
	public void displayMessage(String message) {
		if(textArea.getLineCount()>=10){
			textArea.setText("");
		}
		textArea.append(message);
		textArea.append("\n");
	}
	/**
	 * updates all the drawn objects
	 * @param delta time since the last update
	 */
	@Override
	public void update(double delta) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mapArea.repaint();
				textAreaPanel.repaint();
			}
		});
	}
	/**
	 * handles all events
	 * @param e the event to handle
	 */
	@Override
	public void handleModelChangeEvent(ModelChangeEvent e){
		displayMessage(e.getMessage());
		List<Drawable2D> drawList = e.getDrawable();
		for(Drawable2D drawable : drawList) {
			//if(drawable.getClass().equals(Player2D.class)) {
			//	player = (Player2D)drawable;
			//}
			if(drawable.getClass().equals(Room2D.class)) {
				mapArea.setCurrentRoom((Room)drawable);
			}
		}
	}
	/**
	 * closes the window on game end
	 */
	@Override
	public void dispose(){
		mainWindow.dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		JTextField source = (JTextField)(event.getSource());

		displayMessage(source.getText());

		String word1 = null;
		String word2 = null;

		Scanner tokenizer = new Scanner(source.getText());
		if(tokenizer.hasNext()) {
			word1 = tokenizer.next();      // get first word
			if(tokenizer.hasNext()) {
				word2 = tokenizer.next();      // get second word
				// note: we just ignore the rest of the input line.
			}
		}
		tokenizer.close();
		Command toNotify = new Command(CommandWord.getCommandFromString(word1), word2);
		notifyInputListeners(new InputEvent(toNotify));

		source.setText("");

		
	}
}
