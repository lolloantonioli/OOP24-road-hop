package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.model.Obstacles.api.MovingObstacleManager;

public class MovingObstacleManagerImpl implements MovingObstacleManager {
    
    private final List<MovingObstacles> obstacles;

    public static final int CELLS_PER_CHUNK = 9;
    
    public MovingObstacleManagerImpl() {
        this.obstacles = new ArrayList<>();
    }
    
    @Override
    public void addObstacle(MovingObstacles obstacle) {
        if (obstacle == null) return;
        // Controlla che non ci siano sovrapposizioni prima di aggiungere
        if (isSafePosition(obstacle.getX(), obstacle.getY(), obstacle.getWidthInCells())) {
        obstacles.add(obstacle);
        }
    }
    
    @Override
    public void addObstacles(MovingObstacles[] newObstacles) {
        for (MovingObstacles obstacle : newObstacles) {
            addObstacle(obstacle);
        }
    }
    
    @Override
    public void removeObstacle(MovingObstacles obstacle) {
        obstacles.remove(obstacle);
    }
    
    @Override
    public void updateAll() {
       obstacles.stream()
        .filter(MovingObstacles::isMovable)
        .forEach(MovingObstacles::update);
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
        return obstacles.stream()
                .filter(obstacle -> obstacle.getType().toString().equalsIgnoreCase(typeStr))
                .collect(Collectors.toList());
    }

    /**
     * Ottiene ostacoli in un chunk specifico.
     */
    @Override
    public List<MovingObstacles> getObstaclesInChunk(int chunkY) {
        List<MovingObstacles> result = new ArrayList<>();
        
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() == chunkY) {
                result.add(obstacle);
            }
        }
        
        return result;
    }
    
    @Override
    public boolean checkCollision(int cellX, int chunkY) {
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() == chunkY && obstacle.isVisible()) {
                // Usa il metodo occupiesCell per controlli multi-cella
                if (obstacle.occupiesCell(cellX)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void increaseSpeed(int factor) {
        for (MovingObstacles obstacle : obstacles) {
            obstacle.increaseSpeed(factor);
        }
    }
    
    @Override
    public void cleanupOffscreenObstacles() {
        Iterator<MovingObstacles> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            MovingObstacles obstacle = iterator.next();
            
            if (!obstacle.isVisible()) {
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
        }
    }

    /**
     * Controlla collisione con un'area specifica (utile per ostacoli multi-cella).
     */
    @Override
    public boolean checkCollisionInArea(int startCellX, int endCellX, int chunkY) {
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() != chunkY || !obstacle.isVisible()) {
                continue;
            }
            
            int obstacleStart = obstacle.getX();
            int obstacleEnd = obstacleStart + obstacle.getWidthInCells();
            
            // Controlla sovrapposizione
            if (startCellX < obstacleEnd && endCellX > obstacleStart) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controlla se una posizione è sicura per posizionare un nuovo ostacolo.
     */
    @Override
    public boolean isSafePosition(int cellX, int chunkY, int widthInCells) {
        for (MovingObstacles obstacle : obstacles) {
            if (obstacle.getY() != chunkY) {
                continue; // Diverso chunk, nessuna collisione
            }
            
            // Controlla sovrapposizione tra aree
            int obstacleStart = obstacle.getX();
            int obstacleEnd = obstacleStart + obstacle.getWidthInCells();
            int newObstacleEnd = cellX + widthInCells;
            
            // Se c'è sovrapposizione, la posizione non è sicura
            if (cellX < obstacleEnd && newObstacleEnd > obstacleStart) {
                return false;
            }
        }
        return true;
    }
}