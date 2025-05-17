package it.unibo.view;

import java.awt.Dimension;
import java.awt.Font;

public interface ScaleManager {
    
    /**
     * Updates the scale factors based on new dimensions.
     * 
     * @param newWidth The new width of the game window
     * @param newHeight The new height of the game window
     */
    void updateScale(int newWidth, int newHeight);

    /**
     * Scales an X coordinate from logical to screen space.
     * 
     * @param x The X coordinate to scale
     * @return The scaled X coordinate
     */
    int scaleX(int x);

    /**
     * Scales a Y coordinate from logical to screen space.
     * 
     * @param y The Y coordinate to scale
     * @return The scaled Y coordinate
     */
    int scaleY(int y);

    /**
     * Scales a width from logical to screen space.
     * 
     * @param width The width to scale
     * @return The scaled width
     */
    int scaleWidth(int width);

    /**
     * Scales a height from logical to screen space.
     * 
     * @param height The height to scale
     * @return The scaled height
     */
    int scaleHeight(int height);

    /**
     * Scales a dimension from logical to screen space.
     * 
     * @param width The width to scale
     * @param height The height to scale
     * @return The scaled dimension
     */
    Dimension scaleDimension(int width, int height);

    /**
     * Converts a screen X coordinate back to logical space.
     * 
     * @param screenX The X coordinate to convert
     * @return The converted X coordinate
     */
    int unscaleX(int screenX);

    /**
     * Converts a screen Y coordinate back to logical space.
     * 
     * @param screenY The Y coordinate to convert
     * @return The converted Y coordinate
     */
    int unscaleY(int screenY);

    /**
     * Scales a font size based on the current scale factors.
     * 
     * @param font The original font to scale
     * @return A new font with size adjusted according to scale
     */
    Font scaleFont(Font font);

    /**
     * gets the current scale factor for X axis
     * @return the current scale factor for X axis
     */
    float getScaleX();

    /**
     * gets the current scale factor for Y axis
     * @return the current scale factor for Y axis
     */
    float getScaleY();

    /**
     * gets the current width of the game window
     * @return the current width of the game window
     */
    int getCurrentWidth();

    /**
     * gets the current height of the game window
     * @return the current height of the game window
     */
    int getCurrentHeight();

    /**
     * gets the base width of the game window
     * @return the base width of the game window
     */
    int getBaseWidth();

    /**
     * gets the base height of the game window
     * @return the base height of the game window
     */
    int getBaseHeight();
}
