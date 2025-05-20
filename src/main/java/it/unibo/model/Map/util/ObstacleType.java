package it.unibo.model.Map.util;

public final class ObstacleType {
    
    public static final ObstacleType CAR = new ObstacleType("CAR");
    public static final ObstacleType TRAIN = new ObstacleType("TRAIN");
    public static final ObstacleType TREE = new ObstacleType("TREE");
    public static final ObstacleType WATER = new ObstacleType("WATER");

    private final String name;

    private ObstacleType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}