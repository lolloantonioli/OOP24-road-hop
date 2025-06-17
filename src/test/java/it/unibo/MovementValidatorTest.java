package it.unibo;

import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.api.Obstacle;
import it.unibo.model.map.impl.CellImpl;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.map.impl.ObstacleImpl;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.player.api.MovementValidator;
import it.unibo.model.player.impl.MovementValidatorImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

class MovementValidatorTest {

    private MovementValidator validator;
    private GameMap map;

    @BeforeEach
    void setUp() {
        validator = new MovementValidatorImpl();
        map = new GameMapImpl();
    }

    @Test
    void testCanMoveToFreeCell() {
        final Cell cell = new CellImpl(2, 2);
        final Set<GameObject> objs = map.getAllChunks().get(2).getCellAt(2).getContent();
        objs.forEach(o -> map.getAllChunks().get(2).getCellAt(2).removeObject(o));
        assertTrue(validator.canMoveTo(map, cell));
    }

    @Test
    void testCanMoveToCellWithTreeObstacle() {
        final Cell cell = new CellImpl(1, 1);
        final Obstacle tree = new ObstacleImpl(1, 1, ObstacleType.TREE, false);
        map.getVisibleChunks().get(1).getCellAt(1).addObject(tree);
        assertFalse(validator.canMoveTo(map, cell));
    }

    @Test
    void testIsOutOfBoundsInside() {
        final Cell cell = new CellImpl(0, 0);
        assertFalse(validator.isOutOfBounds(cell, map));
    }

    @Test
    void testIsOutOfBoundsOutside() {
        final Cell cell = new CellImpl(-1, 0);
        assertTrue(validator.isOutOfBounds(cell, map));
        final Cell cell2 = new CellImpl(0, 10);
        assertTrue(validator.isOutOfBounds(cell2, map));
    }
}
