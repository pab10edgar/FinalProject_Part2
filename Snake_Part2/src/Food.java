/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - Food Class
 * 
 * Class Description:
 * The Food class creates a Point that is represented as a "Food" object. The snake can "eat" this point.
 * 
 * Game Description:
 * In a snake game the objective is to navigate a snake through a walled space (or maze), 
 * consuming food along the way. The user must avoid colliding with walls or the snake’s ever-growing body. 
 * The length of the snake increases each time food is consumed, so the difficulty of avoiding a collision
 * increases as the game progresses.
 */

public class Food {

    Point point;

    /**
     * Constructor for Food object that places food at a point (x, y)
     * 
     * @param point p for food location
     */
    public Food(Point p) {
        this.point = p;
    }

    /**
     * Contains method to check if point is equal to a food object
     * 
     * @param Point p
     * @return boolean value whether point equals food
     */
    public boolean contains(Point p) {
        return p.equals(point);
    }
}
