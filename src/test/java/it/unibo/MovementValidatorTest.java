package it.unibo;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.CellImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.impl.ObstacleImpl;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Player.impl.MovementValidatorImpl;
import it.unibo.model.Player.api.MovementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovementValidatorTest {

    private MovementValidator validator;
    private GameMap map;

    @BeforeEach
    void setUp() {
        validator = new MovementValidatorImpl();
        map = new GameMapImpl();
    }

    @Test
    void testCanMoveToFreeCell() {
        Cell cell = new CellImpl(2, 2);
        assertTrue(validator.canMoveTo(map, cell));
    }

    @Test
    void testCanMoveToCellWithTreeObstacle() {
        Cell cell = new CellImpl(1, 1);
        Obstacle tree = new ObstacleImpl(1, 1, ObstacleType.TREE, false);
        map.getVisibleChunks().get(1).getCellAt(1).addObject(tree);
        assertFalse(validator.canMoveTo(map, cell));
    }

    @Test
    void testIsOutOfBoundsInside() {
        Cell cell = new CellImpl(0, 0);
        assertFalse(validator.isOutOfBounds(cell, map));
    }

    @Test
    void testIsOutOfBoundsOutside() {
        Cell cell = new CellImpl(-1, 0);
        assertTrue(validator.isOutOfBounds(cell, map));
        Cell cell2 = new CellImpl(0, 10);
        assertTrue(validator.isOutOfBounds(cell2, map));
    }
}