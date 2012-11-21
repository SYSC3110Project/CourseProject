CourseProject
=============

github: https://github.com/SYSC3110Project/CourseProject

Work Seperation:

For Milestone 2 we completely remodeled out design to use the MVC design pattern, this way we can run our game in any of the available views (currently
there is the text view and the 2Dview.
For Milestone 3 a map view was added in addition to more functionality for the top down view.

Work Seperation:

	Micheal worked on making the parser a part of the text view which notifies the controller (CommandInterpreter) of the input events. 
		-Added test cases for the player and monster classes in the model.
		-Made the map view and inventory window.
		
	Andrew added the inventory class to represent the list of items used by the room, the player and the monsters. He also modified the game class to
		no longer be the one to output game messages, making the text view handle the output.
		-Added test cases for the game class in the model.
		-Made the character window.
		
	Denis worked primarily on the 2D view, making the controller update the view and the model appropriately, also added the ability to control 
		the player with the mouse. Also helped out a little with modifying the parser to work with the text view.
		-Added test cases for the room and inventory classes in the model.
		-Made the undo and redo.
	
	Matthew worked on the making the 2D view able to draw all the 2D actors (player2D, room2D, item2D, monster2D), have hit collisions with each other, 
		and appropriately call the correct game commands based on mouse input.
		-Added test cases for the Item class in the model. 
		-Made the map panel as well as working with graphics.
		
	Everyone contributed to identifying and fixing bugs
	
Known Issues:

	-Java does not stop running upon quitting the text view.
	
	-The undo does not currently work very well with the mouse. 

Design Decisions:
	
	Milestone1:
	
	-Every time you enter a room, it creates a checkpoint and when you undo, it brings you (the player), to
	that checkpoint.
	
	-Every time you undo, it creates a redo point, If you decide to do any action aside from undo or redo,
	that redo point gets removed.
	
	-Everytime you leave a room that contains a monster, the next time you enter that room, the monster
	will be back at full health (even if the player killed the monster previously).
	
	-Players and Monsters have many similarities, therefore we made them both extend the the abstract class
	creatures.
	
	-Creatures have a weapon Item, an Armor Item, an Attack stat and a Defense Stat.
	
	-If the player is in the same room as a monster, that monster will attack the player everytime
	the player performs an action (excluding looking at the character stats).
	
	-The player may equip items he picks up (if the item is a weapon or armor)

	
	Milestone2:
	
	-We decided to modify the structure of our code to accommodate the MVC design pattern
	
		2D view decision:
		
		-we decided that the current room and everything in it is to be drawn to the screen for the user to see.
		
		-we decided that the player will be able to walk around each room using mouse inputs (you click somewhere in the room and the player moves there)
		
		-we decided that the player picks up an item he walks over
		
		-we decided that the player attack the monster if he goes in range (then the monster attacks back)
		
	Milestone3:
	
	-We decided not to provide a 'look' button for the top down view as all items and monsters can be seen on the screen.
	
	-We decided to split what was the 'inventory' command into two different commands, 'inventory' and 'use'.
		

.Java Files Used:

	Util.java

	Controller:
	
		Command.java
		CommandInterpreter.java
		CommandWord.java
		InputEvent.java
		InputEvent2D.java
		InputListener.java
		TextController.java
	
	Model:
		
		Creature.java
		ExitDirection.java
		Game.java
		Inventory.java
		Item.java
		ItemType.java
		ModelChangeEvent.java
		ModelListener.java
		Monster.java
		Player.java
		Room.java
		
	View:
		
		View.java
	
		MapView:
		
			MapPanel.java
			ViewMapD.java
	
		TextView:
			
			ViewText.java
			
		2DView:
		
			View2D.java
			
			Drawable:
			
				Drawable2D.java
				Drawable2DArea.java
				Item2D.java
				Monster2D.java
				Player2D.java
				Room2D.java
			