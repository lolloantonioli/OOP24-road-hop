package it.unibo.model.Player.util;

/**
 * Enum representing the four cardinal directions with their associated movement values.
 * Each direction has a delta X and delta Y value that indicates how it affects movement
 * in a 2D space.
 */
public enum Direction {
    /**
     * UP - moves the player one unit up (positive Y direction).
     */
    UP(0, 1),
    /**
     * DOWN - moves the player one unit down (negative Y direction).
     */
    DOWN(0, -1),
    /**
     * LEFT - moves the player one unit left (negative X direction).
     */
    LEFT(-1, 0),
    /**
     * RIGHT - moves the player one unit right (positive X direction).
     */
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    /**
     * Constructor for Direction enum.
     * @param deltaX the X-axis movement value associated with this direction
     * @param deltaY the Y-axis movement value associated with this direction
     * * @throws IllegalArgumentException if the direction is not valid (i.e., not a unit vector)
     */
    Direction(final int deltaX, final int deltaY) {
        if (Math.abs(deltaX + deltaY) != 1) {
            throw new IllegalArgumentException("Direction is not valid.");
        }
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
