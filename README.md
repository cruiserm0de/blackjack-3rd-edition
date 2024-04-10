# Blackjack
Simplified digital version of a popular casino card game of Blackjack with robotic Dealer.

Third edition of a small java project by @cruiserm0de.
# Launching
The project runs in Terminal of any IDE with installed Java and Java SDK, as it has no external UI yet. 

Main method is located and executed from Game.java project class.

Additional text file to store the names of the players for the very next game session will be created in a project folder during the first launch.
# Description
[The original game on Wikipedia](https://en.wikipedia.org/wiki/Blackjack)

The project preserves the order and the concept of the original Blackjack game. However, being a simplified version of one, 
it does not include the Double down, Split, Surrender, and Insurance game choices, as it was never intended to be played seriously with real money involved. 

Number of players for each game session varies from 2 to 7. List of players of the preceding game session is saved in a text file, and the user can choose to continue with the same list, remove some players from the list, add some new players to the list, or create new list of the players at the beginning of every game session. Deleting the text file leads to a loss of the list of players from the preceding game session. The program is running and offering the user to play one more game until the user exits the program by typing a particular number in response to Game Menu.