# Monopoly

*Version 3.0 *

Date: 22/11/21

# Team Members:
Quinton Tracey  
Jestan Brar  
Raul Hoyos-Jimenez  
Ese Iyamu

# MILESTONE 1
In this milestone, players are able to play the game via keyboard. The player is able to roll the dice and depending on the state of the space on the board where they land, they would be able to do the following:- pass on a property, purchase a property, pay rent if owned by another player. The player would also be able to check their status in the game. Once their turn is over, they would pass the dice to the next player.

# MILESTONE 2
In this milestone, players utilize the GUI to play the game, interacting with the click of a mouse button. The code is refactored in a model - view - controller format. The model performs the calculations, handles the player rolls, exchanges of money etc.. The view displays all the componenents such as the board, the properties, the players, buttons etc.. The controller communicates between the model and the view. The rest of the functionality is the same as the previous model except information is displayed visually, such as status information, transactions, etc.. 

# MILESTONE 3
In this milestone, additional features such as houses, hotels, jail and A.I players were implemented.   
The houses and hotels feature enables the players to build houses on their properties and also get paid rent.   
The jail is used when players roll a double or land on "Go To Jail". They are sent to jail. Here they coudld either skip three turns, roll a double or pay the fine to get out of jail.   
Players can add A.I players to play the game with them. The A.I players would be able to play the game just like any other player woudld. They'll be able to purchased properties, build houses and hotels, pay rent, go to jail, etc.  
Changes Made:  
In this milestone, in addition to the features added, changes were made to the constructors of MonopolyModel and Player classes to include A.I. Also the access modifiers for method nextTurn() was changed from private to public to enable more efficient access to nextTurn().

# Deliverables:
### Project Code:  
  The project code consist of 16 classes:
  - modelTests
  - MonopolyController
  - MonopolyFrame 
  - MonopolyModel
  - MonopolyView
  - Board Generator 
  - Command
  - Dice
  - Event
  - Game
  - Jail
  - Player
  - Property
  - Railroad
  - Space
  - Utility  

### UML Class Diagram:  
A UML Class Diagram which shows and describes the structure of our monopoly system. It shows the classes, their attributes and methods. It also shows the relationship between classes.

### UML Sequence Diagram:
There are 3 sequence diagrams that show the interactions for when a  
  - A player buys a property/railroad/utility
  - A player pays rent on a property and goes bankrupt
  - An A.I buys a property

# Issues to be addressed
- Organize code better
- Display player names in a more visually appealling way 

# Future Milestones/Roadmap:
As the project progresses, the following would be added:         
In the next milestone, the game would have a save/load feature and international versions with custom street names, values and currencies.  


