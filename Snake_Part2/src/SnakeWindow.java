import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SnakeWindow extends JFrame implements ActionListener, KeyListener {

    // Timer delay
    private static final int DELAY = 500;

    GameManager game;
    JPanel buttonPanel;
    GamePanel gamePanel;
    Timer timer = new Timer(DELAY, this);
    JButton startButton;

    /**
     * Snake Window Constructor. Takes in a GameManager object as a parameter.
     * Created panels and generic set-up of GUI.
     * 
     * @param GameManager
     *            game
     */
    public SnakeWindow(GameManager game) {
        this.game = game;

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.addKeyListener(this);

        gamePanel = new GamePanel();
        buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.setSize(1000, 1000);
        this.setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if (src == timer) {

            game.updateBoard();

            if (game.gameEnded() && game.isSelfHit()) {

                timer.stop();

                // Custom button text
                Object[] options = { "Play Again on Same Level",
                        "Play a Different Level", "Change Game Difficulty",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit Itself and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);

            } else if (game.gameEnded() && game.isWallHit()) {

                timer.stop();

                // Custom button text
                Object[] options = { "Play Again on Same Level",
                        "Play a Different Level", "Change Game Difficulty",
                        "Exit Game" };
                int n = JOptionPane.showOptionDialog(gamePanel,
                        "GAME OVER - Your Snake Hit a Wall and Died! ",
                        "Game Over!", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[2]);
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            GameManager game = new GameManager("src/maze-cross.txt");

            public void run() {

                new SnakeWindow(game);
            }
        });
    }

    private class GamePanel extends JPanel {

        BufferedImage img = null;

        @Override
        public void paintComponent(Graphics g2) {

            super.paintComponent(g2);
            try {
                img = ImageIO.read(new File("src/Desktop/snake_food.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            // g2.drawImage(img, 0, 0, w, h, null);

            int scale = 2;

            int width = getWidth() / game.getBoardWidth();
            int height = getHeight() / game.getBoardHeight();

            char[] symbols = game.toString().toCharArray();
            int x = 0;
            int y = 0;
            for (char c : symbols) {
                switch (c) {
                case 'X':
                    g2.setColor(Color.BLACK);
                    break;
                case 's':
                    g2.setColor(Color.GREEN);
                    break;
                case 'f':
                    g2.setColor(Color.ORANGE);
                    g2.drawImage(img, x, y, w * scale, h * scale, null);
                    break;
                case '.':
                    g2.setColor(Color.WHITE);
                    break;
                case '\n':
                    y += height;
                    x = 0;
                    continue;
                }

                g2.fillRect(x, y, width, height);
                x += width;
            }
        }

    }
}

// @Override
// public void actionPerformed(ActionEvent e) {
//
// // Put "right" keystroke to map
//
// map1.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
//
// getActionMap().put("moveLeft", new AbstractAction() {
// private static final long serialVersionUID = 1L;
//
// public void actionPerformed(ActionEvent e) {
// snake.changeDirection(Point.WEST);
//
// }
// });
//
// // Put "left" keystroke to map
// map1.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
//
// getActionMap().put("moveLeft", new AbstractAction() {
// private static final long serialVersionUID = 1L;
//
// public void actionPerformed(ActionEvent e) {
// snake.changeDirection(Point.WEST);
//
// }
// });
//
// // Put "down" keystroke to map
// map1.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
//
// getActionMap().put("moveDown", new AbstractAction() {
// private static final long serialVersionUID = 1L;
//
// public void actionPerformed(ActionEvent e) {
// snake.changeDirection(Point.SOUTH);
//
// }
// });
//
// // Put "up" keystroke to map
// map1.put(KeyStroke.getKeyStroke("UP"), "moveUp");
//
// getActionMap().put("moveUp", new AbstractAction() {
// private static final long serialVersionUID = 1L;
//
// public void actionPerformed(ActionEvent e) {
// snake.changeDirection(Point.NORTH);
//
// }
// });
// }
//
// }
//
// public static void main(String[] args) {
//
// SwingUtilities.invokeLater(new Runnable() {
//
// public void run() {
// JFrame f = new JFrame("Snake Game");
// JPanel p = new JPanel(); // Make a JPanel;
//
// f.getContentPane().add(p); // Add panel P to JFrame f
//
// f.setSize(600, 500);
// f.setResizable(true);
// f.setVisible(true);
// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
// while (!game.gameEnded()) {
// game.controlSnake();
// game.updateBoard();
// }
// }
// });
//
// }
//
// }
//
//// void initializeGame() {
//// snake2.setJoints(3); // set our snake's initial size
////
//// // Create our snake's body
//// for (int i = 0; i < snake.getJoints(); i++) {
//// snake2.setSnakeX(BOARDWIDTH / 2);
//// snake2.setSnakeY(BOARDHEIGHT / 2);
//// }
//// // Start off our snake moving right
//// snake2.setMovingRight(true);
////
//// // Generate our first 'food'
//// food.createFood();
////
//// // set the timer to record our game's speed / make the game move
//// timer = new Timer(speed, this);
//// timer.start();
//// }
////
//// public void run()
//// {
//// JPanel j = new JPanel();
//// j.add(g1.drawSnake(g));
//// }
//
//// public SnakeWindow() {
//// super();
//// }
//
//// public void drawWall(Graphics2D g) {
//// g.setColor(Color.BLACK);
//// g.setStroke(new BasicStroke(5));
//// g.drawLine(0, 0, super.getBoardWidth(), 0);
//// g.drawLine(0, 0, super.getBoardHeight(), 0);
//// }
