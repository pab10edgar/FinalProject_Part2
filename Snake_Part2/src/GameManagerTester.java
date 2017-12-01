/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - GameManagerTester Class
 * 
 * Class Description:
 * The GameManagerTester class checks Snake game functionality.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */

import java.awt.Toolkit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManagerTester {

    /**
     * Main Method
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Import a scanner to get user input to choose level
        Scanner reader = new Scanner(System.in);

        // Create new instances of GameManager objects
        // Use file names as parameters
        GameManager complexWallGame = new GameManager("maze-cross.txt");
        GameManager simpleWallGame = new GameManager("maze-simple.txt");

        // Catch input error with try/catch
        try {

            // Prompt user to start demo
            System.out.println(
                    "This demo is using maze-simple.txt and maze-cross.txt as input");
            System.out.println("Type '1' to begin. Then press ENTER ");

            // Implement reader to gather selection
            int n = reader.nextInt();
            System.out.println("");
            reader.close();

            // Run game after user presses '1'
            if (n == 1) {

                System.out.println("-Testing map from First File-");

                // Print out original board prior to starting
                System.out.println("The original board looks like this: ");
                System.out.println(simpleWallGame);

                // Loop through hard-coded values
                for (int i = 0; i < 69; i++) {

                    // Control Snake using values
                    simpleWallGame.controlSnake("first");
                    // Redraw board
                    simpleWallGame.updateBoard();
                    // Print new board
                    System.out.println(simpleWallGame);
                }

                // Print out separating statement and original board for second
                // file
                System.out.println(" - Testing map from Second File -");
                System.out.println("The original board looks like this: ");
                System.out.println(complexWallGame);

                // Loop through hard-coded values
                for (int i = 0; i < 20; i++) {

                    // Control Snake using values
                    complexWallGame.controlSnake("second");
                    // Redraw board
                    complexWallGame.updateBoard();
                    // Print new board
                    System.out.println(complexWallGame);
                }

                // If game ended (snake dies) make a noise and alert user
                if (complexWallGame.gameEnded() == true) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Snake collided with wall");
                    System.out.println("You died!");
                }

                // Resume first game
                System.out.println("");
                System.out.println("- Resume testing from First File - ");
                System.out.println("");

                // Loop through remaining hard-coded values
                for (int i = 0; i < 5; i++) {
                    simpleWallGame.controlSnake("restartsecond");
                    simpleWallGame.updateBoard();
                    System.out.println(simpleWallGame);
                }

                // Alert user when game ends with sound and text
                if (simpleWallGame.gameEnded() == true) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Snake collided with itself");
                    System.out.println("You died!");
                }
            }

            // Catch input exception
        } catch (InputMismatchException e) {
            System.out
                    .print("Wrong input! Enter either the number '1' or '2' ");
        }
    }
}
