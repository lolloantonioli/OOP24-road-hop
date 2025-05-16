package it.unibo.controller.Shop.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.controller.GameStateManager;
import it.unibo.controller.Shop.api.ShopController;
import it.unibo.model.Shop.api.Skin;

public class ShopControllerImpl implements ShopController {
    private int playerCoins;
    private String selectedSkin;
    private final Map<String, Skin> availableSkins = null;
    private final GameStateManager gameStateManager = new GameStateManager();
    
    @Override
    public void setPlayerCoins(int coins) {
        this.playerCoins = coins;
    }

    @Override
    public int getPlayerCoins() {
        return playerCoins;
    }

    @Override
    public void buySkin(String skinId) {
        if(!availableSkins.containsKey(skinId)) {
            return;
        }
        Skin skin = availableSkins.get(skinId);

        //Check if the skin is already owned or if the player has enough coins
        if (skin.isUnlocked() || playerCoins < skin.getPrice()) {
            return;
        }

        //Buy the skin
        playerCoins -= skin.getPrice();
        skin.setUnlocked(true);
    }

    @Override
    public void selectSkin(String skinId) {
        if(availableSkins.containsKey(skinId) && availableSkins.get(skinId).isUnlocked())
        this.selectedSkin = skinId;
    }

    @Override
    public List<Skin> getAvailableSkins() {
        return new ArrayList<>(availableSkins.values());       
    }

    @Override
    public Skin getSelectedSkin() {
        return availableSkins.get(selectedSkin);
    }

    @Override
    public Skin getSkinById(String id) {
        return availableSkins.get(id);  
    }

    @Override
    public void returnToMainMenu() {
        gameStateManager.returnToMenu();
    }

    
    
}
