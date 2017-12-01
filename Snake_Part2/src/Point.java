/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - Point Class
 * 
 * Class Description: The Point class allows the entire game and its
 * objects to be referenced by (x,y). Also gives direction to snake
 * during movements (North,South,East,West). Use (0,0) to be top-left
 * corner.
 * 
 * Game Description: In a snake game the objective is to navigate a
 * snake through a walled space (or maze), consuming food along the
 * way. The user must avoid colliding with walls or the snake’s
 * ever-growing body. The length of the snake increases each time food
 * is consumed, so the difficulty of avoiding a collision increases as
 * the game progresses.
 */

public class Point {

    // Create final variables for point to dictate direction
    // (Use (0,0) to be top left corner)
    public static final Point NORTH = new Point(0, -1);
    public static final Point EAST = new Point(1, 0);
    public static final Point WEST = new Point(-1, 0);
    public static final Point SOUTH = new Point(0, 1);
    public static final Point NO_DIRECTION = new Point(0, 0);

    int x;
    int y;

    /**
     * Opposing direction for functionality of external method. Prevents snake
     * from moving ontop of itself
     * 
     * @param direction
     * @return a direction (Point)
     */
    public static Point getReverseDirection(Point direction) {

        if (direction.equals(NORTH))
            return SOUTH;
        if (direction.equals(SOUTH))
            return NORTH;
        if (direction.equals(WEST))
            return EAST;
        if (direction.equals(EAST))
            return WEST;
        return NO_DIRECTION;
    }

    /**
     * Constructor that takes in x/y coordinates to build a Point in the game
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * 
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieve the x coordinate of a Point
     * 
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieve the y coordinate of a Point
     * 
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Check if Point equals a specific object by overriding equals method
     * 
     * @param general
     *            object (o)
     * @return boolean false/true if point equals an object
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Point)) {
            return false;
        }

        Point that = (Point) o;

        return this.x == that.x && this.y == that.y;
    }

    /**
     * toString() method to print direction that snake is currently moving. Use
     * (0,0) as top left corner
     */
    public String toString() {

        String direction = "";

        if (getY() == -1) {
            direction = "Snake is moving UP";
        }
        if (getY() == 1) {
            direction = "Snake is moving DOWN";
        }
        if (getX() == 1) {
            direction = "Snake is moving RIGHT";
        }
        if (getX() == -1) {
            direction = "Snake is moving LEFT";
        }
        return direction;
    }
}