package it.unibo.view.Menu.impl;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import it.unibo.controller.Menu.api.MenuController;
import it.unibo.view.Menu.api.MenuView;

public class MenuViewImpl implements MenuView {

    private MenuController controller;
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;

    private int currentWidth;
    private int currentHeight;

    @Override
    public void render(Graphics g) {
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, currentWidth, currentHeight, null);
        }

        // Draw the logo image
        if (logoImage != null) {
            int logoX = (currentWidth - logoImage.getWidth()) / 2;
            int logoY = (currentHeight - logoImage.getHeight()) / 2;
            g.drawImage(logoImage, logoX, logoY, null);
        }

    }

    @Override
    public void updateDimensions(int width, int height) {
        this.currentWidth = width;
        this.currentHeight = height;

        // Update the size of the background image if necessary
        if (backgroundImage != null) {
            backgroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
    }

    @Override
    public Font scaleFont(Font font) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scaleFont'");
    }

    @Override
    public boolean handleClick(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleClick'");
    }

    @Override
    public void setController(MenuController controller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setController'");
    }
    
}
