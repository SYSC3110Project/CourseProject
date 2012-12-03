package courseProject.controller;
import java.awt.Point;
import java.io.File;

import courseProject.model.ExitDirection;
import courseProject.model.Game;
import courseProject.model.Player;
import courseProject.model.Room;
import courseProject.model.Item;
import courseProject.model.ItemType;
import courseProject.model.Monster;
import courseProject.view.twoD.drawable.Item2D;
import courseProject.view.twoD.drawable.Room2D;
import courseProject.view.twoD.drawable.Monster2D;
import courseProject.view.twoD.drawable.Player2D;
import courseProject.view.twoD.drawable.SerializableBufferedImage;

import java.util.ArrayList;
import java.util.List;
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
	private Player mc;
	private List<Document> docs=null;
	
	/**
	 * creates a new LevelLoader
	 * @param 
	 */
	public LevelLoader()
	{
		rooms=new HashMap<String,Room>();
		docs=new ArrayList<Document>();
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
			Document doc=db.parse(f);
			System.out.println(doc);
			docs.add(doc);
			
		}catch(Exception e) {	
			System.out.println("exception");
		}
	}
	
	
	
	
	/**
	 * Parses the document made by parseXMLFile. Creates a a list of rooms
	 * with monsters and items in them and a player in one of the rooms.
	 */
	private void parseDocument(int docNumber){
		
		//get the root element
		Element docEle = docs.get(docNumber).getDocumentElement();

		//get a nodelist of elements for each room file tag
		NodeList nodelist = docEle.getElementsByTagName("room");
		if(nodelist != null && nodelist.getLength() > 0) {
			for(int i = 0 ; i < nodelist.getLength();i++) {
				
				
				//get the room element
				Element el = (Element)nodelist.item(i);
				
				String fileName=el.getTextContent();
				//System.out.println(fileName);
				parseXmlFile(new File(fileName));
				//System.out.println(docs);
				
				
		}
		}
		
		for(int i=1;i<docs.size();i++)
		{
			//get the root element
			Element roomdocEle = docs.get(i).getDocumentElement();

			//get a nodelist of elements for each room
			NodeList roomnodelist = roomdocEle.getElementsByTagName("room");
			
			if(roomnodelist != null && roomnodelist.getLength() > 0) {
				for(int j = 0 ; j < roomnodelist.getLength();j++) {
					
					
					//get the room element
					Element roomElement = (Element)roomnodelist.item(j);
					
					getRoom(roomElement);
					
					
			}
			}
			
		}
		
		
		nodelist = docEle.getElementsByTagName("connect");
		if(nodelist != null && nodelist.getLength() > 0) {
			for(int i = 0 ; i < nodelist.getLength();i++) {

				//get the room element
				Element el = (Element)nodelist.item(i);
				
				//get the Room object
				getConnection(el);
			}
		}
		nodelist=docEle.getElementsByTagName("player");
		if(nodelist != null && nodelist.getLength() > 0) {
			for(int i = 0 ; i < nodelist.getLength();i++) {
				//get the player element
				Element el = (Element)nodelist.item(0);
				mc=getPlayer(el);
			}
		}
	}
	
	/**
	 * Adds the room represented by roomElement to the rooms map.
	 */
	private void getRoom(Element roomElement) {
		String spriteName=roomElement.getAttribute("sprite");
		String name=getTextValue(roomElement,"name");
		String description=getTextValue(roomElement,"description");
		
		

		SerializableBufferedImage sprite = new SerializableBufferedImage(spriteName);
		
		//Create a new Room with the value read from the xml nodes
		Room2D room = new Room2D(description,sprite);
		
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
		
		
		SerializableBufferedImage sprite = new SerializableBufferedImage(spriteName);
		
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
		
		SerializableBufferedImage sprite = new SerializableBufferedImage(spriteName);
		
        Monster2D monster=new Monster2D(name,health,attack,defence, 0,0, sprite);
        
		monster.setLocation(new Point(xloc,yloc));
		return monster;
	}
	
	/**
	 * Connects two rooms based off a connection element.
	 */
	private void getConnection(Element connectElement)
	{
		String room1Name=""; 
		String room2Name="";
		String exit1="";
		String exit2=""; 
		
		Element el;
		
		NodeList nl = connectElement.getElementsByTagName("room1");
		if(nl != null && nl.getLength() > 0) 
		{
			el = (Element)nl.item(0);
			room1Name=el.getAttribute("name");
			
			nl = el.getElementsByTagName("exit");
			if(nl != null && nl.getLength() > 0) 
			{
				el = (Element)nl.item(0);
				exit1=el.getAttribute("type");
			}
		}
		
		
		nl = connectElement.getElementsByTagName("room2");
		if(nl != null && nl.getLength() > 0) 
		{
			el = (Element)nl.item(0);
			room2Name=el.getAttribute("name");
			
			nl = el.getElementsByTagName("exit");
			if(nl != null && nl.getLength() > 0) 
			{
				el = (Element)nl.item(0);
				exit2=el.getAttribute("type");
			}
		}
		
		
		
		Room room1=rooms.get(room1Name);
		Room room2=rooms.get(room2Name);
		System.out.println(exit1);
		System.out.println(exit2);
		
		room1.addExit(ExitDirection.parse(exit1),room2);
		room2.addExit(ExitDirection.parse(exit2),room1);
	
	}
	/**
	 * Creates a player based off of the xml and gives it a current room.
	 * @param playerElement
	 */
	private Player getPlayer(Element playerElement)
	{
		String startRoom=playerElement.getAttribute("startRoom");
		int health=getIntValue(playerElement,"health");
		int attack=getIntValue(playerElement,"attack");
		int defence=getIntValue(playerElement,"defence");
		int xloc=getIntValue(playerElement,"xloc");
		int yloc=getIntValue(playerElement,"yloc");
		
		String spriteName = playerElement.getAttribute("sprite");
		
		SerializableBufferedImage sprite = new SerializableBufferedImage(spriteName);
        
        Room room=rooms.get(startRoom);
        Player  mc = new Player2D(room,health,attack,defence, sprite);
	    ((Player2D)mc).setLocation(new Point(xloc,yloc));
	    
	    return mc;
		
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
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	/**
	 * 
	 */
	public static void main(String[] args){
		LevelLoader loader=new LevelLoader();
    	
    	Game game=new Game();
		try {
			Player player=loader.LoadLevel("res\\gameTest.xml");
			game.setPlayer(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a Player in a room based off the XML file fileName
	 * @param fileName
	 * @return the new game
	 */
	public Player LoadLevel(String fileName) throws Exception
	{
		File f=new File(fileName);
		parseXmlFile(f);
		parseDocument(0);
		return mc;
	}
	
}
