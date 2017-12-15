
/**
 * @author Pablo Edgar
 * 
 * December 7th, 2017
 * 
 * Final Project "Snake Game" Part 2 - SnakeMain Class
 * 
 * Class Description:
 * Runs the Snake Game. Allows for command line input for config file for walls.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

public class SnakeMain {
    
    public static String arg = "";

    // Default constructor
    public SnakeMain() {}

    /**
     * Gets current argument that is passes in command line
     * 
     * @return String
     */
    public String getArgument() {
        return arg;
    }

    /**
     * Main Method that invokes new GameManager and SnakeWindow to start game
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Check if command line argument exists
        if (args.length != 1) {

            SwingUtilities.invokeLater(new Runnable() {
                GameManager game = new GameManager("/maze-cross.txt");

                @SuppressWarnings("static-access")
                public void run() {
                    // Try/catch to prevent exception errors
                    try {
                        // Open an audio input stream.
                        InputStream soundInputStream = getClass()
                                .getResourceAsStream("/backgroundMusic.wav");
                        InputStream bufferedIn = new BufferedInputStream(
                                soundInputStream);
                        AudioInputStream audioIn = AudioSystem
                                .getAudioInputStream(bufferedIn);

                        // Get a sound clip resource.
                        Clip clip = AudioSystem.getClip();

                        // Load sample from audio stream
                        clip.open(audioIn);
                        clip.start();
                        clip.loop(clip.LOOP_CONTINUOUSLY);

                    } catch (UnsupportedAudioFileException f) {
                        f.printStackTrace();
                    } catch (IOException g) {
                        g.printStackTrace();
                    } catch (LineUnavailableException h) {
                        h.printStackTrace();
                    }
                    // Create a new SnakeWindow with new GameManager
                    new SnakeWindow(game);
                }
            });
            
        } else {
            // Apply custom map config file to new GameManager
            GameManager game = new GameManager(args[0]);
            
            // Convert argument to string
            arg = args[0].toString();

            SwingUtilities.invokeLater(new Runnable() {  
                @SuppressWarnings("static-access")
                public void run() {
                    try {
                        // Open an audio input stream.
                        InputStream soundInputStream = getClass()
                                .getResourceAsStream("/backgroundMusic.wav");
                        InputStream bufferedIn = new BufferedInputStream(
                                soundInputStream);
                        AudioInputStream audioIn = AudioSystem
                                .getAudioInputStream(bufferedIn);

                        // Get a sound clip resource.
                        Clip clip = AudioSystem.getClip();
                        
                        // Load sample from audio stream
                        clip.open(audioIn);
                        clip.start();
                        clip.loop(clip.LOOP_CONTINUOUSLY);

                    } catch (UnsupportedAudioFileException f) {
                        f.printStackTrace();
                    } catch (IOException g) {
                        g.printStackTrace();
                    } catch (LineUnavailableException h) {
                        h.printStackTrace();
                    }
                    // Create a new SnakeWindow with new GameManager
                    new SnakeWindow(game);
                }
            });
        }
    }
}
