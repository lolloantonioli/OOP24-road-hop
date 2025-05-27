package it.unibo.controller.Menu.impl;

import it.unibo.controller.MainController;
import it.unibo.controller.Menu.api.MenuObserver;
import it.unibo.view.MenuPanel;

public class MenuObserverImpl implements MenuObserver{

    private final MainController mainController;
    private final MenuPanel menuPanel;

    public MenuObserverImpl(final MainController mainController, final MenuPanel menuPanel) {
        this.mainController = mainController;
        this.menuPanel = menuPanel;
    }

    public void activate() {
        menuPanel.setPlayAction(() -> mainController.goToGame());
        menuPanel.setSettingsAction(() -> mainController.goToInstructions());
        menuPanel.setShopAction(() -> mainController.goToShop());
    }

}
