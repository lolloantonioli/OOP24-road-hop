package it.unibo.controller.Player.impl;

import it.unibo.controller.MainController;
import it.unibo.controller.Player.api.DeathObserver;

public class DeathObserverImpl implements DeathObserver {

    private final MainController mainController;

    public DeathObserverImpl (MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void endGame() {
        mainController.showGameOverPanel();
    }

}
