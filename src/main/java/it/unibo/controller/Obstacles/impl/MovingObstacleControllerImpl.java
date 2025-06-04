package it.unibo.controller.Obstacles.impl;

import java.util.List;
import java.util.Random;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.api.MovingObstacleManager;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.Obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.Obstacles.impl.MovingObstacles;

// da finire !

public class MovingObstacleControllerImpl implements MovingObstacleController {

    private final MovingObstacleFactory factory;
    private final MovingObstacleManager manager;

    public MovingObstacleControllerImpl() {
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
    public void generateObstacles(int difficultyLevel) {
        Random random = new Random();
    
        // Determina il numero di ostacoli da generare in base al livello di difficoltà
        int obstacleCount = difficultyLevel + random.nextInt(3); // Aumenta con la difficoltà

        for (int i = 0; i < obstacleCount; i++) {
            // Scegli un tipo di ostacolo casualmente tra CAR, TRAIN e LOG
            int obstacleTypeIndex = random.nextInt(3); // 0 = CAR, 1 = TRAIN, 2 = LOG

            // Scegli una posizione verticale casuale
            int y = random.nextInt(10) * 50; // Supponendo che l'altezza delle righe sia 50

            // Scegli una direzione casuale
            boolean leftToRight = random.nextBoolean();

            // Genera ostacoli in base al tipo
            if (obstacleTypeIndex == 0) {
                // Genera un set di CAR
                createCarSet(y, random.nextInt(3) + 1, leftToRight);
            } else if (obstacleTypeIndex == 1) {
                // Genera un set di TRAIN
                createTrainSet(y, random.nextInt(2) + 1, leftToRight);
            } else if (obstacleTypeIndex == 2) {
                // Genera un set di LOG
                createLogSet(y, random.nextInt(4) + 1, leftToRight);
            } else {
                throw new IllegalArgumentException("Tipo di ostacolo non supportato: " + obstacleTypeIndex);
            }
        }
    }

}
