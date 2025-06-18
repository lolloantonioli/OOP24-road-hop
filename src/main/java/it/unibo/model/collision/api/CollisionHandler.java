package it.unibo.model.collision.api;

import it.unibo.model.map.api.Collectible;
import it.unibo.model.player.api.Player;

/**
 * Interface for handling collisions between the player and other game objects.
 * It defines methods to handle fatal collisions and collectible collisions.
 */
public interface CollisionHandler {

    /**
     * Handles a fatal collision with the player.
     * @param player the player involved in the collision
     */
    void handleFatalCollision(Player player);

    /**
     * Handle the collision between the player and a collectible object.
     * @param player the player involved in the collision
     * @param obj the collectible object involved in the collision
     */
    void handleCollectibleCollision(Player player, Collectible obj);
}
