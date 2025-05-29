package it.unibo.model.Collision.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.model.Collision.api.CollisionHandler;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.util.ObstacleType;

//forse con gli stream si possono mettere a posto i for annidati
//quando si capirà come funzionano i treni da cambiare
//da capire anche i fiumi e i tronchi

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
        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .anyMatch(obj -> obj instanceof Obstacle && checkCollision(position, obj));

    }


    @Override
    public boolean isFatalCollisions(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        // Controlla collisioni fatali con tutti gli ostacoli nei chunk visibili
        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(Obstacle.class::isInstance)
            .map(Obstacle.class::cast)
            .filter(obstacle -> checkCollision(position, obstacle))
            .anyMatch(obstacle -> !obstacle.getType().equals(ObstacleType.TREE));
    }

    @Override
    public boolean canMoveTo(GameMap map, Cell newPosition) {
        checkNotNull(newPosition, "not valid position");
        checkNotNull(map, "not valid map");
        
        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(Obstacle.class::isInstance)
            .map(Obstacle.class::cast)
            .filter(obstacle -> checkCollision(newPosition, obstacle))
            .noneMatch(obstacle -> obstacle.getType().equals(ObstacleType.TREE))
            && !isOutOfBounds(newPosition, map);

    }

    @Override
    public boolean isCollectibleCollision(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");


        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(Collectible.class::isInstance)
            .map(Collectible.class::cast)
            .filter(collectible -> checkCollision(position, collectible))
            .anyMatch(collectible -> !collectible.isCollected());

    }

    @Override
    public boolean isOutOfBounds(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        return !map.getVisibleChunks().stream()
            .anyMatch(c -> c.getCells().contains(position));
    }

    
}
