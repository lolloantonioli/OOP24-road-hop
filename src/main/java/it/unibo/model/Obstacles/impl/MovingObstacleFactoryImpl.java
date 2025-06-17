package it.unibo.model.Obstacles.impl;

import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.Util.SpeedConfig;

/**
 * Implementation of MovingObstacleFactory.
 * This class provides methods to create and manage moving obstacles in the game.
 */
public final class MovingObstacleFactoryImpl implements MovingObstacleFactory {

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
            return SpeedConfig.randomCarSpeed(random);
        } else if (type == ObstacleType.TRAIN) {
            return SpeedConfig.randomTrainSpeed(random);
        } else if (type == ObstacleType.LOG) {
            return SpeedConfig.randomLogSpeed(random);
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }
    
    @Override
    public void increaseSpeedLimits() {
        SpeedConfig.increaseAllSpeeds();
    }

    @Override
    public int getObstacleWidth(final ObstacleType type) {
        if (type == ObstacleType.CAR) {
            return MovingObstacles.CAR_WIDTH_CELLS;
        } else if (type == ObstacleType.TRAIN) {
            return MovingObstacles.TRAIN_WIDTH_CELLS;
        } else if (type == ObstacleType.LOG) {
            return MovingObstacles.LOG_WIDTH_CELLS;
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
    }
}
