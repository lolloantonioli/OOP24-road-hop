package it.unibo.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import it.unibo.view.MenuPanel;

public class MenuObserver implements Observer {

    private final MainController mainController;
    private final MenuPanel menuPanel;

    private static final String MSG_CONTROLLER = "MainController cannot be null";
    private static final String MSG_PANEL = "MenuPanel cannot be null";

    public MenuObserver(final MainController mainController, final MenuPanel menuPanel) {
        this.mainController = checkNotNull(mainController, MSG_CONTROLLER);
        this.menuPanel = checkNotNull(menuPanel, MSG_PANEL);
    }

    public void activate() {
        menuPanel.setPlayAction(() -> mainController.goToGame());
        menuPanel.setSettingsAction(() -> mainController.goToInstructions());
        menuPanel.setShopAction(() -> mainController.goToShop());
    }

}
