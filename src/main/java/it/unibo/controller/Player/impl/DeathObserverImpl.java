package it.unibo.controller.Player.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.MainController;
import it.unibo.controller.Player.api.DeathObserver;

/**
 * Implementation of the DeathObserver interface.
 * This class defines the action to take when the player dies, which is to end the game.
 */
public final class DeathObserverImpl implements DeathObserver {

    private final MainController mainController;

    /**
     * Constructor for DeathObserverImpl.
     * @param mainController the main controller to interact with when the player dies
     */
    public DeathObserverImpl(final MainController mainController) {
        checkNotNull(mainController, "mainController cannot be null");
        this.mainController = mainController;
    }

    @Override
    public void endGame() {
        mainController.showGameOverPanel();
    }

}
