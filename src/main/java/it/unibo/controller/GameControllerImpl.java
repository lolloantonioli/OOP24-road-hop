package it.unibo.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Map.impl.MapFormatterImpl;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.State.impl.PauseState;
import it.unibo.controller.Util.StateName;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Player.api.Player;

/**
 * Main game controller that handles all game logic and input.
 * This is the central controller for the game state management.
 */
public class GameControllerImpl extends KeyAdapter implements GameController {
    
    private final GameMap gameMap;
    private final MovingObstacleController obstacleController;
    private final MapFormatter mapAdapter;
    private final MainController mainController;

    public GameControllerImpl(final GameMap gameMap,
                              final MovingObstacleController obstacleController,
                              final MainController mainController) {
        this.gameMap = gameMap;
        this.obstacleController = obstacleController;
        this.mapAdapter = new MapFormatterImpl(gameMap);
        this.mainController = mainController;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (mainController.getGameEngine().get().getState().getName() == StateName.ON_GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_P:
                    mainController.getGameEngine().get().setState(new PauseState());
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    movePlayerLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    movePlayerRight();
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    movePlayerUp();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    movePlayerDown();
                    break;
                default:
                    break;
            }
        }
    }

    private void movePlayerLeft() {
        int newX = player.getX() - 1;
        if (newX >= 0) {
            player.setPosition(newX, player.getY());
        }
    }

    private void movePlayerRight() {
        int newX = player.getX() + 1;
        if (newX < getMapWidth()) {
            player.setPosition(newX, player.getY());
        }
    }

    private void movePlayerUp() {
        int newY = player.getY() - 1;
        if (newY >= 0) {
            player.setPosition(player.getX(), newY);
        }
    }

    private void movePlayerDown() {
        int newY = player.getY() + 1;
        if (newY < getMapHeight()) {
            player.setPosition(player.getX(), newY);
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