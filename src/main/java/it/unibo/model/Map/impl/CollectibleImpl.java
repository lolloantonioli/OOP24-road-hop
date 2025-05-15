package it.unibo.model.Map.impl;

import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.util.CollectibleType;

public class CollectibleImpl extends GameObjectImpl implements Collectible {

    private final CollectibleType type;
    private boolean collected;

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
