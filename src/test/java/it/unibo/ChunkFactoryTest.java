package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.ChunkFactoryImpl;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

class ChunkFactoryTest {

    private ChunkFactory chunkFactory;

    private static final int INVALID_COORD = -1;
    private static final int VALID_COORD = 1;

    @BeforeEach
    void setUp() {
        chunkFactory = new ChunkFactoryImpl();
    }

    @Test
    void testNegativePosition() {
        assertThrows(IllegalArgumentException.class, () -> chunkFactory.createRandomChunk(INVALID_COORD));
    }

    @Test
    void testValidPosition() {
        final Chunk chunk = chunkFactory.createRandomChunk(VALID_COORD);
        assertNotNull(chunk);
        assertEquals(VALID_COORD, chunk.getPosition());
        assertNotNull(chunk.getType());
    }

    @Test
    void testTypesNumber() {
        final Set<ChunkType> encounteredTypes = new HashSet<>();
        IntStream.range(0, 50).forEach(i -> {
            final Chunk chunk = chunkFactory.createRandomChunk(i);
            encounteredTypes.add(chunk.getType());
        });
        assertTrue(encounteredTypes.size() >= 2);
    }

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

    @Test
    void testObjectsPositions() {
        final Chunk chunk = chunkFactory.createRandomChunk(VALID_COORD);
        chunk.getObjects().forEach(obj -> {
            assertTrue(obj.getX() >= 0);
            assertTrue(obj.getX() < ChunkImpl.CELLS_PER_ROW);
            assertEquals(chunk.getPosition(), obj.getY());
        });
    }

    @Test
    void testObjectTypes() {
        IntStream.range(0, 50).forEach(i -> {
            final Chunk chunk = chunkFactory.createGrassChunk(i);
            chunk.getObjects().forEach(obj -> {
                if (obj instanceof Obstacle obstacle) {
                    assertEquals(ObstacleType.TREE, obstacle.getType());
                    assertFalse(obstacle.isMovable());
                } else if (obj instanceof Collectible collectible) {
                    assertTrue(collectible.getType().equals(CollectibleType.COIN) || collectible.getType().equals(CollectibleType.SECOND_LIFE));
                }
            });
        });
    }

    @Test
    void testGrassChunkObjectPlacement() {
        boolean hasObstacles = false;
        boolean hasCollectibles = false;
        for (int i = 0; i < 100; i++) {
            final Chunk grassChunk = chunkFactory.createGrassChunk(i);
            for (GameObject obj : grassChunk.getObjects()) {
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

    @Test
    void testObjectsPlacement() {
        IntStream.range(0, 50).forEach(i -> {
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