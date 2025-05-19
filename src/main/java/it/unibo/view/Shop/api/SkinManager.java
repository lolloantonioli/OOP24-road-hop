package it.unibo.view.Shop.api;

import java.util.List;

import it.unibo.model.Shop.api.Skin;

public interface SkinManager {
    
    /**
     * Loads the available skins and their unlocked status.
     */
    void loadSkins();

    /**
     * Loads the unlocked status of the skins from preferences.
     */
    void loadUnlockedStatus();

    /**
     * buys a skin if the player has enough coins.
     * @param skinId the id of the skin to buy
     * @param coins the number of coins the player has
     */
    void buySkin(String skinId, int coins);

    /**
     * Selects a skin if it is unlocked.
     * @param skinId the id of the skin to select
     */
    void selectSkin(String skinId);

    /**
     * Gets the currently selected skin.
     * @return the currently selected skin
     */
    Skin getCurrentSkin();

    /**
     * Gets the list of available skins.
     * @return the list of available skins
     */
    List<Skin> getAvailableSkins();
}
