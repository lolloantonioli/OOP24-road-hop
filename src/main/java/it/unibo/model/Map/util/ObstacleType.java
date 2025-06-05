package it.unibo.model.Map.util;

/**
 * Represents the type of a obstacle in the game.
 */
public final class ObstacleType {
    
    public static final ObstacleType CAR = new ObstacleType("CAR");
    public static final ObstacleType TRAIN = new ObstacleType("TRAIN");
    public static final ObstacleType TREE = new ObstacleType("TREE");
    public static final ObstacleType WATER = new ObstacleType("WATER");
    public static final ObstacleType LOG = new ObstacleType("LOG");

    private final String name;

    private ObstacleType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}