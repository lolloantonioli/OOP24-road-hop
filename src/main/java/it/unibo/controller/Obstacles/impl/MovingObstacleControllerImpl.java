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
    private final Random random;

    private boolean generationActive = false;
    private boolean generationPaused = false;
    private int difficultyLevel = 1;

    public MovingObstacleControllerImpl() {
        this.factory = new MovingObstacleFactoryImpl();
        this.manager = new MovingObstacleManagerImpl();
        this.random = new Random();
    }

    @Override
    public void startObstacleGeneration() {
         if (generationActive) {
            return; // Già attivo
        }

        generationActive = true;
        generationPaused = false;
        // Avvia un thread o un task per generare ostacoli a intervalli regolari
        
    }

    @Override
    public void stopObstacleGeneration() {
        generationActive = false;
        generationPaused = false;

        // Ferma il thread o task di generazione ostacoli
    }

    @Override
    public MovingObstacles createObstacle(ObstacleType type, int x, int y, int speed) {
        MovingObstacles obstacle = factory.createObstacleByType(type, x, y, speed);
        manager.addObstacle(obstacle);
        return obstacle;
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
         // Aggiorna tutti gli ostacoli
        manager.updateAll();
        
        // Rimuovi ostacoli fuori schermo
        manager.cleanupOffscreenObstaclesHorizontal();
        
        // Ogni tot update, pulisci anche quelli verticali se necessario
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
    public void increaseDifficulty(int factor) {
        difficultyLevel += factor;
        
        // Aumenta la velocità degli ostacoli esistenti
        manager.increaseSpeed(factor);
    }

    @Override
    public void resetObstacles() {
        // Ferma la generazione
        stopObstacleGeneration();
        
        // Reset degli ostacoli esistenti
        manager.resetAll();
        
        // Reset del livello di difficoltà
        difficultyLevel = 1;
        
        // Riavvia la generazione o ANCHE NON QUA(?)
        startObstacleGeneration();
    }

    @Override
    public int getCurrentDifficultyLevel() {
        return difficultyLevel;
    }

    @Override
    public void dispose() {
        stopObstacleGeneration();
        // Eventuale pulizia di altre risorse se necessario
    }

    @Override
    public boolean checkCollision(int x, int y) {
        return manager.checkCollision(x, y);
    }

}
