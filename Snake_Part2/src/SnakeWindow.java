/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
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

    // Timer delay
    public static int DELAY = 480;

    GameManager game;
    JPanel buttonPanel;
    GamePanel gamePanel;
    Timer timer = new Timer(DELAY, this);
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
    GameManager game2;
    FlowLayout flow;
    Dimension d;

    static int level = 1;
    static int gameScoreAmount = 0;

    /**
     * Snake Window Constructor. Takes in a GameManager object as a parameter.
     * Created panels and generic set-up of GUI.
     * 
     * @param GameManager
     *            game
     */
    public SnakeWindow(GameManager game) {

        d = new Dimension(getWidth(), 40);
        this.game = game;
        this.setTitle("Snake");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        flow = new FlowLayout(0, 10, 0);
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.addKeyListener(this);

        myLevel = new JLabel("DIFFICULTY LEVEL:");
        levelIs = new JLabel();
        levelIs.setText(Integer.toString(level));

        score = new JLabel();
        score.setText(Integer.toString(gameScoreAmount));

        instructions = new JLabel("SCORE:");
        arrows = new JLabel(
                "Press start, then press an arrow key to start moving!");
        f = arrows.getFont();
        g = score.getFont();

        arrows.setFont(f.deriveFont(f.getStyle() | Font.ITALIC));
        score.setFont(g.deriveFont(g.getStyle() | Font.BOLD));
        levelIs.setForeground(Color.BLUE);
        score.setForeground(Color.BLUE);

        gamePanel = new GamePanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(flow);
        buttonPanel.setPreferredSize(d);

        buttonPanel.setBackground(Color.LIGHT_GRAY);

        buttonPanel.add(arrows);
        buttonPanel.add(startButton);
        buttonPanel.add(instructions);
        buttonPanel.add(score);
        buttonPanel.add(myLevel);
        buttonPanel.add(levelIs);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.setSize(800, 800);
        this.setVisible(true);

        try {

            burgerImg = ImageIO.read(new File("bur.png"));
            wallImg = ImageIO.read(new File("brick-wall.jpg"));
            snakeImg = ImageIO.read(new File("snakeSkin2.png"));
            headImg = ImageIO.read(new File("snakeHead.png"));
            gameImg = ImageIO.read(new File("bg.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // timer.start();

    }

    /**
     * Generic Override due to ActionListener Implementaion. Not used.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * keyPressed method that translates KeyEvent (Arrow Keys) into Snake
     * movement. Set
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

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @SuppressWarnings("static-access")
    @Override
    public void actionPerformed(ActionEvent e) {

        // int gameScoreAmount = 0;
        // String gameScore = Integer.toString(gameScoreAmount);

        Object src = e.getSource();

        if (src == timer) {

            game.updateBoard();

            if (game.isFoodEaten()) {
                this.gameScoreAmount = this.gameScoreAmount + 100;
                this.score.setText(Integer.toString(gameScoreAmount));
                try {
                    // Open an audio input stream.
                    File soundFile = new File("burp.wav");
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(soundFile);
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
                    File soundFile = new File("bubbles.wav");
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(soundFile);
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
                // timer.stop();

                Object[] options = { "Restart Game at Level 1",
                        "Restart and Increase Difficulty (Snake Speed)",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit Itself and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);

                if (n == 0) {

                    dispose();
                    DELAY = 480;
                    level = 1;
                    this.gameScoreAmount = 0;
                    this.levelIs.setText(Integer.toString(level));
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    }

                    // GameManager game1 = new GameManager("maze-cross.txt");
                    // new SnakeWindow(game1);

                } else if (n == 1) {
                    dispose();
                    if (DELAY >= 120) {
                        DELAY = DELAY - 90;
                    }
                    level++;
                    this.gameScoreAmount = 0;
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    }
                }
                // } else if (n == 2) {
                // dispose();
                // DELAY = 480;
                // level = 1;
                // this.levelIs.setText(Integer.toString(level));
                // GameManager game2 = new GameManager("src/maze-simple.txt");
                // new SnakeWindow(game2);
                // this.setSize(1000, 800);
                // } else
                else {
                    dispose();
                }
                timer.stop();

            } else if (game.gameEnded() && game.isWallHit()) {

                try {
                    // Open an audio input stream.
                    File soundFile = new File("bomb.wav");
                    AudioInputStream audioIn = AudioSystem
                            .getAudioInputStream(soundFile);
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
                // timer.stop();

                Object[] options = { "Restart Game at Level 1",
                        "Restart and Increase Difficulty (Snake Speed)",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit a Wall and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);

                if (n == 0) {

                    dispose();
                    DELAY = 480;
                    level = 1;
                    this.gameScoreAmount = 0;
                    this.levelIs.setText(Integer.toString(level));
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    }

                    // GameManager game1 = new GameManager("maze-cross.txt");
                    // new SnakeWindow(game1);

                } else if (n == 1) {
                    dispose();
                    if (DELAY >= 120) {
                        DELAY = DELAY - 90;
                    }
                    level++;
                    this.gameScoreAmount = 0;
                    if (!SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager(SnakeMain.arg);
                        new SnakeWindow(game1);
                    } else if (SnakeMain.arg.equals("")) {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    } else {
                        GameManager game1 = new GameManager("maze-cross.txt");
                        new SnakeWindow(game1);
                    }
                }
                // else if (n == 2) {
                // dispose();
                // DELAY = 480;
                // level = 1;
                // this.levelIs.setText(Integer.toString(level));
                // GameManager game2 = new GameManager("src/maze-simple.txt");
                // new SnakeWindow(game2);
                // this.setSize(1000,800);
                // } else {
                else {
                    dispose();
                }
                timer.stop();
            }
            repaint();

        } else if (src == startButton) {

            if (!timer.isRunning()) {
                timer.start();

                startButton.setText("Pause Game");

            } else {

                timer.stop();

                startButton.setText("Start Game");
            }
        }
    }

    

    private class GamePanel extends JPanel {

        @Override
        public void paintComponent(Graphics g2) {

            super.paintComponent(g2);
            g2.drawImage(gameImg, 0, 0, getWidth(), getHeight(), null);

            int width = this.getWidth() / game.getBoardWidth();
            int height = this.getHeight() / game.getBoardHeight();

            char[] symbols = game.toString().toCharArray();
            int x = 0;
            int y = 0;

            // boolean flag = true;
            for (char c : symbols) {
                switch (c) {
                case 'X':
                    g2.setColor(Color.BLACK);
                    // g2.fillRect(x, y, width, height);
                    g2.drawImage(wallImg, x, y, width, height, null);
                    break;

                case 's':
                    // if(flag) {
                    g2.setColor(Color.GREEN);
                    // g2.fillRect(x, y, width, height);
                    // g2.drawImage(headImg, x, y, width, height, null);
                    // flag = false;
                    // }
                    // else {
                    g2.drawImage(snakeImg, x, y, width, height, null);

                    // }
                    break;

                case 'f':
                    g2.setColor(Color.ORANGE);
                    g2.drawImage(burgerImg, x, y, width, height, null);
                    break;

                case '.':
                    // g2.setColor(Color.WHITE);
                    // g2.drawImage(gameImg, x, y, width, height, null);
                    // g2.fillRect(x, y, width, height);
                    break;

                case '\n':
                    y += height;
                    x = 0;
                    continue;
                }

                // g2.fillRect(x, y, width, height);
                x += width;
            }
        }

    }
}
