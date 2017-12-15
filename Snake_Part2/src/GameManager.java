
/**
 * @author Pablo Edgar
 * 
 * December 7th, 2017
 * 
 * Final Project "Snake Game" Part 2 - GameManager Class
 * 
 * Class Description:
 * The GameManager class handles the snake, wall, and food book-keeping.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class GameManager {

    // Private variable for height and width of game
    private int boardHeight;
    private int boardWidth;

    // Random point generation
    private final Random random = new Random();

    // ArrayList to keep track of walls on board, enable easy access
    ArrayList<Wall> wallsOnBoard;

    // Scanners
    private Scanner sc;

    // New Food and snake object
    Food food;
    Snake snake;

    // Boolean variable to keep track of current state of game
    boolean isGameEnded;

    /**
     * Constructor for GameManager that takes in a file as parameter. Parses
     * through file and places walls in a list. Try/catch for file exception.
     * Do/while loop that adds snake in a valid location. Generates a new food.
     * 
     * @param a file f1
     */
    public GameManager(String f1) {

        // Initialize isGameEnded
        isGameEnded = false;
        InputStream in = getClass().getResourceAsStream(f1);
        Scanner sc = new Scanner(in);

        String gameboardDimensions = sc.nextLine();

        // Gather numbers from text file in a array. Split at spaces to
        // separate input
        String[] numInput = gameboardDimensions.split(" ");

        // First and second number are board width and height
        boardWidth = Integer.parseInt(numInput[0]);
        boardHeight = Integer.parseInt(numInput[1]);

        wallsOnBoard = new ArrayList<Wall>();

        // Parse through file while there is still other lines
        while (sc.hasNextLine()) {

            String str1 = sc.nextLine();

            // Split at spaces
            numInput = str1.split(" ");

            // Create new x/y points based on pairs of numbers in text file
            Point p1 = new Point(Integer.parseInt(numInput[0]),
                    Integer.parseInt(numInput[1]));

            Point p2 = new Point(Integer.parseInt(numInput[2]),
                    Integer.parseInt(numInput[3]));

            // Add new points (Walls) to a list to keep track
            wallsOnBoard.add(new Wall(p1, p2));
        }

        // Variables for valid location and point head
        boolean isValidLocation;
        Point head;

        do {
            // Check for valid locations to add snake (head)
            isValidLocation = true;
            head = new Point(random.nextInt(boardWidth),
                    random.nextInt(boardHeight));

            // Check all walls to ensure random point is not in wall
            for (Wall wall : wallsOnBoard) {
                if (wall.contains(head)) {
                    isValidLocation = false;
                }
            }
        } while (!isValidLocation);
        
        // Add a new snake at a valid point
        snake = new Snake(head);

        // Add a new food to board
        food = generateFood();

        // Close scanner
        sc.close();
    }

    /**
     * Return Height of Game Board
     * 
     * @return boardHeight
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Return Width of Game Board
     * 
     * @return boardWidth
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Generate Food at random valid location.
     * 
     * @return new Food object at Point P
     */
    private Food generateFood() {

        Point p;
        boolean validLocation = true;

        // Check to make sure random location is not a wall or snake
        do {
            validLocation = true;

            // Create a new point at a random x,y, which will become a food
            p = new Point(random.nextInt(boardWidth),
                    random.nextInt(boardHeight));
            
            // Check that food is not being placed in a wall using for-each
            for (Wall wall : wallsOnBoard) {
                if (wall.contains(p)) {
                    validLocation = false;
                }
            }
            
            // Check that point is not in a snake also
            if (snake.contains(p)) {
                validLocation = false;
            }
            
        } while (!validLocation);

        // Create a new Food at new valid random point
        return new Food(p);
    }

    /**
     * Control snake movement. Implement a direction as parameter to allow 
     * snake to move either North, South, East or West
     *
     * 
     * @param Direction d that snake will move towards
     */
    public void controlSnake(Direction d) {
        snake.changeDirection(d);
    }

    /**
     * Check whether the game is still going or has ended. Return true or
     * false.
     * 
     * @return Boolean isGameEnded
     */
    public boolean gameEnded() {
        return isGameEnded;

    }

    /**
     * Check if snake hit a wall
     * 
     * @return boolean
     */
    public boolean isWallHit() {
        return snake.getWallHit();
    }

    /**
     * Check if snake hit itself
     * 
     * @return boolean
     */
    public boolean isSelfHit() {
        return snake.getSelfHit();
    }

    /**
     * Check if snake ate food
     * 
     * @return boolean
     */
    public boolean isFoodEaten() {
        return snake.didEatFood();
    }

    /**
     * Update board by moving snake one spot to a particular direction, check
     * if a collision occurred, and eat a food object. If food has been eaten,
     * generate a new Food at a random location.
     */
    public void updateBoard() {

        // Move snake a particular direction
        snake.move();

        // check if collision with a wall has occurred by reading list of walls
        isGameEnded = snake.isCollision(wallsOnBoard);

        // check if snake head is on top of food object
        if (food.contains(snake.getHead())) {

            // eat food
            snake.eatFood();

            // generate new food
            food = generateFood();
        }
    }

    /**
     * Override toString() method to print out board at current state. Print an
     * 'X' for walls, a '.' for empty spaces, a 's' for snake, and a 'f' for
     * food object.
     */
    @Override
    public String toString() {

        // Increase program performance with StringBuilder
        StringBuilder str = new StringBuilder();

        // Nested for loops to loop through height and width of board
        for (int i = 0; i < boardHeight; i++) {
            // Continue at x direction the second for loop if point is in wall
            point: for (int j = 0; j < boardWidth; j++) {

                // Create a new point
                Point p = new Point(j, i);

                // Check all walls with for-each loop
                for (Wall wall : wallsOnBoard) {
                    // If wall contains point, add an 'x' to str
                    if (wall.contains(p)) {
                        str.append("X");
                        continue point;
                    }
                }

                // If statements to check if Food or Snake needs to be added to
                // str
                if (snake.contains(p)) {
                    str.append("s");
                } else if (food.contains(p)) {
                    str.append("f");
                } else {
                    str.append(".");
                }
            }
            // Add a new line to maintain required shape of board
            str.append("\n");
        }
        // Return str after building board
        return str.toString();
    }
}
