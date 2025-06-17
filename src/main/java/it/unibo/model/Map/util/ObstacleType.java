package it.unibo.model.map.util;

/**
 * Represents the type of a obstacle in the game.
 */
public final class ObstacleType {

    /**
     * Obstacle type representing a car.
     */
    public static final ObstacleType CAR = new ObstacleType("CAR");
    /**
     * Obstacle type representing a train.
     */
    public static final ObstacleType TRAIN = new ObstacleType("TRAIN");
    /**
     * Obstacle type representing a tree.
     */
    public static final ObstacleType TREE = new ObstacleType("TREE");
    /**
     * Obstacle type representing a water body.
     */
    public static final ObstacleType WATER = new ObstacleType("WATER");
    /**
     * Obstacle type representing a log.
     */
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
