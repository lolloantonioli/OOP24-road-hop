package it.unibo.controller.Shop.api;


import it.unibo.view.Shop.api.ShopView;

public interface ShopController {
    
    /**
     * Sets the number of coins available to the player.
     * @param coins The number of coins to set
     */
    public void setPlayerCoins(int coins);
    
    /**
     * Gets the number of coins available to the player.
     * @return The number of coins available to the player
     */
    public int getPlayerCoins();

    /**
     * Buys a skin for the player.
     * @param skinId The ID of the skin to buy
     */
    public void buySkin(String skinId);
    /**
     * Selects a skin for the player.
     * @param skinId The ID of the skin to select
     */
    public void selectSkin(String skinId);
    /**
     * Gets the view of the shop.
     * @return The shop view
     */
    public ShopView getView();
    
}
