package it.unibo.controller.player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import it.unibo.controller.player.api.DeathObserver;
import it.unibo.controller.player.api.PlatformMovementObserver;
import it.unibo.controller.player.api.PlayerController;
import it.unibo.model.collision.api.CollisionDetector;
import it.unibo.model.collision.api.CollisionHandler;
import it.unibo.model.collision.api.CollisionIdentifier;
import it.unibo.model.collision.impl.CollisionDetectorImpl;
import it.unibo.model.collision.impl.CollisionHandlerImpl;
import it.unibo.model.collision.impl.CollisionIdentifierImpl;
import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.Collectible;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.util.ChunkType;
import it.unibo.model.obstacles.impl.MovingObstacles;
import it.unibo.model.player.api.MovementValidator;
import it.unibo.model.player.api.Player;
import it.unibo.model.player.impl.MovementValidatorImpl;
import it.unibo.model.player.impl.PlayerImpl;
import it.unibo.model.player.util.Direction;
import it.unibo.model.shop.api.Skin;

/**
 * Implementation of the PlayerController interface.
 * Manages player movement, collision detection, and interactions with the game map.
 */
public final class PlayerControllerImpl implements PlayerController {

    private final Player player;
    private final GameMap gameMap;
    private final CollisionDetector collisionDetector;
    private final CollisionIdentifier collisionIdentifier;
    private final MovementValidator movementValidator;
    private final CollisionHandler collisionHandler;
    private final PlatformMovementObserver platformObserver;

    private MovingObstacles currentPlatform;

    /**
     * Constructor for PlayerControllerImpl.
     * @param gameMap the game map where the player operates
     * @param initialSkin the initial skin of the player
     * @param startX the starting X coordinate of the player
     * @param startY the starting Y coordinate of the player
     */
    public PlayerControllerImpl(final GameMap gameMap, final Skin initialSkin, final int startX, final int startY) {
        checkNotNull(gameMap, "GameMap cannot be null");
        checkNotNull(initialSkin, "Initial skin cannot be null");
        this.gameMap = gameMap;
        this.player = new PlayerImpl(startX, startY, initialSkin);
        this.collisionDetector = new CollisionDetectorImpl();
        this.collisionIdentifier = new CollisionIdentifierImpl();
        this.movementValidator = new MovementValidatorImpl();
        this.collisionHandler = new CollisionHandlerImpl();
        this.platformObserver = new PlatformMovementObserverImpl(player);
    }

    @Override
    public boolean movePlayer(final Direction direction) {
        checkNotNull(direction, "Direction cannot be null");

        if (!player.isAlive()) {
            return false;
        }

        final boolean moveSuccessful = player.tryMove(direction, gameMap, movementValidator);

        if (moveSuccessful) {
            processCollisions();
            if (isPlayerDrowned()) {
                killPlayer();
            }
        }

        return moveSuccessful;
    }

    /**
     * Checks if the player is currently drowned.
     * A player is considered drowned if they are in a river chunk and not on a platform.
     * @return true if the player is drowned, false otherwise
     */
    private boolean isPlayerDrowned() {
        return gameMap.getVisibleChunks().stream()
            .filter(chunk -> chunk.getCells().get(0).getY() == player.getCurrentCell().getY())
            .findFirst()
            .get()
            .getType().equals(ChunkType.RIVER) && !isPlayerOnPlatform();
    }

    /**
     * Kills the player by invoking the die method on the player object.
     */
    private void killPlayer() {
        player.die();
    }

    @Override
    public void processCollisions() {
        if (!player.isAlive()) {
            return;
        }

        final List<GameObject> collidedObjects = collisionDetector.getCollidedObjects(player, gameMap);

        collisionIdentifier.checkError(collidedObjects);

        MovingObstacles newPlatform = null;

        for (final GameObject obj : collidedObjects) {
            if (collisionIdentifier.isOnPlatform(obj)) {
                newPlatform = (MovingObstacles) obj;
            }

            if (collisionIdentifier.isFatalCollision(obj)) {
                collisionHandler.handleFatalCollision(player);
            }

            if (collisionIdentifier.isCollectibleCollision(obj)) {
                collisionHandler.handleCollectibleCollision(player, (Collectible) obj);
            }
        }

        setNewPlatform(newPlatform);
    }

    @Override
    public Cell getPlayerPosition() {
        return player.getCurrentCell();
    }

    @Override
    public void resetPlayer() {
        player.reset();
        setNewPlatform(null);
    }

    @Override
    public void updatePlayerSkin(final Skin skin) {
        player.setSkin(skin);
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public int getPlayerScore() {
        return player.getScore();
    }

    @Override
    public int getCollectedCoins() {
        return player.getCollectedCoins();
    }

    @Override
    public void update() {
        if (player.isAlive()) {

            if (!player.isInvincible()) {
                processCollisions();
            }

            player.setOutOfBounds(movementValidator.isOutOfBounds(player.getCurrentCell(), gameMap));
            if (player.isOutOfBounds()) {
                killPlayer();
            }
        }
    }

    @Override
    public boolean isPlayerOnPlatform() {
        return currentPlatform != null;
    }

    private void setNewPlatform(final MovingObstacles newPlatform) {
        if (currentPlatform != null) {
            currentPlatform.removeObserver(platformObserver);
        }

        if (newPlatform != null) {
            newPlatform.addObserver(platformObserver);
        }

        currentPlatform = newPlatform;
    }

    @Override
    public boolean hasPlayerSecondLife() {
        return player.hasSecondLife();
    }

    @Override
    public void addDeathObserver(final DeathObserver observer) {
        player.addObserver(observer);
    }

    @Override
    public void removeDeathObserver(final DeathObserver observer) {
        player.removeObserver(observer);
    }

    @Override
    public Player getPlayer() {
        return new PlayerImpl(player);
    }

}
