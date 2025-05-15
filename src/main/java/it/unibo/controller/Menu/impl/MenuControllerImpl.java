package it.unibo.controller.Menu.impl;

import it.unibo.controller.GameStateManager;
import it.unibo.controller.Menu.api.MenuController;

public class MenuControllerImpl implements MenuController {
   
    private final GameStateManager gameStateManager;
    
    /**
     * Builder for the controller of the menu.
     * @param gameStateManager The game state manager
     */
    public MenuControllerImpl(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateViewDimensions'");
    }

    @Override
    public void handleClick(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleClick'");
    }
   
}
