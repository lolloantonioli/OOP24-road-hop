package it.unibo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import it.unibo.controller.MainController;
import it.unibo.controller.Util.CardName;

public class GameFrame extends JFrame {

    private final CardLayout layout;
    private final JPanel root;
    private final MenuPanel menuPanel;
    private final ShopView shopView;
    private final InstructionsPanel instructionsPanel;
    private final GamePanel gamePanel;
    private final GameOverPanel gameOverPanel;

    private static final String FRAME_NAME = "Road Hop";
    private static final String MSG = "CardName cannot be null";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public GameFrame(final MainController controller) {
        this.layout = new CardLayout();
        this.root = new JPanel(this.layout);
        this.menuPanel = new MenuPanel(controller);
        this.shopView = new ShopView();
        this.instructionsPanel = new InstructionsPanel(controller);
        this.gamePanel = new GamePanel();
        this.gameOverPanel = new GameOverPanel(() -> controller.goToMenu());

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

    public void show(final CardName name) {
        checkNotNull(name, MSG);
        this.layout.show(this.root, name.toString());
    }

    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }

    public ShopView getShopPanel() {
        return this.shopView;
    }

    public InstructionsPanel getInstructionsPanel() {
        return this.instructionsPanel;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public void showPausePanel(Runnable onContinue, Runnable onMenu) {
        final PausePanel pausePanel = new PausePanel(onContinue, onMenu);
        this.root.add(pausePanel, CardName.PAUSE.toString());
        this.layout.show(this.root, CardName.PAUSE.toString());
    }

    public void hidePausePanel() {
        this.layout.show(this.root, CardName.GAME.toString());
    }

    public void showGameOverPanel() {
        this.show(CardName.GAME_OVER);
    }

}
