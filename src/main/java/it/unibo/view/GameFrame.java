package it.unibo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import it.unibo.controller.MainController;
import it.unibo.controller.util.CardName;

/**
 * The main game frame that contains all the panels for the game.
 * It uses a CardLayout to switch between different views such as menu, shop, instructions, game, and game over.
 */
public final class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String FRAME_NAME = "Road Hop";
    private static final String MSG = "CardName cannot be null";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private final CardLayout layout;
    private final JPanel root;
    private final MenuPanel menuPanel;
    private final ShopView shopView;
    private final InstructionsPanel instructionsPanel;
    private final GamePanel gamePanel;
    private final GameOverPanel gameOverPanel;

    @SuppressFBWarnings(
        value = "EI",
        justification = "Intentional: callers need direct access to the InstructionsPanel for layout/testing."
    )

    /**
     * Constructs a new GameFrame with the specified MainController.
     *
     * @param controller the MainController to handle navigation and actions
     */
    public GameFrame(final MainController controller) {
        this.layout = new CardLayout();
        this.root = new JPanel(this.layout);
        this.menuPanel = new MenuPanel(controller);
        this.shopView = new ShopView();
        this.instructionsPanel = new InstructionsPanel(controller);
        this.gamePanel = new GamePanel();
        this.gameOverPanel = new GameOverPanel(controller::goToMenu);

        this.setTitle(FRAME_NAME);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        root.add((Component) menuPanel, CardName.MENU.toString());
        root.add((Component) shopView, CardName.SHOP.toString());
        root.add((Component) instructionsPanel, CardName.INSTRUCTIONS.toString());
        root.add((Component) gamePanel, CardName.GAME.toString());
        root.add((Component) gameOverPanel, CardName.GAME_OVER.toString());
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Shows the specified CardName panel.
     *
     * @param name the CardName of the panel to show
     */
    public void show(final CardName name) {
        checkNotNull(name, MSG);
        this.layout.show(this.root, name.toString());
    }

    /**
     * Returns the ShopView of this GameFrame.
     * @return the ShopView instance
     */
    public ShopView getShopPanel() {
        return this.shopView;
    }

    /**
     * Returns the GamePanel of this GameFrame.
     * @return the GamePanel instance
     */
    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    /**
     * Shows the pause panel with options to continue or go to the menu.
     * @param onContinue the action to perform when continuing the game
     * @param onMenu the action to perform when going to the menu
     */
    public void showPausePanel(final Runnable onContinue, final Runnable onMenu) {
        final PausePanel pausePanel = new PausePanel(onContinue, onMenu);
        this.root.add(pausePanel, CardName.PAUSE.toString());
        this.layout.show(this.root, CardName.PAUSE.toString());
    }

    /**
     * Hides the pause panel and returns to the game view.
     */
    public void hidePausePanel() {
        this.layout.show(this.root, CardName.GAME.toString());
    }

    /**
     * Shows the game over panel with option to return to the menu.
     * @param finalScore the final score of the game
     * @param finalCoins the final coins collected in the game
     * @param maxScore the maximum score achieved in the game
     */
    public void showGameOverPanel(final int finalScore, final int finalCoins, final int maxScore) {
        gameOverPanel.setFinalScore(finalScore);
        gameOverPanel.setFinalCoins(finalCoins);
        gameOverPanel.setMaxScore(maxScore);
        this.show(CardName.GAME_OVER);
    }

}
