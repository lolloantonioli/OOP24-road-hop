package it.unibo.model.Shop.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.model.Shop.api.Skin;

class ShopModelImplExtraTest {
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.addCoins(1000);
    }

    @Test
    void testCannotPurchaseUnlockedSkin() {
        shopModel.purchaseSkin("red");
        int coinsBefore = shopModel.getCoins();
        shopModel.purchaseSkin("red"); // Prova a riacquistare
        assertEquals(coinsBefore, shopModel.getCoins());
    }

    @Test
    void testCannotSelectLockedSkin() {
        Skin before = shopModel.getSelectedSkin();
        shopModel.selectSkin("blue"); // Non sbloccata
        assertEquals(before, shopModel.getSelectedSkin());
    }

    @Test
    void testDefaultSkinAlwaysUnlocked() {
        Skin defaultSkin = shopModel.getSkinById("Default");
        assertTrue(defaultSkin.isUnlocked());
    }
}
