# Monopoly

*Version 1.0 *

Date: 25/10/21

# Team Members:
Quinton Tracey  
Jestan Brar  
Raul Hoyos-Jimenez  
Ese Iyamu

# MILESTONE 1
In this milestone, players are able to play the game via keyboard. The player is able to roll the dice and depending on the state of the space on the board where they land, they would be able to do the following:- pass on a property, purchase a property, pay rent if owned by another player. The player would also be able to check their status in the game. Once their turn is over, they would pass the dice to the next player.

# MILESTONE 2
In this milestone, players utilize the GUI to play the game, interacting with the click of a mouse button. The code is refactored in a model - view - controller format. The model performs the calculations, handles the player rolls, exchanges of money etc.. The view displays all the componenents such as the board, the properties, the players, buttons etc.. The controller communicates between the model and the view. The rest of the functionality is the same as the previous model except information is displayed visually, such as status information, transactions, etc.. 


# Deliverables:
### Project Code:  
  The project code consist of 11 classes:
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
There are 2 sequence diagrams that show the interaction for when a player 
  - buys a property/railroad/utility
  - pays rent on a property and goes bankrupt

# Issues to be addressed
- Organize code better
- Display player names in a more visually appealling way 

# Future Milestones/Roadmap:
As the project progresses, the following would be added:         
adding functionality for houses, hotels and chance. 
Players would be able to play with A.I's / bots
The game would have a save/load feature and international versions with custom street names, values and currencies.  


