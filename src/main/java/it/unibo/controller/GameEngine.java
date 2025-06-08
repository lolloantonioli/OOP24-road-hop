package it.unibo.controller;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.GameMap;
import it.unibo.view.GamePanel;

public class GameEngine implements Runnable {

    private final GameMap mapModel;
    private final GamePanel gamePanel;
    private final MovingObstacleController obstacleController;
    private boolean running = true;

    private static final long PERIOD = 16; // 60fps
    private static final int ANIMATION_STEP = 1; // pixel per frame

    public GameEngine(final GameMap mapModel, final GamePanel gamePanel, final MovingObstacleController obstacleController) {
        this.mapModel = mapModel;
        this.gamePanel = gamePanel;
        this.obstacleController = obstacleController;
    }

    @Override
    public void run() {
        showStartCountdown();
        int animationOffset = 0;
        while (running) {
            long frameStart = System.currentTimeMillis();

            int cellHeight = gamePanel.getCellHeight();
            int speed = mapModel.getScrollSpeed();
            int step = Math.max(1, ANIMATION_STEP * speed);
            animationOffset = animateStep(animationOffset, cellHeight, step);
                
            // Aggiorna gli ostacoli mobili ad ogni frame
            obstacleController.update();

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

    private int animateStep(int animationOffset, final int cellHeight, final int step) {
        if (animationOffset < cellHeight) {
            animationOffset = Math.min(animationOffset + step, cellHeight);
            gamePanel.setAnimationOffset(animationOffset);
            gamePanel.refresh();
        } else {
            mapModel.update();
            animationOffset = 0;
            gamePanel.setAnimationOffset(0);
            gamePanel.refresh();
        }

        // Genera nuovi ostacoli quando la mappa viene aggiornata
        // Puoi adattare il livello di difficoltà in base alla velocità o alla posizione
        int difficultyLevel = Math.min(3, mapModel.getScrollSpeed());
        obstacleController.generateObstacles(difficultyLevel);
        
        return animationOffset;
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

    public void setupView(GamePanel gamePanel) {
        gamePanel.setObstacleController(this.obstacleController);
    }

}
