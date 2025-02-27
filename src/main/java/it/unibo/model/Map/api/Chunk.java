package it.unibo.model.Map.api;

import it.unibo.model.Map.util.ChunkType;

public interface Chunk {

    ChunkType getType();

    int getPosition();

    int getHeight();

    int getWidth();

}
