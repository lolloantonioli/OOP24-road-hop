package it.unibo.controller;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.view.GamePanel;

public class GameEngine implements Runnable {

    private final GameMap mapModel;
    private final GamePanel gamePanel;
    private final MovingObstacleController obstacleController;
    private boolean running = true;
    private int frameCounter = 0;
    private final MovingObstacleFactory obstacleFactory;

    private static final long PERIOD = 16; // 60fps
    private static final int SCROLL_TIME_MS = 1000;

    public GameEngine(final GameMap mapModel, final GamePanel gamePanel, final MovingObstacleController obstacleController, 
                      final MovingObstacleFactory obstacleFactory) {
        this.obstacleFactory = obstacleFactory;
        this.mapModel = mapModel;
        this.gamePanel = gamePanel;
        this.obstacleController = obstacleController;
    }

    @Override
    public void run() {
        showStartCountdown();
        while (running) {
            long frameStart = System.currentTimeMillis();
            //processInput();
            update();
            render();
            waitForNextFrame(frameStart);
        }
    }

    private void showStartCountdown() {
        for (int i = 3; i > 0; i--) {
            gamePanel.showCountdown(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        gamePanel.showCountdown(0); // "GO!"
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        gamePanel.hideCountdown();
    }

    private void update() {
        final int cellHeight = gamePanel.getCellHeight();
        final int speed = mapModel.getScrollSpeed();
        final int scrollTime = SCROLL_TIME_MS / speed;
        final double framesPerCell = scrollTime / (double) PERIOD;
        frameCounter = frameCounter + 1;
        final int offset = (int) ((cellHeight * frameCounter) / framesPerCell);

        if (frameCounter < framesPerCell) {
            gamePanel.setAnimationOffset(offset);
        } else {
            mapModel.update();
            frameCounter = 0;
            gamePanel.setAnimationOffset(0);

            // Qui aumenta la velocità degli ostacoli se è aumentata quella della mapppa
            int newSpeed = mapModel.getScrollSpeed();
            if (newSpeed > speed) {
                obstacleController.increaseAllObstaclesSpeed(10);
                obstacleFactory.increaseSpeedLimits(10); 
            }
            final int difficultyLevel = Math.min(3, mapModel.getScrollSpeed());
            obstacleController.generateObstacles(difficultyLevel);
        }
        obstacleController.update();
    }

    private void render() {
        gamePanel.refresh();
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

}
