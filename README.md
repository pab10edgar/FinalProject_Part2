<h2>SNAKE GAME by <i>Pablo Edgar</i></h2>

<h3>CONTENTS OF THIS FILE</h3>
—————————————————
<ul>
<li>GUI Layout</li>
<li>Keyboard Controls</li>
<li>Objects in Game</li>
<li>Added Extras</li>
<li>Known Bugs</li>
<li>Description of Project Classes</li>
<li>Algorithm Details</li>
<li>GamePlay and Scoring</li>
<li>Moving the Snake</li>
<li>Food and Snake Growth</li>
<li>Wall and Snake Collisions</li>
<li>Game Maps</li>
</ul>

<h3>INTRODUCTION</h3> 
——————————————————
<br>Final Project for CS 251 - Runs a Snake Game playable by the user. 

<h3>REQUIREMENTS</h3>
——————————————————
<br>JRE 7+
<br>(Optional .txt file)  - Passed as args[1] in command line

<h3>GAME INFORMATION</h3>
——————————————————

<h4>GUI LAYOUT:</h4>

A GUI of default size 800x800. Utilizes Java Swing. 

Contains a main panel where game is drawn. Contains a separate grey panel on lower portion of GUI where a START GAME/PAUSE GAME button displays. Button displays START on original opening of application. Several JLabels with current score, current difficulty level, and brief description of game controls also live here. 

<h4>KEYBOARD CONTROLS:</h4>

Up Arrow - CHANGE DIRECTION UP<br>
Down Arrow - CHANGE DIRECTION DOWN<br>
Right Arrow - CHANGE DIRECTION RIGHT<br>
Left Arrow - CHANGE DIRECTION LEFT<br>

<h4>OBJECTS IN GAME:</h4>

"Food" = Burger image<br>
"Snake" = Speckled green tile<br>
"Wall" = "Brick" perimeter<br>

<h4>ADDED EXTRAS:</h4>

- Background game music 

- Sound Effects:
	* Snake Eats Food - "Burp"
	* Snake Hits Wall - "Explosion"
	* Snake Hits Itself - "Goo Blob Sound"

- Custom background image for game board

- Custom art for Food, Walls, and Snake

- Difficulty Levels (speed up snake movement)

- Ability to securely exit out of game by clicking option on pop-out

- Ability to restart game with higher difficulty by clicking option on pop-out

<h4>KNOWN BUGS:</h4>

- Application only resizable to certain extent. May display bottom menu incorrectly if grown/shrunk too much from original loading dimensions.

- Food may display in non-accessible location despite being a valid location (depends on map configuration file). **See Food description below for valid location guidelines 

<h4>DESCRIPTION OF PROJECT CLASSES:</h4>

<i>Snake.java</i><br>
The Snake class gives the “Snake” movement. Handles logic of snake. Snake is represented by a LinkedList that is manipulated throughout game. Implements FindPoint interface to handle a contains method.

<i>Food.java</i><br>
The Food class creates a Point (X,Y) that is represented as a “Food” object. The snake can “eat” this point. 

<i>Wall.java</i><br>
The Wall class creates boundaries within game that “kill” snake. Use starting and ending values, and (0,0) as top left corner for reference.

<i>Point.java</i><br>
The Point class allows the entire game and its objects to be referenced by (X,Y). Also gives direction to snake during movements (North, South, East, West). Use (0,0) to be top-left corner.

<i>FindPoint.java</i><br>
Common FindPoint interface with contains() method. Implemented in Snake class. Used to check if point contains a specific object.

<i>Direction.java</i><br>
Direction Enum that hold various hard coded values for direction of Snake movement.  Uses NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0), NO_DIRECTION(0,0). Takes in (X,Y) as constructor parameters.

<i>GameManager.java</i><br>
The GameManager class handles the snake, wall, and food book-keeping. Takes in a text file as a constructor. Handles parsing of text file and conversion of numbers by storing values in a ArrayList to create representation of board. Contains methods that generate food, control snake, and update board state. 

<i>SnakeWindow.java</i><br>
Creates GUI. Overrides paintComponent. Adds panels, button and contains all other graphical methods required to display application. Adds music/sound effects. Adds KeyListeners and ActionListeners to allow movement of snake through arrow keys. Contains a timer that is started/stopped. Utilizes GameManager methods for creation of GUI.

<i>SnakeMain.java</i><br>
Main class that runs application. Calls Snake Window and allows for argument to be passed in command line. Loads default map if no argument is given.

<h4>ALGORITHM DETAILS:</h4>

<i>Moving and growing the snake</i><br>
Move snake in particular direction using Direction input (North, South, East, West). Direction is dictated by arrow key events on keyboard. Since board starts at (0,0) in the top left corner, inverse values for North and South (eg. North = (0,-1)). Move snake forward by adding a point to front of snake in current direction, and removing the ‘tail’ by polling the last element in the LinkedList. Check if food object is eaten by seeing if head (snake.peekFirst()) contains a Food at a given instance.  If food object eaten, add a new Point to snake to allow the snake to grow. 

<i>Detecting collisions</i><br>
Iterate through walls on board and check to see if head of snake (snake.peekFirst()) contains a Wall object. Use same logic to check if snake collided with food or with itself. Return a boolean value true/false depending on result. 

<i>Detecting end of game</i><br>
Check status of boolean value returned from detectCollision() method in GameManager. If boolean value from collision with wall or snake is true, the game has ended. 

<i>Printing out game board state</i><br>
Override toString() in GameManager to print out representation of board. Use nested for loops to check each point within width and height boundaries of board, then check each point for specific object (Food, Wall, Snake), and print string representation of these objects. Use a StringBuilder to concatenate strings during board creation to improve program performance. 

In SnakeWindow, utilize results of toString() to convert string results to images by re-parsing through concatenated string. Convert these results to components visible in GUI. 

<h4>GAME PLAY AND SCORING:</h4>

The Snake Game begins when user presses the START button. After pressing START, the snake does not start moving until after the user also presses a keyboard arrow to dictate the initial direction of snake movement. The snake will then start moving in that initial direction.  The START button text will then change to PAUSE GAME. 

As the snake moves around the board, the initial score of '0' dictated by a JLabel will not change. When the snake eats a food, the score will increase by an increment of '100', and the updated score will display on the lower panel. The score will be reset when a new game is initialized. Likewise, a score of ‘0’ will be displayed on a new game with an increased difficulty level.

The default difficulty level (snake movement speed) is 1. This is also dictated by a JLabel in the button panel. Difficulty level can only be change after the snake dies playing an initial game of level 1. The difficulty is increased by clicking a option choice on the pop-out that displays when the snake dies. 

If the PAUSE GAME button is pressed, the game will pause and the snake will stop moving. Button text will switch to START GAME. The application will not respond to any keyboard commands during this time. After pressing START GAME again, the game will resume back to the state it was in prior to being paused. The button text will switch back to PAUSE GAME.

The game ends when the snake runs into a wall, or if the snake runs into itself. The user will be notified by a pop-out which will display the reason the snake died, and give the user several options to choose from  (Restart Game at Level 1, Restart Game and Increase Difficulty (Snake Speed), Exit Game).

<h4>MOVING THE SNAKE:</h4>

The snake will begin moving automatically in a given direction after the START button is clicked and an initial arrow key is pressed. Once moving, the snake will change direction with the up, down, left, and right arrow keys. These correspond to UP, DOWN, LEFT, and RIGHT respectively.

The Timer that manipulates the snake movement speed is initially set to 480ms (Difficulty Level 1). Snake movement becomes faster as the user chooses to increase the difficulty (Delay - 90). The user can only choose a different difficulty level after playing an initial round of the game.

<h4>FOOD AND SNAKE GROWTH:</h4>

A "food" item (represented by a "burger") is generated at a random unoccupied location at the start of the game. The location is determined as valid if the point does not contain a "Wall", another "Food", or a "Snake". 

A snake has a original length of 1 segment. If the Snake collides with a food, the snake consumes the food and grows in length by adding 1 segment to the tail. A new food is then generated at a new random valid location.

<h4>WALL AND SNAKE COLLISIONS:</h4>

If a snake collides with a wall or its own body, a popup is displayed on the screen and the game is ended. The popup will display whether the snake died due to a "Self Hit" or a "Wall Hit".  The user can choose to restart the current game at level 1 difficulty, restart the current game with a higher difficulty, or exit the game and perform a secure close of the application.

<h4>GAME MAPS:</h4>

When the user opens the JAR file, a default map with set location of walls is loaded. This map contains walls around all 4 edges of the application screen, and contain a "cross" of walls in the center. 

The user can choose to load a custom wall configuration file to the JAR, by passing an argument containing the file name loaded in the current directory. This map will then become the current map that gameplay will take place in, instead of the default map.
    
