CourseProject
=============
Work Seperation:

Since we have already done some work on the world of zuul in tutorials, we decided to use micheal's code as a starting point, which already had a lot of the code.


Micheal worked primarily on the Room, Player, Item and Game classes, also collaborated with Andrew to make the Player inherit from creature.

Andrew worked primarily on the Monster and Creature classes

Denis Worked on adding the ability to undo and redo things that happen within the game

Matthew worked on making the UML diagrams, Sequence Diagrams, documentation and some refactoring


Design Decisions:
	
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


.Java Files Used:

Command.java - Class that holds the information about the command. This class is unchanged from the world of Zuul.

CommandWords.java - Class that holds the list of commands, we added commands (take, drop, inv, attack, char, undo,
		redo, so that They could be used in the game.

Parser.java - The parser reads the input from the user and interprets it as a command, if it cannot do this, 
		the command is invalid.

Creature.java - This class is abstract, it is used as a base model for the player and monster classes.

Player.java - Class that contains all the information about the player (items, health, stamina, attack, defense, etc)

Monster.java - Class that contains all the information about the monster (rooms may have monsters).

Room.java - Contains all the information about a current room, which items are in the room, which monsters are
		in the room (if there are any), what the exits are, etc.

Item.java - Contains all the information about the items.

Game.java - Main class which is used to play the game.




