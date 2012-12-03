package courseProject.controller;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import courseProject.model.ExitDirection;
import courseProject.model.Game;
import courseProject.model.Player;
import courseProject.model.Room;
import courseProject.model.Item;
import courseProject.model.ItemType;
import courseProject.model.Monster;
import courseProject.view.twoD.drawable.Item2D;
import courseProject.view.twoD.drawable.Monster2D;
import courseProject.view.twoD.drawable.Player2D;


import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * A level loader reads an XML of a level design file and creates a Game
 * based off that design. Will use the files created by the editor.
 * @author Andrew
 *
 */
public class LevelLoader {
	
	private Map<String,Room> rooms;
	Document doc=null;
	
	/**
	 * creates a new LevelLoader
	 * @param 
	 */
	public LevelLoader()
	{
		rooms=new HashMap<String,Room>();
	}
	
	/**
	 * Set creates a document and saves it in doc.
	 * @param fileName
	 */
	private void parseXmlFile(File f){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			doc=db.parse(f);
			
		}catch(Exception e) {	
			System.out.println("exception");
		}
	}
	
	
	
	
	
	private void parseDocument(){
		
		
		//get the root element
		Element docEle = doc.getDocumentElement();

		//get a nodelist of elements for each room
		NodeList nodelist = docEle.getElementsByTagName("room");
		if(nodelist != null && nodelist.getLength() > 0) {
			for(int i = 0 ; i < nodelist.getLength();i++) {

				//get the room element
				Element el = (Element)nodelist.item(i);
				
				getRoom(el);
			}
		}
		
		nodelist = docEle.getElementsByTagName("connection");
		if(nodelist != null && nodelist.getLength() > 0) {
			for(int i = 0 ; i < nodelist.getLength();i++) {

				//get the room element
				Element el = (Element)nodelist.item(i);
				
				//get the Room object
				getConnection(el);
			}
		}
	}
	
	/**
	 * Adds the room represented by roomElement to the rooms map.
	 */
	private void getRoom(Element roomElement) {
		
		String name=getTextValue(roomElement,"name");
		String description=getTextValue(roomElement,"description");
		
		//Create a new Room with the value read from the xml nodes
		Room room = new Room(description);
		
		rooms.put(name, room);
		
		//get a nodelist of item elements
		NodeList nl = roomElement.getElementsByTagName("item");
		if(nl != null && nl.getLength() > 0) 
		{
					for(int i = 0 ; i < nl.getLength();i++) 
					{
						//get the item element
						Element el = (Element)nl.item(i);
						//get the Item object
						Item item = getItem(el);
						room.drop(item);
					}	
		}
		
		//get a nodelist of monster elements
		nl = roomElement.getElementsByTagName("monster");
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0 ; i < nl.getLength();i++) 
			{
				//get the item element
				Element el = (Element)nl.item(i);
				//get the Monster object
				Monster monster = getMonster(el);
				room.addMonster(monster);
			}	
		}
		
	}
	

	/**
	 * Makes and returns an item based of the XML element given
	 * @param itemElement
	 * @return 2DItem
	 */
	private Item getItem(Element itemElement) {
		String name = itemElement.getAttribute("name");
		String description = itemElement.getAttribute("description");
		String typeName = itemElement.getAttribute("type");
		int weight = getIntValue(itemElement,"weight");
		int value = getIntValue(itemElement,"value");
		int xloc=getIntValue(itemElement,"xloc");
		int yloc=getIntValue(itemElement,"yloc");
		String spriteName = itemElement.getAttribute("sprite");
		
		/*
		System.out.println(description);
		System.out.println(typeName);
		System.out.println(weight);
		System.out.println(spriteName);
		*/
		
		BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(spriteName));
        } catch (IOException e) {
        }
		
		Item2D item = new Item2D(name,description,weight, ItemType.ItemTypeFromString(typeName), value, sprite);
        
		item.setLocation(new Point(xloc,yloc));
		return item;
	}
	
	
	/**
	 * Makes and returns an Monster based of the XML element given
	 * @param itemElement
	 * @return 2DItem
	 */
	private Monster getMonster(Element monsterElement) {
		String name = monsterElement.getAttribute("name");
		int health = getIntValue(monsterElement,"health");
		int attack = getIntValue(monsterElement,"attack");
		int defence = getIntValue(monsterElement,"defence");
		int xloc=getIntValue(monsterElement,"xloc");
		int yloc=getIntValue(monsterElement,"yloc");
		String spriteName = monsterElement.getAttribute("sprite");
		
		BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(spriteName));
        } catch (IOException e) {
        }
		
        Monster2D monster=new Monster2D(name,health,attack,defence, 0,0, sprite);
        
		monster.setLocation(new Point(xloc,yloc));
		return monster;
	}
	/**
	 * Connects two rooms based off a connection element.
	 */
	private void getConnection(Element connectElement)
	{
		
		String origName = connectElement.getAttribute("originator");
		String termName = connectElement.getAttribute("terminator");
		
		String typeName = connectElement.getAttribute("type");
		int xloc=getIntValue(connectElement,"xloc");
		int yloc=getIntValue(connectElement,"yloc");
		
		Room originator=rooms.get(origName);
		Room terminator=rooms.get(termName);
		
		originator.addExit(ExitDirection.parse(typeName),terminator);
		
	}
	/**
	 * Searches the given element for the tag tagName then returns
	 * the String value between that opening and closing tag.
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	/**
	 * 
	 */
	public static void main(String[] args){
		LevelLoader loader=new LevelLoader();
		File f=new File("res\\gameTest.xml");
		loader.parseXmlFile(f);
		loader.parseDocument();
	}
	
	
	/**
	 * Creates a Game based off the XML file fileName
	 * @param fileName
	 * @return the new game
	 */
	public Game LoadLevel(String fileName) throws Exception
	{
		Game game= new Game();
		File f=new File(fileName);
		parseXmlFile(f);
		parseDocument();
		
		
		ArrayList<Room> roomList=new ArrayList<Room>();
		for(Room r:rooms.values())
		{
			roomList.add(r);
		}
		//game.setRooms(roomList);
		
		
		BufferedImage george = null;
	    try {
	       george = ImageIO.read(new File("res\\SingleGeorge.png"));
	    } catch (IOException e) {
	    }

	    //initialize player
	    Player  mc = new Player2D(roomList.get(0),20,1,1, george);
	    ((Player2D)mc).setLocation(new Point(75,150));
		
		return game;
		
	}
	/**
	 * retruns the document doc
	 * @return
	 */
	public Document getDoc() {
		return doc;
	}

}
