//
//
//
///**
// * @author Pablo Edgar
// * 
// * November 28, 2017
// * 
// * Final Project "Snake Game" Part 2 - GameManagerTester Class
// * 
// * Class Description:
// * The GameManagerTester class checks Snake game functionality.
// * 
// * Game Description:
// * In a snake game the objective is to navigate a snake through a walled space (or maze), 
// * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
// * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
// * increases as the game progresses.
// */
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import javax.swing.SwingUtilities;
//import javax.swing.border.Border;
//
//public class GameManagerTester extends GameManager{
//
//    public GameManagerTester(String f1) {
//        super(f1);
//        // TODO Auto-generated constructor stub
//    }
//
//    Graphics g;
//    public static void createAndShowGUI() {
//
//        
//        JFrame f = new JFrame("Snake Game");
//        JPanel p = new JPanel(); // Make a JPanel;
//
//        f.getContentPane().add(p); // Add panel P to JFrame f
//
//        f.setSize(600, 500);
//        f.setResizable(true);
//        f.setVisible(true);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        p.add(snakegame);
//        
//    }
//
//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(new Runnable() {
//            
//            public void run() {
//                SnakeWindow snake = new SnakeWindow();
//                
//                createAndShowGUI();
//                 
//             
//            }
//        });
//
//    }
//}
////
////// while(!game2.gameEnded()){game2.MoveSnakeWithKeys1();game2.controlSnake(game2.MoveSnakeWithKeys1());game2.updateBoard();System.out.println(game2);}
////// SwingUtilities.invokeLater(new Runnable() {
//////
////// // Run layoutpractice
////// public void run() {
//////
////// new GameManagerTester();
////
////// });
////// GameManager game1 = new GameManager("maze-simple.txt");
////
////// while (!game1.gameEnded()) {
//////
////// System.out.print("> ");
////// game1.controlSnake();
////// game1.updateFrame();
////// System.out.println(game1);
//////
////// }
////// } else if(args[1].equals("g")) {
////
////// }
////// GameManager game2 = new GameManager("maze-cross.txt");
////
////// import java.awt.Toolkit;
////// import java.util.InputMismatchException;
////// import java.util.Scanner;
//////
////// public class GameManagerTester {
//////
////// /**
////// * Main Method
////// *
////// * @param args
////// */
////// public static void main(String[] args) {
//////
////// Scanner reader = new Scanner(System.in);
//////
////// GameManager complexWallGame = new GameManager("src/maze-cross.txt");
////// GameManager simpleWallGame = new GameManager("src/maze-simple.txt");
//////
////// // Catch input error with try/catch
////// try {
//////
////// System.out.println(
////// "This demo is using maze-simple.txt and maze-cross.txt as input");
////// System.out.println("Type '1' to begin. Then press ENTER ");
//////
////// int n = reader.nextInt();
////// System.out.println("");
////// reader.close();
//////
////// if (n == 1) {
//////
////// System.out.println("-Testing map from First File-");
////// System.out.println("The original board looks like this: ");
////// System.out.println(simpleWallGame);
//////
////// // while snake has not died, loop through game
////// while (!simpleWallGame.gameEnded()) {
//////
////// // Control Snake
////// simpleWallGame.controlSnake();
////// // Redraw board
////// simpleWallGame.updateBoard();
////// // Print new board
////// System.out.println(simpleWallGame);
////// }
//////
////// if (simpleWallGame.gameEnded() == true) {
////// Toolkit.getDefaultToolkit().beep();
////// System.out.println("Snake collided with itself");
////// System.out.println("You died!");
////// // while(!{
////// //
////// // // Control Snake using values
////// // complexWallGame.controlSnake("second");
////// // // Redraw board
////// // complexWallGame.updateBoard();
////// // // Print new board
////// // System.out.println(complexWallGame);
////// // }
//////
////// // // If game ended (snake dies) make a noise and alert
////// // user
////// // if (complexWallGame.gameEnded() == true) {
////// // Toolkit.getDefaultToolkit().beep();
////// // System.out.println("Snake collided with wall");
////// // System.out.println("You died!");
////// // }
////// //
////// // // Resume first game
////// // System.out.println("");
////// // System.out.println("- Resume testing from First File -
////// // ");
////// // System.out.println("");
////// //
////// // // Loop through remaining hard-coded values
////// // for (int i = 0; i < 5; i++) {
////// // simpleWallGame.controlSnake("restartsecond");
////// // simpleWallGame.updateBoard();
////// // System.out.println(simpleWallGame);
////// // }
//////
////// }
//////
////// }
//////
////// // Catch input exception
////// } catch (InputMismatchException e) {
////// System.out
////// .print("Wrong input! Enter either the number '1' or '2' ");
////// }
////// }
////// }
