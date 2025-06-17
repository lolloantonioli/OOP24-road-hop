package it.unibo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unibo.model.collision.impl.CollisionIdentifierImpl;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.CollectibleImpl;
import it.unibo.model.map.impl.ObstacleImpl;
import it.unibo.model.map.util.CollectibleType;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.obstacles.impl.MovingObstacles;

class CollisionIdentifierTest {

    private final CollisionIdentifierImpl identifier = new CollisionIdentifierImpl();

    @Test
    void testIsOnPlatform() {
        final GameObject platform = new MovingObstacles(2, 3, ObstacleType.LOG, 5);
        assertTrue(identifier.isOnPlatform(platform));
    }

    @Test
    void testIsFatalCollision() {
        final GameObject fatal = new MovingObstacles(2, 3, ObstacleType.CAR, 5);
        assertTrue(identifier.isFatalCollision(fatal));
    }

    @Test
    void testIsCollectibleCollision() {
        final GameObject collectible = new CollectibleImpl(0, 0, CollectibleType.COIN);
        assertTrue(identifier.isCollectibleCollision(collectible));
    }

    @Test
    void testCheckErrorNoError() {
        final GameObject platform = new MovingObstacles(2, 3, ObstacleType.LOG, 5);
        final GameObject fatal = new MovingObstacles(2, 3, ObstacleType.CAR, 5);
        final GameObject collectible = new CollectibleImpl(0, 0, CollectibleType.COIN);
        assertDoesNotThrow(() -> identifier.checkError(List.of(platform, fatal, collectible)));
    }

    @Test
    void testCheckErrorThrows() {
        final GameObject other = new ObstacleImpl(0, 0, ObstacleType.TREE, false);
        assertThrows(IllegalStateException.class, () -> identifier.checkError(List.of(other)));
    }
}
