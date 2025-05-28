package it.unibo.controller.Shop.impl;

import java.util.List;

import it.unibo.controller.MainController;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.api.Skin;
import it.unibo.view.ShopView;

public class ShopObserverImpl implements ShopObserver{


    private final MainController mainController;
    private final ShopView shopPanel;
    private final ShopModel shopModel;


    public ShopObserverImpl(final MainController mainController, final ShopView shopPanel, final ShopModel shopModel) {
        this.mainController = mainController;
        this.shopPanel = shopPanel;
        this.shopModel = shopModel;
    }


    @Override
    public void activate() {
        shopPanel.setOnBackToMainMenu(() -> mainController.goToMenu());

        shopPanel.setOnSkinPurchase((skinId, price) -> {
            shopModel.purchaseSkin(skinId);
            updateView();
        });

        shopPanel.setOnSkinSelected(skinId -> {
            shopModel.selectSkin(skinId);
            updateView();
        });

        // Mostra subito le skin quando si entra nel negozio
        updateView();
    }


    private void updateView() {
        shopPanel.setCoins(shopModel.getCoins());
        
        List<Skin> skins = shopModel.getAllSkins();
        shopPanel.setSkins(skins);
    }
    
}
