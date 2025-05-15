package it.unibo.model.Map.api;

import java.util.List;

public interface GameMap {

    void update();

    void generateNewChunk();

    List<Chunk> getVisibleChunks();

    int getCurrentPosition();

    void increaseScrollSpeed();

    List<Chunk> getAllChunks();

    int getMapHeight();

}
