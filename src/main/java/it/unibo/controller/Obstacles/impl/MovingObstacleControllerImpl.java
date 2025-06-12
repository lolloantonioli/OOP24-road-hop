package it.unibo.controller.Obstacles.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.api.MovingObstacleManager;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.Obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public class MovingObstacleControllerImpl implements MovingObstacleController {

    private final MovingObstacleFactory factory;
    private final MovingObstacleManager manager;
    private final MapController mapController;
    private final Map<Integer, Integer> chunkSpeeds = new HashMap<>();
    private final Map<Integer, Boolean> chunkDirections = new HashMap<>();
    private final Random random = new Random();

    private static final int TRAIN_SPAWN_DISTANCE = 6;
    private static final int CAR_SPAWN_DISTANCE = 3;
    private static final int LOG_SPAWN_DISTANCE = 1;

    public MovingObstacleControllerImpl(MapController mapController) {
        this.mapController = mapController;
        this.factory = new MovingObstacleFactoryImpl();
        this.manager = new MovingObstacleManagerImpl();
    }

    @Override
    public List<MovingObstacles> createCarSet(int y, int count, boolean leftToRight, int speed) {
        List<MovingObstacles> cars = factory.createObstacleSet(ObstacleType.CAR, y, count, leftToRight, speed);
        manager.addObstacles(cars);
        return cars;
    }

    @Override
    public List<MovingObstacles> createTrainSet(int y, int count, boolean leftToRight, int speed) {
        List<MovingObstacles> trains = factory.createObstacleSet(ObstacleType.TRAIN, y, count, leftToRight, speed);
        manager.addObstacles(trains);
        return trains;
    }

    @Override
    public List<MovingObstacles> createLogSet(int y, int count, boolean leftToRight, int speed) {
        List<MovingObstacles> logs = factory.createObstacleSet(ObstacleType.LOG, y, count, leftToRight, speed);
        manager.addObstacles(logs);
        return logs;
    }

    @Override
    public void update() {
        manager.updateAll();
        manager.cleanupOffscreenObstacles();
        
        // Genera continuamente nuovi ostacoli
        for (var chunk : mapController.getGameMap().getVisibleChunks()) {
            int y = chunk.getPosition();
            String chunkType = chunk.getType().toString();
            
            if (chunkType.equals("GRASS")) {
                continue;
            }
            
            List<MovingObstacles> obstacles = manager.getObstaclesInChunk(y);
            
            // Se non ci sono ostacoli o l'ultimo si è allontanato abbastanza, genera nuovo
            if (obstacles.isEmpty() || shouldSpawnNew(obstacles, y, chunkType)) {
                spawnObstacle(y, chunkType);
            }
        }
    }

    private boolean shouldSpawnNew(List<MovingObstacles> obstacles, int y, String chunkType) {
        boolean leftToRight = chunkDirections.getOrDefault(y, true);
        
        // Determina la distanza di spawn in base al tipo di chunk
        int spawnDistance = getSpawnDistanceForChunkType(chunkType);
        
        if (leftToRight) {
            // Movimento da sinistra a destra: controlla quanto si è allontanato dal bordo sinistro
            int leftmost = obstacles.stream().mapToInt(MovingObstacles::getX).min().orElse(10);
            return leftmost >= spawnDistance;
        } else {
            // Movimento da destra a sinistra: controlla quanto si è allontanato dal bordo destro
            int rightmost = obstacles.stream().mapToInt(obs -> obs.getX() + obs.getWidthInCells() - 1).max().orElse(10);
            return rightmost <= (8 - spawnDistance); // 8 è l'ultima cella valida
        }
    }

    /**
     * Ottiene la distanza di spawn appropriata per il tipo di chunk.
     */
    private int getSpawnDistanceForChunkType(String chunkType) {
        return switch (chunkType) {
            case "ROAD" -> CAR_SPAWN_DISTANCE;
            case "RAILWAY" -> TRAIN_SPAWN_DISTANCE;
            case "RIVER" -> LOG_SPAWN_DISTANCE;
            default -> CAR_SPAWN_DISTANCE; 
        };
    }

    private void spawnObstacle(int y, String chunkType) {
        ObstacleType type = switch (chunkType) {
            case "ROAD" -> ObstacleType.CAR;
            case "RAILWAY" -> ObstacleType.TRAIN;
            case "RIVER" -> ObstacleType.LOG;
            default -> ObstacleType.CAR;
        };
        
        boolean leftToRight = chunkDirections.computeIfAbsent(y, k -> random.nextBoolean());
        int speed = chunkSpeeds.computeIfAbsent(y, k -> factory.getRandomSpeed(type));
        int x = leftToRight ? -factory.getObstacleWidth(type) : 9;
        
        MovingObstacles obstacle = factory.createObstacleByType(type, x, y, leftToRight ? speed : -speed);
        manager.addObstacle(obstacle);
    }

    @Override
    public List<MovingObstacles> getObstaclesByType(ObstacleType type) {
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
    }

    @Override
    public void generateObstacles(int difficultyLevel) {
        // Inizializza le direzioni e velocità dei chunk
        for (var chunk : mapController.getGameMap().getVisibleChunks()) {
            int y = chunk.getPosition();
            String chunkType = chunk.getType().toString();
            if (chunkType.equals("ROAD") || chunkType.equals("RAILWAY") || chunkType.equals("RIVER")) {
                // Determina il tipo di ostacolo per calcolare la velocità
                ObstacleType type = switch (chunkType) {
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
    public void increaseAllObstaclesSpeed(int i) {
        manager.increaseSpeed(i);
    }
}