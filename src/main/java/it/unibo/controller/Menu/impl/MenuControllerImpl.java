package it.unibo.controller.Menu.impl;

import it.unibo.controller.GameStateManager;
import it.unibo.controller.Menu.api.MenuController;
import it.unibo.view.Menu.api.MenuView;

public class MenuControllerImpl implements MenuController {
    
    private final MenuView view;
    private final GameStateManager gameStateManager;
    
    /**
     * Builder for the controller of the menu.
     * @param gameStateManager The game state manager
     */
    public MenuControllerImpl(MenuView view,GameStateManager gameStateManager) {
        this.view = view;
        this.gameStateManager = gameStateManager;
        this.view.setController(this);
    }

    @Override
    public void startGame() {
      gameStateManager.startGame();
    }

    @Override
    public void openShop() {
       gameStateManager.openShop();
    }

    @Override
    public void exitGame() {
       System.exit(0); 
    }

    @Override
    public void updateViewDimensions(int width, int height) {
        view.updateDimensions(width, height);}

    @Override
    public void handleClick(int x, int y) {
       view.handleClick(x, y);
    }
   
}
