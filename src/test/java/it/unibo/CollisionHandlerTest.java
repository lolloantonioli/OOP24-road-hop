package it.unibo;

import java.awt.Color;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.collision.impl.CollisionHandlerImpl;
import it.unibo.model.map.api.Collectible;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.impl.CollectibleImpl;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.map.util.CollectibleType;
import it.unibo.model.player.api.Player;
import it.unibo.model.player.impl.MovementValidatorImpl;
import it.unibo.model.player.impl.PlayerImpl;
import it.unibo.model.player.util.Direction;
import it.unibo.model.shop.impl.SkinImpl;

class CollisionHandlerTest {

    private static final int PLAYER_START_X = 2;
    private static final int PLAYER_START_Y = 2;

    private Player player;
    private GameMap map;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl(PLAYER_START_X, PLAYER_START_Y, new SkinImpl("id", "name", 10, false, Color.CYAN));
        map = new GameMapImpl();
    }

    @Test
    void testHandleCollectibleCoin() {
        final Collectible coin = new CollectibleImpl(PLAYER_START_X, PLAYER_START_Y, CollectibleType.COIN);

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(1, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleCollectibleSecondLife() {
        final Collectible secondLife = new CollectibleImpl(PLAYER_START_X, PLAYER_START_Y, CollectibleType.SECOND_LIFE);

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, secondLife);

        assertTrue(player.hasSecondLife());
        assertTrue(secondLife.isCollected());
    }

    @Test
    void testHandleCollectibleAlreadyCollected() {
        final Collectible coin = new CollectibleImpl(PLAYER_START_X, PLAYER_START_Y, CollectibleType.COIN);
        coin.collect(); // già raccolto

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(0, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleFatalCollisionImmortal() {
        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertTrue(player.isAlive());
    }

    @Test
    void testHandleFatalCollisionMortal() {
        final Set<GameObject> objs = map.getAllChunks().get(PLAYER_START_Y + 1).getCellAt(PLAYER_START_X).getContent();
        objs.forEach(o -> map.getAllChunks().get(PLAYER_START_Y + 1).getCellAt(PLAYER_START_X).removeObject(o));
        final boolean move = player.tryMove(Direction.UP, map, new MovementValidatorImpl());
        assertTrue(move);
        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertFalse(player.isAlive());
    }
}
