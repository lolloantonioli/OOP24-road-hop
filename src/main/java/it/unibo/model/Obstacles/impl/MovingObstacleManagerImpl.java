package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleManager;

public class MovingObstacleManagerImpl implements MovingObstacleManager {
    
    private final List<MovingObstacles> obstacles;
    
    public MovingObstacleManagerImpl() {
        this.obstacles = new ArrayList<>();
    }
    
    @Override
    public void addObstacle(MovingObstacles obstacle) {
        // Controlla che non ci siano sovrapposizioni prima di aggiungere
        if (obstacle.canBePlacedAt(obstacle.getX(), obstacles)) {
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
    public void updateAll(int mapWidth) {
       // Converti la larghezza della mappa da pixel a chunks (se necessario)
       int mapWidthInChunks = mapWidth / 9; // Assumendo 9 celle per chunk
        
       for (MovingObstacles obstacle : obstacles) {
           if (obstacle.isMovable()) {
               obstacle.setMapWidthInChunks(mapWidthInChunks);
               obstacle.update();
           }
       }
       
       // Gestisci le collisioni tra ostacoli
       handleObstacleCollisions();
    }
    
    /**
     * Gestisce le collisioni tra ostacoli mobili.
     */
    private void handleObstacleCollisions() {
        List<MovingObstacles> cars = getObstaclesByType(ObstacleType.CAR.toString());
        List<MovingObstacles> trains = getObstaclesByType(ObstacleType.TRAIN.toString());
        
        // Gestisci collisioni tra auto dello stesso chunk
        handleSameTypeCollisions(cars);
        
        // Gestisci collisioni tra treni dello stesso chunk
        handleSameTypeCollisions(trains);
    }

    /**
     * Gestisce collisioni tra ostacoli dello stesso tipo.
     */
    private void handleSameTypeCollisions(List<MovingObstacles> sameTypeObstacles) {
        for (int i = 0; i < sameTypeObstacles.size(); i++) {
            MovingObstacles obstacle1 = sameTypeObstacles.get(i);
            if (!obstacle1.isMovable()) continue;
            
            for (int j = i + 1; j < sameTypeObstacles.size(); j++) {
                MovingObstacles obstacle2 = sameTypeObstacles.get(j);
                if (!obstacle2.isMovable() || obstacle1.getY() != obstacle2.getY()) continue;
                
                // Se si muovono nella stessa direzione e si stanno avvicinando
                if (Math.signum(obstacle1.getSpeed()) == Math.signum(obstacle2.getSpeed()) && 
                    obstacle1.collidesWith(obstacle2)) {
                    
                    // L'ostacolo più lento rallenta ulteriormente per evitare la collisione
                    if (Math.abs(obstacle1.getSpeed()) > Math.abs(obstacle2.getSpeed())) {
                        adjustSpeed(obstacle2, -1);
                    } else {
                        adjustSpeed(obstacle1, -1);
                    }
                }
            }
        }
    }

    /**
     * Aggiusta la velocità di un ostacolo mantenendo la direzione.
     */
    private void adjustSpeed(MovingObstacles obstacle, int adjustment) {
        int currentSpeed = obstacle.getSpeed();
        int newSpeed = currentSpeed + adjustment;
        
        // Mantieni la direzione ma non permettere velocità negative se era positiva
        if (currentSpeed > 0) {
            newSpeed = Math.max(0, newSpeed);
        } else if (currentSpeed < 0) {
            newSpeed = Math.min(0, newSpeed);
        }
        
        obstacle.setSpeed(newSpeed);
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
            if (obstacle.collidesWith(cellX, chunkY)) {
                return true;
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
    public void cleanupOffscreenObstacles(int minY, int maxY) {
        Iterator<MovingObstacles> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            MovingObstacles obstacle = iterator.next();
            int y = obstacle.getY();
            
            // Rimuovi ostacoli fuori dall'area visibile
            if (y < minY || y > maxY) {
                iterator.remove();
            }
        }
    }

     /**
     * Pulisce ostacoli che sono usciti completamente dalla griglia.
     */
    @Override
    public void cleanupOffscreenObstaclesHorizontal() {
        Iterator<MovingObstacles> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            MovingObstacles obstacle = iterator.next();
            int x = obstacle.getX();
            int width = obstacle.getWidthInCells();
            
            // Rimuovi se completamente fuori dalla griglia (con un margine)
            if (x > MovingObstacles.CELLS_PER_CHUNK + 10 || x + width < -10) {
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
        return !checkCollisionInArea(cellX, cellX + widthInCells, chunkY);
    }
}