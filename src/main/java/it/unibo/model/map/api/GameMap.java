package it.unibo.model.map.api;

import java.util.List;

/**
 * The GameMap interface defines the contract for the game map model.
 * It provides methods to update the map state, generate new chunks,
 * retrieve visible and all chunks, manage scrolling, and query the current position and speed.
 */
public interface GameMap {

    /**
     * Updates the state of the map.
     */
    void update();

    /**
     * Generates a new chunk and adds it to the map.
     */
    void generateNewChunk();

    /**
     * Returns a list of currently visible chunks in the map.
     *
     * @return a list of visible chunks
     */
    List<Chunk> getVisibleChunks();

    /**
     * Returns the current scroll position of the map.
     *
     * @return the current position
     */
    int getCurrentPosition();

    /**
     * Increases the scroll speed of the map.
     */
    void increaseScrollSpeed();

    /**
     * Returns a list of all chunks currently present in the map.
     *
     * @return a list of all chunks
     */
    List<Chunk> getAllChunks();

    /**
     * Returns the current scroll speed of the map.
     *
     * @return the scroll speed
     */
    int getScrollSpeed();

}
