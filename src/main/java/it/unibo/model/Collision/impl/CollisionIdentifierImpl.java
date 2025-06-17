package it.unibo.model.Collision.impl;

import java.util.List;

import it.unibo.model.Collision.api.CollisionIdentifier;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Obstacles.impl.MovingObstacles;

/**
 * Implementation of the CollisionIdentifier interface.
 * This class provides methods to identify different types of collisions
 * between the player and other game objects, such as platforms, fatal collisions,
 * collectible collisions, and error checking for collided objects.
 */
public final class CollisionIdentifierImpl implements CollisionIdentifier {

    @Override
    public boolean isOnPlatform(final GameObject obj) {
        return obj.isPlatform();
    }

    @Override
    public boolean isFatalCollision(final GameObject obj) {
        return MovingObstacles.class.isInstance(obj) && !obj.isPlatform();
    }

    @Override
    public boolean isCollectibleCollision(final GameObject obj) {
        return Collectible.class.isInstance(obj);
    }

    @Override
    public void checkError(final List<GameObject> collidedWith) {
        if (collidedWith.stream()
            .anyMatch(obj -> !isOnPlatform(obj) 
                                && !isFatalCollision(obj)
                                && !isCollectibleCollision(obj))) {

                throw new IllegalStateException("not valid collision");

            }
    }

}
