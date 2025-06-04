package it.unibo.model.Collision.api;

import java.util.List;

import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;

public interface CollisionDetector {

    /**
     * Checks if two specific game objects collide.
     * 
     * @param obj1 the first game object
     * @param obj2 the second game object
     * @return true if they collide, false otherwise
     */
    boolean checkCollision(GameObject obj1, GameObject obj2);

    /**
     * Returns all the game objects colliding in a specific object
     * 
     * @param obj the game object
     * @param map the game map
     * @return List containing the all collided objects, empty it no collision happened
     */
    List<GameObject> getCollidedObjects(GameObject obj, GameMap map);

}
