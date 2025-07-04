package it.unibo.controller;

import java.util.Optional;

import it.unibo.controller.obstacles.api.MovingObstacleController;
import it.unibo.controller.obstacles.impl.MovingObstacleControllerImpl;
import it.unibo.controller.player.api.PlayerController;
import it.unibo.controller.player.impl.DeathObserverImpl;
import it.unibo.controller.player.impl.PlayerControllerImpl;
import it.unibo.controller.shop.api.ShopObserver;
import it.unibo.controller.shop.impl.ShopObserverImpl;
import it.unibo.controller.util.CardName;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.impl.GameMapImpl;
import it.unibo.model.obstacles.api.MovingObstacleFactory;
import it.unibo.model.obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.shop.api.ShopModel;
import it.unibo.model.shop.impl.ShopModelImpl;
import it.unibo.view.GameFrame;

/**
 * MainController implementation.
 * Centralized controller that manages all game components and their lifecycle.
 */
public final class MainControllerImpl implements MainController {

    private static final int INITIAL_X = 4;
    private static final int INITIAL_Y = 2;

    private final GameFrame gameFrame;
    private final ShopObserver shopObserver;
    private final ShopModel shopModel;
    private final MovingObstacleFactory obstacleFactory;
    private Optional<GameEngine> gameEngine;
    private Optional<GameController> gameController;
    private GameMap gameMap;
    private MovingObstacleController obstacleController;
    private PlayerController playerController;

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
        obstacleController.resetObstacles();
        this.playerController = new PlayerControllerImpl(gameMap, shopModel.getSelectedSkin(), INITIAL_X, INITIAL_Y);
        playerController.addDeathObserver(new DeathObserverImpl(this));
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
        gameController = Optional.of(new GameController(
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
    public GameMap getGameMap() {
        return gameMap;
    }

    @Override
    public Optional<GameController> getGameController() {
        return gameController;
    }

    private void stopCurrentGame() {
        if (gameEngine.isPresent()) {
            gameEngine.get().stop();
            gameEngine = Optional.empty();
            gameFrame.getGamePanel().setAnimationOffset(0);
        }
        gameController = Optional.empty();
    }

    @Override
    public void showPausePanel(final Runnable onContinue, final Runnable onMenu) {
        gameFrame.showPausePanel(onContinue, onMenu);
    }

    @Override
    public void hidePausePanel() {
        gameFrame.hidePausePanel();
    }

    @Override
    public Optional<GameEngine> getGameEngine() {
        return gameEngine;
    }

    @Override
    public void showGameOverPanel() {
        final int score = playerController.getPlayerScore();
        final int coins = playerController.getCollectedCoins();
        shopModel.addCoins(coins);
        shopModel.updateMaxScore(score);
        final int maxScore = shopModel.getMaxScore();
        gameFrame.showGameOverPanel(score, coins, maxScore);
    }
}
