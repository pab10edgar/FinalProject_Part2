/**
 * @author Pablo Edgar
 * 
 * December 7th, 2017
 * 
 * Final Project "Snake Game" Part 2 - Snake Class
 * 
 * Class Description:
 * The Snake class gives the "Snake" movement. Handles logic of snake. Snake is represented by a LinkedList
 * that is manipulated throughout game. Implements FindPoint interface to handle a contains method.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class Snake implements FindPoint {

    // Use a LinkedList as "snake" to enable easy addition and removal of parts
    LinkedList<Point> snake;
    Direction direction;

    private boolean food = false;
    public boolean wallHit = false;
    public boolean selfHit = false;

    // Snake constructor that takes in a point for the head. Initialize
    // LinkedList
    @SuppressWarnings("static-access")
    public Snake(Point head) {
        snake = new LinkedList<>();
        snake.addFirst(head);
        direction = direction.NO_DIRECTION;
    }

    /**
     * Check to make sure Snake cannot move on-top of itself and go LEFT-RIGHT,
     * UP-DOWN, etc in succession
     * 
     * @param point
     *            direction
     */
    public void changeDirection(Direction newDirection) {
        if (direction != newDirection.reverse())
            direction = newDirection;
    }

    /**
     * Move snake in particular direction using Point input (North, South,
     * East, West). Moves snake by adding a point to front and removing the
     * 'tail' by polling the last element in the LinkedList. If a food object
     * is "eaten", set food equal to false.
     */
    public void move() {

        // Print out current direction that snake is moving for testing
        // purposes
        System.out.println(direction);

        // Get head by peeking at first element in list
        Point head = snake.peekFirst();

        // Add new point to front of snake to allow movement forward in
        // specified direction
        snake.addFirst(new Point(head.getX() + direction.getX(),
                head.getY() + direction.getY()));

        if (food) {
            food = false;
        } else {
            // Remove tail from snake
            snake.pollLast();
        }
    }

    /**
     * Method to check for collision of snake with wall or itself. Takes in a
     * list of walls to iterate through.
     * 
     * @param List
     *            of walls in game
     * @return boolean hitSelf
     */
    public boolean isCollision(List<Wall> walls) {

        // Keep track of collision with itself
        boolean didHitItself = false;

        // Check all walls using for-each loop
        for (Wall wall : walls) {

            // If wall contains the 'head' of snake a collision has occurred
            if (wall.contains(snake.peekFirst())) {
                System.out.println("WALL HIT!");
                wallHit = true;
                return true;
            }
        }

        // New point to signify "head" by removing first element of LinkedList
        Point head = snake.removeFirst();

        // Check for collision with itself by checking if LinkedList contains
        // head
        if (snake.contains(head)) {

            System.out.println("SELF HIT!");
            selfHit = true;

            // Set didHitItself to true if snake contains head
            didHitItself = true;
        }
        // Add the head back
        snake.addFirst(head);
        return didHitItself;
    }

    /**
     * Return state of collision
     * 
     * @return boolean true/false
     */
    public boolean getSelfHit() {
        if (selfHit == true) {
            return true;
        }
        return false;
    }

    /**
     * Return state of collision
     * 
     * @return boolean true/false
     */
    public boolean getWallHit() {
        if (wallHit == true) {
            return true;
        }
        return false;
    }

    /**
     * Method that allows snake to grow larger after eating food. Changes the
     * state of food to 'true' if food has been eaten.
     */
    public void eatFood() {

        // Set food to true if method is called
        food = true;
        // For testing, inform user that food was eaten
        System.out.println("Snake ate a food");
    }

    /**
     * Return current state of Food
     * 
     * @return boolean
     */
    public boolean didEatFood() {
        return food;
    }

    /**
     * Implement FindPoint interface by adding contains method to check if
     * snake contains a specified point
     */
    public boolean contains(Point p) {
        return snake.contains(p);
    }

    /**
     * Get method to locate head. Retrieves, but does not remove, the first
     * element of the LinkedList.
     * 
     * @return point "head" that is first element in List
     */
    public Point getHead() {
        return snake.peekFirst();
    }

    /**
     * Return snake size at a given instance
     * 
     * @return int snake size
     */
    public int getSnakeSize() {
        return snake.size();
    }

    /**
     * Return a point on the snake, by getting specific location on list
     * 
     * @param i
     * @return point
     */
    public Point getPointOnSnake(int i) {
        return snake.get(i);
    }
}
