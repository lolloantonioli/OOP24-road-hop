package it.unibo.controller;

import java.util.Optional;

import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Obstacles.impl.MovingObstacleControllerImpl;
import it.unibo.controller.Player.api.DeathObserver;
import it.unibo.controller.Player.api.PlayerController;
import it.unibo.controller.Player.impl.DeathObserverImpl;
import it.unibo.controller.Player.impl.PlayerControllerImpl;
import it.unibo.controller.Shop.api.ShopObserver;
import it.unibo.controller.Shop.impl.ShopObserverImpl;
import it.unibo.controller.Util.CardName;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.model.Shop.impl.ShopModelImpl;
import it.unibo.view.GameFrame;

/**
 * MainController implementation.
 * Centralized controller that manages all game components and their lifecycle.
 */
public class MainControllerImpl implements MainController {

    private final GameFrame gameFrame;
    private final ShopObserver shopObserver;
    private final ShopModel shopModel;
    private final MovingObstacleFactory obstacleFactory;
    private Optional<GameEngine> gameEngine;
    private Optional<GameControllerImpl> gameController;
    private GameMap gameMap;
    private MovingObstacleController obstacleController;
    private PlayerController playerController;
    private DeathObserver deathObserver;

    /**
     * Constructor for MainControllerImpl.
     * Initializes the game frame, shop components, and obstacle factory.
     */
    public MainControllerImpl() {
        this.gameFrame = new GameFrame(this);
        this.shopModel = new ShopModelImpl();
        this.shopObserver = new ShopObserverImpl(this, gameFrame.getShopPanel(), shopModel);
        this.obstacleFactory = new MovingObstacleFactoryImpl();
        this.gameEngine = Optional.empty();
        this.gameController = Optional.empty();
        
        initializeGameComponents();
    }

    private void initializeGameComponents() {
        this.gameMap = new GameMapImpl();
        this.obstacleController = new MovingObstacleControllerImpl(gameMap);
        this.playerController = new PlayerControllerImpl(gameMap, shopModel.getSelectedSkin(), 5, 2);
        this.deathObserver = new DeathObserverImpl(this);
        playerController.getPlayer().addObserver(deathObserver);
    }

    @Override
    public void goToMenu() {
        stopCurrentGame();
        gameFrame.show(CardName.MENU);
    }
    
    @Override
    public void goToGame() {
        stopCurrentGame();
        initializeGameComponents();
        gameFrame.show(CardName.GAME);
        gameController = Optional.of(new GameControllerImpl(
            gameMap,
            obstacleController,
            this,
            playerController
        ));
        gameEngine = Optional.of(new GameEngine(
            gameMap,
            gameFrame.getGamePanel(),
            obstacleController,
            obstacleFactory,
            this,
            gameController.get()
        ));

        
        new Thread(gameEngine.get()).start();
        gameFrame.getGamePanel().setController(gameController.get());
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

    /**
     * Gets the current game map.
     * @return the current GameMap instance, or null if no game is active
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Gets the current game controller.
     * @return the current GameController instance
     */
    public Optional<GameControllerImpl> getGameController() {
        return gameController;
    }

    /**
     * Stops the current game and cleans up resources.
     */
    private void stopCurrentGame() {
        if (gameEngine.isPresent()) {
            gameEngine.get().stop();
            gameEngine = Optional.empty();
            gameFrame.getGamePanel().setAnimationOffset(0);
        }
        gameController = Optional.empty();
    }

    public void goToPause() {
        gameFrame.show(CardName.PAUSE);
    }
    
    @Override
    public void showPausePanel(final Runnable onContinue, final Runnable onMenu) {
        gameFrame.showPausePanel(onContinue, onMenu);
    }

    @Override
    public void hidePausePanel() {
        gameFrame.hidePausePanel();
    }

    /**
     * Gets the current game engine.
     * @return the current GameEngine instance
     */
    public Optional<GameEngine> getGameEngine() {
        return gameEngine;
    }

    @Override
    public void showGameOverPanel() {
        gameFrame.showGameOverPanel();
    }

}