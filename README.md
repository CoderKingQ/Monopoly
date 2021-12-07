# Monopoly

*Version 4.0 *

Date: 06/12/21

# Team Members:
Quinton Tracey  
Jestan Brar  
Raul Hoyos-Jimenez  
Ese Iyamu

# MILESTONE 1
In this milestone, players are able to play the game via keyboard. The player is able to roll the dice and depending on the state of the space on the board where they land, they would be able to do the following:- pass on a property, purchase a property, pay rent if owned by another player. The player would also be able to check their status in the game. Once their turn is over, they would pass the dice to the next player.

Future Roadmap: in the future we will look to improve the user experience by implementing a GUI for the user to interact with. Moreover we will add visual elements to make the game more enjoyable and easier to understand. 

# MILESTONE 2
In this milestone, players utilize the GUI to play the game, interacting with the click of a mouse button. The code is refactored in a model - view - controller format. The model performs the calculations, handles the player rolls, exchanges of money etc.. The view displays all the componenents such as the board, the properties, the players, buttons etc.. The controller communicates between the model and the view. The rest of the functionality is the same as the previous model except information is displayed visually, such as status information, transactions, etc.. 

Future Roadmap: in the future we will look to improve the user experience by implementing additional functionality and features for houses, hotels, jail and even include AI players. This will make the game more interesting as players require more strategy with spending money on houses and hotels in order to win. We also hope to clean up some code areas to make it easier to understand and follow. 

# MILESTONE 3
In this milestone, additional features such as houses, hotels, jail and A.I players were implemented.  
The houses and hotels feature enables the players to build houses on their properties and also get paid rent.   
The jail is used when players roll a double or land on "Go To Jail". They are sent to jail. Here they coudld either skip three turns, roll a double or pay the fine to get out of jail.   
Players can add A.I players to play the game with them. The A.I players would be able to play the game just like any other player woudld. They'll be able to purchase properties, build houses and hotels, pay rent, go to jail, etc.  
Changes Made:  
In this milestone, in addition to the features added, changes were made to the constructors of MonopolyModel and Player classes to include the A.I players. Also the access modifiers for method nextTurn() was changed from private to public to enable more efficient access to nextTurn().

Future Roadmap: in the future we will look to add features to save and load game states and also include international versions for players around the world. These changes will benefit the user because games can be paused and resumed at later stages.

# MILESTONE 4
In this milestone, the following additioinal features were added:

A save and load feature using java serialization: Here the player has the ability to pause a game mid-play, save the game using the save feature and come back at a later time to continue the game using the load feature. When the player loads the game, the game starts from the exact position before the game was saved and continues with the last known current player.  

Option to customize the board: This enables the player to be able to customise the board to use international versions and/or a different currency. This is done with the use of an editable text file and file reader.

Future roadmap: include images to go with the properties, work on some visual aspects with the GUI to better the user experience. 

# Deliverables:
### Project Code:  
  The project code consist of 12 classes:
  - MonopolyModel
  - MonopolyFrame 
  - MonopolyController
  - MonopolyView
  - modelTests
  - Dice
  - Event
  - Player
  - Property
  - Railroad
  - Space
  - Utility  

### UML Class Diagram:  
A UML Class Diagram which shows and describes the structure of our monopoly system. It shows the classes, their attributes and methods. It also shows the relationship between classes.

### UML Sequence Diagram:
There are 8 sequence diagrams that show the interactions for when a player 
  - goes bankrupt
  - buys a space
  - buys houses
  - rolls doubles 3 times and goes to jail
  - lands on the go to jail space
  - saves the game
  - loads a saved game
  - passes go


# Issues to be addressed
There are no observed issues.
