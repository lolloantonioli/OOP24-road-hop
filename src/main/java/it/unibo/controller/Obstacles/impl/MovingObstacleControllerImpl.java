package it.unibo.controller.Obstacles.impl;

import java.util.HashSet;
import java.util.List;
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

// quando si resetta la mappa (giocatore perde) chiama resetObstacles)

public class MovingObstacleControllerImpl implements MovingObstacleController {

    private final MovingObstacleFactory factory;
    private final MovingObstacleManager manager;
    private final MapController mapController;
    private final Set<Integer> populatedChunks = new HashSet<>();

    public MovingObstacleControllerImpl(MapController mapController) {
        this.mapController = mapController;
        this.factory = new MovingObstacleFactoryImpl();
        this.manager = new MovingObstacleManagerImpl();

    }

    @Override
    public MovingObstacles[] createCarSet(int y, int count, boolean leftToRight) {
        MovingObstacles[] cars = factory.createObstacleSet(ObstacleType.CAR, y, count, leftToRight);
        manager.addObstacles(cars);
        return cars;
    }

    @Override
    public MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight) {
        MovingObstacles[] trains = factory.createObstacleSet(ObstacleType.TRAIN, y, count, leftToRight);
        manager.addObstacles(trains);
        return trains;
    }

    @Override
    public MovingObstacles[] createLogSet(int y, int count, boolean leftToRight) {
        MovingObstacles[] logs = factory.createObstacleSet(ObstacleType.LOG, y, count, leftToRight);
        manager.addObstacles(logs);
        return logs;
    }

    @Override
    public void update() {
        try {
            manager.updateAll();
            manager.cleanupOffscreenObstaclesHorizontal();
        } catch (Exception e) {
            System.err.println("Errore durante l'aggiornamento degli ostacoli: " + e.getMessage());
        }
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
        manager.resetAll(); // Assicurati che il manager abbia questo metodo!
    }

    @Override
    public void generateObstacles(int difficultyLevel) {
        Random random = new Random();
        var visibleChunks = mapController.getVisibleChunks();
    
        for (var chunk : visibleChunks) {
            int y = chunk.getPosition();
            if (populatedChunks.contains(y)) {
                continue; // Già popolato, non rigenerare
            }
            populatedChunks.add(y);

            String chunkType = chunk.getType().toString();
            boolean leftToRight = random.nextBoolean();
    
            switch (chunkType) {
                case "ROAD" -> createCarSet(y, random.nextInt(3) + 1, leftToRight);
                case "RAILWAY" -> createTrainSet(y, random.nextInt(2) + 1, leftToRight);
                case "RIVER" -> createLogSet(y, random.nextInt(4) + 1, leftToRight);
            }
        }
    }

}
