package it.unibo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.ShopModelImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

class ShopModelImplTest {
    private static final int COINS_TO_ADD = 50;
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.addCoins(1000); // Assicura abbastanza monete per i test
    }

    @Test
    void testPurchaseSkin() {
        assertFalse(shopModel.getSkinById("red").isUnlocked());
        shopModel.purchaseSkin("red");
        assertTrue(shopModel.getSkinById("red").isUnlocked());
    }

    @Test
    void testSelectSkin() {
        shopModel.purchaseSkin("red");
        shopModel.selectSkin("red");
        final Skin selected = shopModel.getSelectedSkin();
        assertEquals("red", selected.getId());
        assertTrue(selected.isSelected());
    }

    @Test
    void testAddCoins() {
        final int before = shopModel.getCoins();
        shopModel.addCoins(COINS_TO_ADD);
        assertEquals(before + COINS_TO_ADD, shopModel.getCoins());
    }

    @Test
    void testGetAllSkins() {
        final List<Skin> skins = shopModel.getAllSkins();
        assertNotNull(skins);
        assertFalse(skins.isEmpty());
    }

    @Test
    void testCannotPurchaseWithoutCoins() {
        final ShopModelImpl lowCoinsModel = new ShopModelImpl();
        lowCoinsModel.setCoinsForTest(0); // Imposta le monete a 0
        final int coinsBefore = lowCoinsModel.getCoins();
        lowCoinsModel.purchaseSkin("red");
        assertEquals(coinsBefore, lowCoinsModel.getCoins());
        assertFalse(lowCoinsModel.getSkinById("red").isUnlocked());
    }
}
