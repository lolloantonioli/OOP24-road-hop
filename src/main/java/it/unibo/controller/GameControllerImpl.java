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
    
    private final GameMap gameMap;
    private final MovingObstacleController obstacleController;
    private final MapFormatter mapAdapter;
    private final MainController mainController;
    private final PlayerController playerController;

    public GameControllerImpl(final GameMap gameMap,
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

    @Override
    public void updateMap() {
        gameMap.update();
    }

    @Override
    public void updateObstacles() {
        obstacleController.update();
    }

    @Override
    public void updatePlayer() {
        playerController.update();
    }

    @Override
    public GameMap getGameMap() {
        return gameMap;
    }

    @Override
    public MovingObstacleController getObstacleController() {
        return obstacleController;
    }

    @Override
    public PlayerController getPlayerController() {
        return playerController;
    }

    @Override
    public MapFormatter getMapFormatter() {
        return mapAdapter;
    }

    @Override
    public int getMapWidth() {
        return ChunkImpl.CELLS_PER_ROW; 
    }

    @Override
    public int getMapHeight() {
        return GameMapImpl.CHUNKS_NUMBER; 
    }
}