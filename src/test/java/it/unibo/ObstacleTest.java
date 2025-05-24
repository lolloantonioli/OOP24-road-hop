package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.util.ObstacleType;

class ObstacleTest {

    private Obstacle staticObstacle;
    private static final int X_POSITION = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_POSITION = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int INVALID_POSITION = -1;

    @BeforeEach
    void setUp() {
        staticObstacle = new ObstacleImpl(X_POSITION, Y_POSITION, ObstacleType.TREE, false);
    }

    @Test
    void testObstacleCreation() {        
        assertEquals(X_POSITION, staticObstacle.getX());
        assertEquals(Y_POSITION, staticObstacle.getY());
        assertEquals(ObstacleType.TREE, staticObstacle.getType());
        assertFalse(staticObstacle.isMovable());
    }

    @Test
    void testInvalidObstacleCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ObstacleImpl(INVALID_POSITION, Y_POSITION, ObstacleType.TREE, false);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ObstacleImpl(X_POSITION, INVALID_POSITION, ObstacleType.TREE, false);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ObstacleImpl(INVALID_POSITION, INVALID_POSITION, ObstacleType.TREE, false);
        });
    }

}