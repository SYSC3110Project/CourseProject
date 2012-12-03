package courseProject.gameEditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The GameEditor will be a way to construct rooms and their 
 * contents to be playable in the main game. It lets someone 
 * who has very little Coding experience develop content.
 * 
 * @author Matthew Smith
 * @author Mike Hamon
 * @version 11/23/2012
 */
public class GameEditor implements ActionListener, GridListener, FocusListener, DocumentListener{
	
	private JFrame mainWindow;
	private GridImager splitter;
	private RoomBuilder builder;
	private JFrame connectWindow;
	private JPanel buildPanel;
	private JButton modeButton;
	private JButton adButton;
	private EditorMode mode;
	private AddDelMode admode;
	private JTextField name;
	private JTextArea desc;
	
	/**
	 * Game Editor Constructor
	 */
	public GameEditor() {
		
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

				JMenuItem itemEntry = new JMenuItem("Add Item");
				itemEntry.addActionListener(this);
				fileMenu.add(itemEntry);
				
				JMenuItem connectR = new JMenuItem("Connect Rooms");
				connectR.addActionListener(this);
				fileMenu.add(connectR);
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
	 * Helper Method handles building the room connecter window
	 */
	private void gameBuilder(){
		connectWindow = new JFrame("Game Connector");
		connectWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		connectWindow.setLayout(new BorderLayout());
		
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem saveEntry = new JMenuItem("Save Game");
		saveEntry.addActionListener(this);
		fileMenu.add(saveEntry);
		JMenuItem connectEntry = new JMenuItem("Add connection");
		connectEntry.addActionListener(this);
		fileMenu.add(connectEntry);
		menu.add(fileMenu);
		connectWindow.setJMenuBar(menu);
		
		buildPanel = new JPanel();
		buildPanel.setLayout(new GridLayout(0,3));
		addRow();

		name = new JTextField();
		name.setToolTipText("Game Name");
		JTextField nameTitle = new JTextField("Game Name:");
		nameTitle.setEditable(false);
		JPanel nameArea = new JPanel(new BorderLayout());
		nameArea.add(name,BorderLayout.CENTER);
		nameArea.add(nameTitle,BorderLayout.WEST);
		desc = new JTextArea();
		desc.setToolTipText("Description");
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.getDocument().addDocumentListener(this);
		JTextField descTitle = new JTextField("Description:");
		descTitle.setEditable(false);
		JTextField connTitle = new JTextField("Room connections:");
		connTitle.setEditable(false);
		JPanel descArea = new JPanel(new BorderLayout());
		descArea.add(desc,BorderLayout.CENTER);
		descArea.add(descTitle,BorderLayout.NORTH);
		descArea.add(connTitle,BorderLayout.SOUTH);
		connectWindow.add(nameArea,BorderLayout.NORTH);
		connectWindow.add(descArea,BorderLayout.CENTER);
		connectWindow.add(buildPanel,BorderLayout.SOUTH);
		connectWindow.pack();
		connectWindow.setVisible(true);
	}
	/**
	 * adds a new row of room connections
	 */
	private void addRow(){
		String[] dirStr = {"north","south","east","west"};
		JTextField room1 = new JTextField("Room 1");
		room1.setToolTipText("Room 1");
		room1.addFocusListener(this);
		JComboBox<String> direc = new JComboBox<String>(dirStr);
		direc.setToolTipText("Connection");
		JTextField room2 = new JTextField("Room 2");
		room2.setToolTipText("Room 2");
		room2.addFocusListener(this);
		buildPanel.add(room1);
		buildPanel.add(direc);
		buildPanel.add(room2);
		connectWindow.pack();
	}
	/**
	 * converts the current game connector window to XML format
	 */
	private void saveConnections(){
		HashSet<String> roomSet = new HashSet<String>();
		String dir = "";
		String rooms = "";
		String connections = "";
		String xml = "<?xml version=\"1.0\"?>\n<game name=\"";
		xml = xml + name.getText()+"\">\n<description>\n\"";
		xml = xml + desc.getText()+"\"\n</description>\n";
		xml = xml + "<rooms>\n";
		for(int i=0; i<buildPanel.getComponentCount(); i++){
			if(i%3==0){
				JTextField room1 = (JTextField)buildPanel.getComponent(i);
				connections = connections + "<connect>\n<room1 name=\""+room1.getText()+".xml\">\n";
				roomSet.add(room1.getText());
			}else if(i%3==1){
				JComboBox<String> direc = (JComboBox<String>)buildPanel.getComponent(i);
				dir = direc.getSelectedItem().toString();
				connections = connections + "<exit type=\""+dir+"\">\n";
				connections = connections + "</exit>\n</room1>\n";
			}else{
				String dirRe = "";
				switch(dir){
				case "north":
					dirRe = "south";
					break;
				case "south":
					dirRe = "north";
					break;
				case "east":
					dirRe = "west";
					break;
				case "west":
					dirRe = "east";
					break;
				}
				JTextField room2 = (JTextField)buildPanel.getComponent(i);
				connections = connections + "<room2 name=\""+room2.getText()+".xml\">\n";
				connections = connections + "<exit type=\""+dirRe+"\">\n";
				connections = connections + "</exit>\n</room2>\n</connect>\n";
				roomSet.add(room2.getText());
			}
		}
		for (String s:roomSet){
			rooms = rooms + "<room>\""+s+".xml\"</room>\n";
		}
		xml = xml + rooms;
		xml = xml + "</rooms>\n<connections>\n";
		xml = xml + connections;
		xml = xml + "</connections>\n</game>\n";
		
		BufferedWriter out;
		try{
			out = new BufferedWriter(new FileWriter("res/game/Game.xml"));
			out.write(xml);
			out.close();
		}catch (IOException e){
			return;
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
		int save = JOptionPane.showConfirmDialog(
			    mainWindow,"Would you like to save?",
			    "Save",
			    JOptionPane.YES_NO_OPTION);
		System.out.printf(""+save);
		mainWindow.dispose();
		
	}
	/**
	 * adds an item to the map
	 */
	public void addItem(){
		int x;
		int y;
		String s = (String)JOptionPane.showInputDialog(
                mainWindow,
                "Give x and y coordinates\n"
                + "Seperated by /",
                "Add item",
                JOptionPane.PLAIN_MESSAGE,
                null,null,"");
		
		if ((s != null) && (s.length() > 0) && (s.contains("/"))) {
			String[] sar = s.split("/");
			try{
				x = Integer.parseInt(sar[0]);
				y = Integer.parseInt(sar[1]);
			}catch(NumberFormatException e){
				return;
			}
			System.out.printf(""+x+"+"+y);
		}else{
			JOptionPane.showMessageDialog(mainWindow, "Input not valid");
			return;
		}
		String name = (String)JOptionPane.showInputDialog(
                mainWindow,
                "Give the name of the item",
                "Add item",
                JOptionPane.PLAIN_MESSAGE,
                null,null,"");

		//TODO actually add the item to the map
	}
	
	public void saveRoom(){
		//TODO
		String xml = "<?xml version=\"1.0\"?>\n<room name=\"";
		String name = (String)JOptionPane.showInputDialog(
                mainWindow,
                "What would you like to call the room",
                "Add item",
                JOptionPane.PLAIN_MESSAGE,
                null,null,"");
		xml = xml + name;
		xml = xml + "\">\n<description>\"";
		String desc = (String)JOptionPane.showInputDialog(
                mainWindow,
                "Describe the room",
                "Add item",
                JOptionPane.PLAIN_MESSAGE,
                null,null,"");
		xml = xml + desc;
		xml = xml + "\"</description>\n<tileset>\"../res/";
		//add tileset
		xml = xml + "\"</tileset>\n<items>";
		//add items
		xml = xml + "</items>\n<creatures>";
		//add creatures
		xml = xml + "</creatures>\n<layout>\n<background>\n";
		xml = xml + builder.backgroundLayerToString();
		xml = xml + "</background>\n<object>\n";
		xml = xml + builder.objectLayerToString();
		xml = xml + "</object>\n</layout>\n</room>\n";
		System.out.printf(xml);
	}
	/**
	 * handles menu and button sensitive tasks
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().getClass().equals(JMenuItem.class)) {
			JMenuItem pressed = (JMenuItem) event.getSource();		

			if(pressed.getText().equals("Load Image")) {
				loadImage();
			} else if(pressed.getText().equals("Save")) {
				saveRoom();
			} else if(pressed.getText().equals("Load")) {
				
			}else if(pressed.getText().equals("Add Item")){
				addItem();
			}else if(pressed.getText().equals("Connect Rooms")){
				gameBuilder();
			}else if(pressed.getText().equals("Save Game")){
				saveConnections();
			}else if(pressed.getText().equals("Add connection")){
				addRow();
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
	
	/**
	 * handles the user clicking on a grid space
	 */
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
	/**
	 * used to remove default text in room fields of the connect window
	 */
	@Override
	public void focusGained(FocusEvent arg0) {
		JTextField room = (JTextField)arg0.getSource();
		room.setText("");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		//doesn't do anything
		//needed to implement focusListener
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		//doesn't do anything
		//needed to implement documentListener
	}
	/**
	 * updates description field size on a new line word wrapping
	 */
	@Override
	public void insertUpdate(DocumentEvent arg0) {
		connectWindow.pack();
	}
	/**
	 * updates description field size on removing a word wrapped line
	 */
	@Override
	public void removeUpdate(DocumentEvent arg0) {
		connectWindow.pack();
	}
	

}
