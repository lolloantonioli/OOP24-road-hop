package it.unibo;

import com.google.common.math.Quantiles.Scale;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.unibo.controller.GameState;
import it.unibo.controller.GameStateManager;
import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Menu.api.MenuController;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.view.ScaleManager;
import it.unibo.view.ScaleManagerImpl;
import it.unibo.view.Menu.api.MenuView;
import it.unibo.view.Menu.impl.MenuViewImpl;


import javax.swing.JPanel;

public class MainGamePanel extends JPanel implements GameStateManager.GameInitializer, GameStateManager.ShopInitializer {

    private ScaleManager scaleManager;
    private MenuController menuController;
    private final GameStateManager gameStateManager;

    private MenuView menuView;
    private GameMapImpl gameMap;
    private MapControllerImpl mapController;

    public MainGamePanel(int width, int height) {
        // Set the size of the panel
        this.setSize(width, height);
        this.setFocusable(true);
        
        // Initialize the game state manager
        gameStateManager = new GameStateManager(this,this);
        
        // Initialize the scale manager
        scaleManager = new ScaleManagerImpl(800,600);
        scaleManager.updateScale(width, height);

        // Initialize the menu components
        initMenu();

        // Mouse listener for handling clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * Initializes the menu components.
     */
    private void initMenu() {
        menuView = new MenuViewImpl(scaleManager);
        
    }
    private void handleMouseClick(int x, int y) {
        // Handle mouse click events based on the current game state
        switch (gameStateManager.getCurrentState()) {
            case MENU:
            menuController.handleClick(x, y);
                break;
            case PLAYING:
                // Handle game logic
                break;
            case SHOP:
                // Handle shop logic
                break;
            case GAME_OVER:
                // Handle game over logic
                break;
    }
}

    @Override
    public void initShop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initShop'");
    }

    @Override
    public void initGame() {
        if (gameMap == null) {
                gameMap = new GameMapImpl(scaleManager.getBaseWidth());
                mapController = new MapControllerImpl(gameMap);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the current game state
        switch (gameStateManager.getCurrentState()) {
            case MENU:
                menuView.render(g);
                break;
            case PLAYING:
                // Draw game components
                break;
            case SHOP:
                // Draw shop components
                break;
            case GAME_OVER:
                // Draw game over components
                break;
        }
    }

    public void resize(int width, int height) {
        
        scaleManager.updateScale(width, height);

        if (menuController != null){
            menuController.updateViewDimensions(width, height);
        }
        // Update the scale of the menu view
        // Update the scale of the map view
        
    }

    public void update() {
        switch (gameStateManager.getCurrentState()) {
            case MENU:
                if (mapController != null){
                    mapController.updateMap();
                }
                break;
            case PLAYING:
                // Update game logic
                break;
            case SHOP:
                // Update shop logic
                break;
            case GAME_OVER:
                // Update game over logic
                break;
        }

        repaint();
    }

    public void setMenuController(MenuController controller) {
        this.menuController = controller;
        if (menuView != null){
            menuView.setController(controller);
        }
    }

    public MenuView getMenuView() {
        return menuView;
    }
}
