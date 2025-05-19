package it.unibo.model.Player.util;

public enum Direction {
    UP(1),
    DOWN(-1),
    LEFT(-1),
    RIGHT(1);
    
    private final int value;
    
    Direction(final int value) {
        this.value = value;
    }
    
    /**
     * Gets the movement value associated with this direction.
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Determines if this direction is horizontal.
     * 
     * @return true if horizontal, false if vertical
     */
    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }
    
    /**
     * Determines if this direction is vertical.
     * 
     * @return true if vertical, false if horizontal
     */
    public boolean isVertical() {
        return this == UP || this == DOWN;
    }
}
