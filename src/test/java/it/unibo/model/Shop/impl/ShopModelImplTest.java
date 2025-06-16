package it.unibo.model.Shop.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.model.Shop.api.Skin;


import java.util.List;

class ShopModelImplTest {
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
        Skin selected = shopModel.getSelectedSkin();
        assertEquals("red", selected.getId());
        assertTrue(selected.isSelected());
    }

    @Test
    void testAddCoins() {
        int before = shopModel.getCoins();
        shopModel.addCoins(50);
        assertEquals(before + 50, shopModel.getCoins());
    }

    @Test
    void testGetAllSkins() {
        List<Skin> skins = shopModel.getAllSkins();
        assertNotNull(skins);
        assertFalse(skins.isEmpty());
    }
}
