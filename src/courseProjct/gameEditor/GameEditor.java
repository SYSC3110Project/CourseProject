package courseProjct.gameEditor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import courseProject.view.twoD.drawable.Room2D;

/**
 * The GameEditor will be a way to construct rooms and their 
 * contents to be playable in the main game. It lets someone 
 * who has very little Coding experience develop content.
 * 
 * @author Matthew Smith
 * @version 11/23/2012
 */
public class GameEditor implements ActionListener{
	
	private List<Room2D> rooms;
	private JFrame mainWindow;
	private ImageSplitter splitter;

	/**
	 * Game Editor Constructor
	 */
	public GameEditor() {
		
		rooms = new ArrayList<Room2D>();
		
		mainWindow = new JFrame("Game Editor");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new GridLayout(1,2,5,5));
		mainWindow.setJMenuBar(initMenuBar());
		mainWindow.add(initImagePanel());
		mainWindow.add(initRoomPanel());
	}
	
	/**
	 * Helper method returns the MenuBar used in the game editor
	 * (primarily for code sectioning)
	 * @return The GameEditor's Menu Bar
	 */
	private JMenuBar initMenuBar() {
		JMenuBar menu = new JMenuBar();
		{ //Using scope to help section code
			JMenu fileMenu = new JMenu("File");
			{
				JMenuItem loadImageEntry = new JMenuItem("Load Image");
				loadImageEntry.addActionListener(this);
				fileMenu.add(loadImageEntry);
				
				JMenuItem saveEntry = new JMenuItem("Save");
				saveEntry.addActionListener(this);
				fileMenu.add(saveEntry);
			}
			
			menu.add(fileMenu);
		}
		return menu;
	}
	
	/**
	 * Helper method returns the ImageSplitter Panel
	 * @return The ImageSplitter Panel
	 */
	private ImageSplitter initImagePanel() {
		splitter = new ImageSplitter();
		return splitter;
	}
	
	/**
	 * Helper Method initializes the Panel which 
	 * displays the Game's current Room how it 
	 * would be displayed while playing.
	 * @return The Game's Room.
	 */
	private JPanel initRoomPanel() {
		JPanel temp = new JPanel();
		temp.setPreferredSize(new Dimension(512,512));
		return temp; 
	}
	
	/** 
	 * Helper Method handles loading images into the imageSplitter.
	 */
	private void loadImage() {
		JFileChooser fileNamer = new JFileChooser();

		int returnVal = fileNamer.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File source;
			source = fileNamer.getSelectedFile();
			splitter.setImage(source); // set the image of the splitter
			
		}
		else if(returnVal == JFileChooser.ERROR_OPTION) {
			JOptionPane.showMessageDialog(null,"There was an error loading the Image.", "Load Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * Shows the GameEditor and its contents.
	 */
	public void show() {
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	/**
	 * dispose the GameEditor and its contents.
	 */
	public void dispose() {
		//TODO would you like to save?
		mainWindow.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().getClass().equals(JMenuItem.class)) {
			JMenuItem pressed = (JMenuItem) event.getSource();		

			if(pressed.getText().equals("Load Image")) {
				System.out.println("Loading Image");
				loadImage();
			} else if(pressed.getText().equals("Save")) {
				
			}
		}
	}
	

}
