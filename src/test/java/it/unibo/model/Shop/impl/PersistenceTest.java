package it.unibo.model.Shop.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersistenceTest {
    private ShopModelImpl shopModel;

    @BeforeEach
    void setUp() {
        shopModel = new ShopModelImpl();
        shopModel.addCoins(1000);
    }

    @Test
    void testMaxScoreIsUpdatedAndSaved() {
        int oldMax = shopModel.getMaxScore();
        int newScore = oldMax + 10;
        shopModel.updateMaxScore(newScore);
        assertEquals(newScore, shopModel.getMaxScore());
    }

    @Test
    void testMaxScoreNotUpdatedIfLower() {
        int oldMax = shopModel.getMaxScore();
        shopModel.updateMaxScore(oldMax - 1);
        assertEquals(oldMax, shopModel.getMaxScore());
    }

    @Test
    void testAddCoinsPersistsValue() {
        int before = shopModel.getCoins();
        shopModel.addCoins(123);
        ShopModelImpl reloaded = new ShopModelImpl();
        assertTrue(reloaded.getCoins() >= before + 123);
    }
}
