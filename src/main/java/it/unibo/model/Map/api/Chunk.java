package it.unibo.model.Map.api;

import java.util.List;

public interface Chunk {

    boolean addObjectAt(final GameObject obj, final int cellX);

    List<GameObject> getObjects();

    List<Cell> getCells();

    Cell getCellAt(final int cellX);

    ChunkType getType();

    int getPosition();

    int getCellsPerRow();

}
