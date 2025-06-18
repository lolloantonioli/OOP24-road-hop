package it.unibo.model.collision.api;

import java.util.List;

import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;

/**
 * Interface for detecting collisions between game objects.
 * It provides methods to check if two objects collide and to retrieve all objects
 * that collide with a specific object within a game map.
 */
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
     * Returns all the game objects colliding in a specific object.
     * 
     * @param obj the game object
     * @param map the game map
     * @return List containing the all collided objects, empty it no collision happened
     */
    List<GameObject> getCollidedObjects(GameObject obj, GameMap map);

}
