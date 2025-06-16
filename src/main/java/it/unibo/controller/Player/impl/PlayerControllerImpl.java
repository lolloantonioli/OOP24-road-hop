package it.unibo.controller.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import it.unibo.model.Player.api.CollisionHandler;
import it.unibo.controller.Player.api.PlatformMovementObserver;
import it.unibo.controller.Player.api.PlayerController;
import it.unibo.model.Collision.api.CollisionDetector;
import it.unibo.model.Collision.impl.CollisionDetectorImpl;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.model.Player.api.CollisionIdentifier;
import it.unibo.model.Player.api.MovementValidator;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.impl.CollisionHandlerImpl;
import it.unibo.model.Player.impl.CollisionIdentifierImpl;
import it.unibo.model.Player.impl.MovementValidatorImpl;
import it.unibo.model.Player.impl.PlayerImpl;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;
//import it.unibo.controller.MainController;

public class PlayerControllerImpl implements PlayerController {

    //aggiungere il fatto che posse avere un observer che controlla quando muore per terminare il gioco?

    private final Player player;
    private final GameMap gameMap;
    private final CollisionDetector collisionDetector;
    private final CollisionIdentifier collisionIdentifier;
    private final MovementValidator movementValidator;
    private final CollisionHandler collisionHandler;
    private final PlatformMovementObserver platformObserver;

    private MovingObstacles currentPlatform = null;
    
    //da aggiungere current platform all'input?
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
    public boolean movePlayer(Direction direction) {
        checkNotNull(direction, "Direction cannot be null");
        
        if (!player.isAlive()) {
            return false;
        }


        boolean moveSuccessful = player.tryMove(direction, gameMap, movementValidator);
        
        if (moveSuccessful) {
            processCollisions();
            if (isPlayerDrowned()) {
                killPlayer();
            }
        }
        
        return moveSuccessful;
    }

    private boolean isPlayerDrowned() {
        return gameMap.getVisibleChunks().stream()
            .filter(chunk -> chunk.getCells().contains(player.getCurrentCell()))
            .findFirst()
            .get()
            .getType().equals(ChunkType.RIVER) && !isPlayerOnPlatform();
    }

    private void killPlayer() {
        player.die();
    }

    @Override
    public void processCollisions() {
        if (!player.isAlive()) {
            return;
        }

        List<GameObject> collidedObjects = collisionDetector.getCollidedObjects(player, gameMap);

        MovingObstacles newPlatform = null;

        for (GameObject obj : collidedObjects) {
            try {
                if (collisionIdentifier.isOnPlatform(obj)) {
                    newPlatform = (MovingObstacles) obj;
                }
                
                if (collisionIdentifier.isFatalCollision(obj)) {
                    collisionHandler.handleFatalCollision(player);
                }
                
                if (collisionIdentifier.isCollectibleCollision(obj)) {
                    collisionHandler.handleCollectibleCollision(player, (Collectible) obj);
                }
                
            } catch (Exception e) {
                System.err.println("Error processing collision with object: " + obj.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }

        setNewPlatform(newPlatform);
    }

    @Override
    public Player getPlayer() {
        return player;
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
    public void updatePlayerSkin(Skin skin) {
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
        if (player.isAlive()){

            if (!player.isInvincible())
            processCollisions();

            //controlla se il player è ancora in una posizione valida
            player.setOutOfBounds(movementValidator.isOutOfBounds(player.getCurrentCell(), gameMap));
            if(player.isOutOfBounds()) {
                killPlayer();
            }
        }
    }

    @Override
    public boolean isPlayerOnPlatform() {
        return currentPlatform != null;    
    }

    private void setNewPlatform(MovingObstacles newPlatform) {
        if (currentPlatform != null) {
            currentPlatform.removeObserver(platformObserver);
        }

        if (newPlatform != null) {
            newPlatform.addObserver(platformObserver);
        }

        currentPlatform = newPlatform;
    }

}
