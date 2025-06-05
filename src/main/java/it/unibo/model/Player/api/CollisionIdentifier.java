package it.unibo.model.Player.api;

import java.util.List;

import it.unibo.model.Map.api.GameObject;

public interface CollisionIdentifier {

    boolean isOnPlatform(GameObject obj);

    boolean isFatalCollision(GameObject obj);

    boolean isCollectibleCollision(GameObject obj);
    
    void checkError(List<GameObject> collidedWith);
}
