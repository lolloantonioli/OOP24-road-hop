package it.unibo.model.Map.api;

import java.util.Optional;

public interface Cell {

    public boolean addObject(GameObject obj);

    public void removeObject();

    public boolean hasObject();

    public Optional<GameObject> getContent();

    public int getX();
    
    public int getY();

     boolean isOccupiedInChunk(Chunk chunk);

}
