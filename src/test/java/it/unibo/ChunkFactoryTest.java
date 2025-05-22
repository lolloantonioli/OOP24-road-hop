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

    @BeforeEach
    void setUp() {
        chunkFactory = new ChunkFactoryImpl();
    }

    @Test
    void testNegativePosition() {
        assertThrows(IllegalArgumentException.class, () -> {
            chunkFactory.createRandomChunk(-1);
        });
    }

    @Test
    void testZeroPosition() {
        assertDoesNotThrow(() -> {
            Chunk chunk = chunkFactory.createRandomChunk(0);
            assertNotNull(chunk);
            assertEquals(0, chunk.getPosition());
        });
    }

    @Test
    void testValidPosition() {
        Chunk chunk = chunkFactory.createRandomChunk(2);
        assertNotNull(chunk);
        assertEquals(2, chunk.getPosition());
        assertNotNull(chunk.getType());
    }

    @Test
    void testTypesNumber() {
        Set<ChunkType> encounteredTypes = new HashSet<>();
        IntStream.range(0, 50).forEach(i -> {
            Chunk chunk = chunkFactory.createRandomChunk(i);
            encounteredTypes.add(chunk.getType());
        });
        assertTrue(encounteredTypes.size() >= 2);
    }

    @Test
    void testCreateGrassChunks() {
        List<Integer> positions = List.of(0, 3, 7, 15);
        positions.forEach(position -> {
            Chunk chunk = chunkFactory.createGrassChunk(position);
            assertNotNull(chunk);
            assertEquals(position, chunk.getPosition());
            assertEquals(ChunkType.GRASS, chunk.getType());
            assertNotNull(chunk.getObjects());
        });
    }

    @Test
    void testTypes() {
        Set<ChunkType> validTypes = Set.of(
            ChunkType.GRASS, 
            ChunkType.ROAD, 
            ChunkType.RAILWAY, 
            ChunkType.RIVER
        );
        IntStream.range(0, 100).forEach(i -> {
            Chunk chunk = chunkFactory.createRandomChunk(i);
            assertTrue(validTypes.contains(chunk.getType()));
        });
    }

    @Test
    void testObjectsPositions() {
        Chunk chunk = chunkFactory.createRandomChunk(0);
        chunk.getObjects().forEach(obj -> {
            assertTrue(obj.getX() >= 0);
            assertTrue(obj.getX() < ChunkImpl.CELLS_PER_ROW);
            assertEquals(chunk.getPosition(), obj.getY());
        });
    }

    @Test
    void testObjectTypes() {
        IntStream.range(0, 50).forEach(i -> {
            Chunk chunk = chunkFactory.createGrassChunk(i);
            chunk.getObjects().forEach(obj -> {
                if (obj instanceof Obstacle obstacle) {
                    assertEquals(ObstacleType.TREE, obstacle.getType());
                    assertFalse(obstacle.isMovable());
                } else if (obj instanceof Collectible collectible) {
                    assertEquals(CollectibleType.COIN, collectible.getType());
                }
            });
        });
    }

    @Test
    void testGrassChunkObjectPlacement() {
        boolean hasObstacles = false;
        boolean hasCollectibles = false;
        for (int i = 0; i < 100; i++) {
            Chunk grassChunk = chunkFactory.createGrassChunk(i);
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
    }

    @Test
    void testNoObjectsInInvalidPositions() {
        IntStream.range(0, 20).forEach(i -> {
            Chunk chunk = chunkFactory.createRandomChunk(i);
            chunk.getObjects().forEach(obj -> {
                assertTrue(obj.getX() >= 0 && obj.getX() < ChunkImpl.CELLS_PER_ROW);
            });
        });
    }

    @Test
    void testCollectiblesDoNotOverlapWithObstacles() {
        IntStream.range(0, 50).forEach(i -> {
            Chunk chunk = chunkFactory.createGrassChunk(i);
            Set<Integer> obstaclePositions = new HashSet<>();
            Set<Integer> collectiblePositions = new HashSet<>();
            chunk.getObjects().forEach(obj -> {
                if (obj instanceof Obstacle) {
                    obstaclePositions.add(obj.getX());
                } else if (obj instanceof Collectible) {
                    collectiblePositions.add(obj.getX());
                }
            });
            Set<Integer> intersection = new HashSet<>(obstaclePositions);
            intersection.retainAll(collectiblePositions);
            assertTrue(intersection.isEmpty());
        });
    }

    @Test
    void testFactoryConsistency() {
        int position = 10;
        IntStream.range(0, 5).forEach(i -> {
            Chunk chunk = chunkFactory.createGrassChunk(position);
            assertEquals(position, chunk.getPosition());
            assertEquals(ChunkType.GRASS, chunk.getType());
        });
    }
}