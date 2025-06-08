package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;

// i create singoli mi servono? sono utili? li aggiungo al controller?
// COSTANTE CELLSXCHUNK DA CENTRALIZZARE ?

public class MovingObstacleFactoryImpl implements MovingObstacleFactory {
    
    private final Random random;
    
    // Costanti per i limiti di velocità
    public static final int MIN_CAR_SPEED = 10;
    public static final int MAX_CAR_SPEED = 15;
    public static final int MIN_TRAIN_SPEED = 20;
    public static final int MAX_TRAIN_SPEED = 25;
    public static final int MIN_LOG_SPEED = 1;
    public static final int MAX_LOG_SPEED = 1;

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
    public MovingObstacles[] createObstacleSet(ObstacleType type, int y, int count, boolean leftToRight) {
        List<MovingObstacles> obstacles = new ArrayList<>();
        int minDistance = getMinDistance(type);
        int obstacleWidth = getObstacleWidth(type);
        int spacing = obstacleWidth + minDistance;

        // Parti anche da fuori schermo
        int start = -obstacleWidth + 1;
        int end = CELLS_PER_CHUNK - 1  + obstacleWidth - 1;

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
        for (int pos = start; placed < minObstacles && pos <= end; pos += spacing) {
            int baseX = leftToRight ? pos : CELLS_PER_CHUNK - obstacleWidth - (pos - start);
            int speed = getRandomSpeed(type);
            if (!leftToRight) speed = -speed;
            // Puoi aggiungere un controllo per evitare sovrapposizioni se vuoi
            MovingObstacles obstacle = createObstacleByType(type, baseX, y, speed);
            obstacles.add(obstacle);
            placed++;
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
            return MIN_CAR_SPEED + random.nextInt(MAX_CAR_SPEED - MIN_CAR_SPEED + 1);
        } else if (type == ObstacleType.TRAIN) {
            return MIN_TRAIN_SPEED + random.nextInt(MAX_TRAIN_SPEED - MIN_TRAIN_SPEED + 1);
        } else if (type == ObstacleType.LOG) {
            return MIN_LOG_SPEED + random.nextInt(MAX_LOG_SPEED - MIN_LOG_SPEED + 1);
        }
        throw new IllegalArgumentException("Unknown obstacle type: " + type);
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