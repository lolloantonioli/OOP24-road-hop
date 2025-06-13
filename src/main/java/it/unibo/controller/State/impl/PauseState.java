package it.unibo.controller.State.impl;

import it.unibo.controller.GameEngine;
import it.unibo.controller.State.api.GameState;

public class PauseState implements GameState {

    private boolean panelShown = false;

    @Override
    public void update(GameEngine context) {
        
    }

    @Override
    public void render(GameEngine context) {
        if (!panelShown) {
            context.getMainController().showPausePanel(
                () -> { // Riprendi
                    context.getMainController().hidePausePanel();
                    context.getGamePanel().requestFocusInWindow();
                    new Thread(() -> {
                        context.showStartCountdown();
                        context.setState(new OnGameState());
                    }).start();
                },
                () -> { // Torna al menu
                    context.getMainController().hidePausePanel();
                    context.stop(); // Ferma il game loop
                    context.getMainController().goToMenu();
                }
            );
            panelShown = true;
        }
    }

    @Override
    public String getName() {
        return "PAUSE";
    }


}
