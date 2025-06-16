package it.unibo.controller.State.impl;

import it.unibo.controller.GameEngine;
import it.unibo.controller.State.api.GameState;
import it.unibo.controller.Util.StateName;

public class OnGameState implements GameState {

    @Override
    public void update(final GameEngine context) {
        context.doGameUpdate();
    }

    @Override
    public void render(final GameEngine context) {
        context.getGamePanel().refresh();
    }

    @Override
    public StateName getName() {
        return StateName.ON_GAME;
    }

}
