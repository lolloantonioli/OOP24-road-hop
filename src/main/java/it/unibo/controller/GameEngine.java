package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;

public class GameEngine implements Runnable {

    private final MapController mapController;
    private final Runnable onUpdate;
    private boolean running = true;
    private final long period = 20;

    public GameEngine(final MapController mapController, final Runnable onUpdate) {
        this.mapController = mapController;
        this.onUpdate = onUpdate;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (running) {
            final long currentStartTime = System.currentTimeMillis();
            //final long elapsedTime = currentStartTime - startTime;
            mapController.updateMap();
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
