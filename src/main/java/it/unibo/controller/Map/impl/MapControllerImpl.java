package it.unibo.controller.Map.impl;

import java.util.List;

import it.unibo.controller.Map.api.MapController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.GameMapImpl;

public class MapControllerImpl implements MapController {

    private final GameMap mapModel;

    private static final int INITIAL_SPEED = 1;

    public MapControllerImpl() {
        this.mapModel = new GameMapImpl(INITIAL_SPEED);
    }

    @Override
    public void updateMap() {
        mapModel.update();
    }

    @Override
    public List<Chunk> getVisibleChunks() {
        return mapModel.getVisibleChunks();
    }

    @Override
    public void increaseScrollSpeed() {
        mapModel.increaseScrollSpeed();
    }

    @Override
    public int getCurrentPosition() {
        return mapModel.getCurrentPosition();
    }

}
