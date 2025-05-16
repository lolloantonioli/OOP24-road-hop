package it.unibo.controller.Shop.api;

import java.util.List;

import it.unibo.model.Shop.api.Skin;

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
     * Gets the list of available skins.
     * @return A List of available skins
     */
    public List<Skin> getAvailableSkins();

    /**
     * Gets the currently selected skin.
     * @return The selected skin 
     */
    public Skin getSelectedSkin();

    /**
     * Gets a skin by its ID.
     * @param skinId The ID of the skin to get
     * @return The skin with the specified ID, or null if not found
     */
    public Skin getSkinById(String id);

    /**
     * Returns to the main menu.
     */
    public void returnToMainMenu();
    
}
