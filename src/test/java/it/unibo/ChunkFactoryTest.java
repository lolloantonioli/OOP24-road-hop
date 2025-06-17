package it.unibo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.Chunk;
import it.unibo.model.map.api.ChunkFactory;
import it.unibo.model.map.api.Collectible;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.api.Obstacle;
import it.unibo.model.map.impl.ChunkFactoryImpl;
import it.unibo.model.map.impl.ChunkImpl;
import it.unibo.model.map.util.ChunkType;
import it.unibo.model.map.util.CollectibleType;
import it.unibo.model.map.util.ObstacleType;

/**
 * Test class for the {@link ChunkFactoryImpl} class.
 */
class ChunkFactoryTest {

    private static final int NUM_CHUNKS = 50;
    private static final int INVALID_COORD = -1;
    private static final int VALID_COORD = 1;
    private ChunkFactory chunkFactory;

    /**
     * Initializes a new ChunkFactory before each test.
     */
    @BeforeEach
    void setUp() {
        chunkFactory = new ChunkFactoryImpl();
    }

    /**
     * Tests that creating a chunk with a negative position throws an exception.
     */
    @Test
    void testNegativePosition() {
        assertThrows(IllegalArgumentException.class, () -> chunkFactory.createRandomChunk(INVALID_COORD));
    }

    /**
     * Tests that a chunk is correctly created with a valid position and type.
     */
    @Test
    void testValidPosition() {
        final Chunk chunk = chunkFactory.createRandomChunk(VALID_COORD);
        assertNotNull(chunk);
        assertEquals(VALID_COORD, chunk.getPosition());
        assertNotNull(chunk.getType());
    }

    /**
     * Tests that the factory can generate at least two different chunk types over multiple creations.
     */
    @Test
    void testTypesNumber() {
        final Set<ChunkType> encounteredTypes = new HashSet<>();
        IntStream.range(0, NUM_CHUNKS).forEach(i -> {
            final Chunk chunk = chunkFactory.createRandomChunk(i);
            encounteredTypes.add(chunk.getType());
        });
        assertTrue(encounteredTypes.size() >= 2);
    }

    /**
     * Tests that grass chunks are created with the correct position, type, and non-null objects.
     */
    @Test
    void testCreateGrassChunks() {
        final List<Integer> positions = List.of(0, 3, 7, 15);
        positions.forEach(position -> {
            final Chunk chunk = chunkFactory.createGrassChunk(position);
            assertNotNull(chunk);
            assertEquals(position, chunk.getPosition());
            assertEquals(ChunkType.GRASS, chunk.getType());
            assertNotNull(chunk.getObjects());
        });
    }

    /**
     * Tests that all generated chunk types are among the expected set of types.
     */
    @Test
    void testTypes() {
        final Set<ChunkType> types = Set.of(
            ChunkType.GRASS, 
            ChunkType.ROAD, 
            ChunkType.RAILWAY, 
            ChunkType.RIVER
        );
        IntStream.range(0, 100).forEach(i -> {
            final Chunk chunk = chunkFactory.createRandomChunk(i);
            assertTrue(types.contains(chunk.getType()));
        });
    }

    /**
     * Tests that all objects in a chunk have valid positions within the chunk.
     */
    @Test
    void testObjectsPositions() {
        final Chunk chunk = chunkFactory.createRandomChunk(VALID_COORD);
        chunk.getObjects().forEach(obj -> {
            assertTrue(obj.getX() >= 0);
            assertTrue(obj.getX() < ChunkImpl.CELLS_PER_ROW);
            assertEquals(chunk.getPosition(), obj.getY());
        });
    }

    /**
     * Tests that objects in grass chunks are either static trees or valid collectibles.
     */
    @Test
    void testObjectTypes() {
        IntStream.range(0, NUM_CHUNKS).forEach(i -> {
            final Chunk chunk = chunkFactory.createGrassChunk(i);
            chunk.getObjects().forEach(obj -> {
                if (obj instanceof Obstacle obstacle) {
                    assertEquals(ObstacleType.TREE, obstacle.getType());
                    assertFalse(obstacle.isMovable());
                } else if (obj instanceof Collectible collectible) {
                    assertTrue(
                        collectible.getType().equals(CollectibleType.COIN) 
                        || collectible.getType().equals(CollectibleType.SECOND_LIFE)
                    );
                }
            });
        });
    }

    /**
     * Tests that grass chunks can contain both obstacles and collectibles.
     */
    @Test
    void testGrassChunkObjectPlacement() {
        boolean hasObstacles = false;
        boolean hasCollectibles = false;
        for (int i = 0; i < 100; i++) {
            final Chunk grassChunk = chunkFactory.createGrassChunk(i);
            for (final GameObject obj : grassChunk.getObjects()) {
                if (obj instanceof Obstacle) {
                    hasObstacles = true;
                }
                if (obj instanceof Collectible) {
                    hasCollectibles = true;
                }
            }
        }
        assertTrue(hasObstacles);
        assertTrue(hasCollectibles);
    }

    /**
     * Tests that obstacles and collectibles are never placed in the same cell in a grass chunk.
     */
    @Test
    void testObjectsPlacement() {
        IntStream.range(0, NUM_CHUNKS).forEach(i -> {
            final Chunk chunk = chunkFactory.createGrassChunk(i);
            final Set<Integer> obstaclePositions = new HashSet<>();
            final Set<Integer> collectiblePositions = new HashSet<>();
            chunk.getObjects().forEach(obj -> {
                if (obj instanceof Obstacle) {
                    obstaclePositions.add(obj.getX());
                } else if (obj instanceof Collectible) {
                    collectiblePositions.add(obj.getX());
                }
            });
            final Set<Integer> intersection = new HashSet<>(obstaclePositions);
            intersection.retainAll(collectiblePositions);
            assertTrue(intersection.isEmpty());
        });
    }

}
