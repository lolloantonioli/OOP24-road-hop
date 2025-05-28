package it.unibo.controller.Menu.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.controller.MainController;
import it.unibo.controller.Menu.api.MenuObserver;
import it.unibo.view.MenuPanel;

public class MenuObserverImpl implements MenuObserver {

    private final MainController mainController;
    private final MenuPanel menuPanel;

    private static final String MSG_CONTROLLER = "MainController cannot be null";
    private static final String MSG_PANEL = "MenuPanel cannot be null";

    public MenuObserverImpl(final MainController mainController, final MenuPanel menuPanel) {
        this.mainController = checkNotNull(mainController, MSG_CONTROLLER);
        this.menuPanel = checkNotNull(menuPanel, MSG_PANEL);
    }

    public void activate() {
        menuPanel.setPlayAction(() -> mainController.goToGame());
        menuPanel.setSettingsAction(() -> mainController.goToInstructions());
        menuPanel.setShopAction(() -> mainController.goToShop());
    }

}
