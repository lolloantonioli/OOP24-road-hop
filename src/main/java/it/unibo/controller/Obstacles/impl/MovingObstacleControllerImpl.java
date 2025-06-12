package it.unibo.controller.Obstacles.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
    private final Set<Integer> populatedChunks = new HashSet<>();
    private final Map<String, Integer> chunkSpeeds = new HashMap<>();
    private final Map<Integer, Boolean> chunkDirections = new HashMap<>();
    private final Random random = new Random();

    public MovingObstacleControllerImpl(MapController mapController) {
        this.mapController = mapController;
        this.factory = new MovingObstacleFactoryImpl();
        this.manager = new MovingObstacleManagerImpl();
    }

    @Override
    public MovingObstacles[] createCarSet(int y, int count, boolean leftToRight, int speed) {
        MovingObstacles[] cars = factory.createObstacleSet(ObstacleType.CAR, y, count, leftToRight, speed);
        manager.addObstacles(cars);
        return cars;
    }

    @Override
    public MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight, int speed) {
        MovingObstacles[] trains = factory.createObstacleSet(ObstacleType.TRAIN, y, count, leftToRight, speed);
        manager.addObstacles(trains);
        return trains;
    }

    @Override
    public MovingObstacles[] createLogSet(int y, int count, boolean leftToRight, int speed) {
        MovingObstacles[] logs = factory.createObstacleSet(ObstacleType.LOG, y, count, leftToRight, speed);
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
            
            if (!chunkType.equals("ROAD") && !chunkType.equals("RAILWAY") && !chunkType.equals("RIVER")) {
                continue;
            }
            
            List<MovingObstacles> obstacles = manager.getObstaclesInChunk(y);
            
            // Se non ci sono ostacoli o l'ultimo si è allontanato abbastanza, genera nuovo
            if (obstacles.isEmpty() || shouldSpawnNew(obstacles, y)) {
                spawnObstacle(y, chunkType);
            }
        }
    }

    private boolean shouldSpawnNew(List<MovingObstacles> obstacles, int y) {
        boolean leftToRight = chunkDirections.getOrDefault(y, true);
        
        if (leftToRight) {
            int leftmost = obstacles.stream().mapToInt(MovingObstacles::getX).min().orElse(10);
            return leftmost >= 4; // Spawn quando si è allontanato di 4 celle
        } else {
            int rightmost = obstacles.stream().mapToInt(obs -> obs.getX() + obs.getWidthInCells()).max().orElse(-1);
            return rightmost <= 4; // Spawn quando si è allontanato di 4 celle
        }
    }

    private void spawnObstacle(int y, String chunkType) {
        ObstacleType type = switch (chunkType) {
            case "ROAD" -> ObstacleType.CAR;
            case "RAILWAY" -> ObstacleType.TRAIN;
            case "RIVER" -> ObstacleType.LOG;
            default -> ObstacleType.CAR;
        };
        
        boolean leftToRight = chunkDirections.computeIfAbsent(y, k -> random.nextBoolean());
        int speed = factory.getRandomSpeed(type);
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
        populatedChunks.clear();
        chunkSpeeds.clear();
        chunkDirections.clear();
        manager.resetAll(); 
    }

    @Override
    public void generateObstacles(int difficultyLevel) {
        // Inizializza solo le direzioni dei chunk
        for (var chunk : mapController.getGameMap().getVisibleChunks()) {
            int y = chunk.getPosition();
            String chunkType = chunk.getType().toString();
            if (chunkType.equals("ROAD") || chunkType.equals("RAILWAY") || chunkType.equals("RIVER")) {
                chunkDirections.putIfAbsent(y, random.nextBoolean());
            }
        }
    }

    @Override
    public void increaseAllObstaclesSpeed(int i) {
        manager.increaseSpeed(i);
    }
}