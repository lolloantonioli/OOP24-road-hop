package it.unibo.model.Player.util;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);
    
    private final int deltaX;
    private final int deltaY;
    
    Direction(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    
    /**
     * Gets the X-axis movement value associated with this direction.
     * 
     * @return the delta X value
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Gets the Y-axis movement value associated with this direction.
     * 
     * @return the delta Y value
     */
    public int getDeltaY() {
        return deltaY;
    }
    
    /**
     * Determines if this direction is horizontal.
     * 
     * @return true if horizontal, false if vertical
     */
    public boolean isHorizontal() {
        return deltaY == 0;
    }
    
    /**
     * Determines if this direction is vertical.
     * 
     * @return true if vertical, false if horizontal
     */
    public boolean isVertical() {
        return deltaX == 0;
    }
}
