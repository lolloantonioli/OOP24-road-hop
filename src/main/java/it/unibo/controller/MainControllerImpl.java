package it.unibo.controller;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Obstacles.impl.MovingObstacleControllerImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.impl.ShopModelImpl;
import it.unibo.view.GameFrame;

/**
 * MainController implementation.
 */
public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final ShopObserver shopObserver;
    private final ShopModel shopModel;
    private final MapController mapController;
    private final MovingObstacleController obstacleController;

    /**
     * Constructor for MainControllerImpl.
     * Initializes the game frame, map controller, shop model, and shop observer.
     */
    public MainControllerImpl() {
        this.gameFrame = new GameFrame(this);
        this.mapController = new MapControllerImpl(gameFrame.getGamePanel());
        this.gameFrame.getGamePanel().setController(mapController);
        this.shopModel = new ShopModelImpl();
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
        this.obstacleController = new MovingObstacleControllerImpl(this.mapController);
        this.gameFrame.getGamePanel().setObstacleController(obstacleController);

    }

    @Override
    public void goToMenu() {
        gameFrame.show(CardName.MENU);
    }
    
    @Override
    public void goToGame() {
        gameFrame.show(CardName.GAME);
        GameEngine engine = new GameEngine(mapController, obstacleController);
        new Thread(engine).start();
    }

    @Override
    public void goToInstructions() {
        gameFrame.show(CardName.INSTRUCTIONS);
    }

    @Override
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
