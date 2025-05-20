package it.unibo.controller.Menu.api;

public interface MenuController {
    /**
     * Starts a new game.
     */
    void startGame();

    /**
     * Opens the skin shop.
     */
    void openShop();

    /**
     * Exits the game.
     */
    void exitGame();

    /**
     * Updates the view dimensions.
     *
     * @param width  The new width
     * @param height The new height
     */
    void updateViewDimensions(int width, int height);

    /**
     * Handles a mouse click.
     *
     * @param x The X coordinate of the click
     * @param y The Y coordinate of the click
     */
    boolean handleClick(int x, int y); 
}   