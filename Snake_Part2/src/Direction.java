/**
 * @author Pablo Edgar
 * 
 * December 6th, 2017
 * 
 * Final Project "Snake Game" Part 2 - Direction Enum
 * 
 * Class Description: Direction enum that creates hard coded values for
 * directional movement by snake. Uses NORTH(0, -1), SOUTH(0, 1),
 * EAST(1, 0), WEST(-1, 0), NO_DIRECTION(0,0);
 * 
 * Game Description: In a snake game the objective is to navigate a
 * snake through a walled space (or maze), consuming food along the
 * way. The user must avoid colliding with walls or the snake’s
 * ever-growing body. The length of the snake increases each time food
 * is consumed, so the difficulty of avoiding a collision increases as
 * the game progresses.
 */
public enum Direction {

    // Set default directions
    NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0), NO_DIRECTION(0, 0);

    int dx;
    int dy;

    /**
     * Constructor that takes in x and y coordinate
     * 
     * @param dx
     * @param dy
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Opposing direction for functionality of external method. Prevents snake
     * from moving on top of itself
     * 
     * @param direction
     * @return a direction (Point)
     */
    @SuppressWarnings("incomplete-switch")
    public Direction reverse() {
        switch (this) {
        case NORTH:
            return SOUTH;
        case SOUTH:
            return NORTH;
        case WEST:
            return EAST;
        case EAST:
            return WEST;
        }
        return NO_DIRECTION;
    }

    /**
     * Get y coordinate of direction
     * 
     * @return int dy
     */
    public int getY() {
        return dy;
    }

    /**
     * Get x coordinate of direction
     * 
     * @return int dx
     */
    public int getX() {
        return dx;
    }
}
