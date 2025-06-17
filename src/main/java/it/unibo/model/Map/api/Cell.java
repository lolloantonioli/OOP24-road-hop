package it.unibo.model.map.api;

import java.util.Set;

/**
 * Represents a cell in the game map.
 * Each cell can contain multiple {@code GameObject}.
 */
public interface Cell {

    /**
     * Adds a {@code GameObject} to the cell.
     *
     * @param obj the {@code GameObject} to add.
     * @throws IllegalArgumentException if the object is null.
     * @return true if the object was added successfully, false otherwise.
     */
    boolean addObject(GameObject obj);

    /**
     * Removes a {@code GameObject} from the cell.
     *
     * @param obj the {@code GameObject} to remove.
     * @throws IllegalArgumentException if the object is null.
     * @return true if the object was removed successfully, false otherwise.
     */
    boolean removeObject(GameObject obj);

    /**
     * Checks if the cell contains any {@code GameObject}.
     *
     * @return true if the cell has at least one object, false otherwise.
     */
    boolean hasObject();

    /**
     * Gets the set of {@code GameObject} contained in the cell.
     *
     * @return an immuatble set of {@code GameObject} in the cell.
     */
    Set<GameObject> getContent();

    /**
     * Gets the x-coordinate of the cell.
     *
     * @return the x-coordinate of the cell.
     */
    int getX();

    /**
     * Gets the y-coordinate of the cell.
     *
     * @return the y-coordinate of the cell.
     */
    int getY();

}
