package it.unibo.controller.Map.api;

import java.util.List;

import it.unibo.model.Map.api.Chunk;

public interface MapController {

    void updateMap();

    List<Chunk> getVisibleChunks();

    void increaseScrollSpeed();

    int getCurrentPosition();

}
