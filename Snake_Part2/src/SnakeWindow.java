/**
 * @author Pablo Edgar
 * 
 * December 7th, 2017
 * 
 * Final Project "Snake Game" Part 2 - SnakeWindow
 * 
 * Class Description:
 * Creates a GUI interface for the Snake Game.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.SystemColor;
import java.applet.*;
import sun.audio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

@SuppressWarnings({ "unused", "serial" })
public class SnakeWindow extends JFrame implements ActionListener, KeyListener {

    // Timer delay and initialization
    public static int DELAY = 480;
    Timer timer = new Timer(DELAY, this);

    // Initialize variables
    GameManager game;
    GameManager game2;
    GamePanel gamePanel;
    JPanel buttonPanel;
    JButton startButton;
    JLabel score;
    JLabel instructions;
    JLabel arrows;
    JLabel myLevel;
    JLabel levelIs;
    BufferedImage burgerImg = null;
    BufferedImage wallImg = null;
    BufferedImage snakeImg = null;
    BufferedImage headImg = null;
    BufferedImage gameImg = null;
    Font f;
    Font g;
    FlowLayout flow;
    Dimension d;
    ClassLoader cl = this.getClass().getClassLoader();
    
    static int level = 1;
    static int gameScoreAmount = 0;

    /**
     * Snake Window Constructor. Takes in a GameManager object as a parameter.
     * Created panels and generic set-up of GUI.
     * 
     * @param GameManager game
     */
    public SnakeWindow(GameManager game) {

        // Set frame dimensions
        d = new Dimension(getWidth(), 40);
        
        // Set frame default operations
        this.game = game;
        this.setTitle("Snake Game - By Pablo Edgar");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout of buttonPanel
        flow = new FlowLayout(0, 10, 0);

        // Create Button
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.addKeyListener(this);

        // Create JLabels and set text/font
        myLevel = new JLabel("DIFFICULTY LEVEL:");
        levelIs = new JLabel();
        levelIs.setText(Integer.toString(level));
        score = new JLabel();
        score.setText(Integer.toString(gameScoreAmount));
        instructions = new JLabel("SCORE:");
        arrows = new JLabel("Press start, then press an arrow key to start moving!");

        // Set font
        f = arrows.getFont();
        g = score.getFont();
        arrows.setFont(f.deriveFont(f.getStyle() | Font.ITALIC));
        score.setFont(g.deriveFont(g.getStyle() | Font.BOLD));
        levelIs.setForeground(Color.BLUE);
        score.setForeground(Color.BLUE);

        // Create new panels
        gamePanel = new GamePanel();
        buttonPanel = new JPanel();

        // Add components to buttonPanel
        buttonPanel.setLayout(flow);
        buttonPanel.setPreferredSize(d);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(arrows);
        buttonPanel.add(startButton);
        buttonPanel.add(instructions);
        buttonPanel.add(score);
        buttonPanel.add(myLevel);
        buttonPanel.add(levelIs);

        // Add components to SnakeWindow including KeyListeners
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        // Set size of Frame
        this.setSize(800, 800);
        this.setVisible(true);

        // Import images used in game
        try {
            
            burgerImg = ImageIO
                    .read(getClass().getResourceAsStream("/bur.png"));
            wallImg = ImageIO
                    .read(getClass().getResourceAsStream("/brick-wall.jpg"));
            snakeImg = ImageIO
                    .read(getClass().getResourceAsStream("/snakeSkin2.png"));
            headImg = ImageIO
                    .read(getClass().getResourceAsStream("/snakeHead.png"));
            gameImg = ImageIO.read(getClass().getResourceAsStream("/bg.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic Override due to ActionListener Implementaion. Not used.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * keyPressed method that translates KeyEvent (Arrow Keys) into Snake
     * movement.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // Switch that takes in keycode. Different cases are various keyEvents
        switch (e.getKeyCode()) {

        case KeyEvent.VK_UP:
            // Set direction of snake after taking in KeyEvent
            game.controlSnake(Direction.NORTH);
            break;

        case KeyEvent.VK_DOWN:
            // Set direction of snake after taking in KeyEvent
            game.controlSnake(Direction.SOUTH);
            break;

        case KeyEvent.VK_RIGHT:
            // Set direction of snake after taking in KeyEvent
            game.controlSnake(Direction.EAST);
            break;

        case KeyEvent.VK_LEFT:
            // Set direction of snake after taking in KeyEvent
            game.controlSnake(Direction.WEST);
            break;
        }
    }

    /**
     * Generic Override due to ActionListener Implementaion. Not used.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Override actionPerformed to give directions to button
     */
    @SuppressWarnings("static-access")
    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        // If source is timer, and timer is running, move snake and check for wall, self, 
        // and food collisions
        if (src == timer) {

            // Redraw board
            game.updateBoard();

            if (game.isFoodEaten()) {
                
                // Increase score by 100 when isFoodEaten() is true
                this.gameScoreAmount = this.gameScoreAmount + 100;
                this.score.setText(Integer.toString(gameScoreAmount));

                try {
                    
                    // Open an audio input stream.
                    // Add burp sound effect when snake eats food
                    InputStream soundInputStream = getClass()
                            .getResourceAsStream("/burp.wav");
                    InputStream bufferedIn = new BufferedInputStream(
                            soundInputStream);
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(bufferedIn);

                    // Get a sound clip resource.
                    Clip clip = AudioSystem.getClip();

                    // Open audio clip and load samples from the audio input
                    // stream.
                    clip.open(audioIn);
                    clip.start();

                } catch (UnsupportedAudioFileException f) {
                    f.printStackTrace();
                } catch (IOException g) {
                    g.printStackTrace();
                } catch (LineUnavailableException h) {
                    h.printStackTrace();
                }
            }

            if (game.gameEnded() && game.isSelfHit()) {

                try {
                    
                    // Open an audio input stream.
                    // Add bubbles sound effect if snake hits itself
                    InputStream soundInputStream = getClass()
                            .getResourceAsStream("/bubbles.wav");
                    InputStream bufferedIn = new BufferedInputStream(
                            soundInputStream);
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(bufferedIn);

                    // Get a sound clip resource.
                    Clip clip = AudioSystem.getClip();

                    // Open audio clip and load samples from the audio input
                    // stream.
                    clip.open(audioIn);
                    clip.start();

                } catch (UnsupportedAudioFileException f) {
                    f.printStackTrace();
                } catch (IOException g) {
                    g.printStackTrace();
                } catch (LineUnavailableException h) {
                    h.printStackTrace();
                }

                // JOption pane with various choices after snake dies
                Object[] options = { "Restart Game at Level 1",
                        "Restart and Increase Difficulty (Snake Speed)",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit Itself and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);

                // Give commmands to each option in JOption pane (Option 1)
                if (n == 0) {
                    
                    // End current game
                    dispose();
                    
                    // Reset variables
                    DELAY = 480;
                    level = 1;
                    this.gameScoreAmount = 0;
                    this.levelIs.setText(Integer.toString(level));

                    // Start a new instance of a game depending on config map
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    }

                // Option 2
                } else if (n == 1) {
                    
                    // End current game
                    dispose();
                    
                    // Increase difficulty by decreasing timer delay
                    if (DELAY >= 120) {
                        DELAY = DELAY - 90;
                    }
                    
                    // Increase level and reset score
                    level++;
                    this.gameScoreAmount = 0;

                    // Start new instance of game
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    }
                }

                // Exit game without loading new game (Option 3)
                else {
                    dispose();
                }
                
                // Stop the timer
                timer.stop();
                
            } else if (game.gameEnded() && game.isWallHit()) {

                try {
                    // Open an audio input stream
                    // Add explosion sound effect if snake hits a wall
                    InputStream soundInputStream = getClass()
                            .getResourceAsStream("/bomb.wav");
                    InputStream bufferedIn = new BufferedInputStream(
                            soundInputStream);
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(bufferedIn);

                    // Get a sound clip resource.
                    Clip clip = AudioSystem.getClip();

                    // Open audio clip and load samples from the audio input
                    // stream.
                    clip.open(audioIn);
                    clip.start();

                } catch (UnsupportedAudioFileException f) {
                    f.printStackTrace();
                } catch (IOException g) {
                    g.printStackTrace();
                } catch (LineUnavailableException h) {
                    h.printStackTrace();
                }
                
                // JOption pane with various choices after snake dies
                Object[] options = { "Restart Game at Level 1",
                        "Restart and Increase Difficulty (Snake Speed)",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit a Wall and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);
                
                // Give commmands to each option in JOption pane (Option 1)
                if (n == 0) {
                    
                    // End current game     
                    dispose();
                    
                    // Reset variables
                    DELAY = 480;
                    level = 1;
                    this.gameScoreAmount = 0;
                    this.levelIs.setText(Integer.toString(level));

                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    }
  
                // Option 2
                } else if (n == 1) {

                    // End current game
                    dispose();
                    
                    // Increase difficulty by decreasing timer delay
                    if (DELAY >= 120) {
                        DELAY = DELAY - 90;
                    }
                    
                    // Increase level and reset score
                    level++;
                    this.gameScoreAmount = 0;

                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("/maze-cross.txt");
                        new SnakeWindow(game1);
                    }
                }
                // End game without initializing new game (Option 3)
                else {
                    dispose();
                }
                // Stop the timer
                timer.stop();
            }
            
            // Repaint on JPanel
            repaint();

        // If source is button
        } else if (src == startButton) {

            // Start timer when button is pressed and timer is not running
            if (!timer.isRunning()) {
                
                //Start timer
                timer.start();
                // Change text on button from "Start" to "Pause"
                startButton.setText("Pause Game");

            } else {
                
                // Stop timer
                timer.stop();
                // Change back text to "Start"
                startButton.setText("Start Game");
                
            }
        }
    }
    
    /** Internal nested class that contains paintComponent override. 
    *   Attaches graphics to preset value from toString() to give shape
    *   to game objects.
    */
    private class GamePanel extends JPanel {

        /**
         * Override paintComponent to place graphics on JPanel
         */
        @Override
        public void paintComponent(Graphics g2) {

            super.paintComponent(g2);
            
            // Draw a background image behind entire game board
            g2.drawImage(gameImg, 0, 0, getWidth(), getHeight(), null);
            
            // Get width and height of gameboard
            int width = this.getWidth() / game.getBoardWidth();
            int height = this.getHeight() / game.getBoardHeight();

            // Create a new char array containing values from toString() method 
            // implemented in GameManager class. 
            char[] symbols = game.toString().toCharArray();
            
            int x = 0;
            int y = 0;

            // Iterate through char array and place appropriate image with
            // corresponding case
            for (char c : symbols) {
                
                // Utilize toString() from GameManager to get various cases
                switch (c) {
                case 'X':
                    g2.setColor(Color.BLACK);
                    // Draw a "wall" image instead of 'x' on board 
                    g2.drawImage(wallImg, x, y, width, height, null);
                    break;
                case 's':
                    g2.setColor(Color.GREEN);
                    // Draw a "Snake" image instead of 's' on board 
                    g2.drawImage(snakeImg, x, y, width, height, null);
                    break;
                case 'f':
                    g2.setColor(Color.ORANGE);
                    // Draw a "food" image instead of 'f' on board 
                    g2.drawImage(burgerImg, x, y, width, height, null);
                    break;
                case '.':
                    break;
                case '\n':
                    // Increase height and move down a row
                    y += height;
                    x = 0;
                    continue;
                }
                
                // Move over a row
                x += width;
            }
        }
    }
}
