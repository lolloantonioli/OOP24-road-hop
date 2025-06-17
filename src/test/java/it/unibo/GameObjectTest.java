package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.GameObjectImpl;

import java.util.List;

class GameObjectTest {

    private static final int X_COORD = 5;
    private static final int Y_COORD = 10;
    private static final int WIDTH = 3;
    private static final int SPEED = 2;

    private GameObject gameObject;

    @BeforeEach
    void setUp() {
        gameObject = new GameObjectImpl(X_COORD, Y_COORD);
    }

    @Test
    void testGameObjectCreation() {
        assertEquals(X_COORD, gameObject.getX());
        assertEquals(Y_COORD, gameObject.getY());
        assertEquals(0, gameObject.getSpeed());
        assertFalse(gameObject.isMovable());
        assertFalse(gameObject.isPlatform());
        assertEquals(1, gameObject.getWidthInCells());
    }

    @Test
    void testSettersAndGetters() {
        gameObject.setX(X_COORD);
        gameObject.setY(Y_COORD);
        gameObject.setSpeed(SPEED);
        gameObject.setMovable(true);
        gameObject.setPlatform(true);
        gameObject.setWidthInCells(WIDTH);

        assertEquals(X_COORD, gameObject.getX());
        assertEquals(Y_COORD, gameObject.getY());
        assertEquals(SPEED, gameObject.getSpeed());
        assertTrue(gameObject.isMovable());
        assertTrue(gameObject.isPlatform());
        assertEquals(WIDTH, gameObject.getWidthInCells());
    }

    @Test
    void testGetXes() {
        gameObject.setWidthInCells(WIDTH);
        gameObject.setX(X_COORD);
        final List<Integer> xes = gameObject.getXes();
        assertEquals(List.of(X_COORD, X_COORD + 1, X_COORD + 2), xes);
    }

    @Test
    void testConstructorWithWidth() {
        final GameObject obj = new GameObjectImpl(X_COORD, Y_COORD, WIDTH);
        assertEquals(X_COORD, obj.getX());
        assertEquals(Y_COORD, obj.getY());
        assertEquals(WIDTH, obj.getWidthInCells());
    }

    @Test
    void testInvalidWidthThrows() {
        assertThrows(IllegalArgumentException.class, () -> new GameObjectImpl(X_COORD, Y_COORD, 0));
        assertThrows(IllegalArgumentException.class, () -> gameObject.setWidthInCells(0));
    }
}
