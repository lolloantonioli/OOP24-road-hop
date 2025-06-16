package it.unibo.controller.State.api;

import it.unibo.controller.GameEngine;
import it.unibo.controller.Util.StateName;

public interface GameState {

    /**
     * Updates the game state.
     * @param context the game engine context.
     */
    void update(GameEngine context);

    /**
     * Renders the game state.
     * @param context the game engine context.
     */
    void render(GameEngine context);

    /**
     * Gets the name of the game state.
     * @return the name of the game state.
     */
    StateName getName();

}
