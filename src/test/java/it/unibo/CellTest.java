package it.unibo;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.CellImpl;
import it.unibo.model.map.impl.ChunkImpl;
import it.unibo.model.map.impl.CollectibleImpl;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.map.impl.ObstacleImpl;
import it.unibo.model.map.util.CollectibleType;
import it.unibo.model.map.util.ObstacleType;

/**
 * Test class for the {@link CellImpl} class.
 */
class CellTest {

    private static final int X_COORD = ChunkImpl.CELLS_PER_ROW - 1;
    private static final int Y_COORD = GameMapImpl.CHUNKS_NUMBER - 1;
    private Cell cell;
    private GameObject testObject;

    /**
     * Initializes a cell and a test object before each test.
     */
    @BeforeEach
    void setUp() {
        cell = new CellImpl(X_COORD, Y_COORD);
        testObject = new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN);
    }

    /**
     * Tests that a cell is created with the correct coordinates and is empty.
     */
    @Test
    void testCellCreation() {
        assertEquals(X_COORD, cell.getX());
        assertEquals(Y_COORD, cell.getY());
        assertFalse(cell.hasObject());
        assertTrue(cell.getContent().isEmpty());
    }

    /**
     * Tests adding an object to an empty cell.
     * Verifies that the object is added and the cell reports it contains objects.
     */
    @Test
    void testAddObjectToEmptyCell() {
        assertTrue(cell.addObject(testObject));
        assertTrue(cell.hasObject());
        assertEquals(1, cell.getContent().size());
        assertTrue(cell.getContent().contains(testObject));
    }

    /**
     * Tests that adding the same object twice does not duplicate it in the cell.
     */
    @Test
    void testAddSameObjectTwice() {
        assertTrue(cell.addObject(testObject));
        assertFalse(cell.addObject(testObject)); // Set: non aggiunge duplicati
        assertEquals(1, cell.getContent().size());
    }

    /**
     * Tests adding multiple different objects to the same cell.
     */
    @Test
    void testAddMultipleObjects() {
        final GameObject obj2 = new ObstacleImpl(X_COORD, Y_COORD, ObstacleType.TREE, false);
        assertTrue(cell.addObject(testObject));
        assertTrue(cell.addObject(obj2));
        assertEquals(2, cell.getContent().size());
        assertTrue(cell.getContent().contains(testObject));
        assertTrue(cell.getContent().contains(obj2));
    }

    /**
     * Tests removing objects from the cell, both when present and when not present.
     * Also verifies the cell's state after removals.
     */
    @Test
    void testRemoveObject() {
        cell.addObject(testObject);
        assertTrue(cell.hasObject());
        assertTrue(cell.removeObject(testObject));
        assertFalse(cell.hasObject());
        assertTrue(cell.getContent().isEmpty());
        assertFalse(cell.removeObject(testObject));

        final GameObject obj2 = new ObstacleImpl(X_COORD, Y_COORD, ObstacleType.TREE, false);
        cell.addObject(testObject);
        cell.addObject(obj2);
        assertTrue(cell.hasObject());
        assertTrue(cell.removeObject(testObject));
        assertTrue(cell.hasObject());
        assertEquals(1, cell.getContent().size());
        assertTrue(cell.getContent().contains(obj2));
    }

    /**
     * Tests that adding or removing a null object throws an IllegalArgumentException.
     */
    @Test
    void testNullObject() {
        assertThrows(IllegalArgumentException.class, () -> cell.addObject(null));
        assertThrows(IllegalArgumentException.class, () -> cell.removeObject(null));
    }

    /**
     * Tests that the cell's position (x and y) remains correct.
     */
    @Test
    void testCellPosition() {
        final int x = cell.getX();
        final int y = cell.getY();
        assertEquals(x, cell.getX());
        assertEquals(y, cell.getY());
    }

    /**
     * Tests that the set returned by getContent() is unmodifiable.
     */
    @Test
    void testGetContent() {
        cell.addObject(testObject);
        final Set<GameObject> content = cell.getContent();
        assertThrows(UnsupportedOperationException.class, 
                    () -> content.add(new CollectibleImpl(X_COORD, Y_COORD, CollectibleType.COIN)));
    }
}
