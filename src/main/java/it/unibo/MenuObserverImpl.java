package it.unibo;

import it.unibo.controller.MainController;
import it.unibo.view.MenuPanel;

public class MenuObserverImpl {

    private final MainController mainController;
    private final MenuPanel menuPanel;

    public MenuObserverImpl(final MainController mainController, final MenuPanel menuPanel) {
        this.mainController = mainController;
        this.menuPanel = menuPanel;
    }

    public void activate() {
        menuPanel.setPlayAction(() -> mainController.goToGame());
        menuPanel.setSettingsAction(() -> mainController.goToSettings());
        menuPanel.setShopAction(() -> mainController.goToShop());
    }

}
