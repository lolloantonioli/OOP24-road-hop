package it.unibo.model.Map.api;

import java.util.List;

public interface GameObject {

    void update();

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getSpeed();

    boolean isMovable();

    void setMovable(boolean movable);

    void setSpeed(int speed);

    boolean isPlatform();

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

    /**
     * Gets the width of the game object in pixels.
     * @return the width in pixels.
     */
    List<Integer> getOccupiedCells();

    /**
     * Checks if the game object occupies a specific cell.
     * @param cellX the x-coordinate of the cell.
     * @return true if the game object occupies the cell, false otherwise.
     */
    boolean occupiesCell(int cellX);

    /**
     * Gets the list of cells occupied by the game object in a different format.
     * @return a list of cells occupied by the game object.
     */
    List<Cell> getOccupiedCells2();


}
