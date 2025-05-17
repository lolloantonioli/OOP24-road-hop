package it.unibo.view.Menu.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import it.unibo.controller.Menu.api.MenuController;
import it.unibo.view.ScaleManager;
import it.unibo.view.Menu.api.MenuView;

public class MenuViewImpl implements MenuView {

    private MenuController controller;
    private final ScaleManager scaleManager;
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;

    private int currentWidth;
    private int currentHeight;

    /**
     * Menu View constructor.
     * 
     * @param scaleManager The scale manager
     */
    public MenuViewImpl(ScaleManager scaleManager) {
        this.scaleManager = scaleManager;
        this.currentWidth = scaleManager.getCurrentWidth();
        this.currentHeight = scaleManager.getCurrentHeight();
    }
    @Override
    public void render(Graphics g) {
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, currentWidth, currentHeight, null);
        } else {
            // If the background image is not available, fill the background with a color
            g.setColor(new Color(30, 30, 30));
            g.fillRect(0, 0, currentWidth, currentHeight);
        }

        // Draw the logo image
        if (logoImage != null) {
            int logoWidth = logoImage.getWidth();
            int logoHeight = logoImage.getHeight();
            int logoX = (currentWidth - logoWidth) / 2;
            int logoY = (currentHeight - logoHeight) / 2;
            g.drawImage(
                logoImage,
                logoX, 
                logoY,
                null
                );
        } else {
            g.setFont(scaleFont(new Font("Arial", Font.BOLD, 48)));
            g.setColor(Color.WHITE);
            String title = "ROAD HOP";  
            FontMetrics fm = g.getFontMetrics();
            int titleWidth = fm.stringWidth(title);
            g.drawString(title, (currentWidth - titleWidth) / 2, currentHeight / 4);
        }

    }

    @Override
    public void updateDimensions(int width, int height) {
        this.currentWidth = width;
        this.currentHeight = height;

        scaleManager.updateScale(width, height);
    }

    @Override
    public Font scaleFont(Font font) {
        return scaleManager.scaleFont(font); 
    }

    @Override
    public boolean handleClick(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleClick'");
    }

    @Override
    public void setController(MenuController controller) {
        this.controller = controller;
    }
    
}
