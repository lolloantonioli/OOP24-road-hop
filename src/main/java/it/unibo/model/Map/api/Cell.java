package it.unibo.model.Map.api;

import java.util.Set;

public interface Cell {

    boolean addObject(GameObject obj);

    boolean removeObject(GameObject obj);

    boolean hasObject();

    Set<GameObject> getContent();

    int getX();
    
    int getY();

}
