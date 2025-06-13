package it.unibo.controller.State.api;

import it.unibo.controller.GameEngine;

public interface GameState {

    void update(GameEngine context);

    void render(GameEngine context);

    String getName();

}
