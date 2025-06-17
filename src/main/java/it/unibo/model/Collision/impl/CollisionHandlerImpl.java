package it.unibo.model.collision.impl;

import it.unibo.model.collision.api.CollisionHandler;
import it.unibo.model.map.api.Collectible;
import it.unibo.model.map.util.CollectibleType;
import it.unibo.model.player.api.Player;

/**
 * Implementation of the CollisionHandler interface.
 * This class handles collisions between the player and other game objects,
 * specifically fatal collisions and collectible collisions.
 */
public final class CollisionHandlerImpl implements CollisionHandler {

    @Override
    public void handleFatalCollision(final Player player) {
        player.die();
    }

    @Override
    public void handleCollectibleCollision(final Player player, final Collectible collectible) {

        if (collectible.isCollected()) {
            return;
        }

        try {

            if (collectible.getType().equals(CollectibleType.COIN)) {
                player.collectACoin();
            }

            if (collectible.getType().equals(CollectibleType.SECOND_LIFE)) {
                player.grantSecondLife();
            }

            collectible.collect();
        } catch (final Exception e) {
                System.err.println("Error processing collectible collision - " + e.getMessage());
        }
    }

}

