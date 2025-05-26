package it.unibo.model.Collision.api;

import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.api.Player;

/**
 * Interface for handling collisions between the player and game objects.
 */

public interface CollisionHandler {
    
    /**
     * Checks if the player collides with a specific game object.
     * 
     * @param player the player
     * @param obj the game object
     * @return true if they collide, false otherwise
     */
    boolean checkCollision(Player player, GameObject obj);

    /**
     * Detects if is occurred a fatal collision for the player.
     * 
     * @param player the player
     * @param map the game map
     * @return true if a fatal collision occurred, false otherwise
     */
    boolean fatalCollisions(Player player, GameMap map);

    /**
     * Checks if the player can move to the specified coordinates.
     * 
     * @param player the player
     * @param map the game map
     * @param newX the target X coordinate
     * @param newY the target Y coordinate
     * @return true if the move is valid, false otherwise
     */
    boolean canMoveTo(Player player, GameMap map, int newX, int newY);

    /**
     * Checks if the player is out of bounds of the visible area.
     * 
     * @param player the player
     * @param map the game map
     * @return true if out of bounds, false otherwise
     */
    boolean isOutOfBounds(Player player, GameMap map);

}
