package it.unibo.view;

import it.unibo.controller.MainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Shop.api.ShopModel;
import it.unibo.controller.GameController;
import it.unibo.controller.GameEngine;

class MenuPanelExtraTest {
    private MenuPanel menuPanel;

    private static class DummyMainController implements MainController {
        public void goToMenu() {}
        public void goToGame() {}
        public void goToInstructions() {}
        public void goToShop() {}
        public void showPausePanel(Runnable onContinue, Runnable onMenu) {}
        public void hidePausePanel() {}
        public void showGameOverPanel() {}
        public ShopModel getShopModel() { return null; }
        public GameMap getGameMap() { return null; }
        public Optional<GameController> getGameController() { return Optional.empty(); }
        public Optional<GameEngine> getGameEngine() { return Optional.empty(); }
    }

    @BeforeEach
    void setUp() {
        menuPanel = new MenuPanel(new DummyMainController());
    }

    @Test
    void testHasPlayButton() {
        JButton playButton = findButtonByText(menuPanel, "Play");
        assertNotNull(playButton);
    }

    @Test
    void testHasShopButton() {
        JButton shopButton = findButtonByText(menuPanel, "Shop");
        assertNotNull(shopButton);
    }

    @Test
    void testPlayButtonListener() {
        JButton playButton = findButtonByText(menuPanel, "Play");
        AtomicBoolean clicked = new AtomicBoolean(false);
        playButton.addActionListener(e -> clicked.set(true));
        for (var l : playButton.getActionListeners()) l.actionPerformed(null);
        assertTrue(clicked.get());
    }

    @Test
    void testMenuTitle() {
        JLabel titleLabel = findLabelByText(menuPanel, "Menu");
        assertNotNull(titleLabel);
    }

    // Utility methods
    private JButton findButtonByText(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton && ((JButton) c).getText().equals(text)) {
                return (JButton) c;
            } else if (c instanceof Container) {
                JButton b = findButtonByText((Container) c, text);
                if (b != null) return b;
            }
        }
        return null;
    }
    private JLabel findLabelByText(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel && ((JLabel) c).getText().equals(text)) {
                return (JLabel) c;
            } else if (c instanceof Container) {
                JLabel l = findLabelByText((Container) c, text);
                if (l != null) return l;
            }
        }
        return null;
    }
}
