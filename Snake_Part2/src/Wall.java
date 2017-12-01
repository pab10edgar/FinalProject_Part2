/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - Wall Class
 * 
 * Class Description: The Wall class creates boundaries within game
 * that "kill" snake. Use starting and ending values, and (0,0) as top
 * left corner for reference
 * 
 * Game Description: In a snake game the objective is to navigate a
 * snake through a walled space (or maze), consuming food along the
 * way. The user must avoid colliding with walls or the snake’s
 * ever-growing body. The length of the snake increases each time food
 * is consumed, so the difficulty of avoiding a collision increases as
 * the game progresses.
 */

public class Wall {

    // Points for starting and ending positions of wall
    Point start;
    Point end;

    /**
     * Wall constructor that takes in first and last coordinates of wall
     * 
     * @param start coordinates of wall
     * @param end coordinates of wall
     */
    public Wall(Point start, Point end) {

        // Create x and y variable to get points for start and end values
        int x1 = start.getX(), x2 = end.getX(), y1 = start.getY(),
                y2 = end.getY();

        // Check that start/end points of wall are in correct positions
        // (Not necessary but provides robustness)
        if (start.getX() > end.getX()) {
            x1 = end.getX();
            x2 = start.getX();
        }

        if (start.getY() > end.getY()) {
            y1 = end.getY();
            y2 = start.getY();
        }

        // Set new start and ending points of wall
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Check if a point is in a wall
     * 
     * @param a point p
     * @return boolean true/false
     */
    public boolean contains(Point p) {

        // Create x/y variable
        int x = p.getX();
        int y = p.getY();

        // Use >= or <= to parse through entire wall and return true/false
        return x >= start.getX() && x <= end.getX() && y >= start.getY()
                && y <= end.getY();
    }
}
