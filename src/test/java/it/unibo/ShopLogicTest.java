package it.unibo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.ShopModelImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopLogicTest {
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.addCoins(1000);
    }

    @Test
    void testDefaultSkinIsSelectedAtStart() {
        final Skin selected = shopModel.getSelectedSkin();
        assertNotNull(selected);
        assertEquals("Default", selected.getId());
        assertTrue(selected.isSelected());
    }

    @Test
    void testSelectSkinDeselectsPrevious() {
        shopModel.purchaseSkin("red");
        shopModel.selectSkin("red");
        final Skin red = shopModel.getSkinById("red");
        final Skin def = shopModel.getSkinById("Default");
        assertTrue(red.isSelected());
        assertFalse(def.isSelected());
    }
}
