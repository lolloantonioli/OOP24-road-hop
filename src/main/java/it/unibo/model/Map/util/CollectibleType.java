package it.unibo.model.Map.util;

/**
 * Represents the type of a collectible in the game.
 * Collectibles are objects that can be collected by the player to gain points or other benefits.
 */
public final class CollectibleType {

    /**
     * Type representing a coin collectible.
     */
    public static final CollectibleType COIN = new CollectibleType("COIN");
    /**
     * Type representing a second life collectible.
     */
    public static final CollectibleType SECOND_LIFE = new CollectibleType("SECOND_LIFE");

    private final String name;

    private CollectibleType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
