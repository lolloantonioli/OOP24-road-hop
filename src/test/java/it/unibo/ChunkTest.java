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
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

class ChunkTest {

    private Chunk chunk;
    private GameObject testObject;

    private static final int TEST_POSITION = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int INVALID_POSITION = -1;

    @BeforeEach
    void setUp() {
        chunk = new ChunkImpl(TEST_POSITION, ChunkType.GRASS);
        testObject = new CollectibleImpl(0, TEST_POSITION, CollectibleType.COIN);
    }

    @Test
    void testChunkCreation() {
        assertEquals(TEST_POSITION, chunk.getPosition());
        assertEquals(ChunkType.GRASS, chunk.getType());
        assertEquals(ChunkImpl.CELLS_PER_ROW, chunk.getCells().size());
    }

    @Test
    void testChunkCreationWithInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> new ChunkImpl(INVALID_POSITION, ChunkType.GRASS));
    }

    @Test
    void testChunkCreationWithNullType() {
        assertThrows(NullPointerException.class, () -> new ChunkImpl(TEST_POSITION, null));
    }

    @Test
    void testAddObjectAtValidPosition() {
        assertTrue(chunk.addObjectAt(testObject, TEST_POSITION));
        List<GameObject> objects = chunk.getObjects();
        assertEquals(1, objects.size());
        assertEquals(testObject, objects.get(0));
    }

    @Test
    void testAddObjectAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> chunk.addObjectAt(testObject, INVALID_POSITION));
        assertThrows(IllegalArgumentException.class, () -> chunk.addObjectAt(testObject, ChunkImpl.CELLS_PER_ROW));
    }

    @Test
    void testAddNullObject() {
        assertThrows(NullPointerException.class, () -> chunk.addObjectAt(null, TEST_POSITION));
    }

    @Test
    void testAddObjectToOccupiedCell() {
        GameObject firstObject = new CollectibleImpl(3, TEST_POSITION, CollectibleType.COIN);
        GameObject secondObject = new ObstacleImpl(3, TEST_POSITION, ObstacleType.TREE, false);
        
        assertTrue(chunk.addObjectAt(firstObject, 3));
        assertFalse(chunk.addObjectAt(secondObject, 3));
        
        List<GameObject> objects = chunk.getObjects();
        assertEquals(1, objects.size());
        assertEquals(firstObject, objects.get(0));
    }

    @Test
    void testGetObjectsEmptyChunk() {
        List<GameObject> objects = chunk.getObjects();
        assertTrue(objects.isEmpty());
    }

    @Test
    void testGetObjectsWithMultipleObjects() {
        GameObject obj1 = new CollectibleImpl(0, 5, CollectibleType.COIN);
        GameObject obj2 = new ObstacleImpl(2, 5, ObstacleType.TREE, false);
        GameObject obj3 = new CollectibleImpl(4, 5, CollectibleType.COIN);
        
        chunk.addObjectAt(obj1, 0);
        chunk.addObjectAt(obj2, 2);
        chunk.addObjectAt(obj3, 4);
        
        List<GameObject> objects = chunk.getObjects();
        assertEquals(3, objects.size());
        assertTrue(objects.contains(obj1));
        assertTrue(objects.contains(obj2));
        assertTrue(objects.contains(obj3));
    }

    @Test
    void testGetCells() {
        List<Cell> cells = chunk.getCells();
        assertEquals(ChunkImpl.CELLS_PER_ROW, cells.size());
        IntStream.range(0, ChunkImpl.CELLS_PER_ROW).forEach(i -> {
            Cell cell = cells.get(i);
            assertEquals(i, cell.getX());
            assertEquals(TEST_POSITION, cell.getY());
            assertFalse(cell.hasObject());
        });
    }

    @Test
    void testGetCellsImmutability() {
        List<Cell> cells = chunk.getCells();
        assertThrows(UnsupportedOperationException.class, () -> cells.add(new CellImpl(TEST_POSITION, TEST_POSITION)));
    }

    @Test
    void testGetCellAtValidPosition() {
        Cell cell = chunk.getCellAt(TEST_POSITION);
        assertNotNull(cell);
        assertEquals(TEST_POSITION, cell.getX());
        assertEquals(TEST_POSITION, cell.getY());
    }

    @Test
    void testGetCellAtInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> chunk.getCellAt(INVALID_POSITION));
        assertThrows(IllegalArgumentException.class, () -> chunk.getCellAt(ChunkImpl.CELLS_PER_ROW));
    }

    @Test
    void testCellStateAfterAddingObject() {
        chunk.addObjectAt(testObject, TEST_POSITION);
        Cell cell = chunk.getCellAt(TEST_POSITION);
        assertTrue(cell.hasObject());
        assertTrue(cell.getContent().isPresent());
        assertEquals(testObject, cell.getContent().get());
    }

    @Test
    void testGetType() {
        Chunk grassChunk = new ChunkImpl(0, ChunkType.GRASS);
        Chunk roadChunk = new ChunkImpl(1, ChunkType.ROAD);
        Chunk railwayChunk = new ChunkImpl(2, ChunkType.RAILWAY);
        Chunk riverChunk = new ChunkImpl(3, ChunkType.RIVER);
        assertEquals(ChunkType.GRASS, grassChunk.getType());
        assertEquals(ChunkType.ROAD, roadChunk.getType());
        assertEquals(ChunkType.RAILWAY, railwayChunk.getType());
        assertEquals(ChunkType.RIVER, riverChunk.getType());
    }

    @Test
    void testGetPosition() {
        Chunk chunk1 = new ChunkImpl(0, ChunkType.GRASS);
        Chunk chunk2 = new ChunkImpl(100, ChunkType.ROAD);
        assertEquals(0, chunk1.getPosition());
        assertEquals(100, chunk2.getPosition());
    }
}