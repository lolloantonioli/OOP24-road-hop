package it.unibo.controller.Shop.impl;

import it.unibo.controller.MainController;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.view.ShopView;

public class ShopObserverImpl implements ShopObserver{


    private final MainController mainController;
    private final ShopView shopPanel;


    public ShopObserverImpl(final MainController mainController, final ShopView shopPanel) {
        this.mainController = mainController;
        this.shopPanel = shopPanel;
    }

    @Override
    public void activate() {
        shopPanel.setOnBackToMainMenu(() -> mainController.goToMenu());

        shopPanel.setOnSkinPurchase((skinName, price) -> {
            
        });

        

    }
    
}
