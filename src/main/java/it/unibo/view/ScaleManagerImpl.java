package it.unibo.view;

import java.awt.Dimension;
import java.awt.Font;

public class ScaleManagerImpl implements ScaleManager {

    private int baseWidth;
    private int baseHeight;
    private int currentWidth;
    private int currentHeight;
    private float scaleX;
    private float scaleY;

    /**
     * Constructor for the ScaleManager.
     * 
     * @param baseWidth The base width for which the game was designed
     * @param baseHeight The base height for which the game was designed
     */
    public ScaleManagerImpl(int baseWidth, int baseHeight) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.currentWidth = baseWidth;
        this.currentHeight = baseHeight;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
    }

    @Override
    public void updateScale(int newWidth, int newHeight) {
        this.currentWidth = newWidth;
        this.currentHeight = newHeight;
        this.scaleX = (float) newWidth / baseWidth;
        this.scaleY = (float) newHeight / baseHeight;
    }

    @Override
    public int scaleX(int x) {
        return Math.round(x * scaleX);
    }

    @Override
    public int scaleY(int y) {
        return Math.round(y * scaleY);
    }

    @Override
    public int scaleWidth(int width) {
        return Math.round(width * scaleX);
    }

    @Override
    public int scaleHeight(int height) {
        return Math.round(height * scaleY);
    }

    @Override
    public Dimension scaleDimension(int width, int height) {
        return new Dimension(scaleWidth(width), scaleHeight(height));
    }

    @Override
    public int unscaleX(int screenX) {
       return Math.round(screenX / scaleX);
    }

    @Override
    public int unscaleY(int screenY) {
        return Math.round(screenY / scaleY);
    }

    @Override
    public Font scaleFont(Font font) {
        float scaleFactor = (scaleX + scaleY) / 2.0f;
        int newSize = Math.round(font.getSize() * scaleFactor);
        return new Font(font.getName(), font.getStyle(), newSize);
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;    
    }

    @Override
    public int getCurrentWidth() {  
        return currentWidth;
    }

    @Override
    public int getCurrentHeight() {
        return currentHeight;
    }

    @Override
    public int getBaseWidth() {
        return baseWidth;
    }

    @Override
    public int getBaseHeight() {
        return baseHeight;
    }
    
}
