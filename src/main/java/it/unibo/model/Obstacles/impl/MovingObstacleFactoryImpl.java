package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;

// COSTANTE CELLSXCHUNK DA CENTRALIZZARE ?

public class MovingObstacleFactoryImpl implements MovingObstacleFactory {
    
    private final Random random;
    
    // Costanti per i limiti di velocità
    private int minCarSpeed = 10;
    private int maxCarSpeed = 15;
    private int minTrainSpeed = 20;
    private int maxTrainSpeed = 25;
    private int minLogSpeed = 1;
    private int maxLogSpeed = 1;

    // Costanti per il sistema a griglia
    public static final int CELLS_PER_CHUNK = 9;

    // Configurazione spacing
    private static final int MIN_CAR_DISTANCE = 3;
    private static final int MIN_TRAIN_DISTANCE = 6;
    private static final int MIN_LOG_DISTANCE = 1;
    
    public MovingObstacleFactoryImpl() {
        this.random = new Random();
    }
    
    @Override
    public MovingObstacles createCar(int x, int y, int speed) {
        return new MovingObstacles(x, y, ObstacleType.CAR, speed);
    }
    
    @Override
    public MovingObstacles createTrain(int x, int y, int speed) {
        return new MovingObstacles(x, y, ObstacleType.TRAIN, speed);
    }

    @Override
    public MovingObstacles createLog(int x, int y, int speed) {
        return new MovingObstacles(x, y, ObstacleType.LOG, speed);
    }

    @Override
    public MovingObstacles[] createObstacleSet(ObstacleType type, int y, int count, boolean leftToRight, int speed) {
        List<MovingObstacles> obstacles = new ArrayList<>();
        int minDistance = getMinDistance(type);
        int obstacleWidth = getObstacleWidth(type);
        int spacing = obstacleWidth + minDistance;

        int minObstacles;
        if (type == ObstacleType.CAR) {
            minObstacles = Math.max(3, count);
        } else if (type == ObstacleType.LOG) {
            minObstacles = Math.max(2, count);
        } else if (type == ObstacleType.TRAIN) {
            minObstacles = Math.max(1, count);
        } else {
            throw new IllegalArgumentException("Unknown obstacle type: " + type);
        }
        
        int placed = 0;
        if (leftToRight) {
            int start = -obstacleWidth + 1;
            int end = CELLS_PER_CHUNK - 1 + obstacleWidth - 1;
            for (int pos = start; placed < minObstacles && pos <= end; pos += spacing) {
                int baseX = pos;
                MovingObstacles obstacle = createObstacleByType(type, baseX, y, speed);
                obstacles.add(obstacle);
                placed++;
            }
        } else {
            int start = CELLS_PER_CHUNK + obstacleWidth - 1;
            int end = -obstacleWidth + 1;
            for (int pos = start; placed < minObstacles && pos >= end; pos -= spacing) {
                int baseX = pos;
                MovingObstacles obstacle = createObstacleByType(type, baseX, y, -speed);
                obstacles.add(obstacle);
                placed++;
            }
        }
        return obstacles.toArray(MovingObstacles[]::new);
    }

    @Override
    public MovingObstacles createObstacleByType(ObstacleType type, int x, int y, int speed) {
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
    public int getRandomSpeed(ObstacleType type) {
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
    public void increaseSpeedLimits(int amount) {
        minCarSpeed += amount;
        maxCarSpeed += amount;
        minTrainSpeed += amount;
        maxTrainSpeed += amount;
        minLogSpeed += amount;
        maxLogSpeed += amount;
    }
    
    @Override
    public int getMinDistance(ObstacleType type) {
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
    public int getObstacleWidth(ObstacleType type) {
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