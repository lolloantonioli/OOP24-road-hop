package it.unibo.model.Collision.api;

import java.util.List;

import it.unibo.model.Map.api.GameObject;

/**
 * Interface for identifying collisions between the player and other game objects.
 * It provides methods to check if a collision is on a platform, fatal, or collectible,
 * and to check for errors in the list of collided objects.
 */
public interface CollisionIdentifier {

    /**
     * Checks if the GsameObject is a platform.
     * @param obj the GameObject to check
     * @return true if the GameObject is a platform, false otherwise
     */
    boolean isOnPlatform(GameObject obj);

    /**
     * Checks if the GameObject causes a fatal collision.
     * @param obj the GameObject to check
     * @return true if the GameObject causes a fatal collision, false otherwise
     */
    boolean isFatalCollision(GameObject obj);

    /**
     * Checks if the GameObject is a collectible.
     * @param obj the GameObject to check
     * @return true if the GameObject is a collectible, false otherwise
     */
    boolean isCollectibleCollision(GameObject obj);

    /**
     * Checks for errors in the list of collided GameObjects.
     * If any error is found, it throws an IllegalStateException.
     * @param collidedWith the list of GameObjects the player collided with
     */
    void checkError(List<GameObject> collidedWith);

}
