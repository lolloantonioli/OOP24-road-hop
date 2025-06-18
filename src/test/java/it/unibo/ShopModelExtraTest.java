package it.unibo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.shop.api.Skin;
import it.unibo.model.shop.impl.ShopModelImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShopModelExtraTest {
    private static final int COINS_TO_START = 100;
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.setCoinsForTest(COINS_TO_START);
    }

    @Test
    void testCannotPurchaseUnlockedSkin() {
        shopModel.purchaseSkin("red");
        final int coinsBefore = shopModel.getCoins();
        shopModel.purchaseSkin("red"); // Prova a riacquistare
        assertEquals(coinsBefore, shopModel.getCoins());
    }

    @Test
    void testDefaultSkinAlwaysUnlocked() {
        final Skin defaultSkin = shopModel.getSkinById("Default");
        assertTrue(defaultSkin.isUnlocked());
    }
}
