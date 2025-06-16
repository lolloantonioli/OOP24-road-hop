package it.unibo.model.Map.api;

import java.util.List;

import it.unibo.model.Map.util.ChunkType;

/**
 * Represents a chunk of the game map.
 * A chunk is a map row that could be of different types, such as road, railway, river, or grass.
 * Each chunk contains multiple {@code Cell} and {@code GameObject}.
 */
public interface Chunk {

    /**
     * Adds a {@code GameObject} to the chunk at the specified cell.
     *
     * @param obj the {@code GameObject} to add.
     * @param cellX the x-coordinate of the cell where the object should be added.
     * @throws IllegalArgumentException if the object is null or if the cell does not exist.
     * @return true if the object was added successfully, false otherwise.
     */
    boolean addObjectAt(GameObject obj, int cellX);

    /**
     * Returns the {@code GameObject}s contained in the chunk.
     * @return an immutable list of {@code GameObject} in the chunk. 
     */
    List<GameObject> getObjects();

    /**
     * Returns the {@code Cell}s contained in the chunk.
     * @return an immutable list of {@code Cell} in the chunk.
     */
    List<Cell> getCells();

    /**
     * Gets the cell at the specified x-coordinate.
     *
     * @param cellX the x-coordinate of the cell.
     * @return the {@code Cell} at the specified x-coordinate.
     * @throws IllegalArgumentException if the cell does not exist.
     */
    Cell getCellAt(int cellX);

    /**
     * Gets the type of the chunk.
     *
     * @return the {@code ChunkType} of the chunk.
     */
    ChunkType getType();

    /**
     * Gets the x-coordinate of the chunk.
     *
     * @return the x-coordinate of the chunk.
     */
    int getPosition();

}
