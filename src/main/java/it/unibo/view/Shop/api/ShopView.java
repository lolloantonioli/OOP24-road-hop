package it.unibo.view.Shop.api;

import java.awt.Graphics;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import it.unibo.model.Shop.api.Skin;

public interface ShopView {
    
    /**
     * Sets the number of coins available to the player.
     * @param coins the number of coins to set
     */
    void setCoins(int coins);

    /**
     * Sets the action to be performed when the "Back" button is clicked.
     * @param onBackToMainMenu the action to set
     */
    void setOnBackToMainMenu(Runnable onBackToMainMenu);

    /**
     * Sets the skin.
     * @param skin the skin to set
     */
    void setSkins(List<Skin> skins);

    /**
     * Sets the Skin to Purchase
     * @param skin the skin to set
     */
    void setOnSkinPurchase(BiConsumer<String, Integer> skinPurchase);

    /**
     * Sets the action to be performed when a skin is selected.
     * @param onSkinSelected the action to set
     */
    void setOnSkinSelected(Consumer<String> onSkinSelected);
}
