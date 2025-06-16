package it.unibo.model.Map.impl;

import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.util.CollectibleType;

/**
 * Implementation of the Collectible interface, representing a collectible item in the game.
 * Collectibles can be collected by the player, and their type can be determined.
 */
public final class CollectibleImpl extends GameObjectImpl implements Collectible {

    private final CollectibleType type;
    private boolean collected;

    /**
     * Creates a new CollectibleImpl instance.
     * @param x the x-coordinate of the collectible
     * @param y the y-coordinate of the collectible
     * @param type the type of the collectible
     */
    public CollectibleImpl(final int x, final int y, final CollectibleType type) {
        super(x, y);
        this.type = type;
        this.collected = false;
    }

    @Override
    public CollectibleType getType() {
        return this.type;
    }

    @Override
    public void collect() {
        this.collected = true;
    }

    @Override
    public boolean isCollected() {
        return this.collected;
    }

}
