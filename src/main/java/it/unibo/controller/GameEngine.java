package it.unibo.controller;

import it.unibo.model.Map.api.GameMap;

public class GameEngine implements Runnable {

    private final GameMap map;
    private final Runnable onUpdate;
    private boolean running = true;
    private final long period = 20;

    public GameEngine(final GameMap map, final Runnable onUpdate) {
        this.map = map;
        this.onUpdate = onUpdate;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (running) {
            final long currentStartTime = System.currentTimeMillis();
            //final long elapsedTime = currentStartTime - startTime;
            map.update();
            onUpdate.run();
            waitForNextFrame(currentStartTime);
            startTime = currentStartTime;
        }
    }

    private void waitForNextFrame(final long currentStartTime) {
        final long frameTime = System.currentTimeMillis() - currentStartTime;
        if (frameTime < period) {
            try {
                Thread.sleep(period - frameTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }

}
