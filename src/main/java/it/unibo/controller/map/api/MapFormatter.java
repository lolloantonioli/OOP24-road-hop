package it.unibo.controller.map.api;

import java.awt.Color;

/**
 * Interface which controls the game map and its visual representation.
 * It provides methods to update the map state, retrieve visible chunks, manage scrolling,
 * and query cell and chunk properties for rendering purposes.
 */
public interface MapFormatter {
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

    /**
     * Checks if the specified chunk is a railway cell.
     * @param chunkIndex the index of the cell within the chunk
     * @return true if the chunk is a railway cell, false otherwise
     */
    boolean isRailwayCell(int chunkIndex);

}
