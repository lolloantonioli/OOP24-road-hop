package it.unibo.model.Collision.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import it.unibo.model.Collision.api.CollisionProMax;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.util.ObstacleType;

public class CollisionProMaxImpl implements CollisionProMax{

    @Override
    public boolean checkCollision(Cell position, GameObject obj) {
        checkNotNull(position, "not valid position");
        checkNotNull(obj, "not valid object");

        //se non lavoriamo con le celle fare algoritmo aabb con width e height degli oggetti e player
        return position.equals(new CellImpl(obj.getX(), obj.getY()));
    }

    @Override
    public Optional<GameObject> collidedWith(Cell position, GameMap map) {
        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(obj -> checkCollision(position, obj))
            .findFirst();
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
    public boolean isOutOfBounds(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        return !map.getVisibleChunks().stream()
            .anyMatch(c -> c.getCells().contains(position));
    }

}
