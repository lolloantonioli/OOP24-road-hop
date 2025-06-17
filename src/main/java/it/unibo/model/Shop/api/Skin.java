package it.unibo.model.shop.api;

import java.awt.Color;
/**
 * Skin interface that defines the methods for a skin in the shop.
 * It includes methods to get skin properties, manage unlocked and equipped status,
 * and retrieve the skin's color.
 */
public interface Skin {
    /**
     * Get the id of the skin.
     * @return the id of the skin
     */
    String getId();

    /**
     * Get the name of the skin.
     * @return the name of the skin
     */
    String getName();

    /**
     * Get the price of the skin.
     * @return the price of the skin
     */
    int getPrice();

    /**
     * Check if the skin is unlocked.
     * @return true if the skin is unlocked, false otherwise
     */
    boolean isUnlocked();

    /**
     * Set the unlocked status of the skin.
     */
    void unlock();

    /**
     * Check if the skin is equipped.
     * @return true if the skin is equipped, false otherwise
     */
    boolean isSelected();

    /**
     * Set the equipped status of the skin.
     */
    void select();

    /**
     * Deselect the skin.
     */
    void deselect();

    /**
     * Get the color of the skin.
     * @return the color of the skin
     */
    Color getColor();
}
