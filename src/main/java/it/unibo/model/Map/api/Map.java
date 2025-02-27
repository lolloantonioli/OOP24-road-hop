package it.unibo.model.Map.api;

import java.util.List;

public interface Map {

    void update();

    void generateNewChunk();

    List<Chunk> getVisibleChunks();

    

}
