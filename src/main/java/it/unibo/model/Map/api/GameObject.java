package it.unibo.model.Map.api;

import java.util.List;

/**
 * Represents a game object that can be placed on the map.
 * Each object has a position defined by its X and Y coordinates.
 */
public interface GameObject {

    /**
     * Gets the X coordinate of the object.
     * @return the X coordinate of the object.
     */
    int getX();

    /**
     * Gets the Y coordinate of the object.
     * @return the Y coordinate of the object.
     */
    int getY();

    /**
     * Sets the X coordinate of the object.
     * @param x the new X coordinate of the object.
     * @throws NullPointerException if the X coordinate is null.
     */
    void setX(int x);

    /**
     * Sets the Y coordinate of the object.
     * @param y the new Y coordinate of the object.
     * @throws NullPointerException if the Y coordinate is null.
     */
    void setY(int y);

    /**
     * Gets the speed of the object.
     * @return the speed of the object.
     */
    int getSpeed();

    /**
     * Checks if the object is movable.
     * @return true if the object is movable, false otherwise.
     */
    boolean isMovable();

    /**
     * Sets whether the object is movable.
     * @param movable true if the object should be movable, false otherwise.
     * @throws NullPointerException if the movable parameter is null.
     */
    void setMovable(boolean movable);

    /**
     * Sets the speed of the object.
     * @param speed the new speed of the object.
     * @throws NullPointerException if the speed is null.
     */
    void setSpeed(int speed);

    /**
     * Checks if the object is a platform.
     * @return true if the object is a platform, false otherwise.
     */
    boolean isPlatform();

    /**
     * Sets whether the object is a platform.
     * @param platform true if the object should be a platform, false otherwise.
     * @throws NullPointerException if the platform parameter is null.
     */
    void setPlatform(boolean platform);

    /**
     * Sets the width of the game object in cells.
     * @param widthInCells the width in cells.
     */
    void setWidthInCells(final int widthInCells);

    /**
     * Gets the width of the game object in cells.
     * @return the width in cells.
     */
    int getWidthInCells();

    List<Integer> getOccupiedCells();

    boolean occupiesCell(int cellX);

    List<Cell> getOccupiedCells2();

}
