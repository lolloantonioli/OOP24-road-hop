package it.unibo.model.map.api;

import it.unibo.model.map.util.CollectibleType;

/**
 * Represents a collectible item in the game.
 * Collectibles can be collected by the player, and their type can be determined.
 */
public interface Collectible extends GameObject {

    /**
     * Gets the type of the collectible.
     *
     * @return the type of the collectible
     */
    CollectibleType getType();

    /**
     * Collects the collectible, marking it as collected.
     * After collection, the collectible should not be available for further collection.
     */
    void collect();

    /**
     * Checks if the collectible has been collected.
     *
     * @return true if the collectible has been collected, false otherwise
     */
    boolean isCollected();

}
