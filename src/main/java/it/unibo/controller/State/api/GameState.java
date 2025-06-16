package it.unibo.controller.State.api;

import it.unibo.controller.GameEngine;
import it.unibo.controller.Util.StateName;

public interface GameState {

    void update(GameEngine context);

    void render(GameEngine context);

    StateName getName();

}
