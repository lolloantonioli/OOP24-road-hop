package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleManager;

public class MovingObstacleManagerImpl implements MovingObstacleManager {
    
    private final List<MovingObstacles> obstacles;
    
    public MovingObstacleManagerImpl() {
        this.obstacles = new ArrayList<>();
    }
    
    @Override
    public void addObstacle(MovingObstacles obstacle) {
        obstacles.add(obstacle);
    }
    
    @Override
    public void addObstacles(MovingObstacles[] newObstacles) {
        obstacles.addAll(Arrays.asList(newObstacles));
    }
    
    @Override
    public void removeObstacle(MovingObstacles obstacle) {
        obstacles.remove(obstacle);
    }
    
    @Override
    public void updateAll(int mapWidth) {
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.isMovable()) {
                obstacle.setMapWidth(mapWidth);
                obstacle.update();
            }
        }
        
        // Controllo delle collisioni tra ostacoli (opzionale, per comportamenti avanzati)
        checkObstacleCollisions();
    }
    
    /**
     * Controlla se ci sono collisioni tra gli ostacoli e se serve le sistema.
     */
    private void checkObstacleCollisions() {
        // Per semplicità, controlliamo solo collisioni tra ostacoli dello stesso tipo
        // che si muovono nella stessa direzione
        
        List<MovingObstacles> cars = getObstaclesByType(ObstacleType.CAR.toString());
        
        // Controlla collisioni tra auto
        for (int i = 0; i < cars.size(); i++) {
            MovingObstacles car1 = cars.get(i);
            if (!car1.isMovable()) continue;
            
            for (int j = i + 1; j < cars.size(); j++) {
                MovingObstacles car2 = cars.get(j);
                if (!car2.isMovable() || Math.signum(car1.getSpeed()) != Math.signum(car2.getSpeed())) continue;
                
                if (car1.collidesWith(car2)) {
                    // Le auto dello stesso senso di marcia si adattano
                    if (Math.abs(car1.getSpeed()) > Math.abs(car2.getSpeed())) {
                        car1.setSpeed(car2.getSpeed());
                    } else {
                        car2.setSpeed(car1.getSpeed());
                    }
                }
            }
        }
    }
    
    @Override
    public List<MovingObstacles> getActiveObstacles() {
        return new ArrayList<>(obstacles);
    }
    
    @Override
    public List<MovingObstacles> getObstaclesByType(String typeStr) {
        List<MovingObstacles> result = new ArrayList<>();
    
        // Se typeStr è null, ritorniamo una lista vuota
        if (typeStr == null) {
            return result;
        }
    
        // Iteriamo attraverso tutti gli ostacoli e confrontiamo il tipo come stringa
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.getType().toString().equalsIgnoreCase(typeStr)) {
                result.add(obstacle);
            }
        }

        return result;
    }
    
    @Override
    public boolean checkCollision(int x, int y) {
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.collidesWith(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void increaseSpeed(int factor) {
        for (MovingObstacles obstacle : obstacles) {
            int currentSpeed = obstacle.getSpeed();
            int newSpeed = currentSpeed;
            
            // Incrementa mantenendo il segno (direzione)
            if (currentSpeed > 0) {
                newSpeed += factor;
            } else {
                newSpeed -= factor;
            }
            
            obstacle.setSpeed(newSpeed);
        }
    }
    
    @Override
    public void cleanupOffscreenObstacles(int minY, int maxY) {
        Iterator<MovingObstacles> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            MovingObstacles obstacle = iterator.next();
            int y = obstacle.getY();
            
            // Rimuovi ostacoli fuori dall'area visibile in verticale
            if (y < minY || y > maxY) {
                iterator.remove();
            }
        }
    }
    
    @Override
    public int getObstacleCount() {
        return obstacles.size();
    }
    
    @Override
    public void resetAll() {
        for (MovingObstacles obstacle : obstacles) {
            obstacle.reset();
        
            // Ripristina anche la velocità originale se è stata modificata
            // Potremmo mantenere una mappa delle velocità iniziali o aggiungere 
            // un campo initialSpeed a MovingObstacles
            if (obstacle.getType() == ObstacleType.CAR) {
                int direction = Integer.signum(obstacle.getSpeed());
                obstacle.setSpeed(direction * (MovingObstacleFactoryImpl.MIN_CAR_SPEED + 
                              new Random().nextInt(MovingObstacleFactoryImpl.MAX_CAR_SPEED - 
                              MovingObstacleFactoryImpl.MIN_CAR_SPEED + 1)));
            } else if (obstacle.getType() == ObstacleType.TRAIN) {
                int direction = Integer.signum(obstacle.getSpeed());
                obstacle.setSpeed(direction * (MovingObstacleFactoryImpl.MIN_TRAIN_SPEED + 
                              new Random().nextInt(MovingObstacleFactoryImpl.MAX_TRAIN_SPEED - 
                              MovingObstacleFactoryImpl.MIN_TRAIN_SPEED + 1)));
            }
        }
    }
}