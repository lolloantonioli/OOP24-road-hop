package it.unibo.controller.state.api;

import it.unibo.controller.GameEngine;
import it.unibo.controller.util.StateName;

/**
 * Represents a game state in the game engine.
 * Each game state can update and render itself based on the game engine context.
 */
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
