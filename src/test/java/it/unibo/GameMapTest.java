package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.GameMapImpl;

public class GameMapTest {
    
    private GameMap gameMap;

    private static final int INITIAL_SPEED = 1;
    private static final int BUFFER_SIZE = 5;
    private static final int MAX_SPEED = 10;

    @BeforeEach
    public void setUp() {
        gameMap = new GameMapImpl(INITIAL_SPEED);
    }

    @Test
    void testInitallization() {
        assertEquals(GameMapImpl.CHUNKS_NUMBER, gameMap.getAllChunks().size());
        assertEquals(0, gameMap.getCurrentPosition());
    }

    @Test
    void testInvalidSpeedArgument() {
        assertThrows(IllegalArgumentException.class, () -> new GameMapImpl(-1));
        assertThrows(IllegalArgumentException.class, () -> new GameMapImpl(11));
    }

    @Test
    void testUpdate() {
        gameMap.update();
        assertEquals(INITIAL_SPEED, gameMap.getCurrentPosition());
        gameMap.update();
        assertEquals(INITIAL_SPEED * 2, gameMap.getCurrentPosition());
    }

    @Test
    void testGetVisibleChunks() {
        List<Chunk> visibleChunks = gameMap.getVisibleChunks();
        assertEquals(GameMapImpl.CHUNKS_NUMBER, visibleChunks.size());
        IntStream.range(0, 3).forEach(i -> gameMap.update());
        visibleChunks = gameMap.getVisibleChunks();
        assertEquals(GameMapImpl.CHUNKS_NUMBER, visibleChunks.size());
        visibleChunks.forEach(chunk -> {
            assertTrue(chunk.getPosition() >= gameMap.getCurrentPosition());
            assertTrue(chunk.getPosition() < gameMap.getCurrentPosition() + GameMapImpl.CHUNKS_NUMBER);
        });
    }

    @Test
    void testIncreaseScrollSpeed() {
        GameMap fastMap = new GameMapImpl(MAX_SPEED - 1);
        fastMap.increaseScrollSpeed();
        fastMap.update();
        assertEquals(MAX_SPEED, fastMap.getCurrentPosition());
        fastMap.increaseScrollSpeed();
        fastMap.update();
        assertEquals(MAX_SPEED * 2, fastMap.getCurrentPosition());
    }

    @Test
    void testChunkGeneration() {
        int initialChunks = gameMap.getAllChunks().size();
        IntStream.range(0, 20).forEach(i -> gameMap.update());
        assertTrue(gameMap.getAllChunks().size() > initialChunks);
    }

    @Test
    void testChunkCleanup() {
        IntStream.range(0, 20).forEach(i -> gameMap.update());
        gameMap.getAllChunks().forEach(chunk -> {
            assertTrue(chunk.getPosition() >= gameMap.getCurrentPosition() - GameMapImpl.CHUNKS_NUMBER);
        });
    }

    @Test
    void testBufferChunks() {
        IntStream.range(0, 20).forEach(i -> gameMap.update());
        int farthestPosition = 0;
        for (Chunk chunk : gameMap.getAllChunks()) {
            farthestPosition = Math.max(farthestPosition, chunk.getPosition());
        }
        int currentPosition = gameMap.getCurrentPosition();
        assertTrue(farthestPosition >= currentPosition + BUFFER_SIZE + GameMapImpl.CHUNKS_NUMBER);
    }

    @Test
    void testManualChunkGeneration() {
        int initialSize = gameMap.getAllChunks().size();
        gameMap.generateNewChunk();
        assertEquals(initialSize + 1, gameMap.getAllChunks().size());
    }

}
