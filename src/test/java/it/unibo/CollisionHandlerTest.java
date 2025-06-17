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

    private Player player;
    private GameMap map;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl(2, 6, new SkinImpl("id", "name", 10, false, Color.CYAN));
    }

    @Test
    void testHandleCollectibleCoin() {
        final Collectible coin = new CollectibleImpl(2, 6, CollectibleType.COIN);

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(1, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleCollectibleSecondLife() {
        final Collectible secondLife = new CollectibleImpl(2, 6, CollectibleType.SECOND_LIFE);

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, secondLife);

        assertTrue(player.hasSecondLife());
        assertTrue(secondLife.isCollected());
    }

    @Test
    void testHandleCollectibleAlreadyCollected() {
        final Collectible coin = new CollectibleImpl(2, 6, CollectibleType.COIN);
        coin.collect(); // già raccolto

        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(0, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleFatalCollisionImmortal() {
        map = new GameMapImpl();
        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertTrue(player.isAlive());
    }

    @Test
    void testHandleFatalCollisionMortal() {
        map = new GameMapImpl();
        final Set<GameObject> objs = map.getAllChunks().get(3).getCellAt(2).getContent();
        objs.forEach(o -> map.getAllChunks().get(3).getCellAt(2).removeObject(o));
        player.tryMove(Direction.UP, map, new MovementValidatorImpl());
        final CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertFalse(player.isAlive());
    }
}
