package it.unibo.model.Player.api;

import it.unibo.model.Map.api.Collectible;

public interface CollisionHandler {

    void handleFatalCollision(Player player);

    void handleCollectibleCollision(Player player, Collectible obj);
}
