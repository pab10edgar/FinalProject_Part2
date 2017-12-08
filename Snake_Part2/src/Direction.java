public enum Direction {
    NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0), NO_DIRECTION(0,0);

    int dx;
    int dy;

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

    public int getY() {
        // TODO Auto-generated method stub
        return dy;
    }

    public int getX() {
        return dx;
    }
}