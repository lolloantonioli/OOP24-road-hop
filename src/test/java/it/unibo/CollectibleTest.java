package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.Collectible;
import it.unibo.model.map.impl.ChunkImpl;
import it.unibo.model.map.impl.CollectibleImpl;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.map.util.CollectibleType;

/**
 * Test class for the {@link CollectibleImpl} class.
 */
class CollectibleTest {

    private static final int X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int INVALID_COORD = -1;
    private Collectible collectible;

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
