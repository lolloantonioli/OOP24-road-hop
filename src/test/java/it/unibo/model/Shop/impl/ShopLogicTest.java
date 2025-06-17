package it.unibo.model.Shop.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.model.Shop.api.Skin;

class ShopLogicTest {
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.addCoins(1000);
    }

    @Test
    void testDefaultSkinIsSelectedAtStart() {
        Skin selected = shopModel.getSelectedSkin();
        assertNotNull(selected);
        assertEquals("Default", selected.getId());
        assertTrue(selected.isSelected());
    }

    @Test
    void testSelectSkinDeselectsPrevious() {
        shopModel.purchaseSkin("red");
        shopModel.selectSkin("red");
        Skin red = shopModel.getSkinById("red");
        Skin def = shopModel.getSkinById("Default");
        assertTrue(red.isSelected());
        assertFalse(def.isSelected());
    }
}
