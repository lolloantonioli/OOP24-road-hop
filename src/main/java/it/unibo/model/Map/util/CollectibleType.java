package it.unibo.model.Map.util;

/**
 * Represents the type of a collectible in the game.
 * Collectibles are objects that can be collected by the player to gain points or other benefits.
 */
public final class CollectibleType {

    public static final CollectibleType COIN = new CollectibleType("COIN");
    public static final CollectibleType SECOND_LIFE = new CollectibleType("SECOND_LIFE");

    private final String name;

    private CollectibleType(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
