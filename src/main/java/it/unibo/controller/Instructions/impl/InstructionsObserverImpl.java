package it.unibo.controller.Instructions.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.MainController;
import it.unibo.controller.Instructions.api.InstructionsObserver;
import it.unibo.view.InstructionsPanel;

public class InstructionsObserverImpl implements InstructionsObserver {

    private final MainController mainController;
    private final InstructionsPanel instructionsPanel;

    private static final String MSG_CONTROLLER = "MainController cannot be null";
    private static final String MSG_PANEL = "InstructionsPanel cannot be null";

    public InstructionsObserverImpl(final MainController mainController, final InstructionsPanel instructionsPanel) {
        this.mainController = checkNotNull(mainController, MSG_CONTROLLER);
        this.instructionsPanel = checkNotNull(instructionsPanel, MSG_PANEL);
    }

    public void activate() {
        instructionsPanel.setBackAction(() -> mainController.goToMenu());
    }

}
