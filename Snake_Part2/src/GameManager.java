/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - GameManager Class
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

import java.util.Scanner;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileNotFoundException;

public class GameManager {

    // Private variable for height and width of game
    private int boardHeight;
    private int boardWidth;

    // Make a seed to ensure point is not "random" for testing purposes
    private static final int SEED = 300;
    private final Random random = new Random(SEED);

    // ArrayList to keep track of walls on board, enable easy access
    ArrayList<Wall> wallsOnBoard;

    // Scanners to keep track of hard-coded values for testing purposes
    private Scanner sc;
    private Scanner sc1;
    private Scanner sc2;

    // New Food and snake object
    Food food;
    Snake snake;

    // Boolean variable to keep track of current state of game
    boolean isGameEnded;

    // Boolean variables to keep track of hard-coded values for testing
    // (Set to false initially to allow switch between multiple test games)
    boolean isSC = false;
    boolean isSC1 = false;
    boolean isSC2 = false;

    /**
     * Constructor for GameManager that takes in a file as parameter. Parses
     * through file and places walls in a list. Try/catch for file exception.
     * Do/while loop that adds snake in a valid location. Generates a new food.
     * Beep sound on computer if file not found to alert user
     * 
     * @param a file f1
     */
    public GameManager(String f1) {

        // Hard-coded movement values for input 1 and 2 for testing purposes
        // The program is capable of user input if scanners are deleted
        sc = new Scanner(
                "u u r r r r r r r d d r r r r r r u u u u u l l l l l l l l l l l l l d d d d r r r r r r r r r r r r r u u u u u l l l l l l l l l l l l l l d r");
        sc1 = new Scanner("d d d d d d r r r r r r r r r u u u l l");
        sc2 = new Scanner("d d r u l");

        // Try/Catch loop to pull in file and error check
        try {

            // Initialize isGameEnded
            isGameEnded = false;

            Scanner sc = new Scanner(new File(f1));
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

            sc.close();

            // Catch file exception
        } catch (FileNotFoundException e) {
            Toolkit.getDefaultToolkit().beep();
            System.out.println(
                    "Can't find that file! Try adding a different one.");
        }
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
     * Distinguish which scanner to use for hard-coded movement testing
     * purposes.
     * 
     * @param s1 a string
     */
    public void chooseHardCodedScanner(String s1) {

        // Check string input to choose which scanner to use
        if (s1.equalsIgnoreCase("first")) {
            isSC = true;
        }
        if (s1.equalsIgnoreCase("second")) {
            isSC1 = true;
        }
        if (s1.equalsIgnoreCase("restartsecond")) {
            isSC2 = true;
        }
    }

    /**
     * Control snake movement. For testing purposes, use hard coded values.
     * Snake can move up, left, down and right.
     * 
     * @param String chooseScanner for Switch
     */
    public void controlSnake(String chooseScanner) {

        this.chooseHardCodedScanner(chooseScanner);

        if (isSC == true) {

            // Switch to allow movement of snake, up/down/left/right
            // Use starting letters to distinguish movement
            switch (sc.next()) {
            case "u":
            case "U":
                snake.changeDirection(Point.NORTH);
                break;
            case "d":
            case "D":
                snake.changeDirection(Point.SOUTH);
                break;
            case "r":
            case "R":
                snake.changeDirection(Point.EAST);
            case "l":
            case "L":
                snake.changeDirection(Point.WEST);
                break;
            }
            // Reset boolean variable
            isSC = false;
        }

        // Repeat switch for other scanners
        if (isSC1 == true) {
            switch (sc1.next()) {
            case "u":
            case "U":
                snake.changeDirection(Point.NORTH);
                break;
            case "d":
            case "D":
                snake.changeDirection(Point.SOUTH);
                break;
            case "r":
            case "R":
                snake.changeDirection(Point.EAST);
            case "l":
            case "L":
                snake.changeDirection(Point.WEST);
                break;
            }
            // Reset boolean variable
            isSC1 = false;
        }

        // Repeat switch for other scanner
        if (isSC2 == true) {
            switch (sc2.next()) {
            case "u":
            case "U":
                snake.changeDirection(Point.NORTH);
                break;
            case "d":
            case "D":
                snake.changeDirection(Point.SOUTH);
                break;
            case "r":
            case "R":
                snake.changeDirection(Point.EAST);
            case "l":
            case "L":
                snake.changeDirection(Point.WEST);
                break;
            }
            // Reset boolean variable
            isSC2 = false;
        }
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
     * Override toString() method to print out board at current state. Print
     * an 'X' for walls, a '.' for empty spaces, a 's' for snake, and a 'f' for
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