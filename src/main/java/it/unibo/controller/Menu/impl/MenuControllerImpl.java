package it.unibo.controller.Menu.impl;

import it.unibo.MainGamePanel;
import it.unibo.controller.GameState;
import it.unibo.controller.GameStateManager;
import it.unibo.controller.Menu.api.MenuController;
import it.unibo.view.Menu.api.MenuView;

public class MenuControllerImpl implements MenuController {
    
   
    private  MenuView view;
    private final GameStateManager gameStateManager;
    
    /**
     * Builder for the controller of the menu.
     * @param gameStateManager The game state manager
     */
    public MenuControllerImpl(MenuView view) {
        this.gameStateManager = GameStateManager.getInstance();
        this.view = view;

        if(this.view != null){
            this.view.setController(this);
        }
    }

    @Override
    public void startGame() {
        System.out.println("Starting game...");
      gameStateManager.setState(GameState.PLAYING);
    }

    @Override
    public void openShop() {
        System.out.println("Opening shop...");
        gameStateManager.setState(GameState.SHOP);
    }

    @Override
    public void exitGame() {
        System.out.println("Exiting game...");
        System.exit(0); 
    }

    @Override
    public void updateViewDimensions(int width, int height) {
        view.updateDimensions(width, height);}

    @Override
    public boolean handleClick(int x, int y) {
       return view.handleClick(x, y);
    }
   
}
