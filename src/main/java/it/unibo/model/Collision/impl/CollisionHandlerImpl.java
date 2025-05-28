package it.unibo.model.Collision.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.model.Collision.api.CollisionHandler;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.util.ObstacleType;

//forse con gli stream si possono mettere a posto i for annidati
//quando si capirà come funzionano i treni da cambiare

public class CollisionHandlerImpl implements CollisionHandler{

    @Override
    public boolean checkCollision(Cell position, GameObject obj) {
        checkNotNull(position, "not valid position");
        checkNotNull(obj, "not valid object");

        //se non lavoriamo con le celle fare algoritmo aabb con width e height degli oggetti e player
        return position.equals(new CellImpl(obj.getX(), obj.getY()));
    }

    @Override
    public boolean happenedCollision(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (checkCollision(position, obj)) {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public boolean isFatalCollisions(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Obstacle && checkCollision(position, obj)) {
                    Obstacle obstacle = (Obstacle) obj;
                    
                    if (!obstacle.getType().equals(ObstacleType.TREE)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean canMoveTo(GameMap map, Cell newPosition) {
        checkNotNull(newPosition, "not valid position");
        checkNotNull(map, "not valid map");
        
        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Obstacle && checkCollision(newPosition, obj)) {
                    Obstacle obstacle = (Obstacle) obj;
                    
                    //gli alberi bloccano il movimento del player
                    if (obstacle.getType().equals(ObstacleType.TREE)) {
                        return false;
                    }
                }
            }
        }

        return !isOutOfBounds(newPosition, map);

    }

    @Override
    public boolean isCollectibleCollision(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        for (Chunk chunk : map.getVisibleChunks()) {
            for (GameObject obj : chunk.getObjects()) {
                if (obj instanceof Collectible && checkCollision(position, obj)) {
                    Collectible collectible = (Collectible) obj;
                    
                    if (!collectible.isCollected()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isOutOfBounds(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        return !map.getVisibleChunks().stream()
            .anyMatch(c -> c.getCells().contains(position));
    }

    
}
