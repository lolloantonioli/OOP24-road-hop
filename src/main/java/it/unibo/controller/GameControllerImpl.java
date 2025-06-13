package it.unibo.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Map.impl.MapFormatterImpl;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Player.api.PlayerController;
import it.unibo.controller.State.impl.PauseState;
import it.unibo.controller.Util.StateName;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Player.util.Direction;

/**
 * Main game controller that handles all game logic and input.
 * This is the central controller for the game state management.
 */
public class GameControllerImpl extends KeyAdapter implements GameController {
    
    private final GameEngine gameEngine;
    private final GameMap gameMap;
    private final MovingObstacleController obstacleController;
    private final MapFormatter mapAdapter;
    private final PlayerController playerController;

    public GameControllerImpl(final GameEngine gameEngine,
                              final GameMap gameMap,
                              final MovingObstacleController obstacleController,
                              final PlayerController playerController) {
        this.gameEngine = gameEngine;
        this.gameMap = gameMap;
        this.obstacleController = obstacleController;
        this.playerController = playerController;
        this.mapAdapter = new MapFormatterImpl(gameMap);
    
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        Direction movementDirection = null;
        if (gameEngine.getState().getName() == StateName.ON_GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    gameEngine.setState(new PauseState());
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    movementDirection = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    movementDirection = Direction.RIGHT;
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    movementDirection = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    movementDirection = Direction.DOWN;
                    break;
                default:
                    break;
            }
            if (movementDirection != null) {
                playerController.movePlayer(movementDirection);
            }
        }
    }

    /**
     * Updates the game logic. Called by GameEngine.
     */
    public void updateMap() {
        gameMap.update();
    }

    public void updateObstacles() {
        obstacleController.update();
    }

    @Override
    public void updatePlayer() {
        playerController.update();
    }

    /**
     * Gets the game map.
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Gets the obstacle controller.
     */
    public MovingObstacleController getObstacleController() {
        return obstacleController;
    }

    @Override
    public PlayerController getPlayerController() {
        return playerController;
    }

    public MapFormatter getMapFormatter() {
        return mapAdapter;
    }

    public int getMapWidth() {
        return ChunkImpl.CELLS_PER_ROW; // Placeholder
    }

    public int getMapHeight() {
        return GameMapImpl.CHUNKS_NUMBER; // Placeholder
    }
}