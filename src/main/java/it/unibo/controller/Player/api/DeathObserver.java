package it.unibo.controller.Player.api;

/**
 * Interface for observing player death events.
 * Implementations should define actions to take when the player dies.
 */
public interface DeathObserver {

    /**
     * Called when the player dies.
     * Ends the game.
     */
    void endGame();

}
