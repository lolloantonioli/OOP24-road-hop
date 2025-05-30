package it.unibo.model.Map.api;

import java.util.Optional;

public interface Cell {

    boolean addObject(GameObject obj);

    void removeObject();

    boolean hasObject();

    Optional<GameObject> getContent();

    int getX();
    
    int getY();

}
