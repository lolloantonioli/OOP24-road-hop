package it.unibo.controller.Map.api;

import java.awt.Color;
import java.util.List;

import it.unibo.model.Map.api.Chunk;

public interface MapController {

    void updateMap();

    List<Chunk> getVisibleChunks();

    void increaseScrollSpeed();

    int getCurrentPosition();

    int getScrollSpeed();

    void setAnimationOffset(int offset);

    void updateView();

    int getCellHeight();

    int getChunksNumber();

    int getCellsPerRow();
    
    Color getChunkColor(int chunkIndex);
    
    boolean hasCellObjects(int chunkIndex, int cellIndex);
    
    Color getCellObjectColor(int chunkIndex, int cellIndex);
    
    boolean isCellObjectCircular(int chunkIndex, int cellIndex);

}
