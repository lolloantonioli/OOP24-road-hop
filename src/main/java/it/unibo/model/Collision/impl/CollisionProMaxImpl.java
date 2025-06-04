package it.unibo.model.Collision.impl;

/*NON UTILIZZATO */

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

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
    public List<GameObject> getCollidedObjects(Cell position, GameMap map) {
        
        //metodo specifico perchè non possono esserci più oggetti nella stessa cella
        //dovrei espanderlo e poi farlo gestire da game manager?
        //se mi restituisco una lista e contiene più di un object posso usarlo per capire se ci sono stati errori
        //PLAYER IS A GAME OBJECT!
        return map.getVisibleChunks().stream()
            .flatMap(chunk -> chunk.getObjects().stream())
            .filter(obj -> checkCollision(position, obj))
            .toList();
    }

    @Override
    public boolean canMoveTo(GameMap map, Cell newPosition) {
        checkNotNull(newPosition, "not valid position");
        checkNotNull(map, "not valid map");
    
        return !isOutOfBounds(newPosition, map) && 
            getCollidedObjects(newPosition, map)
            .stream()
            .filter(Obstacle.class::isInstance)
            .map(Obstacle.class::cast)
            .noneMatch(obstacle -> obstacle.getType().equals(ObstacleType.TREE));

    }

    @Override
    public boolean isOutOfBounds(Cell position, GameMap map) {
        checkNotNull(position, "not valid position");
        checkNotNull(map, "not valid map");

        return !map.getVisibleChunks().stream()
            .anyMatch(c -> c.getCells().contains(position));
    }

}
