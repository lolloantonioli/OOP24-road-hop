package it.unibo.controller;

import it.unibo.MenuObserverImpl;
import it.unibo.view.GameFrame;
import it.unibo.view.GameFrameImpl;

public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final MenuObserverImpl menuObserver;

    public MainControllerImpl() {
        this.gameFrame = new GameFrameImpl();
        this.menuObserver = new MenuObserverImpl(this, gameFrame.getMenuPanel());
    }

    public void launch() {
        gameFrame.show("menu");
        menuObserver.activate();
    }

    public void goToGame() {
        gameFrame.show("game");
    }

    public void goToSettings() {
        gameFrame.show("settings");
    }

    public void goToShop() {
        gameFrame.show("shop");
    }

}
