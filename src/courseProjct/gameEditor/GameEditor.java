package courseProjct.gameEditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
public class GameEditor implements ActionListener, GridListener{
	
	private List<Room2D> rooms;
	private JFrame mainWindow;
	private GridImager splitter;
	private RoomBuilder builder;
	private JButton modeButton;
	private JButton adButton;
	private EditorMode mode;
	private AddDelMode admode;
	
	/**
	 * Game Editor Constructor
	 */
	public GameEditor() {
		
		rooms = new ArrayList<Room2D>();
		mode = EditorMode.Background;
		admode = AddDelMode.Add;
		
		mainWindow = new JFrame("Game Editor");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel(new GridLayout(1,2,5,5));
		contentPanel.add(initImagePanel());
		contentPanel.add(initRoomPanel());
		
		modeButton = new JButton("Background Mode");
		modeButton.addActionListener(this);
		
		adButton = new JButton("Add Tiles");
		adButton.addActionListener(this);
		
		mainWindow.add(contentPanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(adButton, BorderLayout.NORTH);
		buttonPanel.add(modeButton, BorderLayout.SOUTH);
		mainWindow.add(buttonPanel, BorderLayout.SOUTH);
		mainWindow.setJMenuBar(initMenuBar());
		
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
	private GridImager initImagePanel() {
		splitter = new ImageSplitter();
		splitter.addGridListener(this);
		return splitter;
	}
	
	/**
	 * Helper Method initializes the Panel which 
	 * displays the Game's current Room how it 
	 * would be displayed while playing.
	 * @return The Game's Room.
	 */
	private RoomBuilder initRoomPanel() {
		builder = new RoomBuilder();
		builder.addGridListener(this);
		return builder; 
	}
	
	/** 
	 * Helper Method handles loading images into the imageSplitter.
	 */
	private void loadImage() {
		JFileChooser fileNamer = new JFileChooser("res\\");

		int returnVal = fileNamer.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File source;
			source = fileNamer.getSelectedFile();
			splitter.setImage(source); // set the image of the splitter
			builder.setImage(source);
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
				loadImage();
			} else if(pressed.getText().equals("Save")) {
				System.out.println(builder.backgroundLayerToString());
				System.out.println(builder.objectLayerToString());
			} else if(pressed.getText().equals("Load")) {
				
			}
		} else if(event.getSource().equals(modeButton)) {
			switch(mode) {
			case Background:
				modeButton.setText("Object Mode");
				mode = EditorMode.Object;
				break;
			case Object:
				modeButton.setText("Background Mode");
				mode = EditorMode.Background;
				break;
			default:
				break;
			}
		} else if(event.getSource().equals(adButton)){
			switch(admode){
			case Add:
				adButton.setText("Delete Tiles");
				admode = AddDelMode.Del;
				break;
			case Del:
				adButton.setText("Add Tiles");
				admode = AddDelMode.Add;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void handleGridEvent(GridEvent e) {
		if(e.getSource().equals(builder)) {
			if(admode.equals(AddDelMode.Add)){
				if(mode.equals(EditorMode.Background)) {
					builder.setBackgroundAtSelector(splitter.getSelectorPoint());
				} else if(mode.equals(EditorMode.Object)) {
					builder.setObjectAtSelector(splitter.getSelectorPoint());
				}
			}else if(admode.equals(AddDelMode.Del)){
				if(mode.equals(EditorMode.Background)) {
					builder.delBackgroundAtSelector();
				} else if(mode.equals(EditorMode.Object)) {
					builder.delObjectAtSelector();
				}
			}
			
		} else if(e.getSource().equals(splitter)) {
			
		}
	}
	

}
