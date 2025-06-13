package it.unibo.controller;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.State.api.GameState;
import it.unibo.controller.State.impl.OnGameState;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.view.GamePanel;

//aggiungere il playerController

public class GameEngine implements Runnable {

    private final GameMap gameMap;
    private final GamePanel gamePanel;
    private final MovingObstacleController obstacleController;
    private final MovingObstacleFactory obstacleFactory;
    private final MainController mainController;
    private final GameControllerImpl gameController;
    private GameState currentState;
    private boolean running = true;
    private int frameCounter = 0;

    private static final long PERIOD = 16; // 60fps
    private static final int SCROLL_TIME_MS = 1000;

    public GameEngine(final GameMap gameMap,
                      final GamePanel gamePanel,
                      final MovingObstacleController obstacleController,
                      final MovingObstacleFactory obstacleFactory,
                      final MainController mainController,
                      final GameControllerImpl gameController) {
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
            long frameStart = System.currentTimeMillis();
            processInput();
            update();
            render();
            waitForNextFrame(frameStart);
        }
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

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
            } catch (InterruptedException e) {
                this.stop();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        this.running = false;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public MainController getMainController() {
        return this.mainController;
    }

    public GameControllerImpl gameController() {
        return this.gameController;
    }

    public void showStartCountdown() {
        for (int i = 3; i > 0; i--) {
            gamePanel.showCountdown(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        gamePanel.showCountdown(0);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        gamePanel.hideCountdown();
    }

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

            int newSpeed = gameMap.getScrollSpeed();
            if (newSpeed > speed) {
                obstacleController.increaseAllObstaclesSpeed(15);
                obstacleFactory.increaseSpeedLimits(15); 
            }
            final int difficultyLevel = Math.min(3, gameMap.getScrollSpeed());
            obstacleController.generateObstacles(difficultyLevel);
        }
        gameController.updateObstacles();
        gameController.updatePlayer();
    }

}
