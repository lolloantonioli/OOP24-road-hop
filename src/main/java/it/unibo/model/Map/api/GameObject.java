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
     * Gets the width of the object in cells.
     * @return the width of the object in cells.
     */
    int getWidthInCells();

    /**
     * Sets the width of the object in cells.
     * @param widthInCells the new width of the object in cells.
     * @throws IllegalArgumentException if the widthInCells is zero or negative.
     */
    void setWidthInCells(int widthInCells);

    /**
     * Gets the cells occupied by the object.
     * @return a list of occupied cell X coordinates.
     */
    List<Integer> getOccupiedCells();

    /**
     * Checks if the object occupies a specific cell.
     * @param cellX the X coordinate of the cell to check.
     * @return true if the object occupies the cell, false otherwise.
     * @throws IllegalArgumentException if the cellX is not between 0 and the width of the map.
     */
    boolean occupiesCell(int cellX);

    /**
     * Gets the cells occupied by the object.
     * @return a list of occupied cells.
     */
    List<Cell> getOccupiedCells2();

}
