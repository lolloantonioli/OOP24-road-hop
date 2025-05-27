package it.unibo.controller;

import it.unibo.controller.Instructions.api.InstructionsObserver;
import it.unibo.controller.Instructions.impl.InstructionsObserverImpl;
import it.unibo.controller.Menu.api.MenuObserver;
import it.unibo.controller.Menu.impl.MenuObserverImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final MenuObserver menuObserver;
    private final InstructionsObserver instructionsObserver;
    private final ShopObserver shopObserver;
    private ShopModel shopModel;

    public MainControllerImpl() {
        this.gameFrame = new GameFrame();
        this.menuObserver = new MenuObserverImpl(this, gameFrame.getMenuPanel());
        this.instructionsObserver = new InstructionsObserverImpl(this, gameFrame.getInstructionsPanel());
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
    }

    public void goToMenu() {
        gameFrame.show(CardName.MENU);
        menuObserver.activate();
    }

    public void goToGame() {
        gameFrame.show(CardName.GAME);
    }

    public void goToInstructions() {
        gameFrame.show(CardName.INSTRUCTIONS);
        instructionsObserver.activate();
    }

    public void goToShop() {
        gameFrame.show(CardName.SHOP);
    }

    @Override
    public ShopModel getShopModel() {
        return shopModel;
    }

}
