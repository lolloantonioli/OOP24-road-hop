package it.unibo.model.Collision.impl;

import it.unibo.model.Collision.api.CollisionHandler;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.impl.CellImpl;

public class CollisionHandlerImpl implements CollisionHandler{

    @Override
    public boolean checkCollision(Cell position, GameObject obj) {
        if (obj == null || position == null) {
            return false;
        }

        //se non lavoriamo con le celle fare algoritmo aabb con width e height degli oggetti e player
        return position.equals(new CellImpl(obj.getX(), obj.getY()));
    }

    @Override
    public boolean happenedCollision(Cell position, GameMap map) {
        if (position == null || map == null) {
            return false;
        }

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) obj;
                    
                    if (checkCollision(position, obstacle)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    @Override
    public boolean isFatalCollisions(Cell position, GameMap map) {
        if (position == null || map == null) {
            return false;
        }

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) obj;
                    
                    if (checkCollision(position, obstacle) && 
                        !obstacle.getType().toString().equals("TREE") && 
                        !isCollectibleCollision(position, map)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean canMoveTo(GameMap map, Cell newPosition) {
        /**controllare se il player sta cercando di entrare in un albero o
         * se sta cercando di uscire lateralmente dalla mappa
         * se sta cercando di andare più avanti della parte visualizzata di mappa
        */ 
        throw new UnsupportedOperationException("Unimplemented method 'canMoveTo'");
    }

    @Override
    public boolean isCollectibleCollision(Cell position, GameMap map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCollectibleCollision'");
    }

    @Override
    public boolean isOutOfBounds(Cell position, GameMap map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOutOfBounds'");
    }

    
}
