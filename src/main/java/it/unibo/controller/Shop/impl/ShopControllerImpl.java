package it.unibo.controller.Shop.impl;



import com.google.common.math.Quantiles.Scale;

import it.unibo.controller.Shop.api.ShopController;

import it.unibo.view.ScaleManager;
import it.unibo.view.ShopView;
import it.unibo.view.Shop.api.ShopView;
import it.unibo.view.Shop.api.SkinManager;

public class ShopControllerImpl implements ShopController {
    private int playerCoins;
    private final SkinManager skinManager;
    private final ShopView shopView;



    public ShopControllerImpl(SkinManager skinManager, ScaleManager scaleManager) {
        this.skinManager = skinManager;
        this.playerCoins = 0;
        this.shopView = new ShopView(skinManager, scaleManager);
    }
    
    @Override
    public void setPlayerCoins(int coins) {
        this.playerCoins = coins;
        shopView.setCoins(coins);
    }

    @Override
    public int getPlayerCoins() {
        return playerCoins;
    }

    @Override
    public void buySkin(String skinId) {
        if(skinManager.buySkin(skinId, playerCoins)) {
            playerCoins -= skinManager.getAvailableSkins().stream()
                .filter(s -> s.getId().equals(skinId))
                .findFirst()
                .orElseThrow()
                .getPrice();
            shopView.setCoins(playerCoins);
        }
    }

    @Override
    public void selectSkin(String skinId) {
        skinManager.selectSkin(skinId);
    }

    @Override
    public ShopView getView() {
        return shopView;
    }

    
    
}
