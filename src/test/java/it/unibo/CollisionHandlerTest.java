package it.unibo;

import it.unibo.model.Collision.impl.CollisionHandlerImpl;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.CollectibleImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.impl.MovementValidatorImpl;
import it.unibo.model.Player.impl.PlayerImpl;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.impl.SkinImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Set;

public class CollisionHandlerTest {

    private Player player;
    private GameMap map;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl(2, 6, new SkinImpl("id", "name", 10, false, Color.CYAN));
        
    }

    @Test
    void testHandleCollectibleCoin() {
        Collectible coin = new CollectibleImpl(2, 6, CollectibleType.COIN);

        CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(1, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleCollectibleSecondLife() {
        Collectible secondLife = new CollectibleImpl(2, 6, CollectibleType.SECOND_LIFE);

        CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, secondLife);

        assertTrue(player.hasSecondLife());
        assertTrue(secondLife.isCollected());
    }

    @Test
    void testHandleCollectibleAlreadyCollected() {
        Collectible coin = new CollectibleImpl(2, 6, CollectibleType.COIN);
        coin.collect(); // già raccolto

        CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleCollectibleCollision(player, coin);

        assertEquals(0, player.getCollectedCoins());
        assertTrue(coin.isCollected());
    }

    @Test
    void testHandleFatalCollisionImmortal() {
    
        map = new GameMapImpl();
        CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertTrue(player.isAlive());
    }

    @Test
    void testHandleFatalCollisionMortal() {
    
        map = new GameMapImpl();
        Set<GameObject> objs = map.getAllChunks().get(3).getCellAt(2).getContent();
        objs.forEach(o -> map.getAllChunks().get(3).getCellAt(2).removeObject(o));
        player.tryMove(Direction.UP, map, new MovementValidatorImpl());
        CollisionHandlerImpl handler = new CollisionHandlerImpl();
        handler.handleFatalCollision(player);
        assertFalse(player.isAlive());
    }
}