package it.unibo.model.Shop.api;

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
     * Check if the skin is unlocked.
     * @return true if the skin is unlocked, false otherwise
     */
    public boolean isUnlocked();

    /**
     * Set the unlocked status of the skin.
     */
    public void unlock();

    /**
     * Check if the skin is equipped.
     * @return true if the skin is equipped, false otherwise
     */
    public boolean isSelected();

    /**
     * Set the equipped status of the skin.
     * @param selected true if the skin is equipped, false otherwise
     */
    public void select();

    /**
     * Deselect the skin.
     */
    public void deselect();
}
