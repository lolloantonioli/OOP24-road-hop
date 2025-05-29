package it.unibo.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.view.InstructionsPanel;

public class InstructionsObserver implements Observer {

    private final MainController mainController;
    private final InstructionsPanel instructionsPanel;

    private static final String MSG_CONTROLLER = "MainController cannot be null";
    private static final String MSG_PANEL = "InstructionsPanel cannot be null";

    public InstructionsObserver(final MainController mainController, final InstructionsPanel instructionsPanel) {
        this.mainController = checkNotNull(mainController, MSG_CONTROLLER);
        this.instructionsPanel = checkNotNull(instructionsPanel, MSG_PANEL);
    }

    public void activate() {
        instructionsPanel.setBackAction(() -> mainController.goToMenu());
    }

}
