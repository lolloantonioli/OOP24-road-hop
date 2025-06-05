package it.unibo.controller.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.Player.api.PlayerController;
import it.unibo.model.Collision.api.CollisionDetector;
import it.unibo.model.Collision.impl.CollisionDetectorImpl;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.api.CollisionIdentifier;
import it.unibo.model.Player.api.MovementValidator;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.impl.CollisionIdentifierImpl;
import it.unibo.model.Player.impl.MovementValidatorImpl;
import it.unibo.model.Player.impl.PlayerImpl;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;

public class PlayerControllerImpl implements PlayerController {

    private final Player player;
    private final GameMap gameMap;
    private final CollisionDetector collisionDetector;
    private final CollisionIdentifier collisionIdentifier;
    private final MovementValidator movementValidator;
    
    private GameObject currentPlatform;

    public PlayerControllerImpl(final GameMap gameMap, final Skin initialSkin, final int startX, final int startY) {
        checkNotNull(gameMap, "GameMap cannot be null");
        checkNotNull(initialSkin, "Initial skin cannot be null");
        
        this.gameMap = gameMap;
        this.player = new PlayerImpl(startX, startY, initialSkin);
        this.collisionDetector = new CollisionDetectorImpl();
        this.collisionIdentifier = new CollisionIdentifierImpl();
        this.movementValidator = new MovementValidatorImpl();
        this.currentPlatform = null;
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
        }
        
        return moveSuccessful;
    }

    @Override
    public void processCollisions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processCollisions'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPlayer'");
    }

    @Override
    public void updatePlayerSkin(Skin skin) {
        player.reset();
        currentPlatform = null;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean isPlayerOnPlatform() {
        return currentPlatform != null;    
    }

}
