package it.unibo.controller.obstacles.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.controller.obstacles.api.MovingObstacleController;
import it.unibo.model.map.api.Chunk;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.util.ChunkType;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.obstacles.Util.GameConstant;
import it.unibo.model.obstacles.Util.SpeedConfig;
import it.unibo.model.obstacles.api.MovingObstacleFactory;
import it.unibo.model.obstacles.api.MovingObstacleManager;
import it.unibo.model.obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.obstacles.impl.MovingObstacles;

// CONTROLLA CHE è DIFFICULTYLEVEL

/**
 * Implementation of MovingObstacleController.
 * Manages the creation, updating, and retrieval of moving obstacles in the game.
 */
public final class MovingObstacleControllerImpl implements MovingObstacleController {

    private static final int CELLS = GameConstant.CELLS_PER_CHUNK;
    private static final int TRAIN_SPAWN_DISTANCE = 9;
    private static final int CAR_SPAWN_DISTANCE = 4;
    private static final int LOG_SPAWN_DISTANCE = 1;

    private final MovingObstacleFactory factory;
    private final MovingObstacleManager manager;
    private final GameMap gameMap;
    private final Map<Integer, Integer> chunkSpeeds = new HashMap<>();
    private final Map<Integer, Boolean> chunkDirections = new HashMap<>();
    private final Random random = new Random();

    /**
     * Constructor for MovingObstacleControllerImpl.
     * Initializes the factory and manager for moving obstacles.
     *
     * @param gameMap the game map where obstacles will be created
     */
    public MovingObstacleControllerImpl(final GameMap gameMap) {
        this.gameMap = gameMap;
        this.factory = new MovingObstacleFactoryImpl();
        this.manager = new MovingObstacleManagerImpl();
    }

    @Override
    public void update() {
        manager.updateAll();
        manager.cleanupOffscreenObstacles();

        for (final var chunk : gameMap.getVisibleChunks()) {
            final int y = chunk.getPosition();
            final String chunkType = chunk.getType().toString();
            final List<MovingObstacles> obstacles = manager.getObstaclesInChunk(y);

            if ("GRASS".equals(chunkType)) {
                continue;
            }
            if (obstacles.isEmpty() || shouldSpawnNew(obstacles, y, chunkType)) {
                spawnObstacle(y, chunk);
            }
        }
    }

    private boolean shouldSpawnNew(final List<MovingObstacles> obstacles, final int y, final String chunkType) {
        final boolean leftToRight = chunkDirections.getOrDefault(y, true);
        final int spawnDistance = getSpawnDistanceForChunkType(chunkType);
        if (leftToRight) {
            final int leftmost = obstacles.stream().mapToInt(MovingObstacles::getX).min().orElse(10);
            return leftmost >= spawnDistance;
        } else {
            final int rightmost = obstacles.stream().mapToInt(obs -> obs.getX() + obs.getWidthInCells() - 1).max().orElse(10);
            return rightmost <= ((CELLS - 1) - spawnDistance);
        }
    }

    private int getSpawnDistanceForChunkType(final String chunkType) {
        return switch (chunkType) {
            case "ROAD" -> CAR_SPAWN_DISTANCE;
            case "RAILWAY" -> TRAIN_SPAWN_DISTANCE;
            case "RIVER" -> LOG_SPAWN_DISTANCE;
            default -> CAR_SPAWN_DISTANCE; 
        };
    }

    private void spawnObstacle(final int y, final Chunk chunk) {
        final ChunkType chunkType = chunk.getType();
        final ObstacleType type = switch (chunkType.toString()) {
            case "ROAD" -> ObstacleType.CAR;
            case "RAILWAY" -> ObstacleType.TRAIN;
            case "RIVER" -> ObstacleType.LOG;
            default -> ObstacleType.CAR;
        };
        final boolean leftToRight = chunkDirections.computeIfAbsent(y, k -> random.nextBoolean());
        final int speed = chunkSpeeds.computeIfAbsent(y, k -> factory.getRandomSpeed(type));
        final int x = leftToRight ? -factory.getObstacleWidth(type) : CELLS;
        final MovingObstacles obstacle = factory.createObstacleByType(type, x, y, leftToRight ? speed : -speed);
        manager.addObstacle(obstacle);
        chunk.addObjectAt(obstacle, 0);
    }

    @Override
    public List<MovingObstacles> getObstaclesByType(final ObstacleType type) {
        return manager.getObstaclesByType(type.toString());
    }

    @Override
    public List<MovingObstacles> getAllObstacles() {
        return manager.getActiveObstacles();
    }

    @Override
    public void resetObstacles() {
        chunkSpeeds.clear();
        chunkDirections.clear();
        manager.resetAll(); 
        SpeedConfig.resetDefaultSpeeds();
    }

    @Override
    public void generateObstacles(final int difficultyLevel) {
        for (final var chunk : gameMap.getVisibleChunks()) {
            final int y = chunk.getPosition();
            final String chunkType = chunk.getType().toString();
            if ("ROAD".equals(chunkType) || "RAILWAY".equals(chunkType) || "RIVER".equals(chunkType)) {
                final ObstacleType type = switch (chunkType) {
                    case "ROAD" -> ObstacleType.CAR;
                    case "RAILWAY" -> ObstacleType.TRAIN;
                    case "RIVER" -> ObstacleType.LOG;
                    default -> ObstacleType.CAR;
                };
                chunkDirections.putIfAbsent(y, random.nextBoolean());
                chunkSpeeds.putIfAbsent(y, factory.getRandomSpeed(type));
            }
        }
    }

    @Override
    public void increaseAllObstaclesSpeed(final int amount) {
        manager.increaseSpeed(amount);
    }
}
