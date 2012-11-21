CourseProject
=============

github: https://github.com/SYSC3110Project/CourseProject

Work Seperation:

For Milestone 3 made a few changes to our game, we have fixed the bug that made our undo/redo unusable and we have added GUI interfaces for the Inventory and
		Character information, we have also added a 2D text view or map view (what we were supposed to have for milestone 2 (since it turned out that we did
		the 3d view instead). We also added functionality to the current top down view (3D view)

Work Seperation:

	Micheal worked on adding the GUI view for the inventory (meaning it is no longer a text based inventory when in the 3D view
	-Made sequence Diagrams for go, use and Character commands		

	Andrew worked on adding the GUI view for the character information (display which items the player has equipped, show the amount of health remaining, etc)
	-Made sequence Diagrams for Help, Take and Drop commands		

	Denis worked on fixing the undo/redo methods so that they work properly (items no longer disappear when undoing)
	-Made sequence diagrams for undo, redo and quit commands	

	Matthew worked on adding the 2D text view to the game (a view where you essentially see a map if the area and where you are in it) 
	-Made sequence diagrams for look, attack and inventory commands

	
		
	
Known Issues:

	-From time to time, the game doesn't start, this is rare. To bypass this, the game can
	be restarted. 
	
	-The undo does not currently work very well with the mouse. 
	
	-The undo doesn't work properly in some cases

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
	
		TextView:
			
			ViewText.java
			
		2D:
			
			Drawable2D.java
			Drawable2DArea.java
			Item2D.java
			Monster2D.java
			Player2D.java
			Room2D.java
			