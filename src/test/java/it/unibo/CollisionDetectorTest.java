package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import it.unibo.model.Collision.api.CollisionDetector;
import it.unibo.model.Collision.impl.CollisionDetectorImpl;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.GameObjectImpl;
import org.junit.jupiter.api.Test;

public class CollisionDetectorTest {

    private final CollisionDetector collisionDetector = new CollisionDetectorImpl();

    @Test
    void testCheckCollisionTrue() {
        final GameObject obj1 = new GameObjectImpl(1, 2, 4);
        final GameObject obj2 = new GameObjectImpl(4, 2);
        assertTrue(collisionDetector.checkCollision(obj1, obj2));
    }

    @Test
    void testCheckCollisionFalseDifferentY() {
        final GameObject obj1 = new GameObjectImpl(1, 3, 4);
        final GameObject obj2 = new GameObjectImpl(4, 2);
        assertFalse(collisionDetector.checkCollision(obj1, obj2));
    }

    @Test
    void testCheckCollisionFalseNoCommonX() {
        final GameObject obj1 = new GameObjectImpl(1, 2, 4);
        final GameObject obj2 = new GameObjectImpl(6, 2);
        assertFalse(collisionDetector.checkCollision(obj1, obj2));
    }

}
