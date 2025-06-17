package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.CollectibleImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

/**
 * Test class for the {@link ChunkImpl} class.
 */
class ChunkTest {

    private Chunk chunk;
    private GameObject testObject;

    private static final int TEST_CHUNK_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int TEST_X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int INVALID_COORD = -1;

    /**
     * Initializes a chunk and a test object before each test.
     */
    @BeforeEach
    void setUp() {
        chunk = new ChunkImpl(TEST_CHUNK_COORD, ChunkType.GRASS);
        testObject = new CollectibleImpl(TEST_X_COORD, TEST_CHUNK_COORD, CollectibleType.COIN);
    }

    /**
     * Tests that a chunk is created with the correct position, type, and number of cells.
     */
    @Test
    void testChunkCreation() {
        assertEquals(TEST_CHUNK_COORD, chunk.getPosition());
        assertEquals(ChunkType.GRASS, chunk.getType());
        assertEquals(ChunkImpl.CELLS_PER_ROW, chunk.getCells().size());
    }

    /**
     * Tests that creating a chunk with an invalid position throws an exception.
     */
    @Test
    void testChunkCreationWithInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> new ChunkImpl(INVALID_COORD, ChunkType.GRASS));
    }

    /**
     * Tests that creating a chunk with a null type throws a NullPointerException.
     */
    @Test
    void testChunkCreationWithNullType() {
        assertThrows(NullPointerException.class, () -> new ChunkImpl(TEST_CHUNK_COORD, null));
    }

    /**
     * Tests adding an object at a valid position in the chunk.
     */
    @Test
    void testAddObjectAtValidPosition() {
        assertTrue(chunk.addObjectAt(testObject, TEST_X_COORD));
        final List<GameObject> objects = chunk.getObjects();
        assertEquals(1, objects.size());
        assertEquals(testObject, objects.get(0));
    }

    /**
     * Tests that adding an object at an invalid position throws an exception.
     */
    @Test
    void testAddObjectAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> chunk.addObjectAt(testObject, INVALID_COORD));
        assertThrows(IllegalArgumentException.class, () -> chunk.addObjectAt(testObject, ChunkImpl.CELLS_PER_ROW));
    }

    /**
     * Tests that adding a null object throws a NullPointerException.
     */
    @Test
    void testAddNullObject() {
        assertThrows(NullPointerException.class, () -> chunk.addObjectAt(null, TEST_X_COORD));
    }

    /**
     * Tests adding multiple objects to the same cell in the chunk.
     */
    @Test
    void testAddObjectToOccupiedCell() {
        final GameObject firstObject = new CollectibleImpl(TEST_X_COORD, TEST_CHUNK_COORD, CollectibleType.COIN);
        final GameObject secondObject = new ObstacleImpl(TEST_X_COORD, TEST_CHUNK_COORD, ObstacleType.TREE, false);

        assertTrue(chunk.addObjectAt(firstObject, TEST_X_COORD));
        assertTrue(chunk.addObjectAt(secondObject, TEST_X_COORD));

        final List<GameObject> objects = chunk.getObjects();
        assertEquals(2, objects.size());
        assertEquals(firstObject, objects.get(0));
    }

    /**
     * Tests that getObjects returns an empty list for a new chunk.
     */
    @Test
    void testGetObjectsEmptyChunk() {
        final List<GameObject> objects = chunk.getObjects();
        assertTrue(objects.isEmpty());
    }

    /**
     * Tests that getObjects returns all objects added to different cells in the chunk.
     */
    @Test
    void testGetObjectsWithMultipleObjects() {
        final GameObject obj1 = new CollectibleImpl(0, TEST_CHUNK_COORD, CollectibleType.COIN);
        final GameObject obj2 = new ObstacleImpl(2, TEST_CHUNK_COORD, ObstacleType.TREE, false);
        final GameObject obj3 = new CollectibleImpl(4, TEST_CHUNK_COORD, CollectibleType.COIN);

        chunk.addObjectAt(obj1, 0);
        chunk.addObjectAt(obj2, 2);
        chunk.addObjectAt(obj3, 4);

        final List<GameObject> objects = chunk.getObjects();
        assertEquals(3, objects.size());
        assertTrue(objects.contains(obj1));
        assertTrue(objects.contains(obj2));
        assertTrue(objects.contains(obj3));
    }

    /**
     * Tests that getCells returns all cells in the chunk with correct coordinates and no objects.
     */
    @Test
    void testGetCells() {
        final List<Cell> cells = chunk.getCells();
        assertEquals(ChunkImpl.CELLS_PER_ROW, cells.size());
        IntStream.range(0, ChunkImpl.CELLS_PER_ROW).forEach(i -> {
            final Cell cell = cells.get(i);
            assertEquals(i, cell.getX());
            assertEquals(TEST_CHUNK_COORD, cell.getY());
            assertFalse(cell.hasObject());
        });
    }

    /**
     * Tests that the list returned by getCells is unmodifiable.
     */
    @Test
    void testGetCellsImmutability() {
        final List<Cell> cells = chunk.getCells();
        assertThrows(UnsupportedOperationException.class, () -> cells.add(new CellImpl(TEST_X_COORD, TEST_CHUNK_COORD)));
    }

    /**
     * Tests retrieving a cell at a valid position.
     */
    @Test
    void testGetCellAtValidPosition() {
        final Cell cell = chunk.getCellAt(TEST_X_COORD);
        assertNotNull(cell);
        assertEquals(TEST_X_COORD, cell.getX());
        assertEquals(TEST_CHUNK_COORD, cell.getY());
    }

    /**
     * Tests that retrieving a cell at an invalid position throws an exception.
     */
    @Test
    void testGetCellAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> chunk.getCellAt(INVALID_COORD));
        assertThrows(IllegalArgumentException.class, () -> chunk.getCellAt(ChunkImpl.CELLS_PER_ROW));
    }

    /**
     * Tests the state of a cell after adding an object to it.
     */
    @Test
    void testCellStateAfterAddingObject() {
        chunk.addObjectAt(testObject, TEST_X_COORD);
        final Cell cell = chunk.getCellAt(TEST_X_COORD);
        assertTrue(cell.hasObject());
        assertEquals(1, cell.getContent().size());
        assertTrue(cell.getContent().contains(testObject));
    }

    /**
     * Tests that getType returns the correct chunk type for different chunk instances.
     */
    @Test
    void testGetType() {
        final Chunk grassChunk = new ChunkImpl(0, ChunkType.GRASS);
        final Chunk roadChunk = new ChunkImpl(1, ChunkType.ROAD);
        final Chunk railwayChunk = new ChunkImpl(2, ChunkType.RAILWAY);
        final Chunk riverChunk = new ChunkImpl(3, ChunkType.RIVER);
        assertEquals(ChunkType.GRASS, grassChunk.getType());
        assertEquals(ChunkType.ROAD, roadChunk.getType());
        assertEquals(ChunkType.RAILWAY, railwayChunk.getType());
        assertEquals(ChunkType.RIVER, riverChunk.getType());
    }

    /**
     * Tests that getPosition returns the correct position for different chunk instances.
     */
    @Test
    void testGetPosition() {
        final Chunk chunk1 = new ChunkImpl(0, ChunkType.GRASS);
        final Chunk chunk2 = new ChunkImpl(100, ChunkType.ROAD);
        assertEquals(0, chunk1.getPosition());
        assertEquals(100, chunk2.getPosition());
    }
}