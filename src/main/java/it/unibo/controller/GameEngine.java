package it.unibo.controller;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.State.api.GameState;
import it.unibo.controller.State.impl.OnGameState;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.view.GamePanel;

//aggiungere il playerController

/**
 * GameEngine is the main game loop that handles the game state, updates, and rendering.
 * It runs in a separate thread and manages the game flow.
 */
public final class GameEngine implements Runnable {

    private final static long PERIOD = 16; // 60fps
    private final static int SCROLL_TIME_MS = 1000;
    private final static int WAIT_TIME = 700; // 700ms for countdown
    private final static int INCREASE_SPEED = 15; // Speed increase for obstacles

    private final GameMap gameMap;
    private final GamePanel gamePanel;
    private final MovingObstacleController obstacleController;
    private final MovingObstacleFactory obstacleFactory;
    private final MainController mainController;
    private final GameController gameController;
    private GameState currentState;
    private boolean running = true;
    private int frameCounter;

    /**
     * Constructor for GameEngine.
     * Initializes the game engine with the provided game map, game panel, obstacle controller,
     * obstacle factory, main controller, and game controller.
     *
     * @param gameMap the game map instance
     * @param gamePanel the game panel instance
     * @param obstacleController the controller for moving obstacles
     * @param obstacleFactory the factory for creating moving obstacles
     * @param mainController the main controller for managing game states
     * @param gameController the controller for handling game logic and input
     */
    public GameEngine(final GameMap gameMap,
                      final GamePanel gamePanel,
                      final MovingObstacleController obstacleController,
                      final MovingObstacleFactory obstacleFactory,
                      final MainController mainController,
                      final GameController gameController) {
        this.gameMap = gameMap;
        this.gamePanel = gamePanel;
        this.obstacleController = obstacleController;
        this.obstacleFactory = obstacleFactory;
        this.mainController = mainController;
        this.gameController = gameController;
        this.currentState = new OnGameState();
    }

    @Override
    public void run() {
        showStartCountdown();
        while (running) {
            final long frameStart = System.currentTimeMillis();
            processInput();
            update();
            render();
            waitForNextFrame(frameStart);
        }
    }

    /**
     * Sets the current game state.
     * @param state the new game state to set.
     */
    public void setState(final GameState state) {
        this.currentState = state;
    }

    /**
     * Gets the current game state.
     * @return the current game state.
     */
    public GameState getState() {
        return this.currentState;
    }

    private void processInput() {
        // Il GameController (KeyListener) chiama metodi su GameEngine per cambiare stato
    }

    private void update() {
        currentState.update(this);
    }

    private void render() {
        currentState.render(this);
    }

    private void waitForNextFrame(final long frameStart) {
        final long elapsed = System.currentTimeMillis() - frameStart;
        if (elapsed < PERIOD) {
            try {
                Thread.sleep(PERIOD - elapsed);
            } catch (final InterruptedException e) {
                this.stop();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Stops the game engine.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Returns the GamePanel associated with this game engine.
     * @return the GamePanel instance.
     */
    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    /**
     * Returns the MainController associated with this game engine.
     * @return the MainController instance.
     */
    public MainController getMainController() {
        return this.mainController;
    }

    /**
     * Returns the GameController associated with this game engine.
     * @return the GameController instance.
     */
    public GameController gameController() {
        return this.gameController;
    }

    /**
     * Shows a countdown from 3 to 0 before starting or resuming the game.
     */
    public void showStartCountdown() {
        for (int i = 3; i > 0; i--) {
            gamePanel.showCountdown(i);
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        gamePanel.showCountdown(0);
        try {
            Thread.sleep(WAIT_TIME);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        gamePanel.hideCountdown();
    }

    /**
     * Performs a game update, scrolling the map and updating obstacles and player state.
     */
    public void doGameUpdate() {
        final int cellHeight = gamePanel.getCellHeight();
        final int speed = gameMap.getScrollSpeed();
        final int scrollTime = SCROLL_TIME_MS / speed;
        final double framesPerCell = scrollTime / (double) PERIOD;
        frameCounter = frameCounter + 1;
        final int offset = (int) ((cellHeight * frameCounter) / framesPerCell);

        if (frameCounter < framesPerCell) {
            gamePanel.setAnimationOffset(offset);
        } else {
            gameController.updateMap();
            frameCounter = 0;
            gamePanel.setAnimationOffset(0);

            final int newSpeed = gameMap.getScrollSpeed();
            if (newSpeed > speed) {
                obstacleController.increaseAllObstaclesSpeed(INCREASE_SPEED);
                obstacleFactory.increaseSpeedLimits(INCREASE_SPEED); 
            }
            final int difficultyLevel = Math.min(3, gameMap.getScrollSpeed());
            obstacleController.generateObstacles(difficultyLevel);
        }
        gameController.updateObstacles();
        gameController.updatePlayer();
    }

}
