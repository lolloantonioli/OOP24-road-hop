package it.unibo.model.Player.impl;

import java.util.List;

import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.model.Player.api.CollisionIdentifier;

public class CollisionIdentifierImpl implements CollisionIdentifier{

    @Override
    public boolean isOnPlatform(GameObject obj) {
        return obj.isPlatform();
    }

    @Override
    public boolean isFatalCollision(GameObject obj) {
        return MovingObstacles.class.isInstance(obj) && !obj.isPlatform();
    }

    @Override
    public boolean isCollectibleCollision(GameObject obj) {
        return Collectible.class.isInstance(obj);
    }

    @Override
    public void checkError(List<GameObject> collidedWith) {
        if (collidedWith.stream()
            .filter(Obstacle.class::isInstance)
            .map(Obstacle.class::cast)
            .anyMatch(obstacle -> obstacle.getType().equals(ObstacleType.TREE))) {

                throw new IllegalStateException("not valid position");

            }
    }

}
