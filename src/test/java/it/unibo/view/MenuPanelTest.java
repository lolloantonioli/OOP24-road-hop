package it.unibo.view;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuPanelTest {
    @Test
    void testMenuPanelCreation() {
        MenuPanel menuPanel = new MenuPanel(null);
        assertNotNull(menuPanel);
        assertTrue(menuPanel instanceof JPanel);
    }
}
