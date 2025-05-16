package it.unibo.model.Shop.api;

import java.awt.image.BufferedImage;

public interface Skin {
    /**
     * Get the id of the skin.
     * @return the id of the skin
     */
    public String getId();

    /**
     * Get the name of the skin.
     * @return the name of the skin
     */
    public String getName();

    /**
     * Get the price of the skin.
     * @return the price of the skin
     */
    public int getPrice();

    /**
     * Get the image of the skin.
     * @return the image of the skin
     */
    public BufferedImage getImage();

    /**
     * Check if the skin is unlocked.
     * @return true if the skin is unlocked, false otherwise
     */
    public boolean isUnlocked();

    /**
     * Set the unlocked status of the skin.
     * @param unlocked true if the skin is unlocked, false otherwise
     */
    public void setUnlocked(boolean unlocked);
}
