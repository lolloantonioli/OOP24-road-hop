package it.unibo.view.Menu.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import it.unibo.controller.Menu.api.MenuController;
import it.unibo.view.ScaleManager;
import it.unibo.view.Menu.api.MenuView;

public class MenuViewImpl implements MenuView {

    private MenuController controller;
    private final ScaleManager scaleManager;
    private BufferedImage backgroundImage;
    private BufferedImage logoImage;

    private List<MenuButton> buttons;
    private static final Color BUTTON_COLOR = new Color(76, 175, 80);
    private static final Color HOVER_COLOR = new Color(129, 199, 132);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 48);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

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
    
        initButtons();
        loadImages();
    }

    private void initButtons(){
        buttons = new ArrayList<>();
        int buttonWidth = scaleManager.scaleWidth(200);
        int buttonHeight = scaleManager.scaleHeight(50);
        int spacing = scaleManager.scaleHeight(20);
        int startY = currentHeight / 2;

        // Play button
        MenuButton playButton = new MenuButton(
            (currentWidth - buttonWidth) / 2,
            startY,
            buttonWidth,
            buttonHeight,
            "Play",
            e -> controller.startGame()
        );
        buttons.add(playButton);

        // Shop button
        MenuButton shopButton = new MenuButton(
            (currentWidth - buttonWidth) / 2,
            startY + buttonHeight + spacing,
            buttonWidth,
            buttonHeight,
            "Shop",
            e -> controller.openShop()
        );
        buttons.add(shopButton);

        //exit button
        MenuButton exitButton = new MenuButton(
            (currentWidth - buttonWidth) / 2,
            startY + 2 * (buttonHeight + spacing),
            buttonWidth,
            buttonHeight,
            "Exit",
            e -> controller.exitGame()
        );
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("Immagine da mettere"));
            logoImage = ImageIO.read(getClass().getResourceAsStream("Immagine da mettere"));
        } catch (IOException | NullPointerException e) {
            // If we can't load the image, use a background color
            System.err.println("Unable to load menu images: " + e.getMessage());
            backgroundImage = null;
            logoImage = null;
        }
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

        // Update button positions and sizes
        initButtons();
    }

    @Override
    public Font scaleFont(Font font) {
        return scaleManager.scaleFont(font); 
    }

    @Override
    public boolean handleClick(int x, int y) {
        int unscaledX = scaleManager.unscaleX(x);
        int unscaledY = scaleManager.unscaleY(y);

        for (MenuButton button : buttons) {
            if (button.contains(unscaledX, unscaledY)) {
                button.onClick();
                return true;
            }
        }
        return false;    
    }

    @Override
    public void setController(MenuController controller) {
        this.controller = controller;
    }

    /*
     * Inner class to manage the buttons
     */
    private static class MenuButton {
        private final Rectangle bounds;
        private final String text;
        private final ButtonClickListener listener;

        /**
         * Button constructor.
         * 
         * @param x     The x coordinate
         * @param y     The y coordinate
         * @param width The width of the button
         * @param height The height of the button
         * @param text The text to display
         * @param listener The click listener
         */
        public MenuButton(int x, int y, int width, int height, String text, ButtonClickListener listener) {
            this.bounds = new Rectangle(x, y, width, height);
            this.text = text;
            this.listener = listener;
        }

        /**
         * Render the button.
         * 
         * @param g The graphics context
         * @param menuView The menu view
         */
        public void render(Graphics g, MenuView menuView){
            ScaleManager scaleManager = ((MenuViewImpl) menuView).scaleManager;
            int scaledX = scaleManager.scaleX(bounds.x);
            int scaledY = scaleManager.scaleY(bounds.y);
            int scaledWidth = scaleManager.scaleWidth(bounds.width);
            int scaledHeight = scaleManager.scaleHeight(bounds.height);

            g.setColor(BUTTON_COLOR);
            g.fillRoundRect(scaledX, scaledY, scaledWidth, scaledHeight, 15, 15);

            g.setColor(TEXT_COLOR);
            g.drawRoundRect(scaledX, scaledY, scaledWidth, scaledHeight, 15, 15);

            Font scaledFont = menuView.scaleFont(BUTTON_FONT);
            g.setFont(scaledFont);
            g.setColor(TEXT_COLOR);

            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            g.drawString(
                text,
                scaledX + (scaledWidth - textWidth) / 2,
                scaledY + (scaledHeight + textHeight / 2) / 2
            );
        }

        /**
         * Check if the point is in the button zone.
         * 
         * @param x The x coordinate
         * @param y The y coordinate
         * @return True if it is, false otherwise
         */
        public boolean contains(int x, int y) {
            return bounds.contains(x, y);
 
        }
        
        /**
         * execute the button click action.
         */
        public void onClick(){
            listener.onClick(this);
        }
    }
    /**
    * listener interface for button clicks
    */
    @FunctionalInterface
    interface ButtonClickListener {
        void onClick(MenuButton button);
    }
}