package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.impl.ShopModelImpl;
import it.unibo.view.GameFrame;

public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final ShopObserver shopObserver;
    private final ShopModel shopModel;
    private final MapController mapController;

    public MainControllerImpl() {
        this.gameFrame = new GameFrame(this);
        this.mapController = new MapControllerImpl(gameFrame.getGamePanel());
        this.gameFrame.getGamePanel().setController(mapController);
        this.shopModel = new ShopModelImpl();
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
    }

    public void goToMenu() {
        gameFrame.show(CardName.MENU);
    }

    public void goToGame() {
        gameFrame.show(CardName.GAME);
        GameEngine engine = new GameEngine(mapController);
        new Thread(engine).start();
    }

    public void goToInstructions() {
        gameFrame.show(CardName.INSTRUCTIONS);
    }

    public void goToShop() {
        gameFrame.show(CardName.SHOP);
        shopObserver.activate();
    }

    @Override
    public ShopModel getShopModel() {
        return shopModel;
    }

    @Override
    public MapController getMapController() {
        return mapController;
    }

}
