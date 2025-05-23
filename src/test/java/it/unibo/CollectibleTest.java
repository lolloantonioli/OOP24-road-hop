package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.CollectibleImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.util.CollectibleType;

class CollectibleTest {

    private Collectible collectible;

    private static final int X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int INVALID_COORD = -1;

    @BeforeEach
    void setUp() {
        collectible = new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN);
    }

    @Test
    void testCollectibleCreation() {
        assertEquals(X_COORD, collectible.getX());
        assertEquals(Y_COORD, collectible.getY());
        assertEquals(CollectibleType.COIN, collectible.getType());
        assertFalse(collectible.isCollected());
    }

    @Test
    void testCollectibleCreationWithNegativeCoordinates() {
        assertThrows(IllegalArgumentException.class,
        () -> new CollectibleImpl(INVALID_COORD, Y_COORD, CollectibleType.COIN));
        assertThrows(IllegalArgumentException.class,
        () -> new CollectibleImpl(X_COORD, INVALID_COORD, CollectibleType.COIN));
        assertThrows(IllegalArgumentException.class,
        () -> new CollectibleImpl(INVALID_COORD, INVALID_COORD, CollectibleType.COIN));
    }

    @Test
    void testCollect() {
        assertFalse(collectible.isCollected());
        collectible.collect();
        assertTrue(collectible.isCollected());
    }
    
}