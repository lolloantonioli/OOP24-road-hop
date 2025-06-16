package it.unibo.model.Player.impl;

import it.unibo.model.Player.api.CollisionHandler;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Player.api.Player;

public class CollisionHandlerImpl implements CollisionHandler{

    @Override
    public void handleFatalCollision(Player player) {
        player.die();
    }

    @Override
    public void handleCollectibleCollision(Player player, Collectible collectible) {

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
        } catch (Exception e) {
                System.err.println("Error processing collectible collision with: " + collectible.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

}

