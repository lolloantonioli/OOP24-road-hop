package it.unibo.controller.State.impl;

import it.unibo.controller.GameEngine;
import it.unibo.controller.State.api.GameState;
import it.unibo.controller.Util.StateName;

/**
 * PauseState represents the state of the game when it is paused.
 * It shows a pause panel and allows the player to resume or exit the game.
 */
public final class PauseState implements GameState {

    private boolean panelShown;

    @Override
    public void update(final GameEngine context) {
    }

    @Override
    public void render(final GameEngine context) {
        if (!panelShown) {
            context.getMainController().showPausePanel(
                () -> {
                    context.getMainController().hidePausePanel();
                    context.getGamePanel().requestFocusInWindow();
                    new Thread(() -> {
                        context.showStartCountdown();
                        context.setState(new OnGameState());
                    }).start();
                },
                () -> {
                    context.getMainController().hidePausePanel();
                    context.stop();
                    context.getMainController().goToMenu();
                }
            );
            panelShown = true;
        }
    }

    @Override
    public StateName getName() {
        return StateName.PAUSE;
    }

}
