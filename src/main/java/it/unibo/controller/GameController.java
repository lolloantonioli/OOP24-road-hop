package it.unibo.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.unibo.controller.map.api.MapFormatter;
import it.unibo.controller.map.impl.MapFormatterImpl;
import it.unibo.controller.obstacles.api.MovingObstacleController;
import it.unibo.controller.player.api.PlayerController;
import it.unibo.controller.state.impl.PauseState;
import it.unibo.controller.util.StateName;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.impl.ChunkImpl;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.player.util.Direction;

/**
 * Main game controller that handles all game logic and input.
 * This is the central controller for the game state management.
 */
public final class GameController extends KeyAdapter {
    private final GameMap gameMap;
    private final MovingObstacleController obstacleController;
    private final MapFormatter mapAdapter;
    private final MainController mainController;
    private final PlayerController playerController;

    /**
     * Constructor for GameController.
     *
     * @param gameMap the game map instance
     * @param obstacleController the controller for moving obstacles
     * @param mainController the main controller for game state management
     * @param playerController the controller for player actions and state
     */
    public GameController(final GameMap gameMap,
                              final MovingObstacleController obstacleController,
                              final MainController mainController,
                              final PlayerController playerController) {
        this.gameMap = gameMap;
        this.obstacleController = obstacleController;
        this.playerController = playerController;
        this.mapAdapter = new MapFormatterImpl(gameMap);
        this.mainController = mainController;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        Direction movementDirection = null;
        if (mainController.getGameEngine().get().getState().getName() == StateName.ON_GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P -> mainController.getGameEngine().get().setState(new PauseState());
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> movementDirection = Direction.LEFT;
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> movementDirection = Direction.RIGHT;
                case KeyEvent.VK_UP, KeyEvent.VK_W -> movementDirection = Direction.UP;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> movementDirection = Direction.DOWN;
                default -> {
                }
            }
            if (movementDirection != null) {
                playerController.movePlayer(movementDirection);
            }
        }
    }

    /**
     * Updates the game map state (e.g., scrolling, chunk management).
     */
    public void updateMap() {
        gameMap.update();
    }

    /**
     * Updates the state of all moving obstacles.
     */
    public void updateObstacles() {
        obstacleController.update();
    }

    /**
     * Updates the player state (e.g., position, status).
     */
    public void updatePlayer() {
        playerController.update();
    }

    /**
     * Returns the current game map.
     * @return the game map
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Returns the controller for moving obstacles.
     * @return the moving obstacle controller
     */
    public MovingObstacleController getObstacleController() {
        return obstacleController;
    }

    /**
     * Returns the player controller.
     * @return the player controller
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Returns the map formatter/adapter for the view.
     * @return the map formatter
     */
    public MapFormatter getMapFormatter() {
        return mapAdapter;
    }

    /**
     * Returns the width of the map (number of cells per row).
     * @return the map width
     */
    public int getMapWidth() {
        return ChunkImpl.CELLS_PER_ROW;
    }

    /**
     * Returns the height of the map (number of chunks).
     * @return the map height
     */
    public int getMapHeight() {
        return GameMapImpl.CHUNKS_NUMBER;
    }
}
