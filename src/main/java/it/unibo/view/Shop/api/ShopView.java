package it.unibo.view.Shop.api;

import java.awt.Graphics;

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
     * Handles the mouse click event.
     * @param x the x-coordinate of the mouse click
     * @param y the y-coordinate of the mouse click
     */
    void handleMouseClick(int x, int y);

    /**
     * Paints the component.
     * @param g the graphics context
     */
    void paintComponent(Graphics g);
}
