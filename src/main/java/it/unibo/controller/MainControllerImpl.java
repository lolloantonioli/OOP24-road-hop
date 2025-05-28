package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final Observer menuObserver;
    private final Observer instructionsObserver;
    private final ShopObserver shopObserver;
    private ShopModel shopModel;
    private final MapController mapController;

    public MainControllerImpl() {
        this.gameFrame = new GameFrame();
        this.menuObserver = new MenuObserver(this, gameFrame.getMenuPanel());
        this.instructionsObserver = new InstructionsObserver(this, gameFrame.getInstructionsPanel());
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
        this.mapController = new MapControllerImpl();
    }

    public void goToMenu() {
        gameFrame.show(CardName.MENU);
        menuObserver.activate();
    }

    public void goToGame() {
        gameFrame.show(CardName.GAME);
        GameEngine engine = new GameEngine(mapController, () -> gameFrame.getGamePanel().refresh());
        new Thread(engine).start();
    }

    public void goToInstructions() {
        gameFrame.show(CardName.INSTRUCTIONS);
        instructionsObserver.activate();
    }

    public void goToShop() {
        gameFrame.show(CardName.SHOP);
        shopObserver.activate();
    }

    @Override
    public ShopModel getShopModel() {
        return shopModel;
    }

}
