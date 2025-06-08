package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.CollectibleImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.util.CollectibleType;

/**
 * Test class for the {@link CollectibleImpl} class.
 */
class CollectibleTest {

    private Collectible collectible;

    private static final int X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int INVALID_COORD = -1;

    /**
     * Initializes a collectible before each test.
     */
    @BeforeEach
    void setUp() {
        collectible = new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN);
    }

    /**
     * Tests that a collectible is created with the correct coordinates, type, and is not collected.
     */
    @Test
    void testCollectibleCreation() {
        assertEquals(X_COORD, collectible.getX());
        assertEquals(Y_COORD, collectible.getY());
        assertEquals(CollectibleType.COIN, collectible.getType());
        assertFalse(collectible.isCollected());
    }

    /**
     * Tests that creating a collectible with negative coordinates throws an IllegalArgumentException.
     */
    @Test
    void testCollectibleCreationWithNegativeCoordinates() {
        assertThrows(IllegalArgumentException.class,
            () -> new CollectibleImpl(INVALID_COORD, Y_COORD, CollectibleType.COIN));
        assertThrows(IllegalArgumentException.class,
            () -> new CollectibleImpl(X_COORD, INVALID_COORD, CollectibleType.COIN));
        assertThrows(IllegalArgumentException.class,
            () -> new CollectibleImpl(INVALID_COORD, INVALID_COORD, CollectibleType.COIN));
    }

    /**
     * Tests that collecting a collectible changes its state to collected.
     */
    @Test
    void testCollect() {
        assertFalse(collectible.isCollected());
        collectible.collect();
        assertTrue(collectible.isCollected());
    }
    
}