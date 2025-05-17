package it.unibo.view.Menu.api;
import java.awt.Graphics;

import it.unibo.controller.Menu.api.MenuController;

import java.awt.Font;
/**
 * Interface for the main menu view.
 */
public interface MenuView {
    /**
     * Renderizes the menu screen.
     * @param g The graphics context to draw on
     */
    void render(Graphics g);
    
    /**
     * Updates the dimensions of the screen.
     * @param width The new width
     * @param height The new height
     */
    void updateDimensions(int width, int height);

    /**
     * Scales a font according to the current scale factors.
     * @param font The original font to scale
     * @return A new font with size adjusted according to scale
     */
    Font scaleFont(Font font);

    /**
     * Handles a mouse click on the menu screen.
     * @param x X coordinate of the click
     * @param y Y coordinate of the click
     * @return true if the click was handled, false otherwise
     */
    boolean handleClick(int x, int y);

    /**
     * Sets the controller associated with this view.
     * @param controller The controller to associate
     */
    void setController(MenuController controller);
} 
