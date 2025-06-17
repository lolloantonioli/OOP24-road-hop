package it.unibo.model.shop.api;

import java.util.List;
/**
 * ShopModel interface that defines the methods for managing a shop in a game.
 * It includes methods to retrieve skins, purchase skins, select skins, and manage coins.
 */
public interface ShopModel {

    /**
     * Returns the list of all available skins.
     * @return a list of all skins in the shop
     */
    List<Skin> getAllSkins();

    /**
     * Gets the skin by its ID.
     * @param id the ID of the skin
     * @return the skin with the specified ID, or null if not found
     */
    Skin getSkinById(String id);

    /**
     * Says if the skin is available in the shop.
     * @param id the ID of the skin
     * @return true if the skin is available, false otherwise
     */
    boolean canPurchaseSkin(String id);

    /**
     * Purchases a skin by its ID.
     * @param id the ID of the skin to purchase
     */
    void purchaseSkin(String id);

    /**
     * Selects a skin by its ID.
     * @param id the ID of the skin to select
     */
    void selectSkin(String id);

    /**
     * Gives the selected skin.
     * @return the currently selected skin, or null if no skin is selected
     */
    Skin getSelectedSkin();

    /**
     * Returns the coins available to the player.
     * @return the number of coins
     */
    int getCoins();

    /**
     * Adds coins to the shop's coin balance and saves the value.
     * @param amount the amount of coins to add
     */
    void addCoins(int amount);

    /**
     * Returns the max score saved.
     * @return the max score
     */
    int getMaxScore();

    /**
     * Updates the max score if the new score is higher and saves the data.
     * @param score the new score to check
     */
    void updateMaxScore(int score);
}
