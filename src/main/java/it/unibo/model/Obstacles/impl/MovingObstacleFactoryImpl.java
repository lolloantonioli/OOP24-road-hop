package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;

// FORSE METODI RANDOM NON SERVONO(????)
// Aggiornare ChunkFactoryImpl per supportare i tronchi nei fiumi

public class MovingObstacleFactoryImpl implements MovingObstacleFactory {
    
    private final Random random;
    
    // Costanti per i limiti di velocità
    public static final int MIN_CAR_SPEED = 1;
    public static final int MAX_CAR_SPEED = 2;
    public static final int MIN_TRAIN_SPEED = 1;
    public static final int MAX_TRAIN_SPEED = 3;
    public static final int MIN_LOG_SPEED = 1;
    public static final int MAX_LOG_SPEED = 2;

    // Costanti per il sistema a griglia
    public static final int CELLS_PER_CHUNK = 9;

    // Configurazione spacing
    private static final int MIN_CAR_DISTANCE = 2;
    private static final int MIN_TRAIN_DISTANCE = 6;
    private static final int MIN_LOG_DISTANCE = 4;
    
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

    /**
     * Crea un set di ostacoli del tipo specificato
     */
    @Override
    public MovingObstacles[] createObstacleSet(ObstacleType type, int y, int count, boolean leftToRight) {
        List<MovingObstacles> obstacles = new ArrayList<>();
        
        int minDistance = getMinDistance(type);
        int obstacleWidth = getObstacleWidth(type);
        int totalRange = CELLS_PER_CHUNK + (type == ObstacleType.TRAIN ? 20 : 15);
        int spacing = Math.max(minDistance, totalRange / Math.max(count, 1));
        
        for (int i = 0; i < count; i++) {
            int baseX = calculateBasePosition(i, spacing, totalRange, leftToRight);
            int finalX = addRandomOffset(baseX, spacing, type);
            
            if (isValidPosition(finalX, obstacles, obstacleWidth, minDistance)) {
                int speed = getRandomSpeed(type);
                if (!leftToRight) speed = -speed;
                
                MovingObstacles obstacle = createObstacleByType(type, finalX, y, speed);
                obstacles.add(obstacle);
            }
        }
        
        return obstacles.toArray(MovingObstacles[]::new);
    }
    
    @Override
    public MovingObstacles[] createObstaclesForChunk(int chunkY, String chunkType) {
        return switch (chunkType.toUpperCase()) {
            case "ROAD" -> createObstacleSet(ObstacleType.CAR, chunkY, 2 + random.nextInt(3), random.nextBoolean());
            case "RAILWAY" -> createObstacleSet(ObstacleType.TRAIN, chunkY, 1 + random.nextInt(2), random.nextBoolean());
            case "RIVER" -> createObstacleSet(ObstacleType.LOG, chunkY, 1 + random.nextInt(3), random.nextBoolean());
            default -> new MovingObstacles[0];
        };
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
    
    private int calculateBasePosition(int index, int spacing, int totalRange, boolean leftToRight) {
        int baseX = index * spacing;
        return leftToRight ? baseX : totalRange - baseX;
    }
    
    private int addRandomOffset(int baseX, int spacing, ObstacleType type) {
        int maxOffset;
        if (type == ObstacleType.CAR) {
            maxOffset = spacing / 6;
        } else if (type == ObstacleType.TRAIN || type == ObstacleType.LOG) {
            maxOffset = 1;
        } else {
            maxOffset = 0;
        }
        return baseX + random.nextInt(maxOffset * 2 + 1) - maxOffset;
    }
    
    private boolean isValidPosition(int x, List<MovingObstacles> existing, int width, int minDistance) {
        return existing.stream()
                .noneMatch(obstacle -> Math.abs(obstacle.getX() - x) < width + minDistance);
    }
}