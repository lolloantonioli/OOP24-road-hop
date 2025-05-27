package it.unibo.controller;

import it.unibo.controller.Menu.api.MenuObserver;
import it.unibo.controller.Menu.impl.MenuObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final MenuObserver menuObserver;

    public MainControllerImpl() {
        this.gameFrame = new GameFrame();
        this.menuObserver = new MenuObserverImpl(this, gameFrame.getMenuPanel());
    }

    public void launch() {
        gameFrame.show(CardName.MENU);
        menuObserver.activate();
    }

    public void goToGame() {
        gameFrame.show(CardName.GAME);
    }

    public void goToSettings() {
        gameFrame.show(CardName.SETTINGS);
    }

    public void goToShop() {
        gameFrame.show(CardName.SHOP);
    }

}
