package it.unibo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.ShopModelImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        final int coinsBefore = shopModel.getCoins();
        shopModel.purchaseSkin("red"); // Prova a riacquistare
        assertEquals(coinsBefore, shopModel.getCoins());
    }

    @Test
    void testCannotSelectLockedSkin() {
        final Skin before = shopModel.getSelectedSkin();
        shopModel.selectSkin("blue"); // Non sbloccata
        assertEquals(before, shopModel.getSelectedSkin());
    }

    @Test
    void testDefaultSkinAlwaysUnlocked() {
        final Skin defaultSkin = shopModel.getSkinById("Default");
        assertTrue(defaultSkin.isUnlocked());
    }
}
