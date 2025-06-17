package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.Chunk;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.impl.GameMapImpl;

/**
 * Test class for the {@link GameMapImpl} class.
 */
class GameMapTest {

    private static final int INITIAL_SPEED = 1;
    private static final int BUFFER_SIZE = 5;
    private static final int MAX_SPEED = 10;
    private static final int UPDATES = 20;
    private GameMap gameMap;

    /**
     * Sets up a new GameMap instance before each test.
     */
    @BeforeEach
    public void setUp() {
        gameMap = new GameMapImpl();
    }

    /**
     * Tests that the GameMap is correctly initialized with the expected number of chunks
     * and a current position of zero. I use size() - 1 for the next chunk not visible at all.
     */
    @Test
    void testInitallization() {
        assertEquals(GameMapImpl.CHUNKS_NUMBER, gameMap.getAllChunks().size() - 1);
        assertEquals(0, gameMap.getCurrentPosition());
    }

    /**
     * Tests that the current position of the map increases correctly after each update,
     * based on the scroll speed.
     */
    @Test
    void testUpdate() {
        gameMap.update();
        assertEquals(INITIAL_SPEED, gameMap.getCurrentPosition());
        gameMap.update();
        assertEquals(INITIAL_SPEED * 2, gameMap.getCurrentPosition());
    }

    /**
     * Tests that the list of visible chunks is always aligned with the current position
     * and contains the expected number of elements.
     */
    @Test
    void testGetVisibleChunks() {
        List<Chunk> visibleChunks = gameMap.getVisibleChunks();
        assertEquals(GameMapImpl.CHUNKS_NUMBER, visibleChunks.size() - 1);
        IntStream.range(0, 3).forEach(i -> gameMap.update());
        visibleChunks = gameMap.getVisibleChunks();
        assertEquals(GameMapImpl.CHUNKS_NUMBER, visibleChunks.size() - 1);
        visibleChunks.forEach(chunk -> {
            assertTrue(chunk.getPosition() >= gameMap.getCurrentPosition());
            assertTrue(chunk.getPosition() < gameMap.getCurrentPosition() + GameMapImpl.CHUNKS_NUMBER + 1);
        });
    }

    /**
     * Tests that the scroll speed can be increased up to the maximum allowed value.
     */
    @Test
    void testIncreaseScrollSpeed() {
        final GameMap fastMap = new GameMapImpl();
        assertEquals(1, fastMap.getScrollSpeed());

        IntStream.range(0, MAX_SPEED - 1)
            .forEach(i -> fastMap.increaseScrollSpeed());
        assertEquals(MAX_SPEED, fastMap.getScrollSpeed());

        fastMap.increaseScrollSpeed(); // dovrebbe restare a MAX_SPEED
        assertEquals(MAX_SPEED, fastMap.getScrollSpeed());
    }

    /**
     * Tests that new chunks are generated as the game map is updated over time.
     */
    @Test
    void testChunkGeneration() {
        final int initialChunks = gameMap.getAllChunks().size();
        IntStream.range(0, UPDATES).forEach(i -> gameMap.update());
        assertTrue(gameMap.getAllChunks().size() > initialChunks);
    }

    /**
     * Tests that old chunks are removed (cleaned up) and only chunks
     * near the current position are retained.
     */
    @Test
    void testChunkCleanup() {
        IntStream.range(0, UPDATES).forEach(i -> gameMap.update());
        gameMap.getAllChunks().forEach(chunk -> {
            assertTrue(chunk.getPosition() >= gameMap.getCurrentPosition() - GameMapImpl.CHUNKS_NUMBER);
        });
    }

    /**
     * Tests that buffer chunks are correctly generated beyond the visible area,
     * to ensure smooth scrolling and future visibility.
     */
    @Test
    void testBufferChunks() {
        IntStream.range(0, UPDATES).forEach(i -> gameMap.update());
        int farthestPosition = 0;
        for (final Chunk chunk : gameMap.getAllChunks()) {
            farthestPosition = Math.max(farthestPosition, chunk.getPosition());
        }
        final int currentPosition = gameMap.getCurrentPosition();
        assertTrue(farthestPosition >= currentPosition + BUFFER_SIZE + GameMapImpl.CHUNKS_NUMBER);
    }

    /**
     * Tests the manual generation of a new chunk, ensuring that the total number of chunks increases by one.
     */
    @Test
    void testManualChunkGeneration() {
        final int initialSize = gameMap.getAllChunks().size();
        gameMap.generateNewChunk();
        assertEquals(initialSize + 1, gameMap.getAllChunks().size());
    }

}
