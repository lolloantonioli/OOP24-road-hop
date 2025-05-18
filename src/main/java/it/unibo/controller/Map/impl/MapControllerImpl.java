package it.unibo.controller.Map.impl;

import java.util.List;

import it.unibo.controller.Map.api.MapController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;

public class MapControllerImpl implements MapController {

    private final GameMap mapModel;

    public MapControllerImpl(final GameMap mapModel) {
        this.mapModel = mapModel;
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
