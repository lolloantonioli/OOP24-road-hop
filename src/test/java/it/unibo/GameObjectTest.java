package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.GameObjectImpl;

class GameObjectTest {

    private GameObject gameObject;

    private static final int X_COORD = 5;
    private static final int Y_COORD = 10;
    private static final int INVALID_COORD = -1;

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
    }

    @Test
    void testInvalidGameObjectCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameObjectImpl(INVALID_COORD, Y_COORD);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new GameObjectImpl(X_COORD, INVALID_COORD);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new GameObjectImpl(INVALID_COORD, INVALID_COORD);
        });
    }

}