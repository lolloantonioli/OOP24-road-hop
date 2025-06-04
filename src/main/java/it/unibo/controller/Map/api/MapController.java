package it.unibo.controller.Map.api;

import java.awt.Color;
import java.util.List;

import it.unibo.model.Map.api.Chunk;

/**
 * Interface which controls the game map and its visual representation.
 * It provides methods to update the map state, retrieve visible chunks, manage scrolling,
 * and query cell and chunk properties for rendering purposes.
 */
public interface MapController {

    /**
     * Updates the state of the map, typically advancing the game logic.
     */
    void updateMap();

    /**
     * Returns a list of currently visible chunks in the map.
     *
     * @return a list of visible chunks
     */
    List<Chunk> getVisibleChunks();

    /**
     * Increases the scroll speed of the map.
     */
    void increaseScrollSpeed();

    /**
     * Returns the current scroll position of the map.
     *
     * @return the current position
     */
    int getCurrentPosition();

    /**
     * Returns the current scroll speed of the map.
     *
     * @return the scroll speed
     */
    int getScrollSpeed();

    /**
     * Sets the animation offset for the map view.
     *
     * @param offset the animation offset to set
     */
    void setAnimationOffset(int offset);

    /**
     * Requests the view to refresh and update its display.
     */
    void updateView();

    /**
     * Returns the height of a single cell in the view.
     *
     * @return the cell height
     */
    int getCellHeight();

    /**
     * Returns the total number of chunks in the map.
     *
     * @return the number of chunks
     */
    int getChunksNumber();

    /**
     * Returns the number of cells per row in a chunk.
     *
     * @return the number of cells per row
     */
    int getCellsPerRow();
    
    /**
     * Returns the color associated with the specified chunk.
     *
     * @param chunkIndex the index of the chunk
     * @return the color of the chunk
     */
    Color getChunkColor(int chunkIndex);
    
    /**
     * Checks if the specified cell contains any objects.
     *
     * @param chunkIndex the index of the chunk
     * @param cellIndex the index of the cell within the chunk
     * @return true if the cell contains objects, false otherwise
     */
    boolean hasCellObjects(int chunkIndex, int cellIndex);
    
    /**
     * Returns the color representing the object(s) in the specified cell.
     *
     * @param chunkIndex the index of the chunk
     * @param cellIndex the index of the cell within the chunk
     * @return the color of the cell's object(s)
     */
    Color getCellObjectColor(int chunkIndex, int cellIndex);
    
    /**
     * Checks if the object(s) in the specified cell should be rendered as circular.
     *
     * @param chunkIndex the index of the chunk
     * @param cellIndex the index of the cell within the chunk
     * @return true if the cell's object(s) are circular, false otherwise
     */
    boolean isCellObjectCircular(int chunkIndex, int cellIndex);

}
