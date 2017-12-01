/**
 * @author Pablo Edgar
 * 
 * November 28, 2017
 * 
 * Final Project "Snake Game" Part 1 - FindPoint Interface
 * 
 * Class Description: Common FindPoint interface with contains method. Implemented in Snake class. 
 * 
 * Game Description: In a snake game the objective is to navigate a
 * snake through a walled space (or maze), consuming food along the
 * way. The user must avoid colliding with walls or the snake’s
 * ever-growing body. The length of the snake increases each time food
 * is consumed, so the difficulty of avoiding a collision increases as
 * the game progresses.
 */

public interface FindPoint {
    
    /**
     * General contains method implemented in Snake
     * 
     * @param point p
     * @return a point
     */
    public boolean contains(Point p);

}