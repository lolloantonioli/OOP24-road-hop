package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.view.GamePanel;

public class GameEngine implements Runnable {

    private final MapController mapController;
    private final GamePanel gamePanel;
    private boolean running = true;
    private final long period = 16; // 60fps
    private final int animationStep = 1; // pixel per frame

    public GameEngine(final MapController mapController, final GamePanel gamePanel) {
        this.mapController = mapController;
        this.gamePanel = gamePanel;
        System.out.println("GamePanel height: " + gamePanel.getHeight());
    }

    @Override
    public void run() {
        int animationOffset = 0;
        int cellHeight = 1;
        while (running) {
            long frameStart = System.currentTimeMillis();

            // Aggiorna cellHeight se la finestra viene ridimensionata
            cellHeight = gamePanel.getCellHeight();
            int speed = mapController.getScrollSpeed();

            // Più è alta la velocità, più pixel per frame
            int step = Math.max(1, animationStep * speed);

            if (animationOffset < cellHeight) {
                animationOffset += step;
                if (animationOffset > cellHeight) {
                    animationOffset = cellHeight; // Non superare l'altezza della cella
                }
                gamePanel.setAnimationOffset(animationOffset);
                gamePanel.refresh();
            } else {
                mapController.updateMap(); // avanza la posizione logica di una cella
                animationOffset = 0;
                gamePanel.setAnimationOffset(0);
                gamePanel.refresh();
            }
            this.waitForNextFrame(frameStart);
        }
    }

    private void waitForNextFrame(final long frameStart) {
        final long elapsed = System.currentTimeMillis() - frameStart;
        if (elapsed < period) {
            try {
                Thread.sleep(period - elapsed);
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        this.running = false;
    }

}
