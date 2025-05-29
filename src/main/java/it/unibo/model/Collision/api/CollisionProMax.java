package it.unibo.model.Collision.api;

import java.util.Optional;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;

public interface CollisionProMax {

    /**
     * Checks if the player collides with a specific game object.
     * 
     * @param player the player
     * @param obj the game object
     * @return true if they collide, false otherwise
     */
    boolean checkCollision(Cell position, GameObject obj);

    /**
     * Checks if the player collides with a game object.
     * 
     * @param player the player
     * @param map the game map
     * @return true a collision happened, false otherwise
     */
    Optional<GameObject> collidedWith(Cell position, GameMap map);

    /**
     * Checks if the player can move to the specified coordinates.
     * 
     * @param player the player
     * @param map the game map
     * @param newX the target X coordinate
     * @param newY the target Y coordinate
     * @return true if the move is valid, false otherwise
     */
    boolean canMoveTo(GameMap map, Cell newPosition);

    /**
     * Checks if the player is out of bounds of the visible area.
     * 
     * @param player the player
     * @param map the game map
     * @return true if out of bounds, false otherwise
     */
    boolean isOutOfBounds(Cell position, GameMap map);

}
