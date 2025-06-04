package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Obstacles.api.MovingObstacleController;

public class GameEngine implements Runnable {

    private final MapController mapController;
    private final MovingObstacleController obstacleController;
    private boolean running = true;

    private static final long PERIOD = 16; // 60fps
    private static final int ANIMATION_STEP = 1; // pixel per frame

    public GameEngine(final MapController mapController, final MovingObstacleController obstacleController) {
        this.mapController = mapController;
        this.obstacleController = obstacleController;
    }

    @Override
    public void run() {
        int animationOffset = 0;
        while (running) {
            long frameStart = System.currentTimeMillis();

            int cellHeight = mapController.getCellHeight();
            int speed = mapController.getScrollSpeed();
            int step = Math.max(1, ANIMATION_STEP * speed);
            animationOffset = animateStep(animationOffset, cellHeight, step);
                
            // Aggiorna gli ostacoli mobili ad ogni frame
            obstacleController.update();

            waitForNextFrame(frameStart);
        }
    }

    private int animateStep(int animationOffset, final int cellHeight, final int step) {
        if (animationOffset < cellHeight) {
            animationOffset = Math.min(animationOffset + step, cellHeight);
            mapController.setAnimationOffset(animationOffset);
            mapController.updateView();
        } else {
            mapController.updateMap();
            animationOffset = 0;
            mapController.setAnimationOffset(0);
            mapController.updateView();
        }

        // Genera nuovi ostacoli quando la mappa viene aggiornata
        // Puoi adattare il livello di difficoltà in base alla velocità o alla posizione
        int difficultyLevel = Math.min(3, mapController.getScrollSpeed());
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

}
