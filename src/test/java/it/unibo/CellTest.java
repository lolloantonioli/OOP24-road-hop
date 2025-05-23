package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.CollectibleImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

class CellTest {

    private Cell cell;
    private GameObject testObject;

    private static final int X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private static final int INVALID_COORD = -1;

    @BeforeEach
    void setUp() {
        cell = new CellImpl(X_COORD, Y_COORD);
        testObject = new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN);
    }

    @Test
    void testCellCreation() {
        assertEquals(X_COORD, cell.getX());
        assertEquals(Y_COORD, cell.getY());
        assertFalse(cell.hasObject());
        assertTrue(cell.getContent().isEmpty());
    }

    @Test
    void testInvalidCreation() {
        assertThrows(IllegalArgumentException.class, () -> new CellImpl(INVALID_COORD, Y_COORD));
        assertThrows(IllegalArgumentException.class, () -> new CellImpl(X_COORD, INVALID_COORD));
        assertThrows(IllegalArgumentException.class, () -> new CellImpl(INVALID_COORD, INVALID_COORD));
    }

    @Test
    void testAddObjectToEmptyCell() {
        assertTrue(cell.addObject(testObject));
        assertTrue(cell.hasObject());
        assertTrue(cell.getContent().isPresent());
        assertEquals(testObject, cell.getContent().get());
    }

    @Test
    void testAddObjectToOccupiedCell() {
        GameObject firstObject = new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN);
        GameObject secondObject = new ObstacleImpl(X_COORD, Y_COORD, ObstacleType.TREE, false);
        assertTrue(cell.addObject(firstObject));
        assertFalse(cell.addObject(secondObject));
        assertTrue(cell.hasObject());
        assertEquals(firstObject, cell.getContent().get());
    }

    @Test
    void testRemoveObjectFromOccupiedCell() {
        cell.addObject(testObject);
        assertTrue(cell.hasObject());
        cell.removeObject();
        assertFalse(cell.hasObject());
        assertTrue(cell.getContent().isEmpty());
    }

    @Test
    void testRemoveObjectFromEmptyCell() {
        assertFalse(cell.hasObject());
        cell.removeObject();
        assertFalse(cell.hasObject());
        assertTrue(cell.getContent().isEmpty());
    }

    @Test
    void testGetContent() {
        Optional<GameObject> content = cell.getContent();
        assertTrue(content.isEmpty());
        assertFalse(content.isPresent());
        cell.addObject(testObject);
        content = cell.getContent();
        assertTrue(content.isPresent());
        assertEquals(testObject, content.get());
    }

}