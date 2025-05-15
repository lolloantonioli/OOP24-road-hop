package it.unibo.model.Map.api;

import it.unibo.model.Map.util.CollectibleType;

public interface Collectible extends GameObject {

    CollectibleType getType();

    void collect();

    boolean isCollected();

}
