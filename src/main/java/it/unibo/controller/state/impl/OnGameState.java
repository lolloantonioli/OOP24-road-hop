package it.unibo.controller.state.impl;

import it.unibo.controller.GameEngine;
import it.unibo.controller.state.api.GameState;
import it.unibo.controller.util.StateName;

/**
 * OnGameState represents the state of the game when it is actively being played.
 * It handles the game updates and rendering during gameplay.
 */
public final class OnGameState implements GameState {

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
