package it.unibo.controller.State.impl;

import it.unibo.controller.GameEngine;
import it.unibo.controller.State.api.GameState;

public class OnGameState implements GameState {

    @Override
    public void update(GameEngine context) {
        context.doGameUpdate();
    }

    @Override
    public void render(GameEngine context) {
        context.doGameRender();
    }

    @Override
    public String getName() {
        return "ON_GAME";
    }

}
