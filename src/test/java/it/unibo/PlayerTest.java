package it.unibo;

import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.map.impl.ObstacleImpl;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.player.api.MovementValidator;
import it.unibo.model.player.impl.MovementValidatorImpl;
import it.unibo.model.player.impl.PlayerImpl;
import it.unibo.model.player.util.Direction;
import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.SkinImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

public class PlayerTest {

    private PlayerImpl player;
    private GameMap map;
    private MovementValidator validator;
    private Skin skin = new SkinImpl("idskin", "name", 10, true, Color.GREEN);

    @BeforeEach
    void setUp() {
        player = new PlayerImpl(2, 2, skin);
        map = new GameMapImpl();
        validator = new MovementValidatorImpl();
    }

    @Test
    void testInitialState() {
        assertEquals(0, player.getScore());
        assertEquals(0, player.getCollectedCoins());
        assertTrue(player.isAlive());
        assertTrue(player.isInvincible());
        assertFalse(player.isOutOfBounds());
        assertEquals(skin , player.getCurrentSkin());
    }

    @Test
    void testFailedMoveAndScore() {
        map.getAllChunks().get(3).getCellAt(2).addObject(new ObstacleImpl(2, 3, ObstacleType.TREE, false));
        boolean moved = player.tryMove(Direction.UP, map, validator);

        assertFalse(moved);
        assertEquals(0, player.getScore());
        assertTrue(player.isInvincible());
    }

    @Test
    void testSuccessfulMoveAndScore() {
        Set<GameObject> objs = map.getAllChunks().get(3).getCellAt(2).getContent();
        objs.forEach(o -> map.getAllChunks().get(3).getCellAt(2).removeObject(o));
        boolean moved = player.tryMove(Direction.UP, map, validator);
        assertTrue(moved);
        assertEquals(1, player.getScore());
        assertFalse(player.isInvincible());
    }

    @Test
    void testCollectCoin() {
        player.collectACoin();
        player.collectACoin();
        assertEquals(2, player.getCollectedCoins());
    }

    @Test
    void testDieAndReset() {
        player.setOutOfBounds(true);
        player.die();
        assertFalse(player.isAlive());
        player.reset();
        assertTrue(player.isAlive());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getCollectedCoins());
    }

    @Test
    void testSecondLife() {
        Set<GameObject> objs = map.getAllChunks().get(3).getCellAt(2).getContent();
        objs.forEach(o -> map.getAllChunks().get(3).getCellAt(2).removeObject(o));
        player.tryMove(Direction.UP, map, validator);
        player.grantSecondLife();
        assertTrue(player.hasSecondLife());
        player.die();
        assertTrue(player.isAlive());
        assertFalse(player.hasSecondLife());
        assertTrue(player.isInvincible());
    }

    @Test
    void testSetSkin() {
        Skin newSkin = new SkinImpl("idskin2", "name2", 105, true, Color.BLACK);
        player.setSkin(newSkin);
        assertEquals(newSkin, player.getCurrentSkin());
    }
}