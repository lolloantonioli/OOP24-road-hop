package it.unibo.model.Collision.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import it.unibo.model.Collision.api.CollisionDetector;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;

public class CollisionDetectorImpl implements CollisionDetector{

    @Override
    public boolean checkCollision(GameObject obj1, GameObject obj2) {
        checkNotNull(obj1, "not valid object");
        checkNotNull(obj2, "not valid object");

        return obj1.getOccupiedCells2().stream()
            .anyMatch(c -> obj2.getOccupiedCells2().contains(c));
    }

    @Override
    public List<GameObject> getCollidedObjects(GameObject obj, GameMap map) {
        checkNotNull(obj, "not valid object");
        checkNotNull(map, "not valid map");
        
        return map.getAllChunks()
            .get(obj.getY())
            .getObjects()
            .stream()
            .filter(object -> checkCollision(obj, object))
            .filter(o -> !o.equals(obj))
            .toList();
    }

}
