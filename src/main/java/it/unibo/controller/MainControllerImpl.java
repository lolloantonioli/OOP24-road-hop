package it.unibo.controller;

import java.util.Optional;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Map.impl.MapControllerImpl;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Obstacles.impl.MovingObstacleControllerImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
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
    private MapController mapController;
    private MovingObstacleController obstacleController;
    private final MovingObstacleFactory obstacleFactory = new MovingObstacleFactoryImpl();
    private Optional<GameEngine> gameEngine;

    /**
     * Constructor for MainControllerImpl.
     * Initializes the game frame, map controller, shop model, and shop observer.
     */
    public MainControllerImpl() {
        this.gameFrame = new GameFrame(this);
        this.mapController = new MapControllerImpl(gameFrame.getGamePanel());
        this.obstacleController = new MovingObstacleControllerImpl(mapController);
        this.shopModel = new ShopModelImpl();
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
        this.gameEngine = Optional.empty();
    }

    @Override
    public void goToMenu() {
        if (gameEngine.isPresent()) {
            gameEngine.get().stop(); // Ferma il thread della partita attuale
            gameEngine = Optional.empty(); // Rimuovi il riferimento
            this.mapController = new MapControllerImpl(gameFrame.getGamePanel());
            this.obstacleController = new MovingObstacleControllerImpl(mapController);
        }
        gameFrame.show(CardName.MENU);
    }
    
    @Override
    public void goToGame() {
        // Ferma la partita precedente se esiste
        if (gameEngine.isPresent()) {
            gameEngine.get().stop();
            gameEngine = Optional.empty();
        }
        // Mostra il pannello di gioco
        gameFrame.show(CardName.GAME);
        // Crea una nuova partita (nuovo GameEngine)
        gameEngine = Optional.of(new GameEngine(
            mapController.getGameMap(),
            gameFrame.getGamePanel(),
            obstacleController,
            obstacleFactory,
            this
        ));
        // Avvia il nuovo thread di gioco
        new Thread(gameEngine.get()).start();
        // Collega il controller della tastiera
        gameFrame.getGamePanel().setController(
            mapController,
            obstacleController,
            new GameController(gameEngine.get())
        );
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

    public void goToPause() {
        gameFrame.show(CardName.PAUSE);
    }
    
    @Override
    public void showPausePanel(Runnable onContinue, Runnable onMenu) {
        gameFrame.showPausePanel(onContinue, onMenu);
    }

    @Override
    public void hidePausePanel() {
        gameFrame.hidePausePanel();
    }

}
