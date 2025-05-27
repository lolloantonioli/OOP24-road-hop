package it.unibo.controller.Instructions.impl;

import it.unibo.controller.MainController;
import it.unibo.controller.Instructions.api.InstructionsObserver;
import it.unibo.view.InstructionsPanel;

public class InstructionsObserverImpl implements InstructionsObserver {

    private final MainController mainController;
    private final InstructionsPanel instructionsPanel;

    public InstructionsObserverImpl(final MainController mainController, final InstructionsPanel instructionsPanel) {
        this.mainController = mainController;
        this.instructionsPanel = instructionsPanel;
    }

    public void activate() {
        instructionsPanel.setBackAction(() -> mainController.goToMenu());
    }

}
