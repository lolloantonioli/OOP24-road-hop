package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;

// COSTANTE CELLSXCHUNK DA CENTRALIZZARE ?

/**
 * Implementation of MovingObstacleFactory.
 * This class provides methods to create and manage moving obstacles in the game.
 */
public final class MovingObstacleFactoryImpl implements MovingObstacleFactory {

    private static final int MIN_CAR_DISTANCE = 3;
    private static final int MIN_TRAIN_DISTANCE = 6;
    private static final int MIN_LOG_DISTANCE = 1;
    
    private static int minCarSpeed = 15;
    private static int maxCarSpeed = 20;
    private static int minTrainSpeed = 25;
    private static int maxTrainSpeed = 30;
    private static int minLogSpeed = 10;
    private static int maxLogSpeed = 15;
    private final Random random;

    /**
     * Constructor for MovingObstacleFactoryImpl.
     * Initializes the random number generator.
     */
    public MovingObstacleFactoryImpl() {
        this.random = new Random();
    }

    @Override
    public MovingObstacles createCar(final int x, final int y, final int speed) {
        return new MovingObstacles(x, y, ObstacleType.CAR, speed);
    }

    @Override
    public MovingObstacles createTrain(final int x, final int y, final int speed) {
        return new MovingObstacles(x, y, ObstacleType.TRAIN, speed);
    }

    @Override
    public MovingObstacles createLog(final int x, final int y, final int speed) {
        return new MovingObstacles(x, y, ObstacleType.LOG, speed);
    }

    @Override
    public List<MovingObstacles> createObstacleSet(final ObstacleType type, final int y, 
                                                    final int count, final boolean leftToRight, final int speed) {
        final List<MovingObstacles> obstacles = new ArrayList<>();
        final int minDistance = getMinDistance(type);
        final int obstacleWidth = getObstacleWidth(type);
        final int spacing = obstacleWidth + minDistance;
        final int minObstacles;
        int placed = 0;
        if (type == ObstacleType.CAR) {
            minObstacles = Math.max(3, count);
        } else if (type == ObstacleType.LOG) {
            minObstacles = Math.max(2, count);
        } else if (type == ObstacleType.TRAIN) {
            minObstacles = Math.max(1, count);
        } else {
            throw new IllegalArgumentException("Unknown obstacle type: " + type);
        }
        if (leftToRight) {
            final int start = -obstacleWidth + 1;
            final int end = ChunkImpl.CELLS_PER_ROW - 1 + obstacleWidth - 1;
            for (int pos = start; placed < minObstacles && pos <= end; pos += spacing) {
                final int baseX = pos;
                final MovingObstacles obstacle = createObstacleByType(type, baseX, y, speed);
                obstacles.add(obstacle);
                placed++;
            }
        } else {
            final int start = ChunkImpl.CELLS_PER_ROW + obstacleWidth - 1;
            final int end = -obstacleWidth + 1;
            for (int pos = start; placed < minObstacles && pos >= end; pos -= spacing) {
                final int baseX = pos;
                final MovingObstacles obstacle = createObstacleByType(type, baseX, y, -speed);
                obstacles.add(obstacle);
                placed++;
            }
        }
        return obstacles;
    }

    @Override
    public MovingObstacles createObstacleByType(final ObstacleType type, final int x, final int y, final int speed) {
        if (type == ObstacleType.CAR) {
            return createCar(x, y, speed);
        } else if (type == ObstacleType.TRAIN) {
            return createTrain(x, y, speed);
        } else if (type == ObstacleType.LOG) {
            return createLog(x, y, speed);
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }

    @Override
    public int getRandomSpeed(final ObstacleType type) {
        if (type == ObstacleType.CAR) {
            return minCarSpeed + random.nextInt(maxCarSpeed - minCarSpeed + 1);
        } else if (type == ObstacleType.TRAIN) {
            return minTrainSpeed + random.nextInt(maxTrainSpeed - minTrainSpeed + 1);
        } else if (type == ObstacleType.LOG) {
            return minLogSpeed + random.nextInt(maxLogSpeed - minLogSpeed + 1);
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }

    @Override
    public void increaseSpeedLimits(final int amount) {
        minCarSpeed += amount;
        maxCarSpeed += amount;
        minTrainSpeed += amount;
        maxTrainSpeed += amount;
        minLogSpeed += amount;
        maxLogSpeed += amount;
    }

    @Override
    public int getMinDistance(final ObstacleType type) {
        if (type == ObstacleType.CAR) {
            return MIN_CAR_DISTANCE;
        } else if (type == ObstacleType.TRAIN) {
            return MIN_TRAIN_DISTANCE;
        } else if (type == ObstacleType.LOG) {
            return MIN_LOG_DISTANCE;
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }

    @Override
    public int getObstacleWidth(final ObstacleType type) {
        if (type == ObstacleType.CAR) {
            return 1;
        } else if (type == ObstacleType.TRAIN) {
            return MovingObstacles.TRAIN_WIDTH_CELLS;
        } else if (type == ObstacleType.LOG) {
            return MovingObstacles.LOG_WIDTH_CELLS;
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }
}
